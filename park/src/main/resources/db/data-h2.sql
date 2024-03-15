DELETE FROM user;
DELETE FROM book;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@163.com'),
(2, 'Jack', 21, 'test2@163.com'),
(3, 'Tom', 28, 'test3@gmail.com'),
(4, 'Sandy', 21, 'test4@gmail.com'),
(5, 'Billie', 24, 'billie5@gmail.com'),
(6, 'Kelly', 45, 'kelly5@gmail.com'),
(7, 'Nothing', 32, 'nothing5@gmail.com'),
(8, 'Sam', 37, 'sam5@gmail.com'),
(9, 'Ted', 56, 'ted5@gmail.com'),
(10, 'Bomb', 23, 'bomb5@gmail.com');

INSERT INTO book (id, name, author, description) VALUES
(1, 'XiYouji', 'wuchengen', 'test1'),
(2, 'SanGuoZhi', 'luoguanzhong', 'test2'),
(3, 'HongLouMeng', 'CaoXueQin', 'test3'),
(4, 'ShuiHuZhuan', 'ShiNaiAn', 'test4'),
(5, 'Billie', '24', 'test5');