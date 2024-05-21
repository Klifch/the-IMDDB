window.addEventListener('load', function () {
    const updateButtons = document
        .querySelectorAll('.update-director-button');

    updateButtons.forEach(updateButton => {
        const directorId = updateButton.getAttribute(
            'data-director-id'
        );

        fetch(`/api/directors/checkPermission/${directorId}`, {method: 'GET'})
            .then(response => {
                if (response.ok) {
                    updateButton.href = `/directors/update/${directorId}`;
                } else if (response.status === 403) {
                    updateButton.style.display = 'none';
                }
            });
    });
});