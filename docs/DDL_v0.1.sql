CREATE DATABASE  IF NOT EXISTS `thisandthat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `thisandthat`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: thisandthat
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `recent_page` bigint NOT NULL,
  `title` varchar(45) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `publish_date` datetime DEFAULT NULL,
  `is_published` tinyint DEFAULT '0',
  `edit_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_book_user1_idx` (`user_id`),
  KEY `fk_book_page1_idx` (`recent_page`),
  CONSTRAINT `fk_book_page1` FOREIGN KEY (`recent_page`) REFERENCES `page` (`id`),
  CONSTRAINT `fk_book_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color` (
  `hex` char(6) COLLATE utf8mb4_0900_as_cs NOT NULL,
  PRIMARY KEY (`hex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drawing`
--

DROP TABLE IF EXISTS `drawing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drawing` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `page_id` bigint NOT NULL,
  `dpi` int NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  `width` int NOT NULL,
  `height` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_image_page1_idx` (`page_id`),
  CONSTRAINT `fk_image_page1` FOREIGN KEY (`page_id`) REFERENCES `page` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drawing`
--

LOCK TABLES `drawing` WRITE;
/*!40000 ALTER TABLE `drawing` DISABLE KEYS */;
/*!40000 ALTER TABLE `drawing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layer`
--

DROP TABLE IF EXISTS `layer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `layer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `drawing_id` bigint NOT NULL,
  `order` int NOT NULL,
  `data` longblob,
  PRIMARY KEY (`id`),
  KEY `fk_layer_drawing1_idx` (`drawing_id`),
  CONSTRAINT `fk_layer_drawing1` FOREIGN KEY (`drawing_id`) REFERENCES `drawing` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layer`
--

LOCK TABLES `layer` WRITE;
/*!40000 ALTER TABLE `layer` DISABLE KEYS */;
/*!40000 ALTER TABLE `layer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `page` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint NOT NULL,
  `is_cover` tinyint NOT NULL,
  `page_number` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_page_book_idx` (`book_id`),
  CONSTRAINT `fk_page_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page`
--

LOCK TABLES `page` WRITE;
/*!40000 ALTER TABLE `page` DISABLE KEYS */;
/*!40000 ALTER TABLE `page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pen_color`
--

DROP TABLE IF EXISTS `pen_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pen_color` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color_hex` char(6) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `image_id` bigint NOT NULL,
  `order` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pen_color_color1_idx` (`color_hex`),
  KEY `fk_pen_color_image1_idx` (`image_id`),
  CONSTRAINT `fk_pen_color_color1` FOREIGN KEY (`color_hex`) REFERENCES `color` (`hex`),
  CONSTRAINT `fk_pen_color_image1` FOREIGN KEY (`image_id`) REFERENCES `drawing` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pen_color`
--

LOCK TABLES `pen_color` WRITE;
/*!40000 ALTER TABLE `pen_color` DISABLE KEYS */;
/*!40000 ALTER TABLE `pen_color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `textbox`
--

DROP TABLE IF EXISTS `textbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `textbox` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `page_id` bigint NOT NULL,
  `content` varchar(1000) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `dpi` int NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  `font_size` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_textbox_page1_idx` (`page_id`),
  CONSTRAINT `fk_textbox_page1` FOREIGN KEY (`page_id`) REFERENCES `page` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `textbox`
--

LOCK TABLES `textbox` WRITE;
/*!40000 ALTER TABLE `textbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `textbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool`
--

DROP TABLE IF EXISTS `tool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tool` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_id` bigint NOT NULL,
  `tool_type` varchar(45) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `size1` int DEFAULT NULL,
  `size2` int DEFAULT NULL,
  `size3` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tool_image1_idx` (`image_id`),
  CONSTRAINT `fk_tool_image1` FOREIGN KEY (`image_id`) REFERENCES `drawing` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool`
--

LOCK TABLES `tool` WRITE;
/*!40000 ALTER TABLE `tool` DISABLE KEYS */;
/*!40000 ALTER TABLE `tool` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` varchar(1000) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'thisandthat'
--

--
-- Dumping routines for database 'thisandthat'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-03 17:15:46
