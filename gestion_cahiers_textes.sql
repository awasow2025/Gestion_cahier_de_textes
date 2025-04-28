-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 28 avr. 2025 à 19:31
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_cahiers_textes`
--

-- --------------------------------------------------------

--
-- Structure de la table `classe`
--

CREATE TABLE `classe` (
  `ID_CLASSE` int(11) NOT NULL,
  `LIB_CLASSE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `classe`
--

INSERT INTO `classe` (`ID_CLASSE`, `LIB_CLASSE`) VALUES
(1, 'L1 Informatique'),
(2, 'L2 Informatique'),
(3, 'L3 Informatique'),
(4, 'M1 Informatique'),
(5, 'M2 Informatique'),
(6, 'L1 Math'),
(7, 'L2 Math'),
(8, 'L3 Math'),
(9, 'M1 Math'),
(10, 'M2 Math');

-- --------------------------------------------------------

--
-- Structure de la table `departement`
--

CREATE TABLE `departement` (
  `ID_DEPARTEMENT` int(11) NOT NULL,
  `LIB_DEPARTEMENT` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `departement`
--

INSERT INTO `departement` (`ID_DEPARTEMENT`, `LIB_DEPARTEMENT`) VALUES
(1, 'Informatique'),
(2, 'Mathématiques'),
(3, 'Physique-Chimie');

-- --------------------------------------------------------

--
-- Structure de la table `enseignant`
--

CREATE TABLE `enseignant` (
  `ID_ENSEIGNANT` int(11) NOT NULL,
  `ID_SPECIALITE` int(11) NOT NULL,
  `ID_GRADE` int(11) NOT NULL,
  `ID_SEXE` int(11) NOT NULL,
  `ID_DEPARTEMENT` int(11) DEFAULT NULL,
  `NOM` varchar(100) NOT NULL,
  `PRENOM` varchar(100) NOT NULL,
  `CONTACT` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `enseignant`
--

INSERT INTO `enseignant` (`ID_ENSEIGNANT`, `ID_SPECIALITE`, `ID_GRADE`, `ID_SEXE`, `ID_DEPARTEMENT`, `NOM`, `PRENOM`, `CONTACT`) VALUES
(1, 1, 1, 1, 1, 'Diop', 'Moussa', '771234567'),
(2, 2, 2, 2, 2, 'Ndiaye', 'Aminata', '781112233'),
(3, 3, 3, 1, 3, 'Fall', 'Abdou', '770000111'),
(4, 4, 1, 2, 3, 'Ba', 'Fatou', '765432109'),
(5, 8, 2, 2, 1, 'Sow', 'Awa', '772027564'),
(6, 2, 4, 1, 2, 'Ba', 'Issa', '76543675');

-- --------------------------------------------------------

--
-- Structure de la table `enseigner`
--

CREATE TABLE `enseigner` (
  `ID_ENS` int(11) NOT NULL,
  `ID_UE` int(11) NOT NULL,
  `ID_CLASSE` int(11) NOT NULL,
  `ID_ENSEIGNANT` int(11) NOT NULL,
  `DATE_ENS` date NOT NULL,
  `DEBUT_ENS` time NOT NULL,
  `FIN_ENS` time NOT NULL,
  `VOL_ENS` float DEFAULT NULL,
  `CONTENU` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `enseigner`
--

INSERT INTO `enseigner` (`ID_ENS`, `ID_UE`, `ID_CLASSE`, `ID_ENSEIGNANT`, `DATE_ENS`, `DEBUT_ENS`, `FIN_ENS`, `VOL_ENS`, `CONTENU`) VALUES
(1, 1, 1, 1, '2025-04-08', '20:39:52', '27:39:52', NULL, NULL),
(2, 3, 2, 3, '2025-04-02', '14:29:40', '14:34:40', NULL, 'jack.pdf'),
(3, 5, 6, 5, '2025-04-09', '14:38:33', '17:48:43', NULL, 'Dev.docx');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `ID_ETUDIANT` int(11) NOT NULL,
  `NOM` varchar(100) DEFAULT NULL,
  `PRENOM` varchar(100) DEFAULT NULL,
  `DATE_NAISSANCE` date DEFAULT NULL,
  `ID_SEXE` int(11) DEFAULT NULL,
  `ID_CLASSE` int(11) DEFAULT NULL,
  `CONTACT` varchar(20) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `MATRICULE` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`ID_ETUDIANT`, `NOM`, `PRENOM`, `DATE_NAISSANCE`, `ID_SEXE`, `ID_CLASSE`, `CONTACT`, `EMAIL`, `MATRICULE`) VALUES
(1, 'SOW', 'AWA', '2015-04-15', 2, 2, '773564354', 'awasow@uni-thies.sn', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `evaluation`
--

CREATE TABLE `evaluation` (
  `ID_EVAL` int(11) NOT NULL,
  `ID_ETUDIANT` int(11) DEFAULT NULL,
  `ID_UE` int(11) DEFAULT NULL,
  `NOTE` float DEFAULT NULL CHECK (`NOTE` between 0 and 20),
  `SESSION` enum('Normale','Rattrapage') DEFAULT NULL,
  `ANNEE_ACADEMIQUE` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `grade`
--

CREATE TABLE `grade` (
  `ID_GRADE` int(11) NOT NULL,
  `LIB_GRADE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `grade`
--

INSERT INTO `grade` (`ID_GRADE`, `LIB_GRADE`) VALUES
(1, 'Assistant'),
(2, 'Maître Assistant'),
(3, 'Maître de Conférences'),
(4, 'Professeur Titulaire');

-- --------------------------------------------------------

--
-- Structure de la table `planning`
--

CREATE TABLE `planning` (
  `ID_PLANNING` int(11) NOT NULL,
  `ID_UE` int(11) DEFAULT NULL,
  `ID_CLASSE` int(11) DEFAULT NULL,
  `DATE_PREVUE` date DEFAULT NULL,
  `HEURE_DEBUT` time DEFAULT NULL,
  `HEURE_FIN` time DEFAULT NULL,
  `SALLE` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `presence`
--

CREATE TABLE `presence` (
  `ID_PRESENCE` int(11) NOT NULL,
  `ID_SEANCE` int(11) DEFAULT NULL,
  `ID_ETUDIANT` int(11) DEFAULT NULL,
  `PRESENT` tinyint(1) DEFAULT 0,
  `JUSTIFIE` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `responsable_classe`
--

CREATE TABLE `responsable_classe` (
  `ID_RESPONSABLE` int(11) NOT NULL,
  `INE` int(12) NOT NULL,
  `ID_ENSEIGNANT` int(11) NOT NULL,
  `ID_CLASSE` int(11) NOT NULL,
  `ANNEE_ACADEMIQUE` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `responsable_classe`
--

INSERT INTO `responsable_classe` (`ID_RESPONSABLE`, `INE`, `ID_ENSEIGNANT`, `ID_CLASSE`, `ANNEE_ACADEMIQUE`) VALUES
(1, 1603010687, 3, 1, '2021-2022');

-- --------------------------------------------------------

--
-- Structure de la table `seance`
--

CREATE TABLE `seance` (
  `ID_SEANCE` int(11) NOT NULL,
  `ID_ENS` int(11) NOT NULL,
  `DATE_SEANCE` date NOT NULL,
  `HEURE_DEBUT` time NOT NULL,
  `HEURE_FIN` time NOT NULL,
  `NOM_COUR` text DEFAULT NULL,
  `CONTENU` text DEFAULT NULL,
  `MATERIEL` text DEFAULT NULL,
  `VALIDE` tinyint(1) DEFAULT 0,
  `VALIDE_PAR` int(11) DEFAULT NULL,
  `ID_CLASSE` int(11) DEFAULT NULL,
  `DATE_VALIDATION` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `seance`
--

INSERT INTO `seance` (`ID_SEANCE`, `ID_ENS`, `DATE_SEANCE`, `HEURE_DEBUT`, `HEURE_FIN`, `NOM_COUR`, `CONTENU`, `MATERIEL`, `VALIDE`, `VALIDE_PAR`, `ID_CLASSE`, `DATE_VALIDATION`) VALUES
(3, 1, '2025-04-07', '12:12:12', '14:12:12', 'Titre.docx', NULL, 'gggd', 1, 1, NULL, '2025-04-28 17:49:48'),
(4, 1, '2025-04-09', '12:13:13', '12:12:15', 'httpsgithub.comSteamDatabaseGameTracking-CS2.txt', NULL, 'gdggdgd', 1, 1, NULL, '2025-04-28 17:49:49'),
(5, 1, '2025-04-09', '12:25:49', '13:25:49', 'Catalyseurs de l’Intelligence Artificielle.pdf', NULL, 'JEE', 1, 1, NULL, '2025-04-28 17:49:49'),
(6, 1, '2025-04-27', '14:13:57', '14:13:57', 'Moyenne Master 1.PNG', NULL, 'Texte', 0, 1, 1, '2025-04-28 17:49:49'),
(7, 1, '2025-04-27', '14:16:29', '14:16:29', 'Moyenne Master 1.PNG', NULL, 'Ordinateur', 0, 1, 1, '2025-04-28 17:49:49'),
(8, 1, '2025-04-27', '17:40:28', '17:40:28', 'yt1s.com - CELINE DION  ALBUM  Grandes Éxitos Inmortal De CELINE DION 20 Éxitos.mp4', NULL, 'Geometrique', 0, 1, 1, '2025-04-28 17:49:49');

-- --------------------------------------------------------

--
-- Structure de la table `sexe`
--

CREATE TABLE `sexe` (
  `ID_SEXE` int(11) NOT NULL,
  `LIB_SEXE` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `sexe`
--

INSERT INTO `sexe` (`ID_SEXE`, `LIB_SEXE`) VALUES
(1, 'Masculin'),
(2, 'Féminin');

-- --------------------------------------------------------

--
-- Structure de la table `specialite`
--

CREATE TABLE `specialite` (
  `ID_SPECIALITE` int(11) NOT NULL,
  `LIB_SPECIALITE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `specialite`
--

INSERT INTO `specialite` (`ID_SPECIALITE`, `LIB_SPECIALITE`) VALUES
(1, 'Informatique'),
(2, 'Mathématiques'),
(3, 'Physique'),
(4, 'Chimie'),
(5, 'Biologie'),
(6, 'Sciences économiques'),
(7, 'Génie civil'),
(8, 'Génie électrique'),
(9, 'Télécommunications'),
(10, 'Statistiques');

-- --------------------------------------------------------

--
-- Structure de la table `ue`
--

CREATE TABLE `ue` (
  `ID_UE` int(11) NOT NULL,
  `LIB_UE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ue`
--

INSERT INTO `ue` (`ID_UE`, `LIB_UE`) VALUES
(1, 'Algorithmique'),
(2, 'Programmation Web'),
(3, 'Bases de Données'),
(4, 'Systèmes d’exploitation'),
(5, 'Analyse Mathématique'),
(6, 'Probabilités et Statistiques'),
(7, 'Électromagnétisme'),
(8, 'Chimie Organique');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_user` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Chef de département','Enseignant','Responsable de classe') NOT NULL,
  `id_enseignant` int(11) DEFAULT NULL,
  `ID_DEPARTEMENT` int(11) DEFAULT NULL,
  `ID_RESPONSABLE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_user`, `username`, `password`, `role`, `id_enseignant`, `ID_DEPARTEMENT`, `ID_RESPONSABLE`) VALUES
(1, 'mdiop', '1234', 'Enseignant', 1, 1, NULL),
(2, 'awa1', '1234', 'Responsable de classe', 2, 2, NULL),
(4, 'fadilou', 'Fadilou625@', 'Chef de département', NULL, 1, NULL),
(5, 'test', 'test', 'Chef de département', NULL, 1, NULL),
(10, 'sow', '1234', 'Enseignant', 5, NULL, NULL),
(11, 'issaba', '1234', 'Chef de département', NULL, 1, NULL),
(13, 'axa', '1234', 'Responsable de classe', NULL, NULL, 1),
(14, 'doudou', '1234', 'Chef de département', NULL, 1, NULL),
(15, 'Samba', '123456', 'Responsable de classe', NULL, NULL, 1),
(18, 'testy', 'test', 'Chef de département', NULL, 1, NULL),
(21, 'test1', 'teas', 'Chef de département', NULL, 1, NULL),
(22, 'ff', 'ff', 'Chef de département', NULL, 1, NULL),
(23, 'sss', 'sssssss', 'Chef de département', NULL, 1, NULL),
(24, 'EE', 'sshhgh', 'Chef de département', NULL, 1, NULL),
(25, 'shgshgs', 'shsghgshhsg', 'Enseignant', 1, NULL, NULL),
(26, 'sfgfsgfsg', 'wwfsfdsf', 'Responsable de classe', NULL, NULL, 1),
(27, 'go', 'go', 'Enseignant', 1, NULL, NULL),
(31, 'loufa', 'loufa', 'Chef de département', NULL, 1, NULL),
(35, 'test4', 'test', 'Chef de département', NULL, 1, NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `classe`
--
ALTER TABLE `classe`
  ADD PRIMARY KEY (`ID_CLASSE`);

--
-- Index pour la table `departement`
--
ALTER TABLE `departement`
  ADD PRIMARY KEY (`ID_DEPARTEMENT`);

--
-- Index pour la table `enseignant`
--
ALTER TABLE `enseignant`
  ADD PRIMARY KEY (`ID_ENSEIGNANT`),
  ADD KEY `ID_SPECIALITE` (`ID_SPECIALITE`),
  ADD KEY `ID_GRADE` (`ID_GRADE`),
  ADD KEY `ID_SEXE` (`ID_SEXE`),
  ADD KEY `ID_DEPARTEMENT` (`ID_DEPARTEMENT`);

--
-- Index pour la table `enseigner`
--
ALTER TABLE `enseigner`
  ADD PRIMARY KEY (`ID_ENS`),
  ADD KEY `ID_UE` (`ID_UE`),
  ADD KEY `ID_CLASSE` (`ID_CLASSE`),
  ADD KEY `ID_ENSEIGNANT` (`ID_ENSEIGNANT`);

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`ID_ETUDIANT`),
  ADD UNIQUE KEY `MATRICULE` (`MATRICULE`),
  ADD KEY `ID_SEXE` (`ID_SEXE`),
  ADD KEY `ID_CLASSE` (`ID_CLASSE`);

--
-- Index pour la table `evaluation`
--
ALTER TABLE `evaluation`
  ADD PRIMARY KEY (`ID_EVAL`),
  ADD KEY `ID_ETUDIANT` (`ID_ETUDIANT`),
  ADD KEY `ID_UE` (`ID_UE`);

--
-- Index pour la table `grade`
--
ALTER TABLE `grade`
  ADD PRIMARY KEY (`ID_GRADE`);

--
-- Index pour la table `planning`
--
ALTER TABLE `planning`
  ADD PRIMARY KEY (`ID_PLANNING`),
  ADD KEY `ID_UE` (`ID_UE`),
  ADD KEY `ID_CLASSE` (`ID_CLASSE`);

--
-- Index pour la table `presence`
--
ALTER TABLE `presence`
  ADD PRIMARY KEY (`ID_PRESENCE`),
  ADD KEY `ID_SEANCE` (`ID_SEANCE`),
  ADD KEY `ID_ETUDIANT` (`ID_ETUDIANT`);

--
-- Index pour la table `responsable_classe`
--
ALTER TABLE `responsable_classe`
  ADD PRIMARY KEY (`ID_RESPONSABLE`),
  ADD UNIQUE KEY `ID_CLASSE` (`ID_CLASSE`,`ANNEE_ACADEMIQUE`),
  ADD KEY `ID_ENSEIGNANT` (`ID_ENSEIGNANT`);

--
-- Index pour la table `seance`
--
ALTER TABLE `seance`
  ADD PRIMARY KEY (`ID_SEANCE`),
  ADD KEY `ID_ENS` (`ID_ENS`),
  ADD KEY `VALIDE_PAR` (`VALIDE_PAR`),
  ADD KEY `fk_seance_classe` (`ID_CLASSE`);

--
-- Index pour la table `sexe`
--
ALTER TABLE `sexe`
  ADD PRIMARY KEY (`ID_SEXE`);

--
-- Index pour la table `specialite`
--
ALTER TABLE `specialite`
  ADD PRIMARY KEY (`ID_SPECIALITE`);

--
-- Index pour la table `ue`
--
ALTER TABLE `ue`
  ADD PRIMARY KEY (`ID_UE`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `id_enseignant` (`id_enseignant`),
  ADD KEY `ID_DEPARTEMENT` (`ID_DEPARTEMENT`),
  ADD KEY `fk_responsable_classe` (`ID_RESPONSABLE`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `classe`
--
ALTER TABLE `classe`
  MODIFY `ID_CLASSE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `departement`
--
ALTER TABLE `departement`
  MODIFY `ID_DEPARTEMENT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `enseignant`
--
ALTER TABLE `enseignant`
  MODIFY `ID_ENSEIGNANT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `enseigner`
--
ALTER TABLE `enseigner`
  MODIFY `ID_ENS` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `ID_ETUDIANT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `evaluation`
--
ALTER TABLE `evaluation`
  MODIFY `ID_EVAL` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `grade`
--
ALTER TABLE `grade`
  MODIFY `ID_GRADE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `planning`
--
ALTER TABLE `planning`
  MODIFY `ID_PLANNING` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `presence`
--
ALTER TABLE `presence`
  MODIFY `ID_PRESENCE` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `responsable_classe`
--
ALTER TABLE `responsable_classe`
  MODIFY `ID_RESPONSABLE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `seance`
--
ALTER TABLE `seance`
  MODIFY `ID_SEANCE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `sexe`
--
ALTER TABLE `sexe`
  MODIFY `ID_SEXE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `specialite`
--
ALTER TABLE `specialite`
  MODIFY `ID_SPECIALITE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `ue`
--
ALTER TABLE `ue`
  MODIFY `ID_UE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `enseignant`
--
ALTER TABLE `enseignant`
  ADD CONSTRAINT `enseignant_ibfk_1` FOREIGN KEY (`ID_SPECIALITE`) REFERENCES `specialite` (`ID_SPECIALITE`),
  ADD CONSTRAINT `enseignant_ibfk_2` FOREIGN KEY (`ID_GRADE`) REFERENCES `grade` (`ID_GRADE`),
  ADD CONSTRAINT `enseignant_ibfk_3` FOREIGN KEY (`ID_SEXE`) REFERENCES `sexe` (`ID_SEXE`),
  ADD CONSTRAINT `enseignant_ibfk_4` FOREIGN KEY (`ID_DEPARTEMENT`) REFERENCES `departement` (`ID_DEPARTEMENT`);

--
-- Contraintes pour la table `enseigner`
--
ALTER TABLE `enseigner`
  ADD CONSTRAINT `enseigner_ibfk_1` FOREIGN KEY (`ID_UE`) REFERENCES `ue` (`ID_UE`),
  ADD CONSTRAINT `enseigner_ibfk_2` FOREIGN KEY (`ID_CLASSE`) REFERENCES `classe` (`ID_CLASSE`),
  ADD CONSTRAINT `enseigner_ibfk_3` FOREIGN KEY (`ID_ENSEIGNANT`) REFERENCES `enseignant` (`ID_ENSEIGNANT`);

--
-- Contraintes pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD CONSTRAINT `etudiant_ibfk_1` FOREIGN KEY (`ID_SEXE`) REFERENCES `sexe` (`ID_SEXE`),
  ADD CONSTRAINT `etudiant_ibfk_2` FOREIGN KEY (`ID_CLASSE`) REFERENCES `classe` (`ID_CLASSE`);

--
-- Contraintes pour la table `evaluation`
--
ALTER TABLE `evaluation`
  ADD CONSTRAINT `evaluation_ibfk_1` FOREIGN KEY (`ID_ETUDIANT`) REFERENCES `etudiant` (`ID_ETUDIANT`),
  ADD CONSTRAINT `evaluation_ibfk_2` FOREIGN KEY (`ID_UE`) REFERENCES `ue` (`ID_UE`);

--
-- Contraintes pour la table `planning`
--
ALTER TABLE `planning`
  ADD CONSTRAINT `planning_ibfk_1` FOREIGN KEY (`ID_UE`) REFERENCES `ue` (`ID_UE`),
  ADD CONSTRAINT `planning_ibfk_2` FOREIGN KEY (`ID_CLASSE`) REFERENCES `classe` (`ID_CLASSE`);

--
-- Contraintes pour la table `presence`
--
ALTER TABLE `presence`
  ADD CONSTRAINT `presence_ibfk_1` FOREIGN KEY (`ID_SEANCE`) REFERENCES `seance` (`ID_SEANCE`),
  ADD CONSTRAINT `presence_ibfk_2` FOREIGN KEY (`ID_ETUDIANT`) REFERENCES `etudiant` (`ID_ETUDIANT`);

--
-- Contraintes pour la table `responsable_classe`
--
ALTER TABLE `responsable_classe`
  ADD CONSTRAINT `responsable_classe_ibfk_1` FOREIGN KEY (`ID_ENSEIGNANT`) REFERENCES `enseignant` (`ID_ENSEIGNANT`),
  ADD CONSTRAINT `responsable_classe_ibfk_2` FOREIGN KEY (`ID_CLASSE`) REFERENCES `classe` (`ID_CLASSE`);

--
-- Contraintes pour la table `seance`
--
ALTER TABLE `seance`
  ADD CONSTRAINT `fk_seance_classe` FOREIGN KEY (`ID_CLASSE`) REFERENCES `classe` (`ID_CLASSE`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `seance_ibfk_1` FOREIGN KEY (`ID_ENS`) REFERENCES `enseigner` (`ID_ENS`),
  ADD CONSTRAINT `seance_ibfk_2` FOREIGN KEY (`VALIDE_PAR`) REFERENCES `utilisateur` (`id_user`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `fk_responsable_classe` FOREIGN KEY (`ID_RESPONSABLE`) REFERENCES `responsable_classe` (`ID_RESPONSABLE`),
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`id_enseignant`) REFERENCES `enseignant` (`ID_ENSEIGNANT`) ON DELETE SET NULL,
  ADD CONSTRAINT `utilisateur_ibfk_2` FOREIGN KEY (`ID_DEPARTEMENT`) REFERENCES `departement` (`ID_DEPARTEMENT`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
