-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: major
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `permission` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(50) NOT NULL,
  `permission_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `resource_url` varchar(100) DEFAULT NULL,
  `resource_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `permission_code` (`permission_code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'权限管理','',NULL,NULL,NULL,NULL,NULL),(2,'权限列表','permission:list','/permission/list','menu',1,1,NULL),(3,'角色管理',NULL,'/role/list','menu',1,2,NULL),(4,'角色录入','role:add','/role/add','button',3,NULL,NULL),(5,'角色删除','role:delete','/role/delete','button',3,NULL,NULL),(6,'角色分配','role:bind','/role/bind','button',3,NULL,NULL),(7,'角色编辑','role:update','/role/update','button',3,NULL,NULL),(8,'模块管理',NULL,NULL,'menu',1,3,NULL),(9,'权限模块访问','permission:visit','/permission/visit','button',8,NULL,NULL),(10,'成绩模块访问','score:visit','/score/visit','button',8,NULL,NULL),(11,'数据模块访问','data:visit','/data/visit','button',8,NULL,NULL),(12,'公告模块访问','notice:visit','/notice/visit','button',8,NULL,NULL),(13,'审核模块访问','check:visit','/check/visit','button',8,NULL,NULL);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(20) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`rid`),
  UNIQUE KEY `role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin','系统管理员'),(2,'EduManager','教务管理员'),(3,'Teacher','教师'),(4,'Student','学生'),(5,'CheckManager','审计员');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,1,2),(2,1,3),(3,1,4),(4,1,5),(5,1,6),(6,1,7),(7,1,9),(8,1,10),(9,1,11),(10,1,12),(11,1,13);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_role_user` (`role_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1),(31,3,1),(32,5,1),(16,1,2),(3,3,2),(4,4,2),(5,5,2),(6,6,3),(7,7,3),(8,8,3),(9,9,3),(10,10,3),(14,1,4),(15,3,4),(11,11,4),(12,12,4),(13,16,4),(2,2,5);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `real_name` varchar(50) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `email` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'root','$2a$10$TrfMbAAGfXZDRSVDNDzfm..XMQZ8cfeCzWhIbC3nkth4GVHlmlI3q','root',1,'root@qq.com','2025-04-06 15:50:46','2025-04-07 15:51:11'),(2,'a2','$2a$10$XTOUor6xlD2i/THgGfMZMuT3qgJS0RAiAPUwIyUhjIRvRQE/f2nxq','张2',1,'zhang2@qq.com','2025-04-06 15:51:39','2025-04-07 15:51:47'),(3,'a3','$2a$10$o5Ccncjx2twYTU5aeHKKF.yj5/Sx.V9CLCbmYoE89OiHeYXaiiagu','张3',1,'zhang3@qq.com','2025-04-06 15:54:14','2025-05-07 15:54:26'),(4,'a4','$2a$10$rQ8A6P55DNUDSpoKHUnFbe5HkUX9Boj5BBM2VGCh7c6jYzlTBa2kC','张4',1,'zhang4@qq.com','2025-04-06 15:54:16','2025-05-07 15:54:27'),(5,'b1','$2a$10$zV7KUmtCgzNlxRYFEdwX9eTHQrgFKZe8aD4lXwBu84XVJ8MdDTrB6','王1',1,'wang1@qq.com','2025-04-06 15:54:17','2025-05-07 15:54:28'),(6,'b2','$2a$10$WkSSQxAZ8ivuOs7zjDC0zOe52eO2EYL7AWdkwrxZhxbytord2bpkO','王2',1,'wang2@qq.com','2025-04-06 15:54:18','2025-05-07 15:54:30'),(7,'b3','$2a$10$a562S6RuzyVeQYqhKw6.vujGSAHg8N5ZpkuUqGwq2dtTmBYk..YIm','王3',1,'wang3@qq.com','2025-04-06 15:54:21','2025-05-07 15:54:31'),(8,'b4','$2a$10$SoZ.vGQdCrdta91KK0fFSuIM0oNqpLVPPBUztiSJm1D0ts9k9FZ22','王4',1,'wang4@qq.com','2025-04-06 15:54:20','2025-05-07 15:54:32'),(9,'c1','$2a$10$47ZlQp.qv0c/cAvXQe75bOyzJYH2jktt0VGfpCyMFwrxXyyLjLnTu','李1',1,'li1@qq.com','2025-04-06 15:54:23','2025-05-07 15:54:33'),(10,'c2','$2a$10$kvlKxmgyJSC0VCJ4q3Aqo.ssCdLVGCIQAmFFo.HZNUc26iZHXat5u','李2',1,'li2@qq.com','2025-04-06 15:56:17','2025-05-07 15:56:34'),(11,'c3','$2a$10$Lo1LiGtPCnkHDQ6WjQuxUOLz7sA2Ujb/n5p2Y.mnu7msRyX8c6jIy','李3',1,'li3@qq.com','2025-04-06 15:56:23','2025-05-07 15:56:35'),(12,'c4','$2a$10$iBRzWgxw8XOXeqaQgyKHl.S2xL.FTiKYWpCl/hMUKDOHI2Eg9/MJG','李4',1,'li4@qq.com','2025-04-06 15:56:29','2025-05-07 15:56:36'),(16,'pppp','$2a$10$KHIHI/KXgEzIGvCfoJKJp.Xi5rEbOyvJKadTIsTAjd8592GR3KyFO',NULL,1,NULL,'2025-05-12 09:32:56',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-02 19:01:37
