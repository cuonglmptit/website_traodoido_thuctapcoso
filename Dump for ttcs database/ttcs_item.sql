-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: ttcs
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `iid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `userid` int NOT NULL,
  `accepttype` int DEFAULT NULL,
  `tradeprice` int DEFAULT NULL,
  `sellprice` int DEFAULT NULL,
  `traderange` int DEFAULT NULL,
  `avaiable` int NOT NULL,
  `descr` text,
  `category` int DEFAULT '0',
  `img1` text,
  `img2` text,
  `img3` text,
  `createddate` date NOT NULL,
  PRIMARY KEY (`iid`),
  KEY `fk_uid_idx` (`userid`),
  KEY `fk_acctype_idx` (`accepttype`),
  KEY `fk_category_idx` (`category`),
  CONSTRAINT `fk_acctype` FOREIGN KEY (`accepttype`) REFERENCES `accepttype` (`acctypeid`),
  CONSTRAINT `fk_category` FOREIGN KEY (`category`) REFERENCES `category` (`catid`),
  CONSTRAINT `fk_uid` FOREIGN KEY (`userid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (2,'Bộ truyện tuổi thơ',1,3,5,5,2,4,'Hay',2,'/uploads/1_1717866148821_63d2ad10-3282-4698-90f4-3a4c7076293a.webp','/uploads/1_1717866148823_ade9fa44-c3f4-4ed4-9a94-c2a2d58874ba.jpg','/uploads/1_1717866148824_bf7c2972-c10e-40db-874a-0503dd94bf0d.jpg','2024-06-09');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-09  0:11:59
