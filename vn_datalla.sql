-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: 05.05.2024 klo 11:52
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

--
-- Vedos taulusta `alue`
--

INSERT INTO `alue` (`alue_id`, `nimi`) VALUES
(1, 'Alue 1'),
(2, 'Alue 2'),
(3, 'Alue 3'),
(4, 'Alue 4'),
(5, 'Alue 5');

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

--
-- Vedos taulusta `asiakas`
--

INSERT INTO `asiakas` (`asiakas_id`, `postinro`, `etunimi`, `sukunimi`, `lahiosoite`, `email`, `puhelinnro`) VALUES
(1, '00100', 'Matti', 'Meikäläinen', 'Esimerkkikatu 1', 'matti@example.com', '0123456789'),
(2, '00200', 'Maija', 'Mallikas', 'Testitie 2', 'maija@example.com', '0987654321'),
(3, '00300', 'Antti', 'Asiakas', 'Katuosoite 3', 'antti@example.com', '0451234567'),
(4, '00400', 'Liisa', 'Lammas', 'Tie 4', 'liisa@example.com', '0409876543'),
(5, '00500', 'Pekka', 'Pilvinen', 'Kujatie 5', 'pekka@example.com', '0501122334');

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

--
-- Vedos taulusta `lasku`
--

INSERT INTO `lasku` (`lasku_id`, `varaus_id`, `summa`, `alv`, `maksettu`) VALUES
(1, 1, 100.00, 24.00, 1),
(2, 2, 150.00, 36.00, 0),
(3, 3, 200.00, 48.00, 1),
(4, 4, 250.00, 60.00, 0),
(5, 5, 300.00, 72.00, 1);

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

--
-- Vedos taulusta `mokki`
--

INSERT INTO `mokki` (`mokki_id`, `alue_id`, `postinro`, `mokkinimi`, `katuosoite`, `hinta`, `kuvaus`, `henkilomaara`, `varustelu`) VALUES
(1, 1, '00100', 'Mökki 1', 'Mökkikatu 1', 150.00, 'Mökki järven rannalla', 4, 'Sauna, takka, keittiö'),
(2, 2, '00200', 'Mökki 2', 'Mökkikatu 2', 200.00, 'Mökki metsän keskellä', 6, 'Sauna, grilli, ulkoterassi'),
(3, 3, '00300', 'Mökki 3', 'Mökkikatu 3', 250.00, 'Mökki vuorten juurella', 8, 'Kylpytynnyri, wifi, televisio'),
(4, 4, '00400', 'Mökki 4', 'Mökkikatu 4', 300.00, 'Mökki rauhallisella alueella', 10, 'Uima-allas, biljardipöytä'),
(5, 5, '00500', 'Mökki 5', 'Mökkikatu 5', 350.00, 'Mökki korkealla mäellä', 12, 'Tennis-kenttä, poreallas');

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

--
-- Vedos taulusta `palvelu`
--

INSERT INTO `palvelu` (`palvelu_id`, `alue_id`, `nimi`, `kuvaus`, `hinta`, `alv`) VALUES
(1, 1, 'Saunavuoro', 'Saunavuoro tunniksi', 20.00, 5.00),
(2, 2, 'Venevuokraus', 'Moottoriveneen vuokraus päiväksi', 50.00, 12.00),
(3, 3, 'Pyyhkeiden vuokraus', 'Pyyhkeiden vuokraus oleskelun ajaksi', 10.00, 2.40),
(4, 4, 'Polkupyörän vuokraus', 'Polkupyörän vuokraus päiväksi', 15.00, 3.60),
(5, 5, 'Retkipaketin vuokraus', 'Retkipaketin vuokraus päiväksi', 30.00, 7.20);

-- --------------------------------------------------------

--
-- Rakenne taululle `posti`
--

CREATE TABLE `posti` (
  `postinro` char(5) NOT NULL,
  `toimipaikka` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Vedos taulusta `posti`
--

INSERT INTO `posti` (`postinro`, `toimipaikka`) VALUES
('00100', 'Helsinki'),
('00200', 'Espoo'),
('00300', 'Vantaa'),
('00400', 'Tampere'),
('00500', 'Turku');

-- --------------------------------------------------------

--
-- Rakenne taululle `varauksen_palvelut`
--

CREATE TABLE `varauksen_palvelut` (
  `varaus_id` int(10) UNSIGNED NOT NULL,
  `palvelu_id` int(10) UNSIGNED NOT NULL,
  `lkm` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Vedos taulusta `varauksen_palvelut`
--

INSERT INTO `varauksen_palvelut` (`varaus_id`, `palvelu_id`, `lkm`) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 3),
(4, 4, 2),
(5, 5, 1);

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
-- Vedos taulusta `varaus`
--

INSERT INTO `varaus` (`varaus_id`, `asiakas_id`, `mokki_id`, `varattu_pvm`, `vahvistus_pvm`, `varattu_alkupvm`, `varattu_loppupvm`) VALUES
(1, 1, 1, '2024-05-01 12:00:00', '2024-05-02 12:00:00', '2024-06-01 00:00:00', '2024-06-07 00:00:00'),
(2, 2, 2, '2024-05-02 12:00:00', '2024-05-03 12:00:00', '2024-07-01 00:00:00', '2024-07-10 00:00:00'),
(3, 3, 3, '2024-05-03 12:00:00', '2024-05-04 12:00:00', '2024-08-01 00:00:00', '2024-08-14 00:00:00'),
(4, 4, 4, '2024-05-04 12:00:00', '2024-05-05 12:00:00', '2024-09-01 00:00:00', '2024-09-21 00:00:00'),
(5, 5, 5, '2024-05-05 12:00:00', '2024-05-06 12:00:00', '2024-10-01 00:00:00', '2024-10-28 00:00:00');

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
  MODIFY `alue_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT for table `asiakas`
--
ALTER TABLE `asiakas`
  MODIFY `asiakas_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `mokki`
--
ALTER TABLE `mokki`
  MODIFY `mokki_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `varaus`
--
ALTER TABLE `varaus`
  MODIFY `varaus_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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
