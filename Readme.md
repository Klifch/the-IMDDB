#  _Internet Movie and Director database_
### _by Oleksii Demydenko ACS201_


[![N|](https://img.shields.io/badge/project-gitlab-orange)](https://gitlab.com/kdg-ti/programming-5/projects-23-24/acs201/oleksii.demydenko/programming-5)

Inforamtion:

- Project created by Oleksii Demydenko
- Student of group ACS201
- Email: oleksii.demydenko@student.kdg.be
- Student ID: 0159614-49
- Main Domain Entities: Director 1 - * Movie * - 1 Studio
- User is domain entity that is connected to the Director as creator of the record

## Tech
Imbd project main technologies:
- [Spring](https://spring.io/) - main framework!
- [AJAX](https://developer.mozilla.org/en-US/docs/Glossary/AJAX) - responsive ui
- [Bootstrap](https://getbootstrap.com/) - framewaork for modern ui.
- [Bootswatch](https://bootswatch.com/) - free themes for Bootstrap
- [NPM](https://www.npmjs.com/) - package manager for JS customization

## Installation

Run project on Java 17+ version

First step after pulling the project is to start docker containers:

```sh
cd ~
docker-compose up -d
```

Install all dependencies:

```sh
npm install
npm run build
```

Enjoy!

## Users
Check all the users here:
| User | Password | Role |
| ------ | ------ | ------ |
| oleksii | 12345 | Regular User |
| sam | qwe | Editor |
| jo3 | 12qw | ADMIN |


## Authorities

_Role Regular_: regular users can get information about Movies, Directors and Studios
_Role Editor_: editors are trusted members of community who can add records and modify their own records
_Role Admin_: admins can modify any record, add record and delete it

It's important that only admins can delete records. Editors only can edit records that they added.
Even if user was demoted to regular From Editor - he won't be able to update records that he created in the past.

## Endpoints

Overview
This Spring Controller manages requests related to Director entities. The controller includes endpoints to display, add, update, and import directors. The endpoints utilize DirectorService for business logic and ModelMapper for mapping entities to view models.

Endpoints
1. Display All Directors
    URL: /directors/show

Method: GET

Description: Displays a list of all directors.

Parameters:

model (Spring Model): Holds model attributes for the view.
userDetails (CustomUserDetails): Holds the details of the authenticated user.
Returns: The view directors/show-directors with a list of directors.

2. Display Single Director
URL: /directors/{id}

Method: GET

Description: Displays details of a single director.

Parameters:

id (Integer): The ID of the director.
model (Spring Model): Holds model attributes for the view.
Security: Requires the user to be authenticated.

Returns: The view /directors/single-director with the director's details.

Throws:

ResponseStatusException with HttpStatus.NOT_FOUND if the director is not found.
3. Show Form to Add Director
URL: /directors/add

Method: GET

Description: Displays a form to add a new director.

Security: Requires the user to have the role ROLE_EDITOR or ROLE_ADMIN.

Returns: The view /directors/addDirector.

4. Show Form to Update Director
URL: /directors/update/{id}

Method: GET

Description: Displays a form to update an existing director.

Parameters:

id (Integer): The ID of the director to be updated.
model (Spring Model): Holds model attributes for the view.
Security:

Requires the user to have the role ROLE_EDITOR and the ability to modify the director (checked by directorServiceImpl.canUserModify).
Alternatively, requires the user to have the role ROLE_ADMIN.
Returns: The view /directors/updateDirector.

Throws:

ResponseStatusException with HttpStatus.NOT_FOUND if the director is not found.
5. Show Form to Import Directors
URL: /directors/import

Method: GET

Description: Displays a form to import directors from a CSV file.

Security: Requires the user to have the role ROLE_ADMIN.

Returns: The view directors/importDirectors.

6. Import Directors from CSV
URL: /directors/import

Method: POST

Description: Handles the import of directors from a CSV file.

Parameters:

file (MultipartFile): The CSV file containing director data.
user (UserDetails): The details of the authenticated user.
model (Spring Model): Holds model attributes for the view.
Security: Requires the user to have the role ROLE_ADMIN.

Returns: The view directors/importDirectors.

Throws:

IOException if an error occurs while reading the file.
Behavior:

If the file is a CSV, the import process starts and attributes wrongFile and inProgress are set accordingly.
If the file is not a CSV, the attribute wrongFile is set to true.

