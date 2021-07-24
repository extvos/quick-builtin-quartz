
-- 表 builtin_quartz_jobs 结构
CREATE TABLE IF NOT EXISTS `builtin_quartz_jobs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `uuid` varchar(48) NOT NULL COMMENT '用于子任务唯一标识',
  `job_name` varchar(128) NOT NULL COMMENT '定时器名称',
  `bean_name` varchar(100) NOT NULL COMMENT 'Bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名称',
  `params` varchar(256) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(64) DEFAULT NULL COMMENT 'cron表达式',
  `paused` tinyint(1) NOT NULL COMMENT '状态，暂时或启动',
  `person_in_charge` varchar(32) DEFAULT NULL COMMENT '负责人',
  `email` varchar(64) DEFAULT NULL COMMENT '报警邮箱',
  `sub_task` varchar(100) DEFAULT NULL COMMENT '子任务',
  `pause_after_failure` tinyint(1) DEFAULT NULL COMMENT '失败后暂停',
  `description` varchar(128) DEFAULT NULL COMMENT '备注',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `builtin_quartz_jobs_UN` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='任务信息表';


-- 表 builtin_quartz_logs 结构
CREATE TABLE IF NOT EXISTS `builtin_quartz_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_name` varchar(128) NOT NULL COMMENT '任务名称',
  `bean_name` varchar(100) NOT NULL COMMENT 'Bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名称',
  `params` varchar(256) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(64) DEFAULT NULL COMMENT 'cron表达式',
  `success` tinyint(1) NOT NULL COMMENT '状态',
  `exception_detail` text COMMENT '异常详情',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '执行耗时',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='任务日志记录';