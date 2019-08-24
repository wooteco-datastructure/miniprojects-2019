insert into user(id, email, nick_name, password, user_name) values(1, 'qwe@naver.com', 'qwe', 'qweqwe', 'qwe');

insert into file_info(id, file_name, file_path) values(1, '', '');
insert into article(id, contents, author, fileinfo_id) values(1, '#qwe#qqq#qww#qee', 1, 1);
insert into article(id, contents, author, fileinfo_id) values(2, '#qwe#qqq', 1, 1);
insert into article(id, contents, author, fileinfo_id) values(3, 'qwe', 1, 1);

insert into hash_tag(id, keyword, article_id) values(1, '#qwe', 1);
insert into hash_tag(id, keyword, article_id) values(2, '#qqq', 1);
insert into hash_tag(id, keyword, article_id) values(3, '#qww', 1);
insert into hash_tag(id, keyword, article_id) values(4, '#qee', 1);
insert into hash_tag(id, keyword, article_id) values(5, '#qwe', 2);
insert into hash_tag(id, keyword, article_id) values(6, '#qqq', 2);