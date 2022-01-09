
-- SQLINES DEMO *** z_jobs 结构
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE builtin_quartz_jobs_seq;

CREATE TABLE IF NOT EXISTS builtin_quartz_jobs (
  id bigint NOT NULL DEFAULT NEXTVAL ('builtin_quartz_jobs_seq') ,
  uuid varchar(48) NOT NULL ,
  job_name varchar(128) NOT NULL ,
  bean_name varchar(100) NOT NULL ,
  method_name varchar(100) NOT NULL ,
  params varchar(256) DEFAULT NULL ,
  cron_expression varchar(64) DEFAULT NULL ,
  paused smallint NOT NULL ,
  person_in_charge varchar(32) DEFAULT NULL ,
  email varchar(64) DEFAULT NULL ,
  sub_task varchar(100) DEFAULT NULL ,
  pause_after_failure smallint DEFAULT NULL ,
  description varchar(128) DEFAULT NULL ,
  created timestamp(0) DEFAULT CURRENT_TIMESTAMP ,
  updated timestamp(0) DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (id),
  CONSTRAINT builtin_quartz_jobs_UN UNIQUE  (uuid)
)   ;

ALTER SEQUENCE builtin_quartz_jobs_seq RESTART WITH 1;


-- SQLINES DEMO *** z_logs 结构
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE builtin_quartz_logs_seq;

CREATE TABLE IF NOT EXISTS builtin_quartz_logs (
  id bigint NOT NULL DEFAULT NEXTVAL ('builtin_quartz_logs_seq') ,
  job_name varchar(128) NOT NULL ,
  bean_name varchar(100) NOT NULL ,
  method_name varchar(100) NOT NULL ,
  params varchar(256) DEFAULT NULL ,
  cron_expression varchar(64) DEFAULT NULL ,
  success smallint NOT NULL ,
  exception_detail text ,
  duration bigint NOT NULL DEFAULT '0' ,
  created timestamp(0) DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (id)
)   ;

ALTER SEQUENCE builtin_quartz_logs_seq RESTART WITH 1;