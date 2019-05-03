<?php
$anniv = isset($_POST['anniv'])?
$_POST['anniv']:""; 
$statut = isset($_POST['statut'])?
$_POST['statut']:""; 
$etude= isset($_POST['etude'])?
$_POST['etude']:"";
$site= isset($_POST['site'])?
$_POST['site']:"";
$CV= isset($_POST['CV'])?
$_POST['CV']:"";
$cartepro= isset($_POST['cartepro'])?
$_POST['cartepro']:"";
$adphoto = isset($_POST['adphoto'])?
$_POST['adphoto']:""; 
$comment = isset($_POST['comment'])?
$_POST['comment']:"";
include("ConnexionBDD.php");
session_start();
$mail = isset($_SESSION['mail'])?
$_SESSION['mail']:"";
$sql = "INSERT INTO profil (mail, dateN, statut, etude, site, CV, cartepro, photo, comment) VALUES ('$mail', '$anniv', '$statut', '$etude','$site', '$CV', '$cartepro', '$adphoto', '$comment')";
$conn->query($sql);
$_SESSION['date'] = $anniv;
$_SESSION['statut'] = $statut;
$_SESSION['etude'] = $etude;
$_SESSION['site'] = $site;
$_SESSION['CV'] = $CV;
$_SESSION['cartepro'] = $cartepro;
$_SESSION['photo'] = $adphoto;
$_SESSION['comment'] = $comment;
header('Location: Vous.php');
  $conn->close();
?>