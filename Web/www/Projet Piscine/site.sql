-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  jeu. 03 mai 2018 à 11:22
-- Version du serveur :  5.7.19
-- Version de PHP :  5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `site`
--

-- --------------------------------------------------------

--
-- Structure de la table `emploi`
--

DROP TABLE IF EXISTS `emploi`;
CREATE TABLE IF NOT EXISTS `emploi` (
  `Nom` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Type` int(11) NOT NULL,
  `Durée` int(11) NOT NULL,
  `MotClé` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `evenements`
--

DROP TABLE IF EXISTS `evenements`;
CREATE TABLE IF NOT EXISTS `evenements` (
  `Lieu` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Date` date NOT NULL,
  `Heure` time NOT NULL,
  `Ressentis` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Activité` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Confidentialité` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Aimer` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Commentaire` varchar(255) COLLATE latin1_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `profil`
--

DROP TABLE IF EXISTS `profil`;
CREATE TABLE IF NOT EXISTS `profil` (
  `email` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Age` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Activité` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Diplôme` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Lien site` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `CV` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Carte Pro` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Photos` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Type` varchar(255) COLLATE latin1_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `Prénom` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Nom` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Pseudo` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `Mot de passe` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Email` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`Nom`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
