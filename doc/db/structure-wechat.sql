-- ----------------------------
-- Table structure for wechat_config 微信公众账号配置
-- ----------------------------
drop table if exists wechat_config;
create table wechat_config (
   id          			bigint(20) not null auto_increment,
   app_id               varchar(100),
   app_secret			varchar(100),
   access_token			varchar(200),
   token				varchar(200),
   expires_time			varchar(50),
   is_expired			int,
   primary key (ID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_tag 用户标签
-- ----------------------------
drop table if exists wechat_tag;
create table wechat_tag (
   id          			bigint(20) not null auto_increment,
   tag_id               bigint(20),
   tag_name				varchar(100),
   count				bigint(20),
   primary key (ID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;