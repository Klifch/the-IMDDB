#  Internet Movie and Director database
### _by Oleksii Demydenko ACS201_


[![N|](https://img.shields.io/badge/project-gitlab-orange)](https://gitlab.com/kdg-ti/programming-5/projects-23-24/acs201/oleksii.demydenko/programming-5)

> I've been working on an exciting project called the IMDB Project, built using Spring Boot for the backend and Bootstrap for the frontend.
> The primary goal of this project is to create a platform to store and manage information about directors, movies, and studios.
> While the current version focuses mainly on directors, similar functionality will soon be extended to movies.

***Information:***

- Project created by Oleksii Demydenko
- Student of group ACS201
- Email: oleksii.demydenko@student.kdg.be
- Student ID: 0159614-49
- Main Domain Entities: Director 1 - * Movie * - 1 Studio
- User is domain entity that is connected to the Director as creator of the record

## Tech
***Imbd project main technologies:***
- [Spring](https://spring.io/) - main framework!
- [AJAX](https://developer.mozilla.org/en-US/docs/Glossary/AJAX) - responsive ui
- [Bootstrap](https://getbootstrap.com/) - framewaork for modern ui.
- [Bootswatch](https://bootswatch.com/) - free themes for Bootstrap
- [NPM](https://www.npmjs.com/) - package manager for JS customization

## Installation

Run project on Java 17+ version

First step after pulling the project is to start docker containers:

```sh
cd /[root project folder]
docker-compose up -d
```

Install all dependencies:

```sh
npm install
npm run build
```

## Tests

All tests use profile "test"

> Test database fills from ***separate*** data file
> _Location: /src/test/resources/sql/data-test.sql_

If you want to run tests you can use following command:
```sh
./gradlew test
```

Tests configured to show results in console. You can find configuration inside _build.gradle.kts_.
For additional html with detailed results as below you can go to _/build/reports/tests/test/index.html_

***Test results:***

> To get a test report just click [here](https://gitlab.com/kdg-ti/programming-5/projects-23-24/acs201/oleksii.demydenko/programming-5/-/jobs/6952090293)

[![image.png](https://i.postimg.cc/CxxQ5hTH/image.png)](https://postimg.cc/9rspNjfr)

Enjoy!

## Eslint

***If you want to check eslint rules and configuration just click [here](https://gitlab.com/kdg-ti/programming-5/projects-23-24/acs201/oleksii.demydenko/programming-5/-/blob/main/eslint.config.js?ref_type=heads)***

In order to run linter for checking errors use this command:
```sh
npm run lint
```

## Users
Check all the users here:
| User | Password | Role |
| ------ | ------ | ------ |
| oleksii | 12345 | REGULAR |
| sam | qwe | EDITOR |
| jo3 | 12qw | ADMIN |


## Some examples of access scope

+ [/directors/show](http://localhost:8080/directors/show) - can be accessed by all visitors (less information)
+ [/movies/show](http://localhost:8080/movies/show) - can be accessed by all visitors (less information)
+ [/directors/1](http://localhost:8080/directors/1) - can only be accessed authenticated users
+ [/movies/search](http://localhost:8080/movies/search) - can only be accessed by authenticated users
+ [/directors/add](http://localhost:8080/directors/add) - can only be accessed by users with authority Editor
+ [/directors/update/1](http://localhost:8080/directors/update/1) - can only be accesssed by user who created it (must still have Editor autority) or Admins
+ [/directors/import](http://localhost:8080/directors/import) - can only be accessed by users with Admin authority

## Authorities

_Role Regular_: Regular users can get detailed information about Movies, Directors and Studios, has access to action "More", that gives detailed info about each record.

_Role Editor_:  Editors are trusted members of our community who can add records and modify their own records. Role logic simmilar to editors on wikipedia - they can create records, but unlike wiki editors users with this role can only modify records they created. Following wiki example editor can't delete records, even their own.

_Role Admin_: Admins can modify any record, add record and delete it.

> ***Important***
> Even if user was demoted to regular from Editor - he won't be able to update records that he created in the past.
> Example: user "oleksii" was demoted to Regular user from Editor for violation of rules, now he can't modify his records! (he was nasty)

## MVC Endpoints
---
### _DirectorController_

***Overview: This Spring Controller manages requests related to Director entities. The controller includes endpoints to display, add, update, and import directors. The endpoints utilize DirectorService for business logic and ModelMapper for mapping entities to view models.***

***1. Display All Directors***

```sh
URL: /directors/show
```
_Method:_ GET

_Description:_ Show the page with a list of all directors.

_Parameters:_

1) model (Spring Model): Holds model attributes for the view.
2) userDetails (CustomUserDetails): Holds the details of the authenticated user.
3) Returns: The view directors/show-directors with a list of directors.

***2. Display Single Director***

```sh
URL: /directors/{id}
```

_Method:_ GET

_Description:_ Displays details of a single director.

_Parameters:_

1) id (Integer): The ID of the director.
2) model (Spring Model): Holds model attributes for the view.
3) Security: Requires the user to be authenticated.

_Returns:_ The view /directors/single-director with the director's details and connections.

_Throws:_ ResponseStatusException with HttpStatus.NOT_FOUND if the director is not found.

_Field of view:_ Some fields are hidden for unauthenticated users.
    Anon: Can see only basic inforamiton.
    Regular: Can check details.
    Editor: Can update Directors that he created.
    Admin: Can update and delete any Director Record.
    
_Examples:_
```sh
1) URL: /directors/1 -> 302 (No auth)
2) URL: /directors/1 -> OK (Auth)
3) URL: /directors/9999 -> NOT FOUND (Auth)
```

***3. Show Form to Add Director***
```sh
URL: /directors/add
```

_Method:_ GET

_Description:_ Displays a form to add a new director.

_Security:_ Requires the user to have the role ROLE_EDITOR or ROLE_ADMIN.

_Returns:_ The view /directors/addDirector.

_Examples:_
```sh
1) URL: /directors/add -> 302 (No auth)
2) URL: /directors/add -> FORBIDDEN (Auth & No authority)
```

***4. Show Form to Update Director***
```sh
URL: /directors/update/{id}
```

_Method:_ GET

_Description:_ Displays a form to update an existing director.

_Parameters:_

1) id (Integer): The ID of the director to be updated.
2) model (Spring Model): Holds model attributes for the view.

_Security:_ Requires the user to have the role ROLE EDITOR and the ability to modify the director (checked by directorServiceImpl.canUserModify). Alternatively, requires the user to have the role ROLE ADMIN.

_Returns:_ The view /directors/updateDirector.

_Throws:_ ResponseStatusException with HttpStatus.NOT_FOUND if the director is not found.

_Examples:_
```sh
1) URL: /directors/update/1 -> FORBIDDEN (No authority)
2) URL: /directors/update/1 -> 302 (No auth)
3) URL: /directors/update/9999 -> NOT_FOUND (Auth & Authority)
```

***5. Show Form to Import Directors***
```sh
URL: /directors/import
```

_Method:_ GET

_Description:_ Displays a form to import directors from a CSV file.

_Security:_ Requires the user to have the role ROLE_ADMIN.

Returns: The view directors/importDirectors.

_Examples:_
```sh
1) URL: /directors/import -> FORBIDDEN (No authority)
2) URL: /directors/import -> 302 (No auth)
```

***6. Import Directors from CSV***
```sh
URL: /directors/import
```

_Method:_ POST

_Description:_ Handles the import of directors from a CSV file.

_Parameters:_

1) file (MultipartFile): The CSV file containing director data.
2) user (UserDetails): The details of the authenticated user.
3) model (Spring Model): Holds model attributes for the view.

_Security:_ Requires the user to have the role ROLE_ADMIN.

_Returns:_ The view directors/importDirectors.

_Throws:_ IOException if an error occurs while reading the file.

_Behavior:_

1) If the file is a CSV, the import process starts and attributes wrongFile and inProgress are set accordingly.
2) If the file is not a CSV, the attribute wrongFile is set to true.


### _MovieController_

***1. Show Movies***

```sh
URL: /movies/show
```

_Method:_ GET

_Description:_ Displays a list of all movies.

_Parameters:_

1) model (Spring Model): Holds model attributes for the view.
2) Returns: The view /movies/show-movies.

***2. Show One Movie***
```sh
URL: /movies/{id}
```

_Method:_ GET

_Description:_ Displays details of a single movie.

_Parameters:_

1) id (Integer): The ID of the movie to be displayed.
2) model (Spring Model): Holds model attributes for the view.

_Security:_ Requires the user to be authenticated (checked by @PreAuthorize("isAuthenticated()")).

_Returns:_ The view /movies/single-movie.

_Throws:_ ResponseStatusException with HttpStatus.NOT_FOUND if the movie is not found.

_Examples:_
```sh
1) URL: /movies/1 -> 302 (No auth)
2) URL: /movies/1 -> OK (Auth)
3) URL: /movies/9999 -> NOT FOUND (Auth)
```

***3. Search Movies***
```sh
URL: /movies/search
```

_Method:_ GET

_Description:_ Displays a form to search for movies.

_Returns:_ The view /movies/searchMovies.

_Examples:_
```sh
1) URL: /movies/search -> 302 (No auth)
```

### _StudioController_

***1. Show Studios***
```sh
URL: /studios/show
```

_Method:_ GET

_Description:_ Displays a list of all studios.

_Parameters:_

1) model (Spring Model): Holds model attributes for the view.
2) Returns: The view /studios/show-studios.

