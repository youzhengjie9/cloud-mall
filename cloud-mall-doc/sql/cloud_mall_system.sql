/*
 Navicat Premium Data Transfer

 Source Server         : demo-01
 Source Server Type    : MySQL
 Source Server Version : 50711
 Source Host           : localhost:3306
 Source Schema         : cloud_mall_system

 Target Server Type    : MySQL
 Target Server Version : 50711
 File Encoding         : 65001

 Date: 01/01/2022 17:51:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classifybar
-- ----------------------------
DROP TABLE IF EXISTS `classifybar`;
CREATE TABLE `classifybar`  (
  `id` bigint(20) NOT NULL,
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classifybar
-- ----------------------------
INSERT INTO `classifybar` VALUES (1, '手机 平板 电话卡');
INSERT INTO `classifybar` VALUES (2, '电视 盒子');
INSERT INTO `classifybar` VALUES (3, '路由器 智能硬件');
INSERT INTO `classifybar` VALUES (4, '移动电源 插线板');
INSERT INTO `classifybar` VALUES (5, '耳机 音箱');
INSERT INTO `classifybar` VALUES (6, '电池 存储卡');
INSERT INTO `classifybar` VALUES (7, '保护套 后盖');
INSERT INTO `classifybar` VALUES (8, '贴膜 其他配件');
INSERT INTO `classifybar` VALUES (9, '米兔 服装');
INSERT INTO `classifybar` VALUES (10, '箱包 其他周边');

-- ----------------------------
-- Table structure for coupons_activity
-- ----------------------------
DROP TABLE IF EXISTS `coupons_activity`;
CREATE TABLE `coupons_activity`  (
  `id` bigint(20) NOT NULL,
  `coupons_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券名称',
  `coupons_count` int(11) NOT NULL COMMENT '优惠券数量',
  `amount` decimal(18, 0) NOT NULL COMMENT '优惠券金额',
  `limit_count` int(11) NOT NULL COMMENT '每人限制领取多少张',
  `min_point` decimal(18, 0) NOT NULL COMMENT '使用门槛',
  `start_time` date NOT NULL COMMENT '优惠券开始时间',
  `end_time` date NOT NULL COMMENT '优惠券结束时间',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠券备注',
  `valid` tinyint(4) NOT NULL COMMENT '是否生效(0是失效,1是生效)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupons_activity
-- ----------------------------
INSERT INTO `coupons_activity` VALUES (7788866, '内测卷012', 31, 88, 2, 1777, '2021-12-11', '2021-12-15', '6666', 1);
INSERT INTO `coupons_activity` VALUES (7788877, '内测卷011', 32, 55, 3, 1777, '2021-12-11', '2021-12-18', '5555', 1);
INSERT INTO `coupons_activity` VALUES (7788888, '内测卷010', 58, 99, 70, 1777, '2021-12-11', '2021-12-16', '555', 1);
INSERT INTO `coupons_activity` VALUES (7788899, '内测卷013', 19, 55, 1, 1888, '2021-12-11', '2021-12-16', '1111', 1);
INSERT INTO `coupons_activity` VALUES (735292248327222, '内测卷009', 25, 88, 3, 2000, '2021-12-11', '2021-12-16', '33', 1);
INSERT INTO `coupons_activity` VALUES (3734969100338181, '内测卷002', 5, 101, 3, 2001, '2021-12-11', '2021-12-15', '6666', 1);
INSERT INTO `coupons_activity` VALUES (3734969100338182, '内测卷005', 8, 150, 5, 1888, '2021-12-11', '2021-12-15', '7777', 1);
INSERT INTO `coupons_activity` VALUES (3735292248327173, '内测卷003', 15, 200, 3, 2000, '2021-12-11', '2021-12-16', '2222', 1);
INSERT INTO `coupons_activity` VALUES (3735292248327188, '内测卷006', 22, 222, 6, 1777, '2021-12-11', '2021-12-16', '9999', 1);
INSERT INTO `coupons_activity` VALUES (3735299452371973, '白金卷', 10, 888, 1, 0, '2021-12-11', '2022-02-19', '白金卷', 1);
INSERT INTO `coupons_activity` VALUES (3735304299807749, '王者卷', 5, 3888, 1, 0, '2021-12-11', '2022-03-11', '王者卷，仅有5张', 1);
INSERT INTO `coupons_activity` VALUES (3735309677495301, '普通卷', 3666, 58, 3, 1000, '2021-12-11', '2022-09-17', '普通卷，人人有份', 1);
INSERT INTO `coupons_activity` VALUES (3847682202731525, '测试01', 20, 150, 3, 100, '2021-12-31', '2022-01-15', '测试', 1);

-- ----------------------------
-- Table structure for coupons_record
-- ----------------------------
DROP TABLE IF EXISTS `coupons_record`;
CREATE TABLE `coupons_record`  (
  `id` bigint(20) NOT NULL,
  `coupons_id` bigint(20) NOT NULL COMMENT '优惠券id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `get_time` date NOT NULL COMMENT '领取优惠券时间',
  `use_type` tinyint(3) NOT NULL COMMENT '优惠券状态(0是未使用,1是已使用)',
  `use_time` date NULL DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupons_record
-- ----------------------------
INSERT INTO `coupons_record` VALUES (3757504566985733, 7788888, 1, '2021-12-14', 1, '2021-12-16');
INSERT INTO `coupons_record` VALUES (3757504668238853, 7788866, 1, '2021-12-14', 0, NULL);
INSERT INTO `coupons_record` VALUES (3757504773686277, 7788877, 1, '2021-12-14', 0, NULL);
INSERT INTO `coupons_record` VALUES (3826204060550149, 7788866, 1, '2021-12-26', 0, NULL);
INSERT INTO `coupons_record` VALUES (3847647818482693, 7788899, 1, '2021-12-30', 0, NULL);
INSERT INTO `coupons_record` VALUES (3847657427043333, 7788888, 3825912092820485, '2021-12-30', 0, NULL);

-- ----------------------------
-- Table structure for mxdp
-- ----------------------------
DROP TABLE IF EXISTS `mxdp`;
CREATE TABLE `mxdp`  (
  `id` bigint(20) NOT NULL,
  `imgName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `productId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mxdp
-- ----------------------------
INSERT INTO `mxdp` VALUES (1, '/static/img/nav/max.jpg', 1);
INSERT INTO `mxdp` VALUES (2, '/static/img/nav/mi5.jpg', 2);
INSERT INTO `mxdp` VALUES (3, '/static/img/nav/mi4c.jpg', 3);
INSERT INTO `mxdp` VALUES (4, '/static/img/nav/mi4c.jpg', 4);
INSERT INTO `mxdp` VALUES (5, '/static/img/nav/mi4c.jpg', 5);
INSERT INTO `mxdp` VALUES (6, '/static/img/nav/mi4c.jpg', 6);
INSERT INTO `mxdp` VALUES (7, '/static/img/nav/mi4c.jpg', 7);
INSERT INTO `mxdp` VALUES (8, '/static/img/nav/8.jpg', 8);
INSERT INTO `mxdp` VALUES (9, '/static/img/nav/9.jpg', 9);

-- ----------------------------
-- Table structure for recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `recharge_record`;
CREATE TABLE `recharge_record`  (
  `id` bigint(20) NOT NULL,
  `cardNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡号',
  `money` decimal(11, 0) NULL DEFAULT NULL COMMENT '面值',
  `created` datetime(0) NOT NULL COMMENT '充值时间',
  `userid` bigint(20) NOT NULL COMMENT '充值用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recharge_record
-- ----------------------------
INSERT INTO `recharge_record` VALUES (3724850763596805, '123666777', 200, '2021-12-08 22:18:34', 1);
INSERT INTO `recharge_record` VALUES (3728794044138501, '12366111', 500, '2021-12-09 15:01:23', 1);
INSERT INTO `recharge_record` VALUES (3728794852197381, '1133312', 150, '2021-12-09 15:01:36', 1);

-- ----------------------------
-- Table structure for rechargecard
-- ----------------------------
DROP TABLE IF EXISTS `rechargecard`;
CREATE TABLE `rechargecard`  (
  `id` bigint(20) NOT NULL,
  `cardNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡号',
  `password` bigint(20) NOT NULL COMMENT '密码',
  `money` decimal(11, 0) NOT NULL COMMENT '卡的面值',
  `created` datetime(0) NOT NULL COMMENT '创建时间',
  `status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '卡的状态：0是未使用,1是已使用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cardNumber`(`cardNumber`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rechargecard
-- ----------------------------
INSERT INTO `rechargecard` VALUES (11, '123666777', 66666666, 200, '2021-12-08 22:17:18', 0);
INSERT INTO `rechargecard` VALUES (15, '12366111', 622222, 500, '2021-12-08 22:17:18', 0);
INSERT INTO `rechargecard` VALUES (17, '1133312', 878888, 150, '2021-12-08 22:17:18', 0);

-- ----------------------------
-- Table structure for slideshow
-- ----------------------------
DROP TABLE IF EXISTS `slideshow`;
CREATE TABLE `slideshow`  (
  `id` bigint(20) NOT NULL,
  `src` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '轮播图url',
  `productId` bigint(20) NOT NULL COMMENT '轮播图对应的商品id',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of slideshow
-- ----------------------------
INSERT INTO `slideshow` VALUES (3859443445138437, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQFROABjSXAACPM0T-aZA693.jpg', 1, 30);
INSERT INTO `slideshow` VALUES (3859444584678405, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQFSSAX4A8AADskBm1ZiI608.jpg', 2, 28);
INSERT INTO `slideshow` VALUES (3859445279622149, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQFS-AT0agAADFkI8owfY016.jpg', 3, 27);
INSERT INTO `slideshow` VALUES (3859446380037125, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQFUCAAG83AACk6xsM0Ao618.jpg', 5, 26);
INSERT INTO `slideshow` VALUES (3859447232857093, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQFU2AQZCJAABiS9nI91s486.jpg', 6, 25);
INSERT INTO `slideshow` VALUES (3859448192435205, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQFVuAEFlpAAHGeTgKSxY147.jpg', 8, 22);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for wntj
-- ----------------------------
DROP TABLE IF EXISTS `wntj`;
CREATE TABLE `wntj`  (
  `id` bigint(20) NOT NULL,
  `imgName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `productId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wntj
-- ----------------------------
INSERT INTO `wntj` VALUES (1, '/static/img/nav/max.jpg', 1);
INSERT INTO `wntj` VALUES (2, '/static/img/nav/mi5.jpg', 2);
INSERT INTO `wntj` VALUES (3, '/static/img/nav/mi4c.jpg', 3);
INSERT INTO `wntj` VALUES (4, '/static/img/nav/mi4c.jpg', 5);
INSERT INTO `wntj` VALUES (5, '/static/img/nav/w6.jpg', 6);
INSERT INTO `wntj` VALUES (6, '/static/img/nav/w7.jpg', 7);

SET FOREIGN_KEY_CHECKS = 1;
