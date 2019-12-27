ALTER TABLE comments
    ADD author_login varchar(20) NOT NULL DEFAULT('');

UPDATE comments
SET author_login = username
FROM comments c, users u
WHERE u.user_id = c.author_id;
