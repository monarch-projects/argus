
create table `rate_limit_info`(
  id bigint(20) not null auto_increment primary key,
  app_name varchar(512) not null default '',
  clz_name varchar(1024) not null default '',
  method_name varchar(512) not null default '',
  `limit` int(11) not null default 1,
  wait_time int(11) not null default 1,
  created bigint(20) not null default 0,
  updated bigint(20) not null default 0,
  deleted tinyint(1) not null default 0,

  key idx_union(`created`,`updated`,`deleted`),
  key idx_updated(`updated`),
  key idx_app_name(`app_name`)
) AUTO_INCREMENT 65535 COMMENT  '限流策略表';


create table `data_base_monitor`(
  id bigint(20) not null auto_increment primary key,
  host varchar(512) not null default 'localhost',
  port varchar(512) not null default '3306',
  db_name varchar(512) not null ,
  username varchar(255) not null default 'root',
  password varchar(255) not null default 'root',
  `status` tinyint(1) not null default 1 comment '0停止，1运行，2异常',
  `type` tinyint(1) not null default 1 comment '1mysql ',
  created bigint(20) not null default 0,
  updated bigint(20) not null default 0,
  deleted tinyint(1) not null default 0,
  key idx_union(`created`,`updated`,`deleted`),
  key idx_updated(`updated`)
)AUTO_INCREMENT = 65535 COMMENT  'db监控表';