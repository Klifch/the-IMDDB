const form = document.querySelector('#add-director-form');

form.addEventListener('submit', async (event) => {

    event.preventDefault();

    const theFirstName = document.querySelector('#firstname').value;
    const theLastName = document.querySelector('#lastname').value;
    const theDateOfBirth = document.querySelector('#dob').value;
    const theNationality = document.querySelector('#nationality').value;
    const theHeight = document.querySelector('#height').value;

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
            'Accept': 'application/json'
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