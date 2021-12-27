/*
 Navicat Premium Data Transfer

 Source Server         : demo-01
 Source Server Type    : MySQL
 Source Server Version : 50711
 Source Host           : localhost:3306
 Source Schema         : cloud_mall_user

 Target Server Type    : MySQL
 Target Server Version : 50711
 File Encoding         : 65001

 Date: 27/12/2021 20:33:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority`  (
  `id` tinyint(4) NOT NULL,
  `authority` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO `t_authority` VALUES (1, 'ROLE_admin');
INSERT INTO `t_authority` VALUES (2, 'ROLE_common');
INSERT INTO `t_authority` VALUES (3, 'ROLE_seller');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` bigint(20) NOT NULL,
  `authority` tinyint(4) NOT NULL COMMENT '权限id',
  `menu` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单JSON',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `authority`(`authority`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, 1, '[{\"id\":1,\"title\":\"工作空间\",\"type\":0,\"icon\":\"layui-icon layui-icon-console\",\"href\":\"\",\"children\":[{\"id\":10,\"title\":\"控制后台\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toconsole\"},{\"id\":14,\"title\":\"百度一下\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"http://www.baidu.com\"}]},{\"id\":\"component\",\"title\":\"商品管理\",\"icon\":\"layui-icon layui-icon-component\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":203,\"title\":\"发布商品\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/topublish\"},{\"id\":205,\"title\":\"商品管理\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toProductManager\"},{\"id\":207,\"title\":\"品牌管理\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toBrandManager\"},{\"id\":208,\"title\":\"分类管理\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toClassifyManager\"},{\"id\":209,\"title\":\"规格管理\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toSpec\"}]},{\"id\":\"result\",\"title\":\"订单管理\",\"icon\":\"layui-icon layui-icon-auz\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":\"success\",\"title\":\"订单管理\",\"icon\":\"layui-icon layui-icon-face-smile\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toOrderManager\"},{\"id\":\"failure\",\"title\":\"退货管理\",\"icon\":\"layui-icon layui-icon-face-cry\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toReturnGoods\"}]},{\"id\":\"error\",\"title\":\"用户管理\",\"icon\":\"layui-icon layui-icon-face-cry\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":403,\"title\":\"用户管理\",\"icon\":\"layui-icon layui-icon-face-smile\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toUserManager\"}]},{\"id\":\"system\",\"title\":\"访客管理\",\"icon\":\"layui-icon layui-icon-set-fill\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":601,\"title\":\"访客记录\",\"icon\":\"layui-icon layui-icon-face-smile\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toVisitorList\"},{\"id\":605,\"title\":\"行为日志\",\"icon\":\"layui-icon layui-icon-face-cry\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toLog\"}]},{\"id\":\"common\",\"title\":\"监控管理\",\"icon\":\"layui-icon layui-icon-component\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":701,\"title\":\"数据监控\",\"icon\":\"layui-icon layui-icon-console\",\"type\":0,\"children\":[{\"id\":2011,\"title\":\"RabbitMQ\",\"icon\":\"layui-icon layui-icon-face-smile\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"http://localhost:15672/\"},{\"id\":2014,\"title\":\"ElasticSearch\",\"icon\":\"layui-icon layui-icon-face-cry\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"http://localhost:5601/\"}]},{\"id\":2017,\"title\":\"接口监控\",\"icon\":\"layui-icon layui-icon-face-smile\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/monitorInterface\"}]},{\"id\":\"echarts\",\"title\":\"数据图表\",\"icon\":\"layui-icon layui-icon-chart\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":12121,\"title\":\"数据图表\",\"icon\":\"layui-icon layui-icon-face-smile\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toCharts\"}]},{\"id\":\"code\",\"title\":\"系统管理\",\"icon\":\"layui-icon layui-icon-util\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":801,\"title\":\"轮播图管理\",\"icon\":\"layui-icon layui-icon-util\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toSlideShow\"},{\"id\":802,\"title\":\"优惠券管理\",\"icon\":\"layui-icon layui-icon-face-cry\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toCoupons\"},{\"id\":803,\"title\":\"秒杀管理\",\"icon\":\"layui-icon layui-icon-face-cry\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toSeckill\"}]}]');
INSERT INTO `t_menu` VALUES (2, 2, '[{\"id\":1,\"title\":\"工作空间\",\"type\":0,\"icon\":\"layui-icon layui-icon-console\",\"href\":\"\",\"children\":[{\"id\":14,\"title\":\"百度一下\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"http://www.baidu.com\"}]}]');
INSERT INTO `t_menu` VALUES (3, 3, '[{\"id\":1,\"title\":\"工作空间\",\"type\":0,\"icon\":\"layui-icon layui-icon-console\",\"href\":\"\",\"children\":[{\"id\":10,\"title\":\"控制后台\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toconsole\"},{\"id\":14,\"title\":\"百度一下\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"http://www.baidu.com\"}]},{\"id\":\"component\",\"title\":\"商品管理\",\"icon\":\"layui-icon layui-icon-component\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":203,\"title\":\"发布商品\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/topublish\"},{\"id\":205,\"title\":\"商品管理\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toProductManager\"},{\"id\":207,\"title\":\"品牌管理\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toBrandManager\"},{\"id\":208,\"title\":\"分类管理\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toClassifyManager\"},{\"id\":209,\"title\":\"规格管理\",\"icon\":\"layui-icon layui-icon-console\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toSpec\"}]},{\"id\":\"result\",\"title\":\"订单管理\",\"icon\":\"layui-icon layui-icon-auz\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":\"success\",\"title\":\"订单管理\",\"icon\":\"layui-icon layui-icon-face-smile\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toOrderManager\"},{\"id\":\"failure\",\"title\":\"退货管理\",\"icon\":\"layui-icon layui-icon-face-cry\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toReturnGoods\"}]},{\"id\":\"code\",\"title\":\"系统管理\",\"icon\":\"layui-icon layui-icon-util\",\"type\":0,\"href\":\"\",\"children\":[{\"id\":803,\"title\":\"秒杀管理\",\"icon\":\"layui-icon layui-icon-face-cry\",\"type\":1,\"openType\":\"_iframe\",\"href\":\"/pear/toSeckill\"}]}]');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '帐号',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `money` decimal(10, 2) NOT NULL COMMENT '用户余额',
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的邮箱',
  `created` date NULL DEFAULT NULL COMMENT '用户创建时间',
  `valid` tinyint(1) NOT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$MCwNLrsS3HTNOicyV6YX6OtIsNYVW0jYf9P33JRfWiVAcSCncFLJK', 11949.22, '1550324080@qq.com', '2018-10-13', 1);
INSERT INTO `t_user` VALUES (3628635267466245, 'mymall', '$2a$10$NjxBVdRHc7qSfJYCKcmr/OKD5aHlvhvSFGGdcWHduiHP8.Yv1FCEW', 16200.15, '1550324080@qq.com', '2021-11-21', 1);
INSERT INTO `t_user` VALUES (3825912092820485, '8310894', '$2a$10$EgtP9xF3T/K3woOnFeAaJuZmIN0JM0c2SxIbBgFaylXq9DQlAVoHi', 16200.00, NULL, '2021-12-26', 1);

-- ----------------------------
-- Table structure for t_user_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_user_authority`;
CREATE TABLE `t_user_authority`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `authority_id` tinyint(4) NOT NULL COMMENT '用户权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_authority
-- ----------------------------
INSERT INTO `t_user_authority` VALUES (1, 1, 1);
INSERT INTO `t_user_authority` VALUES (3628635287389189, 3628635267466245, 3);
INSERT INTO `t_user_authority` VALUES (3825912100029445, 3825912092820485, 2);

-- ----------------------------
-- Table structure for t_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_user_detail`;
CREATE TABLE `t_user_detail`  (
  `id` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `sex` tinyint(3) NOT NULL COMMENT '性别',
  `signature` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '个性签名',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userid`(`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_detail
-- ----------------------------
INSERT INTO `t_user_detail` VALUES (201, 1, 0, '你好 ', '/static/img/user-icon/e6ea154788f347d6b816f1ead6065ee9.jpg');
INSERT INTO `t_user_detail` VALUES (202, 3628635267466245, 1, 'z', '/static/img/user-icon/default-icon.jpg');
INSERT INTO `t_user_detail` VALUES (3825912100029446, 3825912092820485, 0, '默认的个性签名', '/static/img/user-icon/default-icon.jpg');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
INSERT INTO `undo_log` VALUES (4, 214453807390658561, '192.168.184.1:8091:214453802802089984', 'serializer=jackson', 0x7B7D, 1, '2021-12-15 18:42:52', '2021-12-15 18:42:52', NULL);
INSERT INTO `undo_log` VALUES (5, 214453807097057281, '192.168.184.1:8091:214453802802089984', 'serializer=jackson', 0x7B7D, 1, '2021-12-15 18:42:52', '2021-12-15 18:42:52', NULL);

SET FOREIGN_KEY_CHECKS = 1;
