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

SELECT node.name,
  (COUNT(parent.`name`) - 1) AS depth,
  node.id,
  node.parent_id,
  (SELECT name FROM t_category WHERE id = node.parent_id) AS  p_name
FROM t_category AS node
  INNER JOIN t_category AS parent
    on node.lft BETWEEN parent.lft and parent.rgt
WHERE parent.id != 1
GROUP BY node.name
ORDER BY node.lft;

SELECT * FROM t_product AS p
LEFT JOIN t_category AS c ON p.c_id = c.id
WHERE p.id = 1;


SELECT * FROM t_product
WHERE c_id in
(SELECT node.id
               FROM t_category AS node
                 INNER JOIN t_category AS  parent
                   ON  node.lft BETWEEN parent.lft AND parent.rgt
               WHERE parent.id = 4);


SELECT p.id AS p_id, p.name AS p_name, p.price, p.img, p.stock, p.status, p.sales,c.id AS c_id, c.name AS c_name FROM t_product p LEFT JOIN t_category c ON p.c_id = c.id WHERE price BETWEEN 0 AND 800 ORDER BY price;

SELECT DISTINCT node.id FROM t_category AS node INNER JOIN t_category AS parent ON node.lft BETWEEN parent.lft AND parent.rgt WHERE parent.id IN ( 6 ,24 );


SELECT o.id AS oid, o.buy_date as buyDate, o.pay_date as payDate,
  o.deliver_date as deliverDate, o.confirm_date as confirmDate,
  o.status as ostatus, o.total_price AS totalPrice,
  u.id AS uid, u.username, u.password, u.nickname, u.role,
  a.id AS aid, a.recipient, a.address_info AS addressInfo, a.phone, a.zip,
  cp.id AS cpid, cp.price AS cpPrice, cp.num AS purchaseNum,
  p.id as pid, p.name, p.price AS pPrice, p.img, p.stock, p.status AS pstatus, p.sales
FROM t_order AS o
  LEFT JOIN t_cart_product AS cp
  ON o.id = cp.oid
  LEFT JOIN t_user AS u
on o.uid = u.id
LEFT JOIN t_address AS a
on o.aid = a.id
LEFT JOIN t_product as p
on cp.pid = p.id
WHERE o.id = 2;

UPDATE t_order SET total_price = 4000 WHERE id = 3;
UPDATE t_order SET pay_date = NULL, deliver_date = NULL, confirm_date = NULL WHERE id = 2;