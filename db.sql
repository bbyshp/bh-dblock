CREATE TABLE `lock_info` (
  `expiration_time` datetime NOT NULL,
  `tag` varchar(255) NOT NULL,
  PRIMARY KEY (`tag`)
);