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
            if (!element.classList.contains("green-border")) {
                element.classList.add("green-border");
            }
        } else {
            element.classList.remove("green-border");
        }
    });
}

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

    const theFirstName = document.querySelector('#firstname').value;
    const theLastName = document.querySelector('#lastname').value;
    const theDateOfBirth = document.querySelector('#dob').value;
    const theNationality = document.querySelector('#nationality').value;
    const theHeight = document.querySelector('#height').value;

    const requestBody = {
    };

    elements.forEach((item) => {
        if (item.classList.contains("green-border")) {
            addElementToJson(item, requestBody);
        }
    });

    console.log(requestBody);

})