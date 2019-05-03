-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le :  sam. 05 mai 2018 à 17:36
-- Version du serveur :  5.6.38
-- Version de PHP :  7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `Web`
--

-- --------------------------------------------------------

--
-- Structure de la table `emploi`
--

CREATE TABLE `emploi` (
  `Nom` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Type` int(11) NOT NULL,
  `Durée` int(11) NOT NULL,
  `MotClé` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `evenements`
--

CREATE TABLE `evenements` (
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

CREATE TABLE `profil` (
  `mail` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `dateN` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `statut` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `etude` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `site` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `CV` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `cartepro` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `photo` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `comment` varchar(255) COLLATE latin1_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Déchargement des données de la table `profil`
--

INSERT INTO `profil` (`mail`, `dateN`, `statut`, `etude`, `site`, `CV`, `cartepro`, `photo`, `comment`) VALUES
('edesdet@gmail.com', '06', 'Etudiant', 'Baccalauréat', 'https://www.google.com', 'Storyboard.pdf', 'Storyboard.pdf', 'Logo.png', 'Yes');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `mail` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `nom` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `prenom` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `pseudo` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `mdp` varchar(255) COLLATE latin1_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`mail`, `nom`, `prenom`, `pseudo`, `mdp`) VALUES
('edesdet@gmail.com', 'Desdet', 'Emma', 'e', 'e');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `profil`
--
ALTER TABLE `profil`
  ADD PRIMARY KEY (`mail`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`mail`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
