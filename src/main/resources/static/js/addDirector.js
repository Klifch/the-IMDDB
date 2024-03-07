const form = document.querySelector('#add-director-form');

form.addEventListener('submit', async (event) => {

    event.preventDefault();

    const theFirstName = document.querySelector('#firstname').value;
    const theLastName = document.querySelector('#lastname').value;
    const theDateOfBirth = document.querySelector('#dob').value;

    const requestBody = {
        firstName: theFirstName,
        lastName: theLastName,
        dateOfBirth: theDateOfBirth
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


});