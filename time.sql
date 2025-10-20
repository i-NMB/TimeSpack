SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL,
  `cover_img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NULL DEFAULT NULL,
  `category_id` int(0) NOT NULL,
  `create_user` int(0) NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `update_time` datetime(6) NULL DEFAULT NULL,
  `auditing_state` tinyint(1) NULL DEFAULT NULL,
  `likes` int(0) NULL DEFAULT NULL,
  `view` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  INDEX `create_user2`(`create_user`) USING BTREE,
  CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `create_user2` FOREIGN KEY (`create_user`) REFERENCES `user` (`create_user`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for attention
-- ----------------------------
DROP TABLE IF EXISTS `attention`;
CREATE TABLE `attention`  (
  `active_user` int(0) NULL DEFAULT NULL,
  `passive` int(0) NULL DEFAULT NULL,
  `operation_time` datetime(6) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` int(0) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL,
  `category_alias` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL,
  `create_user` int(0) NOT NULL,
  `create_time` date NOT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `create_user`(`create_user`) USING BTREE,
  CONSTRAINT `create_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`create_user`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `commentator_id` int(0) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL,
  `state` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL,
  `article_id` int(0) NOT NULL,
  `create_user` int(0) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `auditing_state` tinyint(1) NOT NULL,
  `likes` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`commentator_id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE,
  INDEX `create_user3`(`create_user`) USING BTREE,
  CONSTRAINT `article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `create_user3` FOREIGN KEY (`create_user`) REFERENCES `user` (`create_user`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 207 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `create_user` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL COMMENT '用户名(用于登陆)',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL COMMENT '用户密码（加密）',
  `nickname` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL COMMENT '用户名称用于展示',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL COMMENT '手机号',
  `email` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL COMMENT '用户邮箱',
  `user_pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NULL DEFAULT NULL COMMENT '用户头像',
  `create_time` datetime(6) NOT NULL COMMENT '创建时间',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `identity` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_croatian_ci NOT NULL COMMENT '用户身份',
  PRIMARY KEY (`create_user`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_croatian_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
