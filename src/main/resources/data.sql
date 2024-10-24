insert into
    movie (id, name, release_year)
values
    (1, 'Movie 1', 1972),
    (2, 'Movie 2', 1942),
    (3, 'Movie 3', 1994),
    (4, 'Movie 4', 1939),
    (5, 'Movie 5', 1960);
    
insert into
    serie (id, name, release_year)
values
    (1, 'Serie 1', 1982),
    (2, 'Serie 2', 1952),
    (3, 'Serie 3', 2004),
    (4, 'Serie 4', 2019),
    (5, 'Serie 5', 2000);

insert into
    spaceship (id, name, release_year)
values
    (default, 'Spaceship 1', 1982),
    (default, 'Spaceship 2', 1952),
    (default, 'Spaceship 3', 2004),
    (default, 'Spaceship 4', 2019),
    (default, 'Spaceship 5', 2000),
    (default, 'Spaceship 6', 1982),
    (default, 'Spaceship 7', 1952),
    (default, 'Spaceship 8', 2004),
    (default, 'Spaceship 9', 2019),
    (default, 'Spaceship 10', 2000),
    (default, 'Spaceship 11', 1982),
    (default, 'Spaceship 12', 1952),
    (default, 'Spaceship 13', 2004),
    (default, 'Spaceship 14', 2019),
    (default, 'Spaceship 15', 2000);
    
insert into
    serie_spaceship (spaceship_id, serie_id)
values
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 3),
    (4, 3),
    (4, 1),
    (4, 2),
    (5, 4),
    (6, 4),
    (7, 4),
    (7, 3),
    (8, 5),
    (9, 5),
    (9, 2),
    (9, 1);
    
insert into
    users (id, username, password)
values
	(default, 'user', '$2a$10$6uhdn0vpc1zNhQExEZxz9uLKy/Zo3W3HX/j9WJzbeg13ugAYZQORm'),
	(default, 'admin', '$2a$10$NY.8KvZrSKz8MowzwQuuSeyAW5bqMT5Z.ga9DA50ABJ54rJBKVXTm');
	
insert into
    roles (id, name)
values
	(default, 'USER'),
	(default, 'ADMIN');
	
insert into
    users_roles (user_id, role_id)
values
	(1, 1),
	(2, 1),
	(2, 2);
    