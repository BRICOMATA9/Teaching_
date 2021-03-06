DROP TABLE Cours;
DROP TABLE TP;
DROP TABLE Inscription;
DROP TABLE Module;
DROP TABLE Salle;
DROP TABLE Batiment;
DROP TABLE Etudiant;
DROP TABLE Enseignant;
DROP TABLE Groupe;
DROP TABLE Section;

Create Table Batiment(
idBatiment INT NOT NULL Primary Key
);

Create Table Salle(
idSalle INT NOT NULL,
capacite INT DEFAULT NULL,
idBatiment INT NOT NULL,
Primary Key (idSalle,idBatiment),
Foreign key (idBatiment) references Batiment(idBatiment) on delete cascade
);

Create Table Section(
idSection INT NOT NULL Primary Key,
annee DATE DEFAULT NULL
);

Create Table Groupe(
idGroupe INT NOT NULL,
idSection INT NOT NULL,
Primary Key (idGroupe,idSection),
Foreign key (idSection) references Section(idSection) on delete cascade
);

Create Table Etudiant(
idPersonne INT NOT NULL Primary Key,
nom VARCHAR(30) NOT NULL,
prenom VARCHAR(30) NOT NULL,
login VARCHAR(30) NOT NULL,
mdp VARCHAR(30) NOT NULL,
idGroupe INT NOT NULL,
Foreign key (idGroupe) references Groupe(idGroupe) on delete cascade
);

Create Table Enseignant(
idPersonne INT NOT NULL Primary Key,
nom VARCHAR(30) NOT NULL,
prenom VARCHAR(30) NOT NULL,
login VARCHAR(30) NOT NULL,
mdp VARCHAR(30) NOT NULL,
grade VARCHAR(30) NOT NULL
);

Create Table Module(
idModule VARCHAR(30) NOT NULL Primary Key,
nom VARCHAR(30) NOT NULL,
coef INT NOT NULL
);

Create Table Inscription(
idEtudiant INT NOT NULL,
idModule VARCHAR(30) NOT NULL,
moyenne DOUBLE DEFAULT NULL,
Primary Key (idEtudiant,idModule),
Foreign key (idEtudiant) references Etudiant(idPersonne) on delete cascade,
Foreign key (idModule) references Module(idModule) on delete cascade
);

Create Table Cours(
idModule VARCHAR(30) NOT NULL,
idSalle INT NOT NULL,
idEnseignant INT NOT NULL,
idSection INT NOT NULL,
dateDebut DATE NOT NULL,
heureDebut TIME NOT NULL,
durree TIME DEFAULT NULL,
Primary Key (idModule,idSalle,idEnseignant,idSection,dateDebut),
Foreign key (idModule) references Module(idModule) on delete cascade,
Foreign key (idSalle) references Salle(idSalle) on delete cascade,
Foreign key (idEnseignant) references Enseignant(idPersonne) on delete cascade,
Foreign key (idSection) references Section(idSection) on delete cascade
);

Create Table TP(
idModule VARCHAR(30) NOT NULL,
idSalle INT NOT NULL,
idEnseignant INT NOT NULL,
idSection INT NOT NULL,
idGroupe INT NOT NULL,
dateDebut DATE NOT NULL,
heureDebut TIME NOT NULL,
durree TIME DEFAULT NULL,
Primary Key (idModule,idSalle,idEnseignant,idGroupe,dateDebut),
Foreign key (idModule) references Module(idModule) on delete cascade,
Foreign key (idSalle) references Salle(idSalle) on delete cascade,
Foreign key (idEnseignant) references Enseignant(idPersonne) on delete cascade,
Foreign key (idSection) references Section(idSection) on delete cascade,
Foreign key (idGroupe) references Groupe(idGroupe) on delete cascade
);

