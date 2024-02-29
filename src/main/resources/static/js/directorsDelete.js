window.addEventListener('load', function () {
    const deleteButtons = document
        .querySelectorAll('.delete-director-button');

    deleteButtons.forEach(deleteButton => {
        const directorId = deleteButton.getAttribute(
            'data-director-id'
        );

        deleteButton.addEventListener('click', function () {
            fetch(`/api/directors/${directorId}`, {method:'DELETE'})
                .then(response => {
                    if (response.status === 204) {
                        document.querySelector(`#director-${directorId}`)
                            .remove();
                    }
                });
        });
    });
});