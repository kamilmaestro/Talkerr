ALTER TABLE posts
    ADD author_login varchar(20) NOT NULL DEFAULT('');

UPDATE posts
SET author_login = username
FROM posts p, users u
WHERE u.user_id = p.author_id;