***2. Show One Studio***
```sh
URL: /studios/{id}
```

_Method:_ GET

_Description:_ Displays details of a single studio.

_Parameters:_

1) id (Integer): The ID of the studio to be displayed.
2) model (Spring Model): Holds model attributes for the view.

_Security:_ Requires the user to be authenticated (checked by @PreAuthorize("isAuthenticated()")).

_Returns:_ The view /studios/single-studio.

_Throws:_ ResponseStatusException with HttpStatus.NOT_FOUND if the studio is not found.

_Examples:_
```sh
1) URL: /studios/1 -> 302 (No auth)
3) URL: /studios/9999 -> NOT FOUND (Auth)
```

## API Endpoints
---
> This part describes the API endpoints available for managing directors and movies in the application.

## _DirectorApiController_

***1. Get Director's Movies***

_Endpoint:_
```sh
GET /api/directors/{id}/movies
```

_Method:_ GET

_Description:_ Retrieves a list of movies directed by a specific director.

_Parameters:_
1) id (Integer): The ID of the director. (Path variable)

_Security:_ Should be authorized.

_Returns:_ A ResponseEntity containing a list of MovieDto objects if successful, or a 404 Not Found status code if the director is not found.

_Example:_

```sh
1) GET /api/directors/1/movies -> 200 OK (Auth)
  {
    "movies": [
      // List of MovieDto objects...
    ]
  }

2) GET /api/directors/999/movies -> 404 NOT FOUND (Auth)

2) GET /api/directors/1/movies -> 403 FORBIDDEN (No auth)
```


