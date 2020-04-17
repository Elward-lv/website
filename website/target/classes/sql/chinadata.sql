/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80003
 Source Host           : localhost:3306
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 80003
 File Encoding         : 65001

 Date: 17/04/2020 09:38:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chinadata
-- ----------------------------
DROP TABLE IF EXISTS `chinadata`;
CREATE TABLE `chinadata`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provinceName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `currentConfirmedCount` int(255) NULL DEFAULT NULL,
  `confirmedCount` bigint(255) NULL DEFAULT NULL,
  `suspectedCount` int(255) NULL DEFAULT NULL,
  `curedCount` bigint(255) NULL DEFAULT NULL,
  `deadCount` int(255) NULL DEFAULT NULL,
  `statisticsData` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `updateTime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
