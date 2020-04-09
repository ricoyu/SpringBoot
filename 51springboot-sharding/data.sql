DROP DATABASE IF EXISTS multi_ds00;
DROP DATABASE IF EXISTS multi_ds01;
DROP DATABASE IF EXISTS multi_ds02;
CREATE DATABASE multi_ds00 DEFAULT CHARACTER SET utf8mb4 default collate utf8mb4_general_ci;
CREATE DATABASE multi_ds01 DEFAULT CHARACTER SET utf8mb4 default collate utf8mb4_general_ci;
CREATE DATABASE multi_ds02 DEFAULT CHARACTER SET utf8mb4 default collate utf8mb4_general_ci;

GRANT ALL ON multi_ds00.* to rico@'%';
GRANT ALL ON multi_ds01.* to rico@'%';
GRANT ALL ON multi_ds02.* to rico@'%';
FLUSH PRIVILEGES;

CREATE TABLE T_ORDER(
	`id` INT NOT NULL PRIMARY KEY auto_increment, 
	USER_ID VARCHAR(20) NOT NULL COMMENT '用户ID', 
	MONEY DECIMAL(11, 2) NOT NULL COMMENT '金额'
) COMMENT '订单';