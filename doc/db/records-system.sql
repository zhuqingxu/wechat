-- ----------------------------
-- Records of ss_user
-- ----------------------------
INSERT INTO `ss_user` VALUES ('1', 'admin', '管理员', '691b14d79bf0fa2215f155235df5e670b64394cc', '7efbd59d9741d34f', 'zhuqingxu@fangxin365.com', 'enabled', '1');
INSERT INTO `ss_user` VALUES ('2', 'user', '普通用户', '2488aa0c31c624687bd9928e0a5d29e7d1ed520b', '6d65d24122c30500', 'zhuqx1130@163.com', 'enabled', '1');

-- ----------------------------
-- Records of ss_team
-- ----------------------------
INSERT INTO `ss_team` VALUES ('1', '放心365', '1');

-- ----------------------------
-- Records of ss_role
-- ----------------------------
INSERT INTO `ss_role` VALUES ('1', 'Admin', '网站超级管理员');
INSERT INTO `ss_role` VALUES ('2', 'Manager', '网站维护管理员');
INSERT INTO `ss_role` VALUES ('3', 'User', '网站普通用户');

-- ----------------------------
-- Records of ss_user_role
-- ----------------------------
INSERT INTO `ss_user_role` VALUES ('1', '1');
INSERT INTO `ss_user_role` VALUES ('1', '2');
INSERT INTO `ss_user_role` VALUES ('1', '3');

-- ----------------------------
-- Records of ss_permission
-- ----------------------------
INSERT INTO `ss_permission` VALUES ('1', 'user:view', '查看用户');
INSERT INTO `ss_permission` VALUES ('2', 'user:add', '添加用户');
INSERT INTO `ss_permission` VALUES ('3', 'user:edit', '修改用户');
INSERT INTO `ss_permission` VALUES ('4', 'user:delete', '删除用户');
INSERT INTO `ss_permission` VALUES ('5', 'user:authRole', '给用户分配角色');

INSERT INTO `ss_permission` VALUES ('6', 'role:view', '查看角色');
INSERT INTO `ss_permission` VALUES ('7', 'role:add', '添加角色');
INSERT INTO `ss_permission` VALUES ('8', 'role:edit', '修改角色');
INSERT INTO `ss_permission` VALUES ('9', 'role:delete', '删除角色');
INSERT INTO `ss_permission` VALUES ('10', 'role:authPermission', '给角色分配权限');

INSERT INTO `ss_permission` VALUES ('11', 'permission:view', '查看权限');
INSERT INTO `ss_permission` VALUES ('12', 'permission:add', '添加权限');
INSERT INTO `ss_permission` VALUES ('13', 'permission:edit', '修改权限');
INSERT INTO `ss_permission` VALUES ('14', 'permission:delete', '删除权限');

-- ----------------------------
-- Records of ss_role_permission
-- ----------------------------
INSERT INTO `ss_role_permission` VALUES ('1', '1');
INSERT INTO `ss_role_permission` VALUES ('1', '2');
INSERT INTO `ss_role_permission` VALUES ('1', '3');
INSERT INTO `ss_role_permission` VALUES ('1', '4');
INSERT INTO `ss_role_permission` VALUES ('1', '5');
INSERT INTO `ss_role_permission` VALUES ('1', '6');
INSERT INTO `ss_role_permission` VALUES ('1', '7');
INSERT INTO `ss_role_permission` VALUES ('1', '8');
INSERT INTO `ss_role_permission` VALUES ('1', '9');
INSERT INTO `ss_role_permission` VALUES ('1', '10');
INSERT INTO `ss_role_permission` VALUES ('1', '11');
INSERT INTO `ss_role_permission` VALUES ('1', '12');
INSERT INTO `ss_role_permission` VALUES ('1', '13');
INSERT INTO `ss_role_permission` VALUES ('1', '14');

