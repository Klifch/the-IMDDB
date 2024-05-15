-- INSERT INTO directors (first_name, last_name, dob, height, nationality) VALUES
--                                                        ('John', 'Doe', '1990-01-01', 188, 'Belgium'),
--                                                        ('Jane', 'Smith', '1985-05-15', 175, 'USA');
--
-- INSERT INTO studios (name, office_location, total_employees) VALUES
--                                                 ('Studio A', 'Location A', 265),
--                                                 ('Studio B', 'Location B', 1972);
--
-- INSERT INTO movies (movie_name, movie_quote, release, status, budget, box_office, rating, description, director_id, studio_id) VALUES
--                                                                                                ('Movie 1', 'Quote 1', '2022-01-15', 'RELEASED', 10000, 200000, 7.8, 'description 1', 1, 1),
--                                                                                                ('Movie 2', 'Quote 2', '2023-02-20', 'PRODUCTION', 324234, 4329584, 9.2, 'description 2', 2, 2);
-- Users
INSERT INTO application_users (username, email, password, active)
VALUES
    ('oleksii', 'oleksii@gmail.com','$2a$10$7BcPi7iy5Pnx/keDO3Wi1.UlGoO3I0EqogNZGjPiegqY6M63pKz7q', true); --12345

INSERT INTO roles (rolename)
VALUES
    ('ROLE_REGULAR'),('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (1, 2);

-- Directors
INSERT INTO directors (first_name, last_name, dob, height, nationality) VALUES
                                                                            ('Christopher', 'Nolan', '1970-07-30', 180, 'UK'),
                                                                            ('Quentin', 'Tarantino', '1963-03-27', 188, 'USA'),
                                                                            ('Greta', 'Gerwig', '1983-08-04', 173, 'USA'),
                                                                            ('Bong', 'Joon-ho', '1969-09-14', 183, 'South Korea'),
                                                                            ('Ava', 'DuVernay', '1972-08-24', 165, 'USA'),
                                                                            ('Denis', 'Villeneuve', '1967-10-03', 188, 'Canada'),
                                                                            ('Sofia', 'Coppola', '1971-05-14', 173, 'USA'),
                                                                            ('Guillermo', 'del Toro', '1964-10-09', 178, 'Mexico'),
                                                                            ('Patty', 'Jenkins', '1971-07-24', 170, 'USA'),
                                                                            ('Martin', 'Scorsese', '1942-11-17', 163, 'USA');

-- Studios
INSERT INTO studios (name, office_location, total_employees) VALUES
                                                                 ('Warner Bros', 'Burbank, California', 8500),
                                                                 ('Disney', 'Burbank, California', 20000),
                                                                 ('Universal Pictures', 'Universal City, California', 7500),
                                                                 ('Sony Pictures', 'Culver City, California', 6000),
                                                                 ('Paramount Pictures', 'Hollywood, California', 3500);

-- Movies
INSERT INTO movies (movie_name, movie_quote, release, status, budget, box_office, rating, description, director_id, studio_id) VALUES
                                                                                                                                   ('The Dark Knight', 'Why so serious?', '2008-07-18', 'RELEASED', 185000000, 1004558444, 9.0, 'A criminal mastermind known as the Joker wreaks havoc on Gotham City.', 1, 1),
                                                                                                                                   ('Pulp Fiction', 'Say ''what'' again!', '1994-10-14', 'RELEASED', 8000000, 213928762, 8.9, 'Interwoven stories of Los Angeles mobsters, fringe players, small-time criminals, and a mysterious briefcase.', 2, 2),
                                                                                                                                   ('Lady Bird', 'Different things can be sad. It doesn’t all have to be about war.', '2017-11-03', 'RELEASED', 10000000, 79000000, 7.4, 'A high school senior and her turbulent relationship with her mother.', 3, 3),
                                                                                                                                   ('Parasite', 'Jessica, only child, Illinois, Chicago.', '2019-05-21', 'RELEASED', 11400000, 264250128, 8.6, 'A poor family schemes to become employed by a wealthy family.', 4, 4),
                                                                                                                                   ('Selma', 'Our lives are not fully lived if we''re not willing to die for those we love and for what we believe.', '2014-12-25', 'RELEASED', 20000000, 66486431, 7.5, 'A chronicle of Martin Luther King Jr.''s campaign to secure equal voting rights.', 5, 5),
                                                                                                                                   ('Blade Runner 2049', 'I always told you, you’re special. Your history isn’t over yet.', '2017-10-06', 'RELEASED', 185000000, 259239658, 8.0, 'A young blade runner''s discovery of a long-buried secret leads him to track down former blade runner Rick Deckard.', 6, 1),
                                                                                                                                   ('Lost in Translation', 'For relaxing times, make it Suntory time.', '2003-09-12', 'RELEASED', 4000000, 119723856, 7.7, 'A faded movie star and a neglected young woman form an unlikely bond after crossing paths in Tokyo.', 7, 2),
                                                                                                                                   ('Pan''s Labyrinth', 'Ofelia, don''t ever be afraid. You are a princess.', '2006-05-27', 'RELEASED', 19000000, 83629776, 8.2, 'In the fascist Spain of 1944, the bookish young stepdaughter of a sadistic army officer escapes into an eerie but captivating fantasy world.', 8, 3),
                                                                                                                                   ('Wonder Woman', 'It''s not about what you deserve. It''s about what you believe.', '2017-05-15', 'RELEASED', 149000000, 821847012, 7.4, 'When a pilot crashes and tells of conflict in the outside world, Diana, an Amazonian warrior in training, leaves home to fight a war, discovering her full powers and true destiny.', 9, 4),
                                                                                                                                   ('Goodfellas', 'Never rat on your friends, and always keep your mouth shut.', '1990-09-12', 'RELEASED', 25000000, 47164414, 8.7, 'A young man grows up in the mob and works very hard to advance himself through the ranks.', 10, 5),
                                                                                                                                   ('The Shape of Water', 'Unable to perceive the shape of You, I find You all around me. Your presence fills my eyes with Your love. It humbles my heart, for You are everywhere.', '2017-12-01', 'RELEASED', 19500000, 195243464, 7.3, 'At a top-secret research facility in the 1960s, a lonely janitor forms a unique relationship with an amphibious creature that is being held in captivity.', 8, 1),
                                                                                                                                   ('Monster', 'I''m good at making friends.', '2003-12-24', 'RELEASED', 8000000, 60467088, 7.3, 'Based on the life of Aileen Wuornos, a Daytona Beach prostitute who became a serial killer.', 9, 2),
                                                                                                                                   ('Joker', 'I used to think that my life was a tragedy, but now I realize, it''s a comedy.', '2019-10-04', 'RELEASED', 55000000, 1074251311, 8.4, 'In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society.', 1, 3),
                                                                                                                                   ('La La Land', 'Here''s to the ones who dream.', '2016-12-09', 'RELEASED', 30000000, 446486224, 8.0, 'While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations for the future.', 3, 4);