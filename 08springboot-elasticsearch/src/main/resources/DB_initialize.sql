create database learning_db default chatacter set utf8mb4 default collate utf8mb4_general_ci;
grant all on learning.* to 'learning'@'%' identified by 'learning123456789';
flush privileges;