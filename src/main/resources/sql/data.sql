INSERT INTO directors (first_name, last_name, dob) VALUES
                                                       ('John', 'Doe', '1990-01-01'),
                                                       ('Jane', 'Smith', '1985-05-15');

INSERT INTO studios (name, office_location, total_employees) VALUES
                                                ('Studio A', 'Location A', 265),
                                                ('Studio B', 'Location B', 1972);

INSERT INTO movies (movie_name, movie_quote, release, status, budget, box_office, rating, description, director_id, studio_id) VALUES
                                                                                               ('Movie 1', 'Quote 1', '2022-01-15', 'RELEASED', 10000, 200000, 7.8, 'description 1', 1, 1),
                                                                                               ('Movie 2', 'Quote 2', '2023-02-20', 'PRODUCTION', 324234, 4329584, 9.2, 'description 2', 2, 2);