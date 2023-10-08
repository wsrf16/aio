CREATE TABLE `table` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `org_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组织ID',
  `app_id` bigint(20) DEFAULT NULL COMMENT '应用id',
  `action` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT 'view' COMMENT '权限动作',
  `resource` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '权限资源类型',
  `resource_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '资源id，可以为*',
  `allocation_type` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '分配类型，org，role，user',
  `principal_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '分配类型id，组织id，角色id，用户id',
  `create_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT null default now() COMMENT '创建时间',
  `update_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime default now() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  index idx_a_r_r_a_p(`action`,`resource`,`resource_id`,`allocation_type`,`principal_id`),
  index idx_o_a_a_r(`org_id`,`app_id`,`action`,`resource`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='访问权限分配表'