***2. Add a Director***

_Endpoint:_
```sh
POST /api/directors
```

_Method:_ POST

_Description:_ Creates a new director in the system.

_Parameters:_
1) AddDirectorDto (Object in request body): Contains details about the new director.

_Security:_ Requires the user to have the role "ROLE EDITOR" or "ROLE ADMIN".

_Returns:_ A ResponseEntity with a status code of 201 Created and a DirectorDto object representing the newly created director, or a 409 Conflict status code if a director with the same details already exists.

_Example:_
```sh
POST /api/directors
  method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeader]: csrfToken
        }
1) (Auth & Authorized)
{
  "firstName": "Quentin",
  "lastName": "Tarantino",
  "dateOfBirth": "1963-03-27",
  "nationality": "American",
  "height": 185
}

-> 201 Created
  {
    "id": 1,
    "firstName": "Quentin",
    "lastName": "Tarantino",
    "dateOfBirth": "1963-03-27",
    "nationality": "American",
    "height": 185,
    "createdBy": "..." (User who sent the request)
  }

2) (Auth & Authorized)
{
  "firstName": "",
  "lastName": "Tarantino",
  "dateOfBirth": "qwe",
  "nationality": "American",
  "height": 185
}
-> 400 Bad Request (Auth & Authority)

3) (No auth or No authorization)
-> 403 FORBIDDEN
```


***3. Update a Director (Partial Update)***

