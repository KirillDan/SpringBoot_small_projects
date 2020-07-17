DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id int(11) NOT NULL,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    PRIMARY KEY (id)
);