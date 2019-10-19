create database learning_db default character set utf8mb4 default collate utf8mb4_unicode_ci;
grant all on learning_db.* to 'learning'@'%' IDENTIFIED by 'learning123456789';
flush privileges;

#MySQL8 要先创建用户
create user 'learning'@'%' IDENTIFIED by 'learning123456789';
grant all on learning_db.* to 'learning'@'%';
flush privileges;