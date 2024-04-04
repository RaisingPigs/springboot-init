-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: boot_init
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `boot_init`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `boot_init` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `boot_init`;

--
-- Table structure for table `sys_login_log`
--

DROP TABLE IF EXISTS `sys_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `login_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '登录方式, 0-默认登录, 1-gitee登录',
  `login_ip` varchar(128) NOT NULL DEFAULT '' COMMENT '登录IP地址',
  `browser` varchar(50) NOT NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) NOT NULL DEFAULT '' COMMENT '操作系统',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '登录状态（1成功 0失败）',
  `msg` varchar(255) NOT NULL DEFAULT '' COMMENT '提示消息',
  `creator_id` bigint(20) unsigned NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint(20) unsigned NOT NULL COMMENT '修改人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除: 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login_log`
--

LOCK TABLES `sys_login_log` WRITE;
/*!40000 ALTER TABLE `sys_login_log` DISABLE KEYS */;
INSERT INTO `sys_login_log` VALUES (1,1,'admin',0,'0:0:0:0:0:0:0:1','MSEdge','Windows 10 or Windows Server 2016',1,'success',1,'2024-04-04 20:33:19',1,'2024-04-04 20:33:19',0),(2,1,'admin',0,'0:0:0:0:0:0:0:1','MSEdge','Windows 10 or Windows Server 2016',1,'success',1,'2024-04-04 20:34:20',1,'2024-04-04 20:34:20',0),(3,1,'admin',0,'0:0:0:0:0:0:0:1','MSEdge','Windows 10 or Windows Server 2016',1,'success',1,'2024-04-04 20:36:21',1,'2024-04-04 20:36:21',0),(4,1,'admin',0,'0:0:0:0:0:0:0:1','MSEdge','Windows 10 or Windows Server 2016',1,'success',1,'2024-04-04 20:36:25',1,'2024-04-04 20:36:25',0);
/*!40000 ALTER TABLE `sys_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operation_log`
--

DROP TABLE IF EXISTS `sys_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_operation_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `business_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除 4查询）',
  `req_method` smallint(6) NOT NULL DEFAULT '0' COMMENT '请求方式(0-get, 1-post, 2-put, 3-delete)',
  `req_module` varchar(64) NOT NULL DEFAULT '' COMMENT '请求模块',
  `req_url` varchar(256) NOT NULL DEFAULT '' COMMENT '请求URL',
  `called_method` varchar(128) NOT NULL DEFAULT '' COMMENT '调用方法',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(32) NOT NULL DEFAULT '' COMMENT '操作人员',
  `operator_ip` varchar(128) NOT NULL DEFAULT '' COMMENT '操作人员ip',
  `req_param` varchar(2048) NOT NULL DEFAULT '' COMMENT '请求参数',
  `req_result` varchar(2048) NOT NULL DEFAULT '' COMMENT '返回参数',
  `creator_id` bigint(20) unsigned NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint(20) unsigned NOT NULL COMMENT '修改人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除: 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operation_log`
--

LOCK TABLES `sys_operation_log` WRITE;
/*!40000 ALTER TABLE `sys_operation_log` DISABLE KEYS */;
INSERT INTO `sys_operation_log` VALUES (1,2,3,'用户模块','/api/app/user/delete/21','deleteUser',1,'admin','0:0:0:0:0:0:0:1','[\"21\"]','{\"code\":20000,\"data\":\"\",\"msg\":\"success\"}',1,'2024-04-04 20:36:43',1,'2024-04-04 20:36:43',0);
/*!40000 ALTER TABLE `sys_operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(256) NOT NULL COMMENT '用户昵称',
  `username` varchar(256) NOT NULL COMMENT '账号',
  `password` varchar(512) NOT NULL COMMENT '密码',
  `avatar` varchar(1024) NOT NULL COMMENT '用户头像',
  `gender` tinyint(1) NOT NULL COMMENT '性别',
  `role` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户角色：0-管理员 1-普通用户',
  `creator_id` bigint(64) unsigned DEFAULT '0' COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint(64) unsigned NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_account` (`username`),
  UNIQUE KEY `user_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'管理员','admin','b42b203d8d0e87b132879b9ee2de0130','default',0,0,1,'2024-01-31 18:13:50',5,'2024-02-01 15:50:10',0),(2,'pan普通用户','111111','df1bdd638fe145905ac269e07d7be92b','default',0,1,1,'2024-02-02 23:34:39',1,'2024-02-02 23:34:43',0),(10,'pan普通用户','111112','df1bdd638fe145905ac269e07d7be92b','default',0,1,1,'2024-02-02 23:34:39',1,'2024-02-02 23:34:43',0),(11,'pan普通用户','111113','df1bdd638fe145905ac269e07d7be92b','default',0,1,1,'2024-02-02 23:34:39',1,'2024-02-02 23:34:43',0),(12,'pan普通用户','111114','df1bdd638fe145905ac269e07d7be92b','default',0,1,1,'2024-02-02 23:34:39',1,'2024-02-02 23:34:43',0),(13,'pan普通用户','111115','df1bdd638fe145905ac269e07d7be92b','default',0,1,1,'2024-02-02 23:34:39',1,'2024-02-02 23:34:43',0),(14,'pan普通用户','111116','df1bdd638fe145905ac269e07d7be92b','default',0,1,1,'2024-02-02 23:34:39',1,'2024-02-02 23:34:43',0),(15,'12345678','12345678','df1bdd638fe145905ac269e07d7be92b','12345678',1,1,1,'2024-02-04 08:46:27',1,'2024-02-04 08:49:12',1),(16,'222222','222222','6ea9163a8b6c7f9f03551c93141fb605','222222',0,0,1,'2024-02-04 08:51:43',1,'2024-02-04 09:03:09',1),(17,'333333','333333','6ea9163a8b6c7f9f03551c93141fb605','333333',0,0,1,'2024-02-04 08:59:36',1,'2024-02-04 09:03:13',1),(18,'2222222','2222222','6ea9163a8b6c7f9f03551c93141fb605','2222222',0,1,1,'2024-02-04 09:09:44',1,'2024-02-04 09:13:52',0),(19,'默认用户名yyuoftpxu5','111111111','28121e98c815d70496a6cc5e68dc202a','',0,1,0,'2024-02-04 09:55:02',0,'2024-02-04 09:55:02',0),(20,'默认用户名pmp91mbktr','44444444','e05ef011fb74fc502139f4993fb2ca3a','',0,1,0,'2024-02-04 10:01:13',0,'2024-02-04 10:01:13',0),(21,'默认用户名g1ls5zg9kd','qwerty','209db44396f45afcade6cc48e4a41045','',0,1,0,'2024-04-04 20:33:10',0,'2024-04-04 20:36:42',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-04 21:03:08
