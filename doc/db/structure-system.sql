SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ss_user
-- ----------------------------
DROP TABLE IF EXISTS `ss_user`;
CREATE TABLE `ss_user` (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`LOGIN_NAME` varchar(255) NOT NULL,
	`NAME` varchar(64) DEFAULT NULL,
	`PASSWORD` varchar(255) DEFAULT NULL,
	`SALT` varchar(64) DEFAULT NULL,
	`EMAIL` varchar(64) DEFAULT NULL,
	`STATUS` varchar(64) DEFAULT NULL,
	`TEAM_ID` varchar(64) DEFAULT NULL,
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ss_team
-- ----------------------------
DROP TABLE IF EXISTS `ss_team`;
CREATE TABLE `ss_team` (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`NAME` varchar(255) NOT NULL,
	`MASTER_ID` bigint(20) DEFAULT NULL,
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ss_role
-- ----------------------------
DROP TABLE IF EXISTS `ss_role`;
CREATE TABLE `ss_role` (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`NAME` varchar(255) NOT NULL,
	`DESCN` varchar(500),
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ss_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_role`;
CREATE TABLE `ss_user_role` (
	`USER_ID` bigint(20) NOT NULL,
	`ROLE_ID` bigint(20) NOT NULL,
	PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ss_permission
-- ----------------------------
DROP TABLE IF EXISTS `ss_permission`;
CREATE TABLE `ss_permission` (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`NAME` varchar(255) NOT NULL,
	`DESCN` varchar(500),
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ss_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `ss_role_permission`;
CREATE TABLE `ss_role_permission` (
	`ROLE_ID` bigint(20) NOT NULL,
	`PERMISSION_ID` bigint(20) NOT NULL,
	PRIMARY KEY (`ROLE_ID`,`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ss_menu
-- ----------------------------
DROP TABLE IF EXISTS `ss_menu`;
CREATE TABLE `ss_menu`  (
	`ID`                   bigint(20) NOT NULL,
	`PARENT_ID`            bigint(20),
	`TITLE`                VARCHAR(200),
	`TIP`                  VARCHAR(200),
	`SEQ`                  INT,
	`IMAGE`                VARCHAR(200),
	`FORWARD`              VARCHAR(200),
	`TARGET`               VARCHAR(100),
	`ISLEAF`               INT,
	`STATUS`               INT,
	`DESCN`                VARCHAR(255),
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ss_menu_role
-- ----------------------------
DROP TABLE IF EXISTS ss_menu_role;
CREATE TABLE ss_menu_role  (
	`ROLE_ID`              bigint(20) NOT NULL,
	`MENU_ID`              bigint(20) NOT NULL,
	PRIMARY KEY (`ROLE_ID`, `MENU_ID`)
);
