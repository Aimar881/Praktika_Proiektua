CREATE DATABASE  IF NOT EXISTS `noten_kudeaketa` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `noten_kudeaketa`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: noten_kudeaketa
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `ebaluazio_zatia`
--

DROP TABLE IF EXISTS `ebaluazio_zatia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ebaluazio_zatia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ebaluazio_id` int NOT NULL,
  `izena` varchar(100) NOT NULL,
  `pisua` decimal(3,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ebaluazio_id` (`ebaluazio_id`),
  CONSTRAINT `ebaluazio_zatia_ibfk_1` FOREIGN KEY (`ebaluazio_id`) REFERENCES `ebaluazioa` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ebaluazio_zatia_chk_1` CHECK (((`pisua` >= 0) and (`pisua` <= 1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ebaluazio_zatia`
--

LOCK TABLES `ebaluazio_zatia` WRITE;
/*!40000 ALTER TABLE `ebaluazio_zatia` DISABLE KEYS */;
/*!40000 ALTER TABLE `ebaluazio_zatia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ebaluazioa`
--

DROP TABLE IF EXISTS `ebaluazioa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ebaluazioa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `izena` varchar(50) NOT NULL,
  `ikasgaia_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ikasgaia_id` (`ikasgaia_id`),
  CONSTRAINT `ebaluazioa_ibfk_1` FOREIGN KEY (`ikasgaia_id`) REFERENCES `ikasgaia` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ebaluazioa`
--

LOCK TABLES `ebaluazioa` WRITE;
/*!40000 ALTER TABLE `ebaluazioa` DISABLE KEYS */;
/*!40000 ALTER TABLE `ebaluazioa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erabiltzailea`
--

DROP TABLE IF EXISTS `erabiltzailea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erabiltzailea` (
  `id` int NOT NULL AUTO_INCREMENT,
  `erabiltzaile_izena` varchar(50) NOT NULL,
  `pasahitza` varchar(255) NOT NULL,
  `rola` enum('IRAKASLEA','IKASLEA','TUTOREA') NOT NULL,
  `izena` varchar(100) DEFAULT NULL,
  `abizena` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `erabiltzaile_izena` (`erabiltzaile_izena`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erabiltzailea`
--

LOCK TABLES `erabiltzailea` WRITE;
/*!40000 ALTER TABLE `erabiltzailea` DISABLE KEYS */;
/*!40000 ALTER TABLE `erabiltzailea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ikasgaia`
--

DROP TABLE IF EXISTS `ikasgaia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ikasgaia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `izena` varchar(100) NOT NULL,
  `maila` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ikasgaia`
--

LOCK TABLES `ikasgaia` WRITE;
/*!40000 ALTER TABLE `ikasgaia` DISABLE KEYS */;
/*!40000 ALTER TABLE `ikasgaia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nota`
--

DROP TABLE IF EXISTS `nota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nota` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ikasle_id` int NOT NULL,
  `zati_id` int NOT NULL,
  `balioa` decimal(4,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ikasle_id` (`ikasle_id`),
  KEY `zati_id` (`zati_id`),
  CONSTRAINT `nota_ibfk_1` FOREIGN KEY (`ikasle_id`) REFERENCES `erabiltzailea` (`id`) ON DELETE CASCADE,
  CONSTRAINT `nota_ibfk_2` FOREIGN KEY (`zati_id`) REFERENCES `ebaluazio_zatia` (`id`) ON DELETE CASCADE,
  CONSTRAINT `nota_chk_1` CHECK (((`balioa` >= 0) and (`balioa` <= 10)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nota`
--

LOCK TABLES `nota` WRITE;
/*!40000 ALTER TABLE `nota` DISABLE KEYS */;
/*!40000 ALTER TABLE `nota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_noten_laburpena`
--

DROP TABLE IF EXISTS `v_noten_laburpena`;
/*!50001 DROP VIEW IF EXISTS `v_noten_laburpena`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_noten_laburpena` AS SELECT 
 1 AS `ikasle_id`,
 1 AS `izena`,
 1 AS `abizena`,
 1 AS `ikasgaia`,
 1 AS `ebaluazioa`,
 1 AS `nota_finala`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'noten_kudeaketa'
--

--
-- Final view structure for view `v_noten_laburpena`
--

/*!50001 DROP VIEW IF EXISTS `v_noten_laburpena`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_noten_laburpena` AS select `u`.`id` AS `ikasle_id`,`u`.`izena` AS `izena`,`u`.`abizena` AS `abizena`,`i`.`izena` AS `ikasgaia`,`e`.`izena` AS `ebaluazioa`,sum((`n`.`balioa` * `ez`.`pisua`)) AS `nota_finala` from ((((`nota` `n` join `erabiltzailea` `u` on((`n`.`ikasle_id` = `u`.`id`))) join `ebaluazio_zatia` `ez` on((`n`.`zati_id` = `ez`.`id`))) join `ebaluazioa` `e` on((`ez`.`ebaluazio_id` = `e`.`id`))) join `ikasgaia` `i` on((`e`.`ikasgaia_id` = `i`.`id`))) group by `u`.`id`,`e`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-11  8:38:00
