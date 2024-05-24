import {csrfToken, csrfHeader} from "./util/csrf.js";

const deleteButtons = document
    .querySelectorAll('.delete-director-button');

deleteButtons.forEach(deleteButton => deleteButton.addEventListener('click', deleteButtonClicked));

async function deleteButtonClicked(e) {
    const directorId = e.target.getAttribute('data-director-id');
    const response = await fetch(
        `/api/directors/${directorId}`,
        {
            method:'DELETE',
            headers: {
                [csrfHeader]: csrfToken
            }
        }
    );
    if (response.status === 204) {
        document.querySelector(`#director-${directorId}`).remove();
    }
}