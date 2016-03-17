use shop_db;
CREATE TABLE t_user (
  id SERIAL PRIMARY KEY,
  username VARCHAR(100),
  password varchar(100),
  nickname VARCHAR(100),
  type int(5)
)
  ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE t_address (
  id        SERIAL PRIMARY KEY,
  recipient VARCHAR(255) NOT NULL,
  address_info VARCHAR(255),
  phone VARCHAR(100),
  zip VARCHAR(100),
  user_id BIGINT UNSIGNED NOT NULL,
  CONSTRAINT FOREIGN KEY (user_id) REFERENCES t_user(id)
)
  ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

show TABLES;
ALTER TABLE t_user CHANGE type role INT(5);

SELECT * FROM t_user;
DESC t_address;


