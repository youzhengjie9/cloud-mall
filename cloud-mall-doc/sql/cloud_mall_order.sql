/*
 Navicat Premium Data Transfer

 Source Server         : demo-01
 Source Server Type    : MySQL
 Source Server Version : 50711
 Source Host           : localhost:3306
 Source Schema         : cloud_mall_order

 Target Server Type    : MySQL
 Target Server Version : 50711
 File Encoding         : 65001

 Date: 25/12/2021 17:14:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` bigint(20) NOT NULL,
  `realname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户真实姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户电话号码',
  `province` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户收货地的省',
  `city` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户收货地的市',
  `area` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户收货地的区/县',
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户收货地的详细地址',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (100, '张三', '123123666', '广东省', '河源市', '源城区', '新江二路南', 1);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint(20) NOT NULL,
  `imgUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `goodsInfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `goodsParams` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `goodsCount` int(20) NOT NULL,
  `singleGoodsMoney` decimal(10, 2) NOT NULL,
  `realname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `userid` bigint(20) NOT NULL,
  `productid` bigint(20) NOT NULL,
  `statusid` bigint(20) NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (3655575498130437, 'http://localhost/static/img/nav/max.jpg', '小米Max', '全网通 4GB内存+64GB容量 指纹识别 黑色 每月20G流量套餐', 2, 2998.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-11-26 16:40:57', 1, 1, 2);
INSERT INTO `order` VALUES (3655575610852357, 'http://localhost/static/img/nav/max.jpg', '小米Max', '全网通 4GB内存+64GB容量 指纹识别 黑色 每月20G流量套餐', 2, 2998.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-11-26 16:40:59', 1, 1, 6);
INSERT INTO `order` VALUES (3672761237177349, 'http://localhost/static/img/nav/max.jpg', '小米Max', '全网通 4GB内存+64GB容量 指纹识别 黑色 每月20G流量套餐', 2, 2998.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-11-29 17:31:31', 1, 1, 6);
INSERT INTO `order` VALUES (3678103231529989, 'http://localhost/static/img/nav/max.jpg', '小米Max', '全网通 4GB内存+64GB容量 指纹识别 黑色 每月20G流量套餐', 1, 1499.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-11-30 16:10:03', 1, 1, 6);
INSERT INTO `order` VALUES (3678347308893189, 'http://localhost/static/img/nav/mi5.jpg', '小米手机5', '标准版', 1, 1999.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-11-30 17:12:07', 1, 2, 4);
INSERT INTO `order` VALUES (3678347330847749, 'http://localhost/static/img/nav/mi5.jpg', '小米手机5', '顶配版', 1, 1999.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-11-30 17:12:08', 1, 2, 1);
INSERT INTO `order` VALUES (3768751143453701, 'http://localhost/static/img/nav/max.jpg', '小米Max', '全网通 3GB内存+32GB容量 红色 每月40G流量套餐', 1, 14911.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-12-16 16:23:00', 1, 1, 1);
INSERT INTO `order` VALUES (3768783721333765, 'http://localhost/static/img/nav/mi5.jpg', '小米手机5', '标准版', 1, 1999.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-12-16 16:31:17', 1, 2, 1);
INSERT INTO `order` VALUES (3768865787479045, 'http://localhost/static/img/nav/mi5.jpg', '小米手机5', '高配版', 1, 1900.00, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-12-16 16:52:10', 1, 2, 1);
INSERT INTO `order` VALUES (3804058047808517, '/static/img/nav/max.jpg', '秒杀商品001', '该商品为秒杀商品', 1, 9.90, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-12-22 22:02:01', 1, 10086, 5);
INSERT INTO `order` VALUES (3804089479726085, '/static/img/nav/max.jpg', '秒杀商品001', '该商品为秒杀商品', 1, 9.90, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-12-22 22:10:00', 1, 10086, 1);
INSERT INTO `order` VALUES (3804125790995461, '/static/img/nav/max.jpg', '秒杀商品001', '该商品为秒杀商品', 1, 9.90, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-12-22 22:19:15', 1, 10086, 5);
INSERT INTO `order` VALUES (3804143809987589, '/static/img/nav/max.jpg', '秒杀商品001', '该商品为秒杀商品', 1, 9.90, '张三', '123123666', '广东省河源市源城区新江二路南', '2021-12-22 22:23:49', 1, 10086, 1);

-- ----------------------------
-- Table structure for orderstatus
-- ----------------------------
DROP TABLE IF EXISTS `orderstatus`;
CREATE TABLE `orderstatus`  (
  `id` bigint(20) NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderstatus
-- ----------------------------
INSERT INTO `orderstatus` VALUES (1, '待发货');
INSERT INTO `orderstatus` VALUES (2, '已发货');
INSERT INTO `orderstatus` VALUES (3, '交易成功');
INSERT INTO `orderstatus` VALUES (4, '已取消');
INSERT INTO `orderstatus` VALUES (5, '退款中');
INSERT INTO `orderstatus` VALUES (6, '退款完成');

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
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
