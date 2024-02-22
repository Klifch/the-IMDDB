INSERT INTO directors (first_name, last_name, dob) VALUES
                                                       ('John', 'Doe', '1990-01-01'),
                                                       ('Jane', 'Smith', '1985-05-15');

INSERT INTO studios (name, office_location) VALUES
                                                ('Studio A', 'Location A'),
                                                ('Studio B', 'Location B');

INSERT INTO movies (movie_name, movie_quote, release, status, director_id, studio_id) VALUES
                                                                                               ('Movie 1', 'Quote 1', '2022-01-15', 'RELEASED', 1, 1),
                                                                                               ('Movie 2', 'Quote 2', '2023-02-20', 'PRODUCTION', 2, 2);