_Endpoint:_
```sh
PATCH /api/directors/{id}
```

_Method:_ PATCH

_Description:_ Updates specific fields of an existing director.

_Parameters:_

1) id (Integer): The ID of the director to update. (Path variable)
2) PatchDirectorDto (Object in request body): Contains the fields and new values to be updated.

_Security:_ Requires the user to have the role "ROLE EDITOR" with permission to modify the director (checked by directorServiceImpl.canUserModify) or "ROLE ADMIN".

_Returns:_ A ResponseEntity with a status code of 200 OK and a DirectorDto object representing the updated director, or a 404 Not Found status code if the director is not found.

_Example:_
```sh
PATCH /api/directors/1
  method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeader]: csrfToken
        }

{
  "firstName": "Quentin Jerome",
  "dateOfBirth": "1963-03-27"
}

-> 200 OK
  {
    "id": 1,
    "firstName": "Jerome",
    "lastName": "Tarantino",
    "dateOfBirth": "1963-03-27",
    "nationality": "American",
    "height": 185,
    "createdBy": "sam" 
  }
 
2) (No auth or No authority)
-> 403 FORBIDDEN
```


***4. Delete a Director***

_Endpoint:_
```sh
DELETE /api/directors/{id}
```

_Method:_ DELETE

_Description:_ Deletes a director from the system.

_Parameters:_
1) id (Integer): The ID of the director to delete. (Path variable)

_Security:_ Requires the user to have the role "ROLE ADMIN".

_Returns:_ An empty ResponseEntity with a status code of 204 No Content if successful, or a 404 Not Found status code if the director is not found.

_Example:_
```sh
1) DELETE /api/directors/1

-> 204 No Content (Auth & Admin authority)

2) DELETE /api/directors/1

-> 403 Forbidden (No auth or/and admin authority)
```

## _MovieApiController_

***1. Search Movies***

_Endpoint:_
```sh
GET /api/movies
```

_Method:_ GET

_Description:_ Searches for movies based on a provided search term.

_Parameters:_
1) searchTerm (String): The term to use for searching movies. (Query parameter)

_Security:_ User should be authenticated

_Returns:_ A ResponseEntity containing a list of MovieDto objects if successful, or an empty list if no movies are found matching the search term.

_Example:_
```sh
1) (Authentificated)
GET /api/movies?searchTerm=sci-fi
-> 200 OK
  [
    {
      "id": 1,
      "title": "Star Wars: Episode IV - A New Hope"
    },
    // ... other matching movies
  ]

GET /api/movies?searchTerm=The
-> 200 OK
  [
    {
        "id": 1,
        "name": "The Dark Knight"
    },
    {
        "id": 11,
        "name": "The Shape of Water"
    }
  ]
  
2) (No auth)
GET /api/movies?searchTerm=The
-> 403 FORBIDDEN
```

## UI Stuff


+ ***JOI validation and Moment***
You can find js script that uses Joi for validating input fields and moment for validating date field by clicking [here](https://gitlab.com/kdg-ti/programming-5/projects-23-24/acs201/oleksii.demydenko/programming-5/-/blob/main/src/main/js/addDirector.js?ref_type=heads) or [here](https://gitlab.com/kdg-ti/programming-5/projects-23-24/acs201/oleksii.demydenko/programming-5/-/blob/main/src/main/js/updateDirector.js?ref_type=heads)

+ ***Bootstrap icons***
Html fragment that uses [envelope heart](https://icons.getbootstrap.com/icons/envelope-heart/) near users email and [person icon](https://icons.getbootstrap.com/icons/person/) near the username field is [here](https://gitlab.com/kdg-ti/programming-5/projects-23-24/acs201/oleksii.demydenko/programming-5/-/blob/main/src/main/resources/templates/fragments/nav-bar.html?ref_type=heads)
Alerts at [this](https://gitlab.com/kdg-ti/programming-5/projects-23-24/acs201/oleksii.demydenko/programming-5/-/blob/main/src/main/resources/templates/directors/importDirectors.html?ref_type=heads) page use [file type csv](https://icons.getbootstrap.com/icons/filetype-csv/) and [hourglas](https://icons.getbootstrap.com/icons/hourglass-split/) icons.




























