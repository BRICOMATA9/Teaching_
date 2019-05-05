<?php
$mail = isset($_POST['mail'])?
$_POST['mail']:""; 
$nom = isset($_POST['nom'])?
$_POST['nom']:""; 
$prenom= isset($_POST['prenom'])?
$_POST['prenom']:"";
$pseudo= isset($_POST['pseudo'])?
$_POST['pseudo']:"";
$mdp = isset($_POST['mdp'])?
$_POST['mdp']:""; 
$confirm = isset($_POST['confirm'])?
$_POST['confirm']:""; 
$error = ""; 
include("ConnexionBDD.php");
if($mail == ""){
  $error = "Vous n'avez pas entré votre adresse mail <br/>"; 
}
if($nom == ""){
  $error = "Vous n'avez pas entré notre nom<br/>"; 
}
if($prenom == ""){
  $error = "Vous n'avez pas entré votre prenom <br/>"; 
}
if($mdp == ""){
  $error = "Vous n'avez pas entré de mot de passe <br/>"; 
}
if($confirm == ""){
  $error = "Vous n'avez pas confirmé votre mot de passe <br/>"; 
}
if ($mdp != $confirm){
  $error = "Vous devez entrer le même mot de passe <br/>";
  }
if($error == ""){
    $sql = "INSERT INTO utilisateur (mail, nom, prenom, pseudo, mdp)
VALUES ( '$mail', '$nom', '$prenom', '$pseudo', '$mdp')";
if ($conn->query($sql) === TRUE) {
  session_start();
$_SESSION['mail'] = $mail;
$_SESSION['nom'] = $nom;
$_SESSION['prenom'] = $prenom;
  
  $conn->close();?>
  <!DOCTYPE html>
<html lang="en">
<head>
  <title>Accueil</title>
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
      <a class="navbar-brand" href="#"><img src="logo.jpeg"></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Accueil</a></li>
        <li><a href="#">Mon réseau</a></li>
        <li><a href="monprofil.php">Mon profil</a></li>
        <li><a href="#">Notifications</a></li>
        <li><a href="#">Ma messagerie</a></li>
        <li><a href="#">Emplois</a></li>
      </ul>
    </div>
  </div>
</nav>
<div class="container-fluid text-center">  
  <h1>Accueil</h1><br>
<p>
  Vous avez bien été inscrit
    </p>
</div>

</body>
  
<?php } else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
} else { echo "Erreur : $error";}
?>