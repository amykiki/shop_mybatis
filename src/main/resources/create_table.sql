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
INSERT INTO t_user VALUES (NULL, 'harry', '222', '哈利', 3);
UPDATE t_user SET role = 1 WHERE id = 5;

SELECT * FROM t_address;
INSERT INTO t_address VALUES (NULL, 'harry', '女贞路999号', '582469', '200001', 4);

SELECT *, t1.id as addr_id FROM t_address t1 LEFT JOIN t_user t2 ON t1.user_id = t2.id WHERE t1.id = 1;
SELECT *, t1.id as t_user_id, t2.id as t_addr_id FROM t_user t1 LEFT JOIN t_address t2 ON t1.id = t2.user_id GROUP BY t1.id;

SHOW TABLES;
DESC t_category;


SELECT
  node.id     AS node_id,
  node.name   AS node_name,
  parent.id   AS parent_id,
  parent.name AS parent_name
FROM t_category AS node
  INNER JOIN t_category AS parent on node.parent_id = parent.id
WHERE parent.id = -1;

SELECT
  node.id     AS node_id,
  node.name   AS node_name,
  parent.id   AS parent_id,
  parent.name AS parent_name
FROM t_category AS node
  INNER JOIN t_category AS parent on node.parent_id = parent.id
WHERE node.id = 2;

SELECT CONCAT( REPEAT('   ', COUNT(parent.`name`) - 1), node.name) AS `node_name`,
  node.name,
       (COUNT(parent.`name`) - 1) AS depth,
  node.id, node.parent_id
FROM t_category AS node
  INNER JOIN t_category AS parent
    on node.lft BETWEEN parent.lft and parent.rgt
WHERE parent.id != 1
GROUP BY node.name
ORDER BY node.lft;



