CREATE DATABASE IF NOT EXISTS `cncounter` DEFAULT CHARACTER SET utf8mb4;
USE `cncounter`;
DROP USER IF EXISTS 'cncounter';
CREATE USER 'cncounter'@'%' IDENTIFIED BY 'cncounter';
-- grant all privileges on cncounter.* to 'cncounter'@'%' identified by 'cncounter';
grant all privileges on cncounter.* to cncounter@localhost identified by 'cncounter';
grant all privileges on cncounter.* to 'cncounter'@'cnc-web1' identified by 'cncounter';
grant all privileges on cncounter.* to 'cncounter'@'cnc-web2' identified by 'cncounter';
flush privileges;

