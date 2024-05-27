import {csrfToken, csrfHeader} from "./util/csrf.js";
import "../scss/update_director.scss";
import moment from "moment";
import Joi from "joi";

const form = document.querySelector('#update-director-form');
const elements= document.querySelectorAll('.form-control');
const submitButton = document.getElementById("update-button");

const initialValues = Array.from(elements).map(element => element.value);

const hasValuesChanged = () => {
    return Array.from(elements).some((element, index) => element.value !== initialValues[index]);
};

const markChangedFields = () => {
    elements.forEach((element, index) => {
        if (element.value !== initialValues[index]) {
            if (!element.classList.contains("text-success")) {
                element.classList.add("text-success");
            }
        } else {
            element.classList.remove("text-success");
        }
    });
}

const updateInitialValues = (responseData) => {
    initialValues[0] = responseData.firstName;
    initialValues[1] = responseData.lastName;
    initialValues[2] = responseData.dateOfBirth;
    initialValues[3] = responseData.nationality;
    initialValues[4] = responseData.height.toString();
};

const updateFieldsWithResponse = (responseData) => {
    document.querySelector('#firstname').value = responseData.firstName;
    document.querySelector('#lastname').value = responseData.lastName;
    document.querySelector('#dob').value = responseData.dateOfBirth;
    document.querySelector('#nationality').value = responseData.nationality;
    document.querySelector('#height').value = responseData.height.toString();
};

elements.forEach((item) => {

    item.addEventListener('change', async (event) => {
        item.value = item.value.trim();

        if (hasValuesChanged()) {
            submitButton.classList.remove("disabled");
            submitButton.disabled = false;
        } else {
            if (!(submitButton.classList.contains("disabled"))) {
                submitButton.classList.add("disabled");
                submitButton.disabled = true;
            }
        }
        markChangedFields();

    });
});

const addElementToJson = (element, requestBody) => {

    const value = element.value;

    switch(element.id) {
        case "firstname":
            requestBody.firstName = value;
            break;
        case "lastname":
            requestBody.lastName = value;
            break;
        case "dob":
            requestBody.dateOfBirth = value;
            break;
        case "nationality":
            requestBody.nationality = value;
            break;
        case "height":
            requestBody.height = value;
    }
}

form.addEventListener('submit', async (event) => {

    event.preventDefault();

    const requestBody = {
    };

    elements.forEach((item) => {
        if (item.classList.contains("text-success")) {
            addElementToJson(item, requestBody);
        }
    });

    if (requestBody.dateOfBirth) {
        const dateFormat = 'YYYY-MM-DD';
        if (!moment(requestBody.dateOfBirth, dateFormat, true).isValid()) {
            alert('Invalid date format. Please use YYYY-MM-DD.');
            return;
        }
    }

    const schema = Joi.object({
        firstName: Joi.string().min(3).max(10),
        lastName: Joi.string().min(3).max(15),
        nationality: Joi.string().min(2).max(10),
        height: Joi.number().min(100).max(250)
    });

    const { error } = schema.validate({
        firstName: requestBody.firstName,
        lastName: requestBody.lastName,
        nationality: requestBody.nationality,
        height: requestBody.height
    });

    if (error) {
        alert(error.details[0].message);
        return;
    }

    const directorId = event.target.getAttribute('director-id');

    const response = await fetch(`/api/directors/${directorId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(requestBody)
    });

    if (response.status === 200) {
        const director = await response.json();

        updateInitialValues(director);

        updateFieldsWithResponse(director);

        markChangedFields();

        alert("Director updated!");

        submitButton.classList.add("disabled");
        submitButton.disabled = true;
    }

})