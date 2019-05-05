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
        <li><a href="Deconnexion.php"> Se deconnecter</a></li></ul>
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
      Date de naissance : <?php echo $_SESSION['date']?><br><br>
      Statut : <?php echo $_SESSION['statut']?><br><br>
      Niveau d'étude : <?php echo $_SESSION['etude']?><br><br>
</p>
           <div class="col-md-4">
    <div class="thumbnail">
        <a href= <?php echo $_SESSION['photo']?> target="_blank">
          <img src= <?php echo $_SESSION['photo']?> alt="Lights" style="width:100%">
          <div class="caption">
            <p> <?php echo $_SESSION['comment'];?></p>
          </div>
        </a>
      </div>
    </div>

    </p>
  </div>

<div class="col-sm-6">
<p>
      <a href= <?php echo $_SESSION['CV']?>>CV</a><br><br>
      <a href= <?php echo $_SESSION['site']?>>Lien vers site professionnel </a><br><br>
      <a href= <?php echo $_SESSION['cartepro']?>>Carte professionnelle </a><br><br>

    </p>
  </div>
  </div>
</form>
</div>
</form>
  <div class="container-fluid text-center">  
<form method="post" action="http://localhost:8888/www/Projet%20Piscine/monprofil.php">
<input type = "Submit" name = "modif" value = "Modifier"><br>
</form>
</div>

</body>