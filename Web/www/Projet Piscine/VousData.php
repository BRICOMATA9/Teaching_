<?php 
include("ConnexionBDD2.php");
session_start();
$mail = isset($_SESSION['mail'])?
$_SESSION['mail']:"";
$nb = 0;
//Récupération de l'utilisateur et de son pass hashé
$req = $conn->prepare('SELECT * FROM profil WHERE mail = ?');
$req -> execute(array($mail));
    while($resultat = $req->fetch())
    { 
    	$nb=1;
        $_SESSION['date'] = $resultat['dateN'];
        $_SESSION['statut'] = $resultat['statut'];
        $_SESSION['etude'] = $resultat['etude'];
        $_SESSION['site'] = $resultat['site'];
        $_SESSION['CV'] = $resultat['CV'];
        $_SESSION['cartepro'] = $resultat['cartepro'];
        $_SESSION['photo'] = $resultat['photo'];
        $_SESSION['comment'] = $resultat['comment'];
    }
    $req->closeCursor();
    if($nb==1){
    	header('Location: Vous.php');
    }else
    {
        echo 'Mauvais identifiant ou mot de passe !';
    }
?>
$conn->close();   
?>