/*
 Navicat Premium Data Transfer

 Source Server         : demo-01
 Source Server Type    : MySQL
 Source Server Version : 50711
 Source Host           : localhost:3306
 Source Schema         : cloud_mall_product

 Target Server Type    : MySQL
 Target Server Version : 50711
 File Encoding         : 65001

 Date: 01/01/2022 17:52:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
  `id` bigint(20) NOT NULL,
  `brandName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '品牌名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (1, 'vivo');
INSERT INTO `brand` VALUES (2, '华为');
INSERT INTO `brand` VALUES (3, '小米');
INSERT INTO `brand` VALUES (4, 'oppo');
INSERT INTO `brand` VALUES (5, '荣耀');
INSERT INTO `brand` VALUES (6, 'apple');
INSERT INTO `brand` VALUES (7, 'iqoo');
INSERT INTO `brand` VALUES (8, 'realme');
INSERT INTO `brand` VALUES (9, '红米');
INSERT INTO `brand` VALUES (10, '哈曼卡顿');
INSERT INTO `brand` VALUES (11, 'B&O');
INSERT INTO `brand` VALUES (12, 'BOSE');
INSERT INTO `brand` VALUES (13, '柏林之声');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` bigint(20) NOT NULL,
  `imgUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购物车商品图片',
  `goodsInfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品信息',
  `goodsParams` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品选择的版本信息参数',
  `price` decimal(10, 2) NOT NULL COMMENT '商品单价',
  `goodsCount` int(11) NOT NULL COMMENT '商品数量',
  `singleGoodsMoney` decimal(10, 2) NOT NULL COMMENT '该商品的单价*数量的值',
  `userid` bigint(20) NOT NULL COMMENT '所属用户的id',
  `productid` bigint(20) NOT NULL COMMENT '商品id',
  `skus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所选sku，逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (3763129876546565, '/static/img/nav/max.jpg', '小米Max', '全网通 4GB内存+64GB容量 指纹识别  黑色  每月20G流量套餐  ', 14911.00, 1, 14911.00, 1, 1, '4,13,14');
INSERT INTO `cart` VALUES (3763132278440965, '/static/img/nav/max.jpg', '小米Max', '全网通 3GB内存+32GB容量  黑色  每月40G流量套餐  ', 14911.00, 1, 14911.00, 1, 1, '3,13,15');
INSERT INTO `cart` VALUES (3768789095744517, '/static/img/nav/max.jpg', '小米Max', '全网通 3GB内存+32GB容量  红色  每月40G流量套餐  ', 14911.00, 2, 29822.00, 1, 1, '3,12,15');
INSERT INTO `cart` VALUES (3768789881848837, '/static/img/nav/max.jpg', '小米Max', '全网通 4GB内存+64GB容量 指纹识别  红色  每月20G流量套餐  ', 14911.00, 1, 14911.00, 1, 1, '4,12,14');
INSERT INTO `cart` VALUES (3768795795293189, '/static/img/nav/mi5.jpg', '小米手机5', '顶配版  ', 1999.00, 1, 1999.00, 1, 2, '8');
INSERT INTO `cart` VALUES (3847644152071173, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', '小米Max', '全网通 3GB内存+32GB容量  黑色  每月20G流量套餐  ', 1911.00, 1, 1911.00, 1, 1, '3,13,14');

-- ----------------------------
-- Table structure for classify
-- ----------------------------
DROP TABLE IF EXISTS `classify`;
CREATE TABLE `classify`  (
  `id` bigint(20) NOT NULL COMMENT '分类id',
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `isNav` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否放入导航',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classify
-- ----------------------------
INSERT INTO `classify` VALUES (1, '手机', 1);
INSERT INTO `classify` VALUES (2, '红米', 1);
INSERT INTO `classify` VALUES (3, '平板', 1);
INSERT INTO `classify` VALUES (4, '电视', 1);
INSERT INTO `classify` VALUES (5, '盒子', 0);
INSERT INTO `classify` VALUES (6, '路由器', 1);
INSERT INTO `classify` VALUES (7, '智能硬件', 0);
INSERT INTO `classify` VALUES (8, '电话卡', 0);
INSERT INTO `classify` VALUES (9, '移动电源', 0);
INSERT INTO `classify` VALUES (10, '插线板', 0);
INSERT INTO `classify` VALUES (11, '耳机', 1);
INSERT INTO `classify` VALUES (12, '音箱', 0);
INSERT INTO `classify` VALUES (13, '电池', 0);
INSERT INTO `classify` VALUES (14, '存储卡', 0);
INSERT INTO `classify` VALUES (15, '保护套', 0);
INSERT INTO `classify` VALUES (16, '后盖', 0);
INSERT INTO `classify` VALUES (17, '贴膜', 1);
INSERT INTO `classify` VALUES (18, '其他配件', 0);
INSERT INTO `classify` VALUES (19, '米兔', 0);
INSERT INTO `classify` VALUES (20, '服装', 0);
INSERT INTO `classify` VALUES (21, '箱包', 0);
INSERT INTO `classify` VALUES (22, '其他周边', 0);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `productId` bigint(20) NOT NULL COMMENT '商品id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品图片地址',
  `number` int(20) NOT NULL COMMENT '还剩商品数量',
  `fl_id` bigint(20) NOT NULL COMMENT '商品分类id',
  `b_id` bigint(20) NOT NULL COMMENT '商品所属品牌id',
  `introduce_img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品介绍图片地址用逗号分隔',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品内容介绍',
  `userid` bigint(20) NOT NULL DEFAULT 1 COMMENT '发布商品的用户id',
  PRIMARY KEY (`productId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '小米Max', 1911.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 956, 1, 3, '/static/img/introduce/2c934a1d8fc57091.jpg,/static/img/introduce/05b406882a48787a.jpg', '<p>hello1</p>', 1);
INSERT INTO `product` VALUES (2, '小米手机5', 1999.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEkeABrfpAAEBZG2fFQU703.jpg', 0, 1, 3, '/static/img/introduce/528c61caf149031e.jpg', '<p>aaa</p>', 1);
INSERT INTO `product` VALUES (3, '小米手机4c', 1699.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEkeABrfpAAEBZG2fFQU703.jpg', 161, 1, 3, '/static/img/introduce/528c61caf149031e.jpg', '<p>bb</p>', 1);
INSERT INTO `product` VALUES (4, '红米Note 3', 1099.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEuCAa26hAABLqT0SFHQ077.jpg', 151, 2, 9, '/static/img/introduce/7860d5b0a1de4362.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg', '<p>aa</p>', 1);
INSERT INTO `product` VALUES (5, '红米手机3S', 1099.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 81, 2, 9, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p>aa</p>', 1);
INSERT INTO `product` VALUES (6, '红米手机3', 1099.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 771, 1, 9, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (7, '小米平板2 16GB', 1099.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 11, 3, 3, '/static/img/introduce/7860d5b0a1de4362.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg', '', 1);
INSERT INTO `product` VALUES (8, '小米平板2 64GB', 1099.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 177, 3, 3, '/static/img/introduce/528c61caf149031e.jpg', '', 1);
INSERT INTO `product` VALUES (9, '小米盒子3', 1222.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 11, 1, 3, '/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg,/static/img/introduce/e9f22bbfc2bf5fc4.jpg', '', 1);
INSERT INTO `product` VALUES (10, '小米电视3S 43英寸', 1299.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 16, 4, 3, '/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg,/static/img/introduce/e9f22bbfc2bf5fc4.jpg', '', 1);
INSERT INTO `product` VALUES (11, '九号平衡车', 1399.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 16, 22, 1, '/static/img/introduce/7860d5b0a1de4362.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg', '', 1);
INSERT INTO `product` VALUES (12, '小米空气净化器 2', 699.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 16, 7, 3, '/static/img/introduce/7860d5b0a1de4362.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg', '', 1);
INSERT INTO `product` VALUES (13, '小米手机4', 1199.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEuCAa26hAABLqT0SFHQ077.jpg', 21, 20, 3, '/static/img/introduce/528c61caf149031e.jpg', '', 1);
INSERT INTO `product` VALUES (14, '小米Note', 2899.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 91, 1, 3, '/static/img/introduce/7860d5b0a1de4362.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg', '', 1);
INSERT INTO `product` VALUES (15, 'vivo tws2耳机', 499.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEuCAa26hAABLqT0SFHQ077.jpg', 200, 11, 1, '/static/img/introduce/20917dcdfe53f26c.jpg,/static/img/introduce/24f334967092fbb5.jpg,/static/img/introduce/e9f22bbfc2bf5fc4.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg', '', 1);
INSERT INTO `product` VALUES (16, '罗马仕充电宝', 79.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 100, 9, 2, '/static/img/introduce/d9f495d22ddd0e66.jpg,/static/img/introduce/e9f22bbfc2bf5fc4.jpg', '', 1);
INSERT INTO `product` VALUES (17, 'iqooneo3电池', 159.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 200, 13, 1, '/static/img/introduce/20917dcdfe53f26c.jpg,/static/img/introduce/24f334967092fbb5.jpg,/static/img/introduce/e9f22bbfc2bf5fc4.jpg,/static/img/introduce/d9f495d22ddd0e66.jpg', '', 1);
INSERT INTO `product` VALUES (18, 'iqooneo3原装手机壳', 29.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEuCAa26hAABLqT0SFHQ077.jpg', 999, 15, 1, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (19, 'iPhone 13pro max钢化膜', 299.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEuCAa26hAABLqT0SFHQ077.jpg', 555, 17, 6, '/static/img/introduce/7045d963bd969cff.jpg,/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/5213c502ac43fe43.jpg', '', 1);
INSERT INTO `product` VALUES (20, '漫步者NB2 pro', 449.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 200, 11, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (21, '宝马顶级哈曼卡顿音响', 68888.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 3, 11, 6, '/static/img/introduce/d9f495d22ddd0e66.jpg,/static/img/introduce/e9f22bbfc2bf5fc4.jpg', '', 1);
INSERT INTO `product` VALUES (3577462888989701, '测试产品1', 16.30, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 2, 1, 1, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462896198661, '测试产品2', 32.60, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 4, 2, 1, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462899475461, '测试产品3', 48.90, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 6, 2, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462902752261, '测试产品4', 65.20, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 8, 10, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462906029061, '测试产品5', 81.50, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 10, 12, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462909961221, '测试产品6', 97.80, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 12, 5, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462913893381, '测试产品7', 114.10, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 14, 6, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462917760005, '测试产品8', 130.40, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 16, 2, 5, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462921102341, '测试产品9', 146.70, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 18, 13, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462925034501, '测试产品10', 163.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 20, 9, 7, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462928966661, '测试产品11', 179.30, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 22, 14, 8, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462932898821, '测试产品12', 195.60, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 24, 16, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462936830981, '测试产品13', 211.90, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 26, 2, 5, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462940107781, '测试产品14', 228.20, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 28, 2, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462944039941, '测试产品15', 244.50, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 30, 15, 7, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462947972101, '测试产品16', 260.80, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 32, 17, 8, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462952559621, '测试产品17', 277.10, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 34, 2, 1, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462956491781, '测试产品18', 293.40, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 36, 18, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462959768581, '测试产品19', 309.70, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 38, 2, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462963635205, '测试产品20', 326.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 40, 20, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462966977541, '测试产品21', 342.30, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 42, 2, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462970909701, '测试产品22', 358.60, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 44, 3, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462974841861, '测试产品23', 374.90, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 46, 21, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462978118661, '测试产品24', 391.20, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 48, 2, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462981395461, '测试产品25', 407.50, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 50, 22, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462985262085, '测试产品26', 423.80, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 52, 3, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462988538885, '测试产品27', 440.10, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 54, 21, 5, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462991881221, '测试产品28', 456.40, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 56, 3, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462995158021, '测试产品29', 472.70, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 58, 3, 7, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577462999090181, '测试产品30', 489.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 60, 22, 8, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463002366981, '测试产品31', 505.30, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 62, 19, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463005643781, '测试产品32', 521.60, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 64, 9, 1, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463009510405, '测试产品33', 537.90, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 66, 18, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463012787205, '测试产品34', 554.20, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 68, 9, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463016129541, '测试产品35', 570.50, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 70, 17, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463019406341, '测试产品36', 586.80, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 72, 16, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463022683141, '测试产品37', 603.10, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 74, 2, 7, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463026615301, '测试产品38', 619.40, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 76, 5, 8, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463030547461, '测试产品39', 635.70, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 78, 14, 9, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463033824261, '测试产品40', 652.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 80, 6, 9, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463037756421, '测试产品41', 668.30, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 82, 2, 9, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463041688581, '测试产品42', 684.60, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 84, 13, 7, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463045620741, '测试产品43', 700.90, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 86, 5, 7, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463049487365, '测试产品44', 717.20, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 88, 5, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463052829701, '测试产品45', 733.50, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 90, 16, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463056761861, '测试产品46', 749.80, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 92, 5, 5, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463060038661, '测试产品47', 766.10, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 94, 13, 5, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463063970821, '测试产品48', 782.40, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 96, 2, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463067837445, '测试产品49', 798.70, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 98, 6, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463071114245, '测试产品50', 815.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 100, 6, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463074456581, '测试产品51', 831.30, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 102, 7, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463077733381, '测试产品52', 847.60, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 104, 7, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463081600005, '测试产品53', 863.90, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 106, 7, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463084942341, '测试产品54', 880.20, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 108, 4, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463088874501, '测试产品55', 896.50, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 110, 13, 1, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463092151301, '测试产品56', 912.80, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 112, 5, 1, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463095428101, '测试产品57', 929.10, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 114, 13, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463098704901, '测试产品58', 945.40, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 116, 2, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463101981701, '测试产品59', 961.70, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 118, 7, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463105258501, '测试产品60', 978.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEkeABrfpAAEBZG2fFQU703.jpg', 120, 9, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463109190661, '测试产品61', 994.30, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 122, 9, 5, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463113057285, '测试产品62', 1010.60, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 124, 9, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463116399621, '测试产品63', 1026.90, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEkeABrfpAAEBZG2fFQU703.jpg', 126, 4, 8, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463119676421, '测试产品64', 1043.20, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 128, 5, 8, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463123608581, '测试产品65', 1059.50, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 130, 6, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463127540741, '测试产品66', 1075.80, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 132, 7, 8, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463130817541, '测试产品67', 1092.10, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEkeABrfpAAEBZG2fFQU703.jpg', 134, 5, 5, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463134749701, '测试产品68', 1108.40, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEkeABrfpAAEBZG2fFQU703.jpg', 136, 5, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463138681861, '测试产品69', 1124.70, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 138, 2, 6, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463142614021, '测试产品70', 1141.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 140, 2, 7, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463146546181, '测试产品71', 1157.30, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 142, 4, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463150478341, '测试产品72', 1173.60, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 144, 5, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463154344965, '测试产品73', 1189.90, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 146, 6, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463158342661, '测试产品74', 1206.20, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 148, 7, 1, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463161619461, '测试产品75', 1222.50, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEkeABrfpAAEBZG2fFQU703.jpg', 150, 12, 9, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463165551621, '测试产品76', 1238.80, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 152, 1, 8, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3577463169483781, '测试产品77', 1255.10, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEkeABrfpAAEBZG2fFQU703.jpg', 154, 2, 7, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '', 1);
INSERT INTO `product` VALUES (3820945337156613, 'aaa', 222.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 11, 3, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p><span style=\"color: #e03e2d;\">hello</span></p>', 1);
INSERT INTO `product` VALUES (3825275569243141, 'aaab', 11.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 122, 6, 3, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p>aaa</p>', 1);
INSERT INTO `product` VALUES (3825283618964485, 'bba', 33.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHQEu2ACGlFAADNnZRgrvA376.jpg', 22, 4, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p>11</p>', 1);
INSERT INTO `product` VALUES (3825286955664389, 'bba1', 322.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 22, 4, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p>11</p>', 1);
INSERT INTO `product` VALUES (3826228014547973, 'abc', 300.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 20, 3, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p><span style=\"color: #e03e2d;\">hello</span></p>', 1);
INSERT INTO `product` VALUES (3831662381696005, 'ccc', 321.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 123, 3, 4, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p>aaa</p>', 1);
INSERT INTO `product` VALUES (3831681638204421, 'a', 22.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHJnlaAAeUuAACWDHC7oD0615.jpg', 11, 2, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p>111</p>', 1);
INSERT INTO `product` VALUES (3847665466606597, 'abc', 330.00, 'http://120.25.158.235:8888/group1/M00/00/00/rBILeWHNVw2AMKozAADNnZRgrvA661.jpg', 20, 7, 2, '/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg', '<p><span style=\"color: #e03e2d;\">hello world</span></p>', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for versioninfo
-- ----------------------------
DROP TABLE IF EXISTS `versioninfo`;
CREATE TABLE `versioninfo`  (
  `versionId` bigint(20) NOT NULL COMMENT '商品版本id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品版本名称',
  `p_id` bigint(20) NOT NULL COMMENT '该版本对应的商品id',
  `price` decimal(10, 2) NOT NULL COMMENT '选了这个版本会增加的价格',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本描述',
  `order` int(11) NULL DEFAULT NULL COMMENT '排序',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '信息标题',
  PRIMARY KEY (`versionId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of versioninfo
-- ----------------------------
INSERT INTO `versioninfo` VALUES (1, '全网通 2GB内存＋16GB容量', 0, 100.00, '高通骁龙820处理器 最高主频 1.8GHz，3GB内存，32GB容量，全网通3.0', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (2, '全网通 3GB内存＋32GB容量 指纹识别', 0, 200.00, '高通骁龙660', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (3, '全网通 3GB内存+32GB容量', 1, 300.00, '高通骁龙820处理器 最高主频 1.8GHz，3GB内存，32GB容量，全网通3.0', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (4, '全网通 4GB内存+64GB容量 指纹识别', 1, 800.00, '高通骁龙820处理器 最高主频 1.8GHz，3GB内存，32GB容量，全网通3.0', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (5, '全网通 6GB内存+128GB容量 指纹识别', 4, 850.00, '高通骁龙820处理器 最高主频 1.8GHz，3GB内存，32GB容量，全网通3.0', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (6, '标准版', 2, 0.00, '高通骁龙835', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (7, '高配版', 2, 1000.00, '高通骁龙855', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (8, '顶配版', 2, 2300.00, '高通骁龙888', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (9, '标准版全网通 2GB内存＋16GB容量', 4, 600.00, '高通骁龙625', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (10, '标准版 2GB内存+16GB容量', 5, 650.00, '高通骁龙625', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (11, '高配版 3GB内存+32GB容量', 5, 700.00, '高通骁龙660', 1, '选择版本');
INSERT INTO `versioninfo` VALUES (12, '红色', 1, 1.00, '各种好看的颜色', 2, '选择颜色');
INSERT INTO `versioninfo` VALUES (13, '黑色', 1, 2.00, '各种好看的颜色', 2, '选择颜色');
INSERT INTO `versioninfo` VALUES (14, '每月20G流量套餐', 1, 69.00, '实惠流量套餐', 3, '选择套餐');
INSERT INTO `versioninfo` VALUES (15, '每月40G流量套餐', 1, 102.00, '实惠流量套餐', 3, '选择套餐');
INSERT INTO `versioninfo` VALUES (3814826232448005, '入门款', 3, 111.00, '描述aaa', 1, '版本选择');
INSERT INTO `versioninfo` VALUES (3814830774551557, '土豪金', 3, 800.00, '土豪金aaa', 2, '颜色');

SET FOREIGN_KEY_CHECKS = 1;
