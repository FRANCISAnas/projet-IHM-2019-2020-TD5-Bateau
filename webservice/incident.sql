CREATE TABLE `incident` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `nature` text,
  `description` text,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `longitude` decimal(11,8) DEFAULT NULL,
  `latitude` decimal(11,8) DEFAULT NULL,
  `android_id` varchar(255) DEFAULT NULL,
  `unique_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4