-- ----------------------------
-- Records of ss_menu
-- ----------------------------
INSERT INTO ss_menu VALUES('10','1','权限管理','',1,'','','',0,1,'');
INSERT INTO ss_menu VALUES('11','10','用户管理','',1,'','/admin/user/manage','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('12','10','角色管理','',2,'','/admin/role/manage','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('13','10','权限管理','',3,'','/admin/permission/manage','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('14','10','微信用户管理','',4,'','/weixin/user','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('15','10','微信接口配置','',5,'','/wechat/config/manage','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('16','10','用户标签管理','',6,'','/wechat/tag/manage','dblselect',1,1,'');

INSERT INTO ss_menu VALUES('1000','1','数据同步','',2,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1100','1000','甄选标准','',1,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1110','1100','导入','',1,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1111','1110','后台分类导入','',1,'','/admin/category/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1112','1110','检测指标导入','',2,'','/admin/index/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1113','1110','指标关系导入','',3,'','/admin/indexRelation/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1114','1110','法规标准导入','',4,'','/admin/standard/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1115','1110','标准限量值导入','',5,'','/admin/standardLimit/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1120','1100','验证','',2,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1121','1120','已同步后台分类','',1,'','/admin/category/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1122','1120','已同步检测指标','',2,'','/admin/index/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1123','1120','已同步法规标准','',3,'','/admin/standard/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1124','1120','已同步标准限量值','',4,'','/admin/standardLimit/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1200','1000','市调采样送检','',2,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1210','1200','导入','',1,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1211','1210','渠道信息导入','',1,'','/admin/channel/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1212','1210','市调信息导入','',2,'','/admin/marketResearch/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1213','1210','采样信息导入','',3,'','/admin/sample/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1214','1210','送检信息导入','',4,'','/admin/sendStage/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1220','1200','验证','',2,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1221','1220','已同步渠道信息','',1,'','/admin/channel/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1222','1220','已同步市调信息','',2,'','/admin/marketResearch/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1223','1220','已同步采样信息','',3,'','/admin/sample/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1224','1220','已同步送检信息','',4,'','/admin/sendStage/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1300','1000','白名单','',3,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1310','1300','导入','',1,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1311','1310','发布产品导入','',1,'','/admin/publishProduct/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1312','1310','发布样品导入','',2,'','/admin/publishSample/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1313','1310','发布样品明细导入','',3,'','/admin/publishSampleDetail/preImport','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1314','1310','发布样品时间导入','',3,'','/admin/publishSampleDetail/preImportTime','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('1320','1300','验证','',2,'','','',0,1,'');
INSERT INTO ss_menu VALUES('1321','1320','已同步发布产品','',1,'','/admin/publishProduct/preList','dblselect',1,1,'');

INSERT INTO ss_menu VALUES('200','1','数据发布','',3,'','','',0,1,'');
INSERT INTO ss_menu VALUES('210','200','前台分类维护','',1,'','/admin/publishCategory/preList','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('220','200','发布数据维护','',2,'','/admin/publishProduct/listCategory','dblselect',1,1,'');

INSERT INTO ss_menu VALUES('300','1','文章发布','',4,'','','',0,1,'');
INSERT INTO ss_menu VALUES('310','300','聚焦热点','',1,'','/admin/hotfocus/manage','dblselect',1,1,'');
INSERT INTO ss_menu VALUES('320','300','专题资讯','',2,'','/admin/news/manage','dblselect',1,1,'');

-- ----------------------------
-- Records of ss_menu_role
-- ----------------------------
INSERT INTO ss_menu_role VALUES ('1','10');
INSERT INTO ss_menu_role VALUES ('1','11');
INSERT INTO ss_menu_role VALUES ('1','12');
INSERT INTO ss_menu_role VALUES ('1','13');
INSERT INTO ss_menu_role VALUES ('1','14');
INSERT INTO ss_menu_role VALUES ('1','15');
INSERT INTO ss_menu_role VALUES ('1','16');
INSERT INTO ss_menu_role VALUES ('1','1000');
INSERT INTO ss_menu_role VALUES ('1','1100');
INSERT INTO ss_menu_role VALUES ('1','1110');
INSERT INTO ss_menu_role VALUES ('1','1111');
INSERT INTO ss_menu_role VALUES ('1','1112');
INSERT INTO ss_menu_role VALUES ('1','1113');
INSERT INTO ss_menu_role VALUES ('1','1114');
INSERT INTO ss_menu_role VALUES ('1','1115');
INSERT INTO ss_menu_role VALUES ('1','1120');
INSERT INTO ss_menu_role VALUES ('1','1121');
INSERT INTO ss_menu_role VALUES ('1','1122');
INSERT INTO ss_menu_role VALUES ('1','1123');
INSERT INTO ss_menu_role VALUES ('1','1124');
INSERT INTO ss_menu_role VALUES ('1','1200');
INSERT INTO ss_menu_role VALUES ('1','1210');
INSERT INTO ss_menu_role VALUES ('1','1211');
INSERT INTO ss_menu_role VALUES ('1','1212');
INSERT INTO ss_menu_role VALUES ('1','1213');
INSERT INTO ss_menu_role VALUES ('1','1214');
INSERT INTO ss_menu_role VALUES ('1','1220');
INSERT INTO ss_menu_role VALUES ('1','1221');
INSERT INTO ss_menu_role VALUES ('1','1222');
INSERT INTO ss_menu_role VALUES ('1','1223');
INSERT INTO ss_menu_role VALUES ('1','1224');
INSERT INTO ss_menu_role VALUES ('1','1300');
INSERT INTO ss_menu_role VALUES ('1','1310');
INSERT INTO ss_menu_role VALUES ('1','1311');
INSERT INTO ss_menu_role VALUES ('1','1312');
INSERT INTO ss_menu_role VALUES ('1','1313');
INSERT INTO ss_menu_role VALUES ('1','1314');
INSERT INTO ss_menu_role VALUES ('1','1320');
INSERT INTO ss_menu_role VALUES ('1','1321');
INSERT INTO ss_menu_role VALUES ('1','200');
INSERT INTO ss_menu_role VALUES ('1','210');
INSERT INTO ss_menu_role VALUES ('1','220');
INSERT INTO ss_menu_role VALUES ('1','300');
INSERT INTO ss_menu_role VALUES ('1','310');
INSERT INTO ss_menu_role VALUES ('1','320');