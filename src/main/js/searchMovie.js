window.addEventListener('load', function () {
   const searchField = document.querySelector('#search');
   searchField.addEventListener('keyup', searchTextChanged);
});

async function searchTextChanged(e) {
    const searchResults = document.querySelector('#search-results');

    const searchTerm = e.target.value;

    const response = await fetch(`/api/movies?searchTerm=${searchTerm}`);
    const movies = await response.json();

    let html = `<p>Found ${movies.length} results</p>`
    html += '<ul>';
    movies.forEach(movie => {
       html += `<li><a href="/movies/${movie.id}">${movie.name}</a></li>`;
    });
    html += '</ul>';

    searchResults.innerHTML = html;
}