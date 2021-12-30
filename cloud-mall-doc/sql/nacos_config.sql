/*
 Navicat Premium Data Transfer

 Source Server         : demo-01
 Source Server Type    : MySQL
 Source Server Version : 50711
 Source Host           : localhost:3306
 Source Schema         : nacos_config

 Target Server Type    : MySQL
 Target Server Version : 50711
 File Encoding         : 65001

 Date: 30/12/2021 19:50:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1102 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (993, 'transport.type', 'SEATA_GROUP', 'TCP', 'b136ef5f6a01d816991fe3cf7a6ac763', '2021-08-10 16:21:31', '2021-08-10 16:21:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (994, 'transport.server', 'SEATA_GROUP', 'NIO', 'b6d9dfc0fb54277321cebc0fff55df2f', '2021-08-10 16:21:32', '2021-08-10 16:21:32', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (995, 'transport.heartbeat', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-08-10 16:21:34', '2021-08-10 16:21:34', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (996, 'transport.enableClientBatchSendRequest', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-08-10 16:21:36', '2021-08-10 16:21:36', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (997, 'transport.threadFactory.bossThreadPrefix', 'SEATA_GROUP', 'NettyBoss', '0f8db59a3b7f2823f38a70c308361836', '2021-08-10 16:21:39', '2021-08-10 16:21:39', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (998, 'transport.threadFactory.workerThreadPrefix', 'SEATA_GROUP', 'NettyServerNIOWorker', 'a78ec7ef5d1631754c4e72ae8a3e9205', '2021-08-10 16:21:43', '2021-08-10 16:21:43', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (999, 'transport.threadFactory.serverExecutorThreadPrefix', 'SEATA_GROUP', 'NettyServerBizHandler', '11a36309f3d9df84fa8b59cf071fa2da', '2021-08-10 16:21:48', '2021-08-10 16:21:48', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1000, 'transport.threadFactory.shareBossWorker', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:21:51', '2021-08-10 16:21:51', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1001, 'transport.threadFactory.clientSelectorThreadPrefix', 'SEATA_GROUP', 'NettyClientSelector', 'cd7ec5a06541e75f5a7913752322c3af', '2021-08-10 16:21:55', '2021-08-10 16:21:55', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1002, 'transport.threadFactory.clientSelectorThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2021-08-10 16:21:58', '2021-08-10 16:21:58', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1003, 'transport.threadFactory.clientWorkerThreadPrefix', 'SEATA_GROUP', 'NettyClientWorkerThread', '61cf4e69a56354cf72f46dc86414a57e', '2021-08-10 16:22:02', '2021-08-10 16:22:02', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1004, 'transport.threadFactory.bossThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2021-08-10 16:22:05', '2021-08-10 16:22:05', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1005, 'transport.threadFactory.workerThreadSize', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-08-10 16:22:08', '2021-08-10 16:22:08', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1006, 'transport.shutdown.wait', 'SEATA_GROUP', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2021-08-10 16:22:09', '2021-08-10 16:22:09', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1007, 'service.vgroupMapping.my_test_tx_group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-08-10 16:22:12', '2021-08-10 16:22:12', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1008, 'service.default.grouplist', 'SEATA_GROUP', '127.0.0.1:8091', 'c32ce0d3e264525dcdada751f98143a3', '2021-08-10 16:22:15', '2021-08-10 16:22:15', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1009, 'service.enableDegrade', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:22:17', '2021-08-10 16:22:17', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1010, 'service.disableGlobalTransaction', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:22:19', '2021-08-10 16:22:19', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1011, 'client.rm.asyncCommitBufferLimit', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2021-08-10 16:22:21', '2021-08-10 16:22:21', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1012, 'client.rm.lock.retryInterval', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2021-08-10 16:22:23', '2021-08-10 16:22:23', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1013, 'client.rm.lock.retryTimes', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2021-08-10 16:22:25', '2021-08-10 16:22:25', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1014, 'client.rm.lock.retryPolicyBranchRollbackOnConflict', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-08-10 16:22:28', '2021-08-10 16:22:28', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1015, 'client.rm.reportRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2021-08-10 16:22:30', '2021-08-10 16:22:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1016, 'client.rm.tableMetaCheckEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:22:32', '2021-08-10 16:22:32', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1017, 'client.rm.tableMetaCheckerInterval', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2021-08-10 16:22:35', '2021-08-10 16:22:35', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1018, 'client.rm.sqlParserType', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2021-08-10 16:22:37', '2021-08-10 16:22:37', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1019, 'client.rm.reportSuccessEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:22:39', '2021-08-10 16:22:39', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1020, 'client.rm.sagaBranchRegisterEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:22:41', '2021-08-10 16:22:41', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1021, 'client.rm.tccActionInterceptorOrder', 'SEATA_GROUP', '-2147482648', 'f056d9efa5dae3872f9da035c83bcde8', '2021-08-10 16:22:44', '2021-08-10 16:22:44', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1022, 'client.tm.commitRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2021-08-10 16:22:46', '2021-08-10 16:22:46', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1023, 'client.tm.rollbackRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2021-08-10 16:22:48', '2021-08-10 16:22:48', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1024, 'client.tm.defaultGlobalTransactionTimeout', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2021-08-10 16:22:51', '2021-08-10 16:22:51', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1025, 'client.tm.degradeCheck', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:22:53', '2021-08-10 16:22:53', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1026, 'client.tm.degradeCheckAllowTimes', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2021-08-10 16:22:55', '2021-08-10 16:22:55', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1027, 'client.tm.degradeCheckPeriod', 'SEATA_GROUP', '2000', '08f90c1a417155361a5c4b8d297e0d78', '2021-08-10 16:22:57', '2021-08-10 16:22:57', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1028, 'client.tm.interceptorOrder', 'SEATA_GROUP', '-2147482648', 'f056d9efa5dae3872f9da035c83bcde8', '2021-08-10 16:22:59', '2021-08-10 16:22:59', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1029, 'store.mode', 'SEATA_GROUP', 'db', 'd77d5e503ad1439f585ac494268b351b', '2021-08-10 16:23:00', '2021-08-10 16:23:00', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1030, 'store.lock.mode', 'SEATA_GROUP', 'file', '8c7dd922ad47494fc02c388e12c00eac', '2021-08-10 16:23:02', '2021-08-10 16:23:02', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1031, 'store.session.mode', 'SEATA_GROUP', 'file', '8c7dd922ad47494fc02c388e12c00eac', '2021-08-10 16:23:03', '2021-08-10 16:23:03', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1032, 'store.file.dir', 'SEATA_GROUP', 'file_store/data', '6a8dec07c44c33a8a9247cba5710bbb2', '2021-08-10 16:23:06', '2021-08-10 16:23:06', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1033, 'store.file.maxBranchSessionSize', 'SEATA_GROUP', '16384', 'c76fe1d8e08462434d800487585be217', '2021-08-10 16:23:08', '2021-08-10 16:23:08', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1034, 'store.file.maxGlobalSessionSize', 'SEATA_GROUP', '512', '10a7cdd970fe135cf4f7bb55c0e3b59f', '2021-08-10 16:23:11', '2021-08-10 16:23:11', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1035, 'store.file.fileWriteBufferCacheSize', 'SEATA_GROUP', '16384', 'c76fe1d8e08462434d800487585be217', '2021-08-10 16:23:13', '2021-08-10 16:23:13', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1036, 'store.file.flushDiskMode', 'SEATA_GROUP', 'async', '0df93e34273b367bb63bad28c94c78d5', '2021-08-10 16:23:15', '2021-08-10 16:23:15', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1037, 'store.file.sessionReloadReadSize', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2021-08-10 16:23:17', '2021-08-10 16:23:17', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1038, 'store.db.datasource', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2021-08-10 16:23:19', '2021-08-10 16:23:19', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1039, 'store.db.dbType', 'SEATA_GROUP', 'mysql', '81c3b080dad537de7e10e0987a4bf52e', '2021-08-10 16:23:20', '2021-08-10 16:23:20', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1040, 'store.db.driverClassName', 'SEATA_GROUP', 'com.mysql.jdbc.Driver', '683cf0c3a5a56cec94dfac94ca16d760', '2021-08-10 16:23:23', '2021-08-10 16:23:23', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1041, 'store.db.url', 'SEATA_GROUP', 'jdbc:mysql://127.0.0.1:3306/seata?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8', '350cacdfee28361927e97c24c5b2f92b', '2021-08-10 16:23:30', '2021-08-10 16:23:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1042, 'store.db.user', 'SEATA_GROUP', 'root', '63a9f0ea7bb98050796b649e85481845', '2021-08-10 16:23:31', '2021-08-10 16:23:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1043, 'store.db.password', 'SEATA_GROUP', '18420163207', 'd957aaa2e2b7c6f89c1e318b8dcb009d', '2021-08-10 16:23:33', '2021-08-10 16:23:33', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1044, 'store.db.minConn', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2021-08-10 16:23:34', '2021-08-10 16:23:34', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1045, 'store.db.maxConn', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2021-08-10 16:23:36', '2021-08-10 16:23:36', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1046, 'store.db.globalTable', 'SEATA_GROUP', 'global_table', '8b28fb6bb4c4f984df2709381f8eba2b', '2021-08-10 16:23:38', '2021-08-10 16:23:38', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1047, 'store.db.branchTable', 'SEATA_GROUP', 'branch_table', '54bcdac38cf62e103fe115bcf46a660c', '2021-08-10 16:23:40', '2021-08-10 16:23:40', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1048, 'store.db.queryLimit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2021-08-10 16:23:42', '2021-08-10 16:23:42', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1049, 'store.db.lockTable', 'SEATA_GROUP', 'lock_table', '55e0cae3b6dc6696b768db90098b8f2f', '2021-08-10 16:23:43', '2021-08-10 16:23:43', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1050, 'store.db.maxWait', 'SEATA_GROUP', '5000', 'a35fe7f7fe8217b4369a0af4244d1fca', '2021-08-10 16:23:45', '2021-08-10 16:23:45', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1051, 'store.redis.mode', 'SEATA_GROUP', 'single', 'dd5c07036f2975ff4bce568b6511d3bc', '2021-08-10 16:23:46', '2021-08-10 16:23:46', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1052, 'store.redis.single.host', 'SEATA_GROUP', '127.0.0.1', 'f528764d624db129b32c21fbca0cb8d6', '2021-08-10 16:23:48', '2021-08-10 16:23:48', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1053, 'store.redis.single.port', 'SEATA_GROUP', '6379', '92c3b916311a5517d9290576e3ea37ad', '2021-08-10 16:23:50', '2021-08-10 16:23:50', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1054, 'store.redis.maxConn', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2021-08-10 16:23:56', '2021-08-10 16:23:56', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1055, 'store.redis.minConn', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2021-08-10 16:23:57', '2021-08-10 16:23:57', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1056, 'store.redis.maxTotal', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2021-08-10 16:23:59', '2021-08-10 16:23:59', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1057, 'store.redis.database', 'SEATA_GROUP', '0', 'cfcd208495d565ef66e7dff9f98764da', '2021-08-10 16:24:00', '2021-08-10 16:24:00', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1058, 'store.redis.queryLimit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2021-08-10 16:24:03', '2021-08-10 16:24:03', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1059, 'server.recovery.committingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2021-08-10 16:24:06', '2021-08-10 16:24:06', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1060, 'server.recovery.asynCommittingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2021-08-10 16:24:09', '2021-08-10 16:24:09', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1061, 'server.recovery.rollbackingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2021-08-10 16:24:11', '2021-08-10 16:24:11', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1062, 'server.recovery.timeoutRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2021-08-10 16:24:14', '2021-08-10 16:24:14', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1063, 'server.maxCommitRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2021-08-10 16:24:16', '2021-08-10 16:24:16', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1064, 'server.maxRollbackRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2021-08-10 16:24:18', '2021-08-10 16:24:18', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1065, 'server.rollbackRetryTimeoutUnlockEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:24:20', '2021-08-10 16:24:20', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1066, 'server.distributedLockExpireTime', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2021-08-10 16:24:23', '2021-08-10 16:24:23', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1067, 'client.undo.dataValidation', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-08-10 16:24:25', '2021-08-10 16:24:25', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1068, 'client.undo.logSerialization', 'SEATA_GROUP', 'jackson', 'b41779690b83f182acc67d6388c7bac9', '2021-08-10 16:24:27', '2021-08-10 16:24:27', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1069, 'client.undo.onlyCareUpdateColumns', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-08-10 16:24:29', '2021-08-10 16:24:29', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1070, 'server.undo.logSaveDays', 'SEATA_GROUP', '7', '8f14e45fceea167a5a36dedd4bea2543', '2021-08-10 16:24:31', '2021-08-10 16:24:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1071, 'server.undo.logDeletePeriod', 'SEATA_GROUP', '86400000', 'f4c122804fe9076cb2710f55c3c6e346', '2021-08-10 16:24:33', '2021-08-10 16:24:33', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1072, 'client.undo.logTable', 'SEATA_GROUP', 'undo_log', '2842d229c24afe9e61437135e8306614', '2021-08-10 16:24:35', '2021-08-10 16:24:35', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1073, 'client.undo.compress.enable', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-08-10 16:24:37', '2021-08-10 16:24:37', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1074, 'client.undo.compress.type', 'SEATA_GROUP', 'zip', 'adcdbd79a8d84175c229b192aadc02f2', '2021-08-10 16:24:39', '2021-08-10 16:24:39', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1075, 'client.undo.compress.threshold', 'SEATA_GROUP', '64k', 'bd44a6458bdbff0b5cac721ba361f035', '2021-08-10 16:24:41', '2021-08-10 16:24:41', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1076, 'log.exceptionRate', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2021-08-10 16:24:43', '2021-08-10 16:24:43', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1077, 'transport.serialization', 'SEATA_GROUP', 'seata', 'b943081c423b9a5416a706524ee05d40', '2021-08-10 16:24:45', '2021-08-10 16:24:45', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1078, 'transport.compressor', 'SEATA_GROUP', 'none', '334c4a4c42fdb79d7ebc3e73b517e6f8', '2021-08-10 16:24:46', '2021-08-10 16:24:46', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1079, 'metrics.enabled', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-08-10 16:24:47', '2021-08-10 16:24:47', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1080, 'metrics.registryType', 'SEATA_GROUP', 'compact', '7cf74ca49c304df8150205fc915cd465', '2021-08-10 16:24:49', '2021-08-10 16:24:49', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1081, 'metrics.exporterList', 'SEATA_GROUP', 'prometheus', 'e4f00638b8a10e6994e67af2f832d51c', '2021-08-10 16:24:51', '2021-08-10 16:24:51', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1082, 'metrics.exporterPrometheusPort', 'SEATA_GROUP', '9898', '7b9dc501afe4ee11c56a4831e20cee71', '2021-08-10 16:24:53', '2021-08-10 16:24:53', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1098, 'sentinel-service-sentinel-flow', 'DEFAULT_GROUP', '[{\"app\":\"sentinel-service\",\"clusterConfig\":{\"fallbackToLocalWhenFail\":true,\"sampleCount\":10,\"strategy\":0,\"thresholdType\":0,\"windowIntervalMs\":1000},\"clusterMode\":false,\"controlBehavior\":0,\"count\":1.0,\"gmtCreate\":1637915244896,\"gmtModified\":1637915244896,\"grade\":1,\"id\":1,\"ip\":\"192.168.184.1\",\"limitApp\":\"default\",\"port\":8720,\"resource\":\"test-testA\",\"strategy\":0}]', '455e3edabf03c8e52a66d2d8c07e737b', '2021-11-26 08:27:25', '2021-11-26 08:27:25', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (1101, 'cloud-mall-order-sentinel-flow', 'DEFAULT_GROUP', '[{\"app\":\"cloud-mall-order\",\"clusterConfig\":{\"fallbackToLocalWhenFail\":true,\"sampleCount\":10,\"strategy\":0,\"thresholdType\":0,\"windowIntervalMs\":1000},\"clusterMode\":false,\"controlBehavior\":0,\"count\":30.0,\"gmtCreate\":1637915982495,\"gmtModified\":1637915982495,\"grade\":1,\"id\":16,\"ip\":\"192.168.184.1\",\"limitApp\":\"default\",\"port\":8726,\"resource\":\"orderBegin\",\"strategy\":0}]', 'b58c5250066c7bd734e0c775826198c2', '2021-11-26 08:39:42', '2021-11-26 08:39:42', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
