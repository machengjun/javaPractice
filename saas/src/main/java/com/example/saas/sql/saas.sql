CREATE SCHEMA `saas_one` DEFAULT CHARACTER SET utf8mb4 ;
CREATE SCHEMA `saas_two` DEFAULT CHARACTER SET utf8mb4 ;

CREATE TABLE `user` (
                                 `id` varchar(36) NOT NULL,
                                 `create_time` datetime DEFAULT NULL,
                                 `modify_time` datetime DEFAULT NULL,
                                 `version` int(11) DEFAULT NULL,
                                 `name` varchar(255) DEFAULT NULL,
                                 `age` int(11) DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

