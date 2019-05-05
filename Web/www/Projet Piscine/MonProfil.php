<?php
session_start();
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Vous</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#"><img src="logo.png"></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><a href="#">Accueil</a></li>
        <li><a href="#">Mon réseau</a></li>
        <li class="active"><a href="#">Vous</a></li>
        <li><a href="#">Notifications</a></li>
        <li><a href="#">Ma messagerie</a></li>
        <li><a href="#">Emplois</a></li>
      </ul>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <li><a href="Deconnexion.php"> Se deconnecter</a></li>
  </div>
</nav>
<div class="container-fluid text-center">  
  <h1>Vous</h1>
  <div>Décrivez votre profil : </div>
</div>
<form method="post" action="http://localhost:8888/www/Projet%20Piscine/InsertVous.php">
<div class="container-fluid">
  <div class="row">
  <div class="col-sm-6"> 
<p>

      E-mail : <?php echo $_SESSION['mail'];?><br><br>
      Nom : <?php echo $_SESSION['nom']?><br><br> 
      Prenom : <?php echo $_SESSION['prenom']?> <br><br>
      Date de naissance <input type = "text" name="anniv"><br><br>
      Statut <select name="statut"><option value = "Aucune information">Sélectionner votre statut</option>
           <option value="Etudiant">Etudiant</option>
           <option value="Enseignant">Enseignant</option>
           <option value="Entreprise">Entreprise</option>
           </select><br><br>
      Niveau d'étude <select name="etude"><option value = "Aucune information">Sélectionner votre niveau d'étude</option>
           <option value="Baccalauréat">Baccalauréat</option>
           <option value="Licence">Licence</option>
           <option value="Master">Master</option>
           <option value="Doctorat">Doctorat</option>
           </select><br><br>

    </p>
  </div>

<div class="col-sm-6">
<p>
      CV <input type="text" name="CV"/> <br><br>
      Lien vers site progessionnel <input type="text" name="site" /><br><br>
      Carte professionnel <input type="text" name="cartepro"/> <br><br>

    </p>
  </div>

  <div class="col-sm-6">
<p>
  <br><br>

Choisissez votre photo <input type="text" name="adphoto"/><br><br>
Ajoutez un commentaire <input type="text" name="comment"/><br><br>
    </p>
  </div>
  </div>
  <div class="container-fluid text-center">  
<input type = "Submit" name = "Validprof" value = "Valider"><br>
</div>

</form>
</body>