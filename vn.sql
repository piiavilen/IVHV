-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: 04.05.2024 klo 15:21
-- Palvelimen versio: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vn`
--

-- --------------------------------------------------------

--
-- Rakenne taululle `alue`
--

CREATE TABLE `alue` (
  `alue_id` int(10) UNSIGNED NOT NULL,
  `nimi` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Rakenne taululle `asiakas`
--

CREATE TABLE `asiakas` (
  `asiakas_id` int(10) UNSIGNED NOT NULL,
  `postinro` char(5) NOT NULL,
  `etunimi` varchar(20) DEFAULT NULL,
  `sukunimi` varchar(40) DEFAULT NULL,
  `lahiosoite` varchar(40) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `puhelinnro` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Rakenne taululle `lasku`
--

CREATE TABLE `lasku` (
  `lasku_id` int(11) NOT NULL,
  `varaus_id` int(10) UNSIGNED NOT NULL,
  `summa` double(8,2) NOT NULL,
  `alv` double(8,2) NOT NULL,
  `maksettu` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Rakenne taululle `mokki`
--

CREATE TABLE `mokki` (
  `mokki_id` int(10) UNSIGNED NOT NULL,
  `alue_id` int(10) UNSIGNED NOT NULL,
  `postinro` char(5) NOT NULL,
  `mokkinimi` varchar(45) DEFAULT NULL,
  `katuosoite` varchar(45) DEFAULT NULL,
  `hinta` double(8,2) NOT NULL,
  `kuvaus` varchar(150) DEFAULT NULL,
  `henkilomaara` int(11) DEFAULT NULL,
  `varustelu` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Rakenne taululle `palvelu`
--

CREATE TABLE `palvelu` (
  `palvelu_id` int(10) UNSIGNED NOT NULL,
  `alue_id` int(10) UNSIGNED NOT NULL,
  `nimi` varchar(40) DEFAULT NULL,
  `kuvaus` varchar(255) DEFAULT NULL,
  `hinta` double(8,2) NOT NULL,
  `alv` double(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Rakenne taululle `posti`
--

CREATE TABLE `posti` (
  `postinro` char(5) NOT NULL,
  `toimipaikka` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Rakenne taululle `varauksen_palvelut`
--

CREATE TABLE `varauksen_palvelut` (
  `varaus_id` int(10) UNSIGNED NOT NULL,
  `palvelu_id` int(10) UNSIGNED NOT NULL,
  `lkm` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Rakenne taululle `varaus`
--

CREATE TABLE `varaus` (
  `varaus_id` int(10) UNSIGNED NOT NULL,
  `asiakas_id` int(10) UNSIGNED NOT NULL,
  `mokki_id` int(10) UNSIGNED NOT NULL,
  `varattu_pvm` datetime DEFAULT NULL,
  `vahvistus_pvm` datetime DEFAULT NULL,
  `varattu_alkupvm` datetime DEFAULT NULL,
  `varattu_loppupvm` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alue`
--
ALTER TABLE `alue`
  ADD PRIMARY KEY (`alue_id`),
  ADD KEY `alue_nimi_index` (`nimi`);

--
-- Indexes for table `asiakas`
--
ALTER TABLE `asiakas`
  ADD PRIMARY KEY (`asiakas_id`),
  ADD KEY `fk_as_posti1_idx` (`postinro`),
  ADD KEY `asiakas_snimi_idx` (`sukunimi`),
  ADD KEY `asiakas_enimi_idx` (`etunimi`);

--
-- Indexes for table `lasku`
--
ALTER TABLE `lasku`
  ADD PRIMARY KEY (`lasku_id`),
  ADD KEY `lasku_ibfk_1` (`varaus_id`);

--
-- Indexes for table `mokki`
--
ALTER TABLE `mokki`
  ADD PRIMARY KEY (`mokki_id`),
  ADD KEY `fk_mokki_alue_idx` (`alue_id`),
  ADD KEY `fk_mokki_posti_idx` (`postinro`);

--
-- Indexes for table `palvelu`
--
ALTER TABLE `palvelu`
  ADD PRIMARY KEY (`palvelu_id`),
  ADD KEY `Palvelu_nimi_index` (`nimi`),
  ADD KEY `palv_toimip_id_ind` (`alue_id`);

--
-- Indexes for table `posti`
--
ALTER TABLE `posti`
  ADD PRIMARY KEY (`postinro`);

--
-- Indexes for table `varauksen_palvelut`
--
ALTER TABLE `varauksen_palvelut`
  ADD PRIMARY KEY (`palvelu_id`,`varaus_id`),
  ADD KEY `vp_varaus_id_index` (`varaus_id`),
  ADD KEY `vp_palvelu_id_index` (`palvelu_id`);

--
-- Indexes for table `varaus`
--
ALTER TABLE `varaus`
  ADD PRIMARY KEY (`varaus_id`),
  ADD KEY `varaus_as_id_index` (`asiakas_id`),
  ADD KEY `fk_var_mok_idx` (`mokki_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alue`
--
ALTER TABLE `alue`
  MODIFY `alue_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `asiakas`
--
ALTER TABLE `asiakas`
  MODIFY `asiakas_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mokki`
--
ALTER TABLE `mokki`
  MODIFY `mokki_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `varaus`
--
ALTER TABLE `varaus`
  MODIFY `varaus_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Rajoitteet vedostauluille
--

--
-- Rajoitteet taululle `asiakas`
--
ALTER TABLE `asiakas`
  ADD CONSTRAINT `fk_asiakas_posti` FOREIGN KEY (`postinro`) REFERENCES `posti` (`postinro`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Rajoitteet taululle `lasku`
--
ALTER TABLE `lasku`
  ADD CONSTRAINT `lasku_ibfk_1` FOREIGN KEY (`varaus_id`) REFERENCES `varaus` (`varaus_id`);

--
-- Rajoitteet taululle `mokki`
--
ALTER TABLE `mokki`
  ADD CONSTRAINT `fk_mokki_alue` FOREIGN KEY (`alue_id`) REFERENCES `alue` (`alue_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_mokki_posti` FOREIGN KEY (`postinro`) REFERENCES `posti` (`postinro`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Rajoitteet taululle `palvelu`
--
ALTER TABLE `palvelu`
  ADD CONSTRAINT `palvelu_ibfk_1` FOREIGN KEY (`alue_id`) REFERENCES `alue` (`alue_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Rajoitteet taululle `varauksen_palvelut`
--
ALTER TABLE `varauksen_palvelut`
  ADD CONSTRAINT `fk_palvelu` FOREIGN KEY (`palvelu_id`) REFERENCES `palvelu` (`palvelu_id`),
  ADD CONSTRAINT `fk_varaus` FOREIGN KEY (`varaus_id`) REFERENCES `varaus` (`varaus_id`);

--
-- Rajoitteet taululle `varaus`
--
ALTER TABLE `varaus`
  ADD CONSTRAINT `fk_varaus_mokki` FOREIGN KEY (`mokki_id`) REFERENCES `mokki` (`mokki_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `varaus_ibfk` FOREIGN KEY (`asiakas_id`) REFERENCES `asiakas` (`asiakas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
