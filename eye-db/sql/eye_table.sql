/*
Navicat MySQL Data Transfer

Source Server         : 47.95.221.81
Source Server Version : 80021
Source Host           : 47.95.221.81:3306
Source Database       : eye

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2020-11-30 14:03:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for eye_accessory
-- ----------------------------
DROP TABLE IF EXISTS `eye_accessory`;
CREATE TABLE `eye_accessory` (
`id`  int NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`goods_id`  int NOT NULL COMMENT '商品ID' ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '附件名称' ,
`size`  bigint NOT NULL DEFAULT 0 COMMENT '附件大小' ,
`type`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件类型' ,
`key`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件云存储key' ,
`url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件云存储URL' ,
`accessory_link`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件百度网盘下载链接' ,
`accessory_code`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件百度网盘提取码' ,
`status`  int NULL DEFAULT 0 COMMENT '0是商品附件,1是文章附件' ,
`addTime`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`updateTime`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='商品附件表'
AUTO_INCREMENT=286

;

-- ----------------------------
-- Table structure for eye_ad
-- ----------------------------
DROP TABLE IF EXISTS `eye_ad`;
CREATE TABLE `eye_ad` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '广告标题' ,
`link`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '所广告的商品页面或者活动页面链接地址' ,
`url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '广告宣传图片' ,
`pc_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PC广告宣传图片' ,
`app_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '广告宣传图片' ,
`position`  tinyint NULL DEFAULT 1 COMMENT '广告位置：1则是首页' ,
`content`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '活动内容' ,
`start_time`  datetime NULL DEFAULT NULL COMMENT '广告开始时间' ,
`end_time`  datetime NULL DEFAULT NULL COMMENT '广告结束时间' ,
`enabled`  tinyint(1) NULL DEFAULT 0 COMMENT '是否启动' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `enabled` (`enabled`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='广告表'
AUTO_INCREMENT=7

;

-- ----------------------------
-- Table structure for eye_address
-- ----------------------------
DROP TABLE IF EXISTS `eye_address`;
CREATE TABLE `eye_address` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '收货人名称' ,
`user_id`  int NOT NULL COMMENT '用户id' ,
`store_sn`  int NOT NULL DEFAULT 0 COMMENT '门店号' ,
`province`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行政区域表的省ID' ,
`city`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行政区域表的市ID' ,
`county`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行政区域表的区县ID' ,
`address_detail`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '详细收货地址' ,
`area_code`  char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地区编码' ,
`postal_code`  char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮政编码' ,
`tel`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号码' ,
`is_default`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认地址' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `user_id` (`store_sn`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='收货地址表'
AUTO_INCREMENT=79

;

-- ----------------------------
-- Table structure for eye_admin
-- ----------------------------
DROP TABLE IF EXISTS `eye_admin`;
CREATE TABLE `eye_admin` (
`id`  int NOT NULL AUTO_INCREMENT ,
`username`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '管理员名称' ,
`password`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '管理员密码' ,
`role_ids`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '[]' COMMENT '角色列表' ,
`last_login_ip`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最近一次登录IP地址' ,
`last_login_time`  datetime NULL DEFAULT NULL COMMENT '最近一次登录时间' ,
`manager_mobile`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理者手机号' ,
`avatar`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '\'' COMMENT '头像图片' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='管理员表'
AUTO_INCREMENT=9

;

-- ----------------------------
-- Table structure for eye_aftersale
-- ----------------------------
DROP TABLE IF EXISTS `eye_aftersale`;
CREATE TABLE `eye_aftersale` (
`id`  int NOT NULL AUTO_INCREMENT ,
`aftersale_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '售后编号' ,
`order_id`  int NOT NULL COMMENT '订单ID' ,
`user_id`  int NOT NULL COMMENT '用户ID' ,
`type`  smallint NULL DEFAULT 0 COMMENT '售后类型，0是未收货退款，1是已收货（无需退货）退款，2用户退货退款' ,
`reason`  varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '退款原因' ,
`amount`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '退款金额' ,
`pictures`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '[]' COMMENT '退款凭证图片链接数组' ,
`comment`  varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '退款说明' ,
`status`  smallint NULL DEFAULT 0 COMMENT '售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消' ,
`handle_time`  datetime NULL DEFAULT NULL COMMENT '管理员操作时间' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '添加时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='售后表'
AUTO_INCREMENT=2

;

-- ----------------------------
-- Table structure for eye_article
-- ----------------------------
DROP TABLE IF EXISTS `eye_article`;
CREATE TABLE `eye_article` (
`id`  int NOT NULL AUTO_INCREMENT ,
`title`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '文章标题' ,
`label`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '标签名' ,
`category_id`  int NULL DEFAULT 0 COMMENT '文章所属类目ID, eye_category表' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章图片' ,
`keywords`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关键字' ,
`context`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文章详细介绍，是富文本格式' ,
`sort_order`  smallint NULL DEFAULT 100 COMMENT '排序字段' ,
`add_user`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人' ,
`is_search`  tinyint(1) NULL DEFAULT 1 COMMENT '是否被搜索' ,
`link`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转链接' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `sort_order` (`sort_order`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='文章表'
AUTO_INCREMENT=92

;

-- ----------------------------
-- Table structure for eye_article_attribute
-- ----------------------------
DROP TABLE IF EXISTS `eye_article_attribute`;
CREATE TABLE `eye_article_attribute` (
`id`  int NOT NULL AUTO_INCREMENT ,
`article_id`  int NOT NULL COMMENT '文章id' ,
`attribute`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性' ,
`value`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性值' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='文章属性表'
AUTO_INCREMENT=41

;

-- ----------------------------
-- Table structure for eye_article_category
-- ----------------------------
DROP TABLE IF EXISTS `eye_article_category`;
CREATE TABLE `eye_article_category` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '类目名称' ,
`keywords`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '类目关键字，以JSON数组格式' ,
`desc`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类目广告语介绍' ,
`pid`  int NOT NULL DEFAULT 0 COMMENT '父类目ID' ,
`icon_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类目图标' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类目图片' ,
`level`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'L1' ,
`in_link`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内部链接' ,
`out_link`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外部' ,
`link_value`  int NULL DEFAULT 0 COMMENT '0是内部链接,1是外部链接' ,
`sort_order`  tinyint NULL DEFAULT 50 COMMENT '排序' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `parent_id` (`pid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='文章分类表'
AUTO_INCREMENT=1036052

;

-- ----------------------------
-- Table structure for eye_brand
-- ----------------------------
DROP TABLE IF EXISTS `eye_brand`;
CREATE TABLE `eye_brand` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '品牌商名称' ,
`desc`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '品牌商简介' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '品牌商页的品牌商图片' ,
`sort_order`  tinyint NULL DEFAULT 50 ,
`floor_price`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '品牌商的商品低价，仅用于页面展示' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='品牌商表'
AUTO_INCREMENT=1046017

;

-- ----------------------------
-- Table structure for eye_brand_web
-- ----------------------------
DROP TABLE IF EXISTS `eye_brand_web`;
CREATE TABLE `eye_brand_web` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL COMMENT '品牌添加者id' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '品牌商页的品牌商图片' ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '品牌商名称' ,
`company`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '品牌商公司' ,
`brief`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品牌商简介' ,
`representative`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '法定代表人' ,
`birthplace`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '品牌发源地' ,
`product`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '主营产品' ,
`telephone`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '电话' ,
`introduce_image`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌介绍图片' ,
`detail`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '品牌商介绍，是富文本格式' ,
`brand_creation_time`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌创立时间' ,
`clicks`  bigint NULL DEFAULT 0 COMMENT '点击量' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='新品牌商表'
AUTO_INCREMENT=1046017

;

-- ----------------------------
-- Table structure for eye_brand_web_mer
-- ----------------------------
DROP TABLE IF EXISTS `eye_brand_web_mer`;
CREATE TABLE `eye_brand_web_mer` (
`id`  int NOT NULL AUTO_INCREMENT ,
`brand_id`  int NOT NULL COMMENT '品牌id' ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '招商品牌名' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '招商图片' ,
`company`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属公司' ,
`employees_number`  int NULL DEFAULT NULL COMMENT '员工人数' ,
`daily_output`  bigint NULL DEFAULT NULL COMMENT '日产量' ,
`inventory`  bigint NULL DEFAULT NULL COMMENT '库存' ,
`business`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属行业' ,
`brief`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '招商简介' ,
`data`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '招商资料' ,
`telephone`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='品牌招商'
AUTO_INCREMENT=3

;

-- ----------------------------
-- Table structure for eye_cart
-- ----------------------------
DROP TABLE IF EXISTS `eye_cart`;
CREATE TABLE `eye_cart` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NULL DEFAULT NULL COMMENT '用户表的用户ID' ,
`goods_id`  int NULL DEFAULT NULL COMMENT '商品表的商品ID' ,
`goods_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品编号' ,
`goods_name`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称' ,
`product_id`  int NULL DEFAULT NULL COMMENT '商品货品表的货品ID' ,
`price`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '商品货品的价格' ,
`number`  smallint NULL DEFAULT 0 COMMENT '商品货品的数量' ,
`specifications`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格值列表，采用JSON数组格式' ,
`checked`  tinyint(1) NULL DEFAULT 1 COMMENT '购物车中商品是否选择状态' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片或者商品货品图片' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='购物车商品表'
AUTO_INCREMENT=756

;

-- ----------------------------
-- Table structure for eye_category
-- ----------------------------
DROP TABLE IF EXISTS `eye_category`;
CREATE TABLE `eye_category` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '类目名称' ,
`keywords`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '类目关键字，以JSON数组格式' ,
`desc`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类目广告语介绍' ,
`pid`  int NOT NULL DEFAULT 0 COMMENT '父类目ID' ,
`icon_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类目图标' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类目图片' ,
`level`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'L1' ,
`sort_order`  tinyint NULL DEFAULT 50 COMMENT '排序' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `parent_id` (`pid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='类目表'
AUTO_INCREMENT=1036063

;

-- ----------------------------
-- Table structure for eye_collect
-- ----------------------------
DROP TABLE IF EXISTS `eye_collect`;
CREATE TABLE `eye_collect` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL DEFAULT 0 COMMENT '用户表的用户ID' ,
`value_id`  int NOT NULL DEFAULT 0 COMMENT '如果type=0，则是商品ID；如果type=1，则是专题ID' ,
`type`  tinyint NOT NULL DEFAULT 0 COMMENT '收藏类型，如果type=0，则是商品ID；如果type=1，则是专题ID' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `user_id` (`user_id`) USING BTREE ,
INDEX `goods_id` (`value_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='收藏表'
AUTO_INCREMENT=108

;

-- ----------------------------
-- Table structure for eye_comment
-- ----------------------------
DROP TABLE IF EXISTS `eye_comment`;
CREATE TABLE `eye_comment` (
`id`  int NOT NULL AUTO_INCREMENT ,
`value_id`  int NOT NULL DEFAULT 0 COMMENT '如果type=0，则是商品评论；如果是type=1，则是专题评论。' ,
`type`  tinyint NOT NULL DEFAULT 0 COMMENT '评论类型，如果type=0，则是商品评论；如果是type=1，则是专题评论；' ,
`content`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '评论内容' ,
`admin_content`  varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '管理员回复内容' ,
`user_id`  int NOT NULL DEFAULT 0 COMMENT '用户表的用户ID' ,
`has_picture`  tinyint(1) NULL DEFAULT 0 COMMENT '是否含有图片' ,
`pic_urls`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片地址列表，采用JSON数组格式' ,
`star`  smallint NULL DEFAULT 1 COMMENT '评分， 1-5' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `id_value` (`value_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='评论表'
AUTO_INCREMENT=1017

;

-- ----------------------------
-- Table structure for eye_commission
-- ----------------------------
DROP TABLE IF EXISTS `eye_commission`;
CREATE TABLE `eye_commission` (
`id`  int NOT NULL AUTO_INCREMENT ,
`order_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号' ,
`manager_mobile`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话' ,
`order_total_price`  decimal(20,2) NOT NULL COMMENT '订单总金额' ,
`commission_ratio`  decimal(10,2) NOT NULL COMMENT '佣金系数' ,
`order_commision`  decimal(10,2) NOT NULL COMMENT '佣金金额' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='订单佣金表'
AUTO_INCREMENT=2

;

-- ----------------------------
-- Table structure for eye_company
-- ----------------------------
DROP TABLE IF EXISTS `eye_company`;
CREATE TABLE `eye_company` (
`id`  int NOT NULL AUTO_INCREMENT ,
`role_ids`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色ID' ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '公司名称' ,
`company_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司地址' ,
`logo`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '\'' COMMENT '公司商标' ,
`slogan`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '公司标语' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='公司表'
AUTO_INCREMENT=21

;

-- ----------------------------
-- Table structure for eye_contact
-- ----------------------------
DROP TABLE IF EXISTS `eye_contact`;
CREATE TABLE `eye_contact` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '联系人名字' ,
`telephone`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话号码' ,
`email`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '邮箱' ,
`company`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '所属公司' ,
`message`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '意见' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='联系表'
AUTO_INCREMENT=4

;

-- ----------------------------
-- Table structure for eye_coupon
-- ----------------------------
DROP TABLE IF EXISTS `eye_coupon`;
CREATE TABLE `eye_coupon` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券名称' ,
`desc`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '优惠券介绍，通常是显示优惠券使用限制文字' ,
`tag`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '优惠券标签，例如新人专用' ,
`total`  int NOT NULL DEFAULT 0 COMMENT '优惠券数量，如果是0，则是无限量' ,
`discount`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '优惠金额，' ,
`min`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '最少消费金额才能使用优惠券。' ,
`limit`  smallint NULL DEFAULT 1 COMMENT '用户领券限制数量，如果是0，则是不限制；默认是1，限领一张.' ,
`type`  smallint NULL DEFAULT 0 COMMENT '优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换；' ,
`status`  smallint NULL DEFAULT 0 COMMENT '优惠券状态，如果是0则是正常可用；如果是1则是过期; 如果是2则是下架。' ,
`goods_type`  smallint NULL DEFAULT 0 COMMENT '商品限制类型，如果0则全商品，如果是1则是类目限制，如果是2则是商品限制。' ,
`goods_value`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '[]' COMMENT '商品限制值，goods_type如果是0则空集合，如果是1则是类目集合，如果是2则是商品集合。' ,
`code`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '优惠券兑换码' ,
`time_type`  smallint NULL DEFAULT 0 COMMENT '有效时间限制，如果是0，则基于领取时间的有效天数days；如果是1，则start_time和end_time是优惠券有效期；' ,
`days`  smallint NULL DEFAULT 0 COMMENT '基于领取时间的有效天数days。' ,
`start_time`  datetime NULL DEFAULT NULL COMMENT '使用券开始时间' ,
`end_time`  datetime NULL DEFAULT NULL COMMENT '使用券截至时间' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `code` (`code`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='优惠券信息及规则表'
AUTO_INCREMENT=9

;

-- ----------------------------
-- Table structure for eye_coupon_user
-- ----------------------------
DROP TABLE IF EXISTS `eye_coupon_user`;
CREATE TABLE `eye_coupon_user` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL COMMENT '用户ID' ,
`coupon_id`  int NOT NULL COMMENT '优惠券ID' ,
`status`  smallint NULL DEFAULT 0 COMMENT '使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架；' ,
`used_time`  datetime NULL DEFAULT NULL COMMENT '使用时间' ,
`start_time`  datetime NULL DEFAULT NULL COMMENT '有效期开始时间' ,
`end_time`  datetime NULL DEFAULT NULL COMMENT '有效期截至时间' ,
`order_id`  int NULL DEFAULT NULL COMMENT '订单ID' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='优惠券用户使用表'
AUTO_INCREMENT=121

;

-- ----------------------------
-- Table structure for eye_domain
-- ----------------------------
DROP TABLE IF EXISTS `eye_domain`;
CREATE TABLE `eye_domain` (
`id`  int NOT NULL AUTO_INCREMENT ,
`domain`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '域名' ,
`describe`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '域名信息描述' ,
`remark`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备份字段' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='域名管理表'
AUTO_INCREMENT=6

;

-- ----------------------------
-- Table structure for eye_feedback
-- ----------------------------
DROP TABLE IF EXISTS `eye_feedback`;
CREATE TABLE `eye_feedback` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL DEFAULT 0 COMMENT '用户表的用户ID' ,
`username`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名称' ,
`mobile`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号' ,
`feed_type`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '反馈类型' ,
`content`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '反馈内容' ,
`status`  int NOT NULL DEFAULT 0 COMMENT '状态' ,
`has_picture`  tinyint(1) NULL DEFAULT 0 COMMENT '是否含有图片' ,
`pic_urls`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片地址列表，采用JSON数组格式' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `id_value` (`status`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='意见反馈表'
AUTO_INCREMENT=8

;

-- ----------------------------
-- Table structure for eye_footprint
-- ----------------------------
DROP TABLE IF EXISTS `eye_footprint`;
CREATE TABLE `eye_footprint` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL DEFAULT 0 COMMENT '用户表的用户ID' ,
`goods_id`  int NOT NULL DEFAULT 0 COMMENT '浏览商品ID' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='用户浏览足迹表'
AUTO_INCREMENT=2522

;

-- ----------------------------
-- Table structure for eye_goods
-- ----------------------------
DROP TABLE IF EXISTS `eye_goods`;
CREATE TABLE `eye_goods` (
`id`  int NOT NULL AUTO_INCREMENT ,
`goods_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品编号' ,
`name`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品名称' ,
`category_id`  int NULL DEFAULT 0 COMMENT '商品所属类目ID' ,
`brand_id`  int NULL DEFAULT 0 ,
`gallery`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品宣传图片列表，采用JSON数组格式' ,
`keywords`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '商品关键字，采用逗号间隔' ,
`brief`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '商品简介' ,
`is_on_sale`  tinyint(1) NULL DEFAULT 1 COMMENT '是否上架' ,
`sort_order`  smallint NULL DEFAULT 100 ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品页面商品图片' ,
`share_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品分享海报' ,
`is_new`  tinyint(1) NULL DEFAULT 0 COMMENT '是否新品首发，如果设置则可以在新品首发页面展示' ,
`is_hot`  tinyint(1) NULL DEFAULT 0 COMMENT '是否人气推荐，如果设置则可以在人气推荐页面展示' ,
`is_kill`  tinyint(1) NULL DEFAULT 0 COMMENT '是否秒杀，如果设置则可以在秒杀活动页面展示' ,
`is_shown`  tinyint(1) NULL DEFAULT 0 COMMENT '是否在小程序展示，如果设置则可以在小程序页面展示' ,
`unit`  varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '’件‘' COMMENT '商品单位，例如件、盒' ,
`counter_price`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '专柜价格' ,
`retail_price`  decimal(10,2) NULL DEFAULT 100000.00 COMMENT '零售价格' ,
`commission_rate`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '佣金比例' ,
`accessory_key`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件key' ,
`accessory_link`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件下载链接' ,
`accessory_code`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件下载提取码' ,
`detail`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品详细介绍，是富文本格式' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `goods_sn` (`goods_sn`) USING BTREE ,
INDEX `cat_id` (`category_id`) USING BTREE ,
INDEX `brand_id` (`brand_id`) USING BTREE ,
INDEX `sort_order` (`sort_order`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='商品基本信息表'
AUTO_INCREMENT=1811356

;

-- ----------------------------
-- Table structure for eye_goods_attribute
-- ----------------------------
DROP TABLE IF EXISTS `eye_goods_attribute`;
CREATE TABLE `eye_goods_attribute` (
`id`  int NOT NULL AUTO_INCREMENT ,
`goods_id`  int NOT NULL DEFAULT 0 COMMENT '商品表的商品ID' ,
`attribute`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品参数名称' ,
`value`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品参数值' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `goods_id` (`goods_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='商品参数表'
AUTO_INCREMENT=1605

;

-- ----------------------------
-- Table structure for eye_goods_kill
-- ----------------------------
DROP TABLE IF EXISTS `eye_goods_kill`;
CREATE TABLE `eye_goods_kill` (
`id`  int NOT NULL AUTO_INCREMENT ,
`goods_id`  int NULL DEFAULT 0 COMMENT '秒杀商品的id' ,
`kill_price`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '秒杀商品价格' ,
`stock_count`  int NULL DEFAULT NULL COMMENT '秒杀商品库存数量' ,
`start_date`  datetime NULL DEFAULT NULL COMMENT '秒杀开始时间' ,
`end_date`  datetime NULL DEFAULT NULL COMMENT '秒杀结束时间' ,
`kill_date`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '秒杀时间' ,
`is_kill`  tinyint(1) NULL DEFAULT 0 COMMENT '是否参加商品秒杀，如果设置则可以在商品秒杀页面展示' ,
`kill_status`  smallint NULL DEFAULT 0 COMMENT '秒杀状态，0是未开始，1是开始中，2是已结束' ,
`now_time`  datetime NULL DEFAULT NULL COMMENT '延时相对现在时间' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='商品秒杀表'
AUTO_INCREMENT=659

;

-- ----------------------------
-- Table structure for eye_goods_product
-- ----------------------------
DROP TABLE IF EXISTS `eye_goods_product`;
CREATE TABLE `eye_goods_product` (
`id`  int NOT NULL AUTO_INCREMENT ,
`goods_id`  int NOT NULL DEFAULT 0 COMMENT '商品表的商品ID' ,
`specifications`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品规格值列表，采用JSON数组格式' ,
`price`  decimal(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品货品价格' ,
`number`  int NOT NULL DEFAULT 0 COMMENT '商品货品数量' ,
`url`  varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品货品图片' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `goods_id` (`goods_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='商品货品表'
AUTO_INCREMENT=848

;

-- ----------------------------
-- Table structure for eye_goods_specification
-- ----------------------------
DROP TABLE IF EXISTS `eye_goods_specification`;
CREATE TABLE `eye_goods_specification` (
`id`  int NOT NULL AUTO_INCREMENT ,
`goods_id`  int NOT NULL DEFAULT 0 COMMENT '商品表的商品ID' ,
`specification`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品规格名称' ,
`value`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品规格值' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品规格图片' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `goods_id` (`goods_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='商品规格表'
AUTO_INCREMENT=741

;

-- ----------------------------
-- Table structure for eye_groupon
-- ----------------------------
DROP TABLE IF EXISTS `eye_groupon`;
CREATE TABLE `eye_groupon` (
`id`  int NOT NULL AUTO_INCREMENT ,
`order_id`  int NOT NULL COMMENT '关联的订单ID' ,
`groupon_id`  int NULL DEFAULT 0 COMMENT '如果是开团用户，则groupon_id是0；如果是参团用户，则groupon_id是团购活动ID' ,
`rules_id`  int NOT NULL COMMENT '团购规则ID，关联eye_groupon_rules表ID字段' ,
`user_id`  int NOT NULL COMMENT '用户ID' ,
`share_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团购分享图片地址' ,
`creator_user_id`  int NOT NULL COMMENT '开团用户ID' ,
`creator_user_time`  datetime NULL DEFAULT NULL COMMENT '开团时间' ,
`status`  smallint NULL DEFAULT 0 COMMENT '团购活动状态，开团未支付则0，开团中则1，团购成功则2，开团失败则3' ,
`add_time`  datetime NOT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='团购活动表'
AUTO_INCREMENT=81

;

-- ----------------------------
-- Table structure for eye_groupon_cart
-- ----------------------------
DROP TABLE IF EXISTS `eye_groupon_cart`;
CREATE TABLE `eye_groupon_cart` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NULL DEFAULT NULL COMMENT '用户表的用户ID' ,
`goods_id`  int NULL DEFAULT NULL COMMENT '商品表的商品ID' ,
`goods_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品编号' ,
`goods_name`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称' ,
`product_id`  int NULL DEFAULT NULL COMMENT '商品货品表的货品ID' ,
`price`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '商品货品的价格' ,
`number`  smallint NULL DEFAULT 0 COMMENT '商品货品的数量' ,
`specifications`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格值列表，采用JSON数组格式' ,
`checked`  tinyint(1) NULL DEFAULT 1 COMMENT '购物车中商品是否选择状态' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片或者商品货品图片' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='购物车商品表'
AUTO_INCREMENT=399

;

-- ----------------------------
-- Table structure for eye_groupon_rules
-- ----------------------------
DROP TABLE IF EXISTS `eye_groupon_rules`;
CREATE TABLE `eye_groupon_rules` (
`id`  int NOT NULL AUTO_INCREMENT ,
`goods_id`  int NOT NULL COMMENT '商品表的商品ID' ,
`goods_name`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片或者商品货品图片' ,
`discount`  decimal(63,0) NOT NULL COMMENT '优惠金额' ,
`discount_member`  int NOT NULL COMMENT '达到优惠条件的人数' ,
`expire_time`  datetime NULL DEFAULT NULL COMMENT '团购过期时间' ,
`status`  smallint NULL DEFAULT 0 COMMENT '团购规则状态，正常上线则0，到期自动下线则1，管理手动下线则2' ,
`add_time`  datetime NOT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `goods_id` (`goods_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='团购规则表'
AUTO_INCREMENT=10

;

-- ----------------------------
-- Table structure for eye_integral
-- ----------------------------
DROP TABLE IF EXISTS `eye_integral`;
CREATE TABLE `eye_integral` (
`id`  int NOT NULL AUTO_INCREMENT COMMENT '会员id' ,
`user_id`  int NOT NULL COMMENT '用户id' ,
`integration`  int NULL DEFAULT 0 COMMENT '会员积分' ,
`status`  smallint NULL DEFAULT 0 COMMENT '积分状态,0可用,1不可用,如果用户会员到期,则积分不可用' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='积分表'
AUTO_INCREMENT=20

;

-- ----------------------------
-- Table structure for eye_integral_goods
-- ----------------------------
DROP TABLE IF EXISTS `eye_integral_goods`;
CREATE TABLE `eye_integral_goods` (
`id`  int NOT NULL AUTO_INCREMENT ,
`goods_id`  int NOT NULL COMMENT '商品表的商品id' ,
`goods_name`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片或商品货品图片' ,
`brief`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品简介' ,
`integration`  int NOT NULL COMMENT '商品积分' ,
`expire_time`  datetime NULL DEFAULT NULL COMMENT '积分商品过期时间' ,
`is_shown`  tinyint(1) NULL DEFAULT 0 COMMENT '商品是否小程序展示' ,
`status`  smallint NULL DEFAULT 0 COMMENT '积分商品,0正常上线,1到期自动下线,2管理员手动下线' ,
`add_time`  datetime NOT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='积分商品表'
AUTO_INCREMENT=26

;

-- ----------------------------
-- Table structure for eye_issue
-- ----------------------------
DROP TABLE IF EXISTS `eye_issue`;
CREATE TABLE `eye_issue` (
`id`  int NOT NULL AUTO_INCREMENT ,
`question`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题标题' ,
`answer`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题答案' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='常见问题表'
AUTO_INCREMENT=5

;

-- ----------------------------
-- Table structure for eye_keyword
-- ----------------------------
DROP TABLE IF EXISTS `eye_keyword`;
CREATE TABLE `eye_keyword` (
`id`  int NOT NULL AUTO_INCREMENT ,
`keyword`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '关键字' ,
`url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '关键字的跳转链接' ,
`is_hot`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否是热门关键字' ,
`is_default`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否是默认关键字' ,
`sort_order`  int NOT NULL DEFAULT 100 COMMENT '排序' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='关键字表'
AUTO_INCREMENT=8

;

-- ----------------------------
-- Table structure for eye_log
-- ----------------------------
DROP TABLE IF EXISTS `eye_log`;
CREATE TABLE `eye_log` (
`id`  int NOT NULL AUTO_INCREMENT ,
`admin`  varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员' ,
`ip`  varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员地址' ,
`type`  int NULL DEFAULT NULL COMMENT '操作分类' ,
`action`  varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作动作' ,
`status`  tinyint(1) NULL DEFAULT NULL COMMENT '操作状态' ,
`result`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作结果，或者成功消息，或者失败消息' ,
`comment`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '补充信息' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='操作日志表'
AUTO_INCREMENT=1462

;

-- ----------------------------
-- Table structure for eye_member_goods
-- ----------------------------
DROP TABLE IF EXISTS `eye_member_goods`;
CREATE TABLE `eye_member_goods` (
`id`  int NOT NULL AUTO_INCREMENT ,
`goods_id`  int NOT NULL COMMENT '商品表的商品ID' ,
`goods_name`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片或者商品货品图片' ,
`retail_price`  decimal(10,2) NULL DEFAULT NULL COMMENT '原价' ,
`discount`  int NOT NULL COMMENT '优惠金额' ,
`discount_price`  decimal(10,2) NULL DEFAULT NULL ,
`expire_time`  datetime NULL DEFAULT NULL COMMENT '到期时间' ,
`is_shown`  tinyint(1) NULL DEFAULT 0 COMMENT '商品是否小程序展示' ,
`status`  smallint NULL DEFAULT 0 COMMENT '会员商品状态,0正常上线,1到期自动下线,2管理手动下线' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='会员商品表'
AUTO_INCREMENT=35

;

-- ----------------------------
-- Table structure for eye_member_order
-- ----------------------------
DROP TABLE IF EXISTS `eye_member_order`;
CREATE TABLE `eye_member_order` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL COMMENT '用户id' ,
`package_id`  int NOT NULL COMMENT '会员套餐id' ,
`order_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '会员订单编号' ,
`consignee`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名' ,
`price`  decimal(10,2) NULL DEFAULT NULL COMMENT '价格' ,
`status`  smallint NULL DEFAULT NULL COMMENT '订单状态' ,
`mobile`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户手机号' ,
`pay_id`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信付款编号' ,
`pay_time`  datetime NULL DEFAULT NULL COMMENT '微信付款时间' ,
`end_time`  datetime NULL DEFAULT NULL COMMENT '订单关闭时间' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '添加时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='会员订单'
AUTO_INCREMENT=398

;

-- ----------------------------
-- Table structure for eye_member_package
-- ----------------------------
DROP TABLE IF EXISTS `eye_member_package`;
CREATE TABLE `eye_member_package` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '会员套餐名称' ,
`price`  decimal(10,2) NULL DEFAULT NULL COMMENT '套餐价格' ,
`months`  int NULL DEFAULT NULL COMMENT '基于购买时间的有效月数' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='会员套餐表'
AUTO_INCREMENT=18

;

-- ----------------------------
-- Table structure for eye_member_user
-- ----------------------------
DROP TABLE IF EXISTS `eye_member_user`;
CREATE TABLE `eye_member_user` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL COMMENT '用户id' ,
`status`  smallint NOT NULL DEFAULT 0 COMMENT '用户会员状态,0现在是会员,1不是会员' ,
`start_time`  datetime NULL DEFAULT NULL COMMENT '会员套餐开始时间' ,
`end_time`  datetime NULL DEFAULT NULL COMMENT '会员套餐截止时间' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deteled`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='会员套餐用户使用表'
AUTO_INCREMENT=17

;

-- ----------------------------
-- Table structure for eye_notice
-- ----------------------------
DROP TABLE IF EXISTS `eye_notice`;
CREATE TABLE `eye_notice` (
`id`  int NOT NULL AUTO_INCREMENT ,
`title`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知标题' ,
`content`  varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知内容' ,
`admin_id`  int NULL DEFAULT 0 COMMENT '创建通知的管理员ID，如果是系统内置通知则是0.' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='通知表'
AUTO_INCREMENT=4

;

-- ----------------------------
-- Table structure for eye_notice_admin
-- ----------------------------
DROP TABLE IF EXISTS `eye_notice_admin`;
CREATE TABLE `eye_notice_admin` (
`id`  int NOT NULL AUTO_INCREMENT ,
`notice_id`  int NULL DEFAULT NULL COMMENT '通知ID' ,
`notice_title`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知标题' ,
`admin_id`  int NULL DEFAULT NULL COMMENT '接收通知的管理员ID' ,
`read_time`  datetime NULL DEFAULT NULL COMMENT '阅读时间，如果是NULL则是未读状态' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='通知管理员表'
AUTO_INCREMENT=11

;

-- ----------------------------
-- Table structure for eye_order
-- ----------------------------
DROP TABLE IF EXISTS `eye_order`;
CREATE TABLE `eye_order` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL COMMENT '用户表的用户ID' ,
`order_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号' ,
`commander_id`  int NULL DEFAULT NULL COMMENT '团长ID' ,
`order_status`  smallint NOT NULL COMMENT '订单状态' ,
`aftersale_status`  smallint NULL DEFAULT 0 COMMENT '售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消' ,
`consignee`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人名称' ,
`mobile`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人手机号' ,
`store_sn`  int NOT NULL DEFAULT 0 COMMENT '绛剧害闂ㄥ簵鍙?' ,
`address`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货具体地址' ,
`message`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户订单留言' ,
`goods_price`  decimal(10,2) NOT NULL COMMENT '商品总费用' ,
`freight_price`  decimal(10,2) NOT NULL COMMENT '配送费用' ,
`coupon_price`  decimal(10,2) NOT NULL COMMENT '优惠券减免' ,
`integral_price`  decimal(10,2) NOT NULL COMMENT '用户积分减免' ,
`groupon_price`  decimal(10,2) NOT NULL COMMENT '团购优惠价减免' ,
`order_price`  decimal(10,2) NOT NULL COMMENT '订单费用， = goods_price + freight_price - coupon_price' ,
`actual_price`  decimal(10,2) NOT NULL COMMENT '实付费用， = order_price - integral_price' ,
`pay_id`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信付款编号' ,
`pay_time`  datetime NULL DEFAULT NULL COMMENT '微信付款时间' ,
`ship_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货编号' ,
`ship_channel`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货快递公司' ,
`ship_time`  datetime NULL DEFAULT NULL COMMENT '发货开始时间' ,
`refund_amount`  decimal(10,2) NULL DEFAULT NULL COMMENT '实际退款金额，（有可能退款金额小于实际支付金额）' ,
`refund_type`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款方式' ,
`refund_content`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款备注' ,
`refund_time`  datetime NULL DEFAULT NULL COMMENT '退款时间' ,
`confirm_time`  datetime NULL DEFAULT NULL COMMENT '用户确认收货时间' ,
`comments`  smallint NULL DEFAULT 0 COMMENT '待评价订单商品数量' ,
`end_time`  datetime NULL DEFAULT NULL COMMENT '订单关闭时间' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
COMMENT='订单表'
AUTO_INCREMENT=1642

;

-- ----------------------------
-- Table structure for eye_order_goods
-- ----------------------------
DROP TABLE IF EXISTS `eye_order_goods`;
CREATE TABLE `eye_order_goods` (
`id`  int NOT NULL AUTO_INCREMENT ,
`order_id`  int NOT NULL DEFAULT 0 COMMENT '订单表的订单ID' ,
`goods_id`  int NOT NULL DEFAULT 0 COMMENT '商品表的商品ID' ,
`goods_name`  varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品名称' ,
`goods_sn`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品编号' ,
`product_id`  int NOT NULL DEFAULT 0 COMMENT '商品货品表的货品ID' ,
`number`  smallint NOT NULL DEFAULT 0 COMMENT '商品货品的购买数量' ,
`price`  decimal(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品货品的售价' ,
`specifications`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品货品的规格列表' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品货品图片或者商品图片' ,
`comment`  int NULL DEFAULT 0 COMMENT '订单商品评论，如果是-1，则超期不能评价；如果是0，则可以评价；如果其他值，则是comment表里面的评论ID。' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `order_id` (`order_id`) USING BTREE ,
INDEX `goods_id` (`goods_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='订单商品表'
AUTO_INCREMENT=1495

;

-- ----------------------------
-- Table structure for eye_permission
-- ----------------------------
DROP TABLE IF EXISTS `eye_permission`;
CREATE TABLE `eye_permission` (
`id`  int NOT NULL AUTO_INCREMENT ,
`role_id`  int NULL DEFAULT NULL COMMENT '角色ID' ,
`permission`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='权限表'
AUTO_INCREMENT=1846

;

-- ----------------------------
-- Table structure for eye_region
-- ----------------------------
DROP TABLE IF EXISTS `eye_region`;
CREATE TABLE `eye_region` (
`id`  int NOT NULL AUTO_INCREMENT ,
`pid`  int NOT NULL DEFAULT 0 COMMENT '行政区域父ID，例如区县的pid指向市，市的pid指向省，省的pid则是0' ,
`name`  varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '行政区域名称' ,
`type`  tinyint NOT NULL DEFAULT 0 COMMENT '行政区域类型，如如1则是省， 如果是2则是市，如果是3则是区县' ,
`code`  int NOT NULL DEFAULT 0 COMMENT '行政区域编码' ,
PRIMARY KEY (`id`),
INDEX `parent_id` (`pid`) USING BTREE ,
INDEX `region_type` (`type`) USING BTREE ,
INDEX `agency_id` (`code`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='行政区域表'
AUTO_INCREMENT=3232

;

-- ----------------------------
-- Table structure for eye_role
-- ----------------------------
DROP TABLE IF EXISTS `eye_role`;
CREATE TABLE `eye_role` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称' ,
`desc`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述' ,
`enabled`  tinyint(1) NULL DEFAULT 1 COMMENT '是否启用' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `name_UNIQUE` (`name`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='角色表'
AUTO_INCREMENT=7

;

-- ----------------------------
-- Table structure for eye_search_history
-- ----------------------------
DROP TABLE IF EXISTS `eye_search_history`;
CREATE TABLE `eye_search_history` (
`id`  int NOT NULL AUTO_INCREMENT ,
`user_id`  int NOT NULL COMMENT '用户表的用户ID' ,
`keyword`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '搜索关键字' ,
`from`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '搜索来源，如pc、wx、app' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='搜索历史表'
AUTO_INCREMENT=18

;

-- ----------------------------
-- Table structure for eye_sign_store
-- ----------------------------
DROP TABLE IF EXISTS `eye_sign_store`;
CREATE TABLE `eye_sign_store` (
`id`  int NOT NULL AUTO_INCREMENT ,
`manager_id`  int NULL DEFAULT NULL COMMENT '团长id' ,
`name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '门店名称' ,
`store_sn`  int NOT NULL COMMENT '门店号' ,
`store_mobile`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '门店座机' ,
`manager`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '门店管理者' ,
`manager_mobile`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '门店管理者手机' ,
`province`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省级id' ,
`city`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '市级id' ,
`county`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区级id' ,
`address_detail`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址' ,
`appid`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '门店商户号' ,
`store_image`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '门店图片' ,
`add_time`  datetime NOT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`commision_rate`  decimal(20,2) NULL DEFAULT NULL ,
`deleted`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='商城签约门店表'
AUTO_INCREMENT=60

;

-- ----------------------------
-- Table structure for eye_storage
-- ----------------------------
DROP TABLE IF EXISTS `eye_storage`;
CREATE TABLE `eye_storage` (
`id`  int NOT NULL AUTO_INCREMENT ,
`key`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件的唯一索引' ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名' ,
`type`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件类型' ,
`size`  int NOT NULL COMMENT '文件大小' ,
`url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件访问链接' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `key` (`key`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='文件存储表'
AUTO_INCREMENT=3537

;

-- ----------------------------
-- Table structure for eye_system
-- ----------------------------
DROP TABLE IF EXISTS `eye_system`;
CREATE TABLE `eye_system` (
`id`  int NOT NULL AUTO_INCREMENT ,
`key_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '系统配置名' ,
`key_value`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '系统配置值' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='系统配置表'
AUTO_INCREMENT=59

;

-- ----------------------------
-- Table structure for eye_tool_account
-- ----------------------------
DROP TABLE IF EXISTS `eye_tool_account`;
CREATE TABLE `eye_tool_account` (
`id`  int NOT NULL AUTO_INCREMENT ,
`account_name`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号名称' ,
`account_password`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号密码' ,
`binding_mobile`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定手机' ,
`verification_code`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '验证码' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '添加时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '是否删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='附带工具账号表'
AUTO_INCREMENT=40

;

-- ----------------------------
-- Table structure for eye_tool_admin
-- ----------------------------
DROP TABLE IF EXISTS `eye_tool_admin`;
CREATE TABLE `eye_tool_admin` (
`id`  int NOT NULL AUTO_INCREMENT ,
`admin_name`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员账号' ,
`admin_mobile`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员手机' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '添加时间' ,
`uodate_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '是否删除' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=36

;

-- ----------------------------
-- Table structure for eye_topic
-- ----------------------------
DROP TABLE IF EXISTS `eye_topic`;
CREATE TABLE `eye_topic` (
`id`  int NOT NULL AUTO_INCREMENT ,
`topic_category_ids`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章分类ids' ,
`title`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '\'' COMMENT '专题标题' ,
`subtitle`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '\'' COMMENT '专题子标题' ,
`content`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '专题内容，富文本格式' ,
`price`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '专题相关商品最低价' ,
`read_count`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1k' COMMENT '专题阅读量' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '专题图片' ,
`sort_order`  int NULL DEFAULT 100 COMMENT '排序' ,
`goods`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '专题相关商品，采用JSON数组格式' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
INDEX `topic_id` (`id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='专题表'
AUTO_INCREMENT=324

;

-- ----------------------------
-- Table structure for eye_topic_category
-- ----------------------------
DROP TABLE IF EXISTS `eye_topic_category`;
CREATE TABLE `eye_topic_category` (
`id`  int NOT NULL AUTO_INCREMENT ,
`name`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文章分类名称' ,
`keywords`  varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL ,
`desc`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL ,
`pid`  int NOT NULL ,
`icon_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '文章类目图标' ,
`pic_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文章分类图片' ,
`level`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL ,
`sort_order`  tinyint NULL DEFAULT NULL COMMENT '排序号' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除,0为启动，1为删除' ,
PRIMARY KEY (`id`),
INDEX `parent_id` (`pid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='主题文章分类表'
AUTO_INCREMENT=20

;

-- ----------------------------
-- Table structure for eye_user
-- ----------------------------
DROP TABLE IF EXISTS `eye_user`;
CREATE TABLE `eye_user` (
`id`  int NOT NULL AUTO_INCREMENT ,
`username`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称' ,
`password`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户密码' ,
`gender`  tinyint NOT NULL DEFAULT 0 COMMENT '性别：0 未知， 1男， 1 女' ,
`birthday`  date NULL DEFAULT NULL COMMENT '生日' ,
`last_login_time`  datetime NULL DEFAULT NULL COMMENT '最近一次登录时间' ,
`last_login_ip`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最近一次登录IP地址' ,
`user_level`  tinyint NULL DEFAULT 0 COMMENT '0 普通用户，1 VIP用户，2 高级VIP用户' ,
`nickname`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户昵称或网络名称' ,
`mobile`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户手机号码' ,
`avatar`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户头像图片' ,
`weixin_openid`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '微信登录openid' ,
`app_openid`  varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'APP登录openid' ,
`union_id`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信开放平台绑定小程序微信用户唯一标识' ,
`session_key`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '微信登录会话KEY' ,
`status`  tinyint NOT NULL DEFAULT 0 COMMENT '0 可用, 1 禁用, 2 注销' ,
`add_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`deleted`  tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `user_name` (`username`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='用户表'
AUTO_INCREMENT=90

;

-- ----------------------------
-- Auto increment value for eye_accessory
-- ----------------------------
ALTER TABLE `eye_accessory` AUTO_INCREMENT=286;

-- ----------------------------
-- Auto increment value for eye_ad
-- ----------------------------
ALTER TABLE `eye_ad` AUTO_INCREMENT=7;

-- ----------------------------
-- Auto increment value for eye_address
-- ----------------------------
ALTER TABLE `eye_address` AUTO_INCREMENT=79;

-- ----------------------------
-- Auto increment value for eye_admin
-- ----------------------------
ALTER TABLE `eye_admin` AUTO_INCREMENT=9;

-- ----------------------------
-- Auto increment value for eye_aftersale
-- ----------------------------
ALTER TABLE `eye_aftersale` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for eye_article
-- ----------------------------
ALTER TABLE `eye_article` AUTO_INCREMENT=92;

-- ----------------------------
-- Auto increment value for eye_article_attribute
-- ----------------------------
ALTER TABLE `eye_article_attribute` AUTO_INCREMENT=41;

-- ----------------------------
-- Auto increment value for eye_article_category
-- ----------------------------
ALTER TABLE `eye_article_category` AUTO_INCREMENT=1036052;

-- ----------------------------
-- Auto increment value for eye_brand
-- ----------------------------
ALTER TABLE `eye_brand` AUTO_INCREMENT=1046017;

-- ----------------------------
-- Auto increment value for eye_brand_web
-- ----------------------------
ALTER TABLE `eye_brand_web` AUTO_INCREMENT=1046017;

-- ----------------------------
-- Auto increment value for eye_brand_web_mer
-- ----------------------------
ALTER TABLE `eye_brand_web_mer` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for eye_cart
-- ----------------------------
ALTER TABLE `eye_cart` AUTO_INCREMENT=756;

-- ----------------------------
-- Auto increment value for eye_category
-- ----------------------------
ALTER TABLE `eye_category` AUTO_INCREMENT=1036063;

-- ----------------------------
-- Auto increment value for eye_collect
-- ----------------------------
ALTER TABLE `eye_collect` AUTO_INCREMENT=108;

-- ----------------------------
-- Auto increment value for eye_comment
-- ----------------------------
ALTER TABLE `eye_comment` AUTO_INCREMENT=1017;

-- ----------------------------
-- Auto increment value for eye_commission
-- ----------------------------
ALTER TABLE `eye_commission` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for eye_company
-- ----------------------------
ALTER TABLE `eye_company` AUTO_INCREMENT=21;

-- ----------------------------
-- Auto increment value for eye_contact
-- ----------------------------
ALTER TABLE `eye_contact` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for eye_coupon
-- ----------------------------
ALTER TABLE `eye_coupon` AUTO_INCREMENT=9;

-- ----------------------------
-- Auto increment value for eye_coupon_user
-- ----------------------------
ALTER TABLE `eye_coupon_user` AUTO_INCREMENT=121;

-- ----------------------------
-- Auto increment value for eye_domain
-- ----------------------------
ALTER TABLE `eye_domain` AUTO_INCREMENT=6;

-- ----------------------------
-- Auto increment value for eye_feedback
-- ----------------------------
ALTER TABLE `eye_feedback` AUTO_INCREMENT=8;

-- ----------------------------
-- Auto increment value for eye_footprint
-- ----------------------------
ALTER TABLE `eye_footprint` AUTO_INCREMENT=2522;

-- ----------------------------
-- Auto increment value for eye_goods
-- ----------------------------
ALTER TABLE `eye_goods` AUTO_INCREMENT=1811356;

-- ----------------------------
-- Auto increment value for eye_goods_attribute
-- ----------------------------
ALTER TABLE `eye_goods_attribute` AUTO_INCREMENT=1605;

-- ----------------------------
-- Auto increment value for eye_goods_kill
-- ----------------------------
ALTER TABLE `eye_goods_kill` AUTO_INCREMENT=659;

-- ----------------------------
-- Auto increment value for eye_goods_product
-- ----------------------------
ALTER TABLE `eye_goods_product` AUTO_INCREMENT=848;

-- ----------------------------
-- Auto increment value for eye_goods_specification
-- ----------------------------
ALTER TABLE `eye_goods_specification` AUTO_INCREMENT=741;

-- ----------------------------
-- Auto increment value for eye_groupon
-- ----------------------------
ALTER TABLE `eye_groupon` AUTO_INCREMENT=81;

-- ----------------------------
-- Auto increment value for eye_groupon_cart
-- ----------------------------
ALTER TABLE `eye_groupon_cart` AUTO_INCREMENT=399;

-- ----------------------------
-- Auto increment value for eye_groupon_rules
-- ----------------------------
ALTER TABLE `eye_groupon_rules` AUTO_INCREMENT=10;

-- ----------------------------
-- Auto increment value for eye_integral
-- ----------------------------
ALTER TABLE `eye_integral` AUTO_INCREMENT=20;

-- ----------------------------
-- Auto increment value for eye_integral_goods
-- ----------------------------
ALTER TABLE `eye_integral_goods` AUTO_INCREMENT=26;

-- ----------------------------
-- Auto increment value for eye_issue
-- ----------------------------
ALTER TABLE `eye_issue` AUTO_INCREMENT=5;

-- ----------------------------
-- Auto increment value for eye_keyword
-- ----------------------------
ALTER TABLE `eye_keyword` AUTO_INCREMENT=8;

-- ----------------------------
-- Auto increment value for eye_log
-- ----------------------------
ALTER TABLE `eye_log` AUTO_INCREMENT=1462;

-- ----------------------------
-- Auto increment value for eye_member_goods
-- ----------------------------
ALTER TABLE `eye_member_goods` AUTO_INCREMENT=35;

-- ----------------------------
-- Auto increment value for eye_member_order
-- ----------------------------
ALTER TABLE `eye_member_order` AUTO_INCREMENT=398;

-- ----------------------------
-- Auto increment value for eye_member_package
-- ----------------------------
ALTER TABLE `eye_member_package` AUTO_INCREMENT=18;

-- ----------------------------
-- Auto increment value for eye_member_user
-- ----------------------------
ALTER TABLE `eye_member_user` AUTO_INCREMENT=17;

-- ----------------------------
-- Auto increment value for eye_notice
-- ----------------------------
ALTER TABLE `eye_notice` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for eye_notice_admin
-- ----------------------------
ALTER TABLE `eye_notice_admin` AUTO_INCREMENT=11;

-- ----------------------------
-- Auto increment value for eye_order
-- ----------------------------
ALTER TABLE `eye_order` AUTO_INCREMENT=1642;

-- ----------------------------
-- Auto increment value for eye_order_goods
-- ----------------------------
ALTER TABLE `eye_order_goods` AUTO_INCREMENT=1495;

-- ----------------------------
-- Auto increment value for eye_permission
-- ----------------------------
ALTER TABLE `eye_permission` AUTO_INCREMENT=1846;

-- ----------------------------
-- Auto increment value for eye_region
-- ----------------------------
ALTER TABLE `eye_region` AUTO_INCREMENT=3232;

-- ----------------------------
-- Auto increment value for eye_role
-- ----------------------------
ALTER TABLE `eye_role` AUTO_INCREMENT=7;

-- ----------------------------
-- Auto increment value for eye_search_history
-- ----------------------------
ALTER TABLE `eye_search_history` AUTO_INCREMENT=18;

-- ----------------------------
-- Auto increment value for eye_sign_store
-- ----------------------------
ALTER TABLE `eye_sign_store` AUTO_INCREMENT=60;

-- ----------------------------
-- Auto increment value for eye_storage
-- ----------------------------
ALTER TABLE `eye_storage` AUTO_INCREMENT=3537;

-- ----------------------------
-- Auto increment value for eye_system
-- ----------------------------
ALTER TABLE `eye_system` AUTO_INCREMENT=59;

-- ----------------------------
-- Auto increment value for eye_tool_account
-- ----------------------------
ALTER TABLE `eye_tool_account` AUTO_INCREMENT=40;

-- ----------------------------
-- Auto increment value for eye_tool_admin
-- ----------------------------
ALTER TABLE `eye_tool_admin` AUTO_INCREMENT=36;

-- ----------------------------
-- Auto increment value for eye_topic
-- ----------------------------
ALTER TABLE `eye_topic` AUTO_INCREMENT=324;

-- ----------------------------
-- Auto increment value for eye_topic_category
-- ----------------------------
ALTER TABLE `eye_topic_category` AUTO_INCREMENT=20;

-- ----------------------------
-- Auto increment value for eye_user
-- ----------------------------
ALTER TABLE `eye_user` AUTO_INCREMENT=90;
