import {csrfToken, csrfHeader} from "./util/csrf.js";
import moment from "moment";
import Joi from "joi";

const form = document.querySelector('#add-director-form');

form.addEventListener('submit', async (event) => {

    event.preventDefault();

    const theFirstName = document.querySelector('#firstname').value;
    const theLastName = document.querySelector('#lastname').value;
    const theDateOfBirth = document.querySelector('#dob').value;
    const theNationality = document.querySelector('#nationality').value;
    const theHeight = document.querySelector('#height').value;

    const schema = Joi.object({
        firstName: Joi.string().min(3).max(15).required().messages({
            'string.empty': 'First Name is required',
            'string.min': 'First Name must be at least 3 character',
            'string.max': 'First Name must be less than 15 characters'
        }),
        lastName: Joi.string().min(3).max(15).required().messages({
            'string.empty': 'Last Name is required',
            'string.min': 'Last Name must be at least 3 character',
            'string.max': 'Last Name must be less than 15 characters'
        }),
        nationality: Joi.string().optional().allow(''),
        height: Joi.number().min(100).max(250).optional().allow('').messages({
            'number.base': 'Height must be a number',
            'number.min': 'Height must be at least 100',
            'number.max': 'height must be less than 250'
        })
    });

    const validationResult = schema.validate({
        firstName: theFirstName,
        lastName: theLastName,
        nationality: theNationality,
        height: theHeight
    }, { abortEarly: false });

    if (validationResult.error) {
        const validationErrors = validationResult.error.details.map(err => err.message).join('\n');
        alert(validationErrors);
        return;
    }

    const dateFormat = 'DD-MM-YYYY';
    if (!moment(theDateOfBirth, dateFormat, true).isValid()) {
        alert('Invalid date format. Please use DD-MM-YYYY.');
        return;
    }

    const requestBody = {
        firstName: theFirstName,
        lastName: theLastName,
        dateOfBirth: theDateOfBirth,
        nationality: theNationality,
        height: theHeight
    };

    const response = await fetch('/api/directors', {
        method: 'POST',
        body: JSON.stringify(requestBody),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeader]: csrfToken
        }
    });

    if (response.status === 201) {
        const director = await response.json();
        alert(`New director added! ID: ${director.id}`);
        window.location.href = `/directors/${director.id}`;
    }

    if (response.status === 400) {
        const errorResponse = await response.json();
        const errors = errorResponse.errors;
        let validationErrors = ""
        errors.forEach(error => {
            validationErrors += `${error.field} ${error.defaultMessage}\n`;
        });
        alert(validationErrors);
    }

});