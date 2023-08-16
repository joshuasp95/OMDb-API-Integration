DROP TABLE IF EXISTS movie;

CREATE TABLE movie(
    imdb_id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    "year" VARCHAR(4) NOT NULL,
    image VARCHAR(255),
    personal_rating INT DEFAULT 0
);

INSERT INTO movie (imdb_id, title, "year", image)
VALUES ('tt1201607', 'Harry Potter and the Deathly Hallows: Part 2', '2011', 'https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg'),
       ('tt0241527', 'Harry Potter and the Sorcerer''s Stone', '2001', 'https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg'),
       ('tt0295297', 'Harry Potter and the Chamber of Secrets', '2002', 'https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg'),
       ('tt0304141', 'Harry Potter and the Prisoner of Azkaban', '2004', 'https://m.media-amazon.com/images/M/MV5BMTY4NTIwODg0N15BMl5BanBnXkFtZTcwOTc0MjEzMw@@._V1_SX300.jpg');