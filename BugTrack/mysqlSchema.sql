-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bugtracker
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(166) NOT NULL,
  `auth` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `adminpassword`
--

DROP TABLE IF EXISTS `adminpassword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adminpassword` (
  `pass` varchar(166) NOT NULL,
  PRIMARY KEY (`pass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `current_bugs`
--

DROP TABLE IF EXISTS `current_bugs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `current_bugs` (
  `Title` varchar(45) NOT NULL,
  `Language` varchar(45) NOT NULL,
  `Priority` varchar(45) NOT NULL,
  `Description` text NOT NULL,
  `File` varchar(100) DEFAULT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`Title`),
  UNIQUE KEY `title_UNIQUE` (`Title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `in_progress`
--

DROP TABLE IF EXISTS `in_progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `in_progress` (
  `Title` varchar(45) NOT NULL,
  `Language` varchar(45) NOT NULL,
  `Priority` varchar(45) NOT NULL,
  `Description` text NOT NULL,
  `File` varchar(100) DEFAULT NULL,
  `Date` date NOT NULL,
  `FixedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`Title`),
  UNIQUE KEY `title_UNIQUE` (`Title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `past_bugs`
--

DROP TABLE IF EXISTS `past_bugs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `past_bugs` (
  `Title` varchar(45) NOT NULL,
  `Language` varchar(45) NOT NULL,
  `Priority` varchar(45) NOT NULL,
  `Description` text NOT NULL,
  `File` varchar(100) DEFAULT NULL,
  `Date` date NOT NULL,
  `FixedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`Title`),
  UNIQUE KEY `title_UNIQUE` (`Title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-07 17:59:22
