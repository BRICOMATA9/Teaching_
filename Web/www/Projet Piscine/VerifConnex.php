<?php 
$mail = isset($_POST['mail'])?
$_POST['mail']:""; 
$mdp = isset($_POST['mdp'])?
$_POST['mdp']:""; 
include("ConnexionBDD2.php");
$nb = 0;
//Récupération de l'utilisateur et de son pass hashé
$req = $conn->prepare('SELECT * FROM utilisateur WHERE mail = ? AND mdp = ?');
$req -> execute(array($_POST['mail'], $_POST['mdp']));
    while($resultat = $req->fetch())
    {
        $nb=1;
        session_start();
        $_SESSION['mail'] = $resultat['mail'];
        $_SESSION['nom'] = $resultat['nom'];
        $_SESSION['prenom'] = $resultat['prenom'];
        
    }
    $req->closeCursor();
    if($nb==1){
        header('Location: Accueil.php');
    }else
    {
        echo 'Mauvais identifiant ou mot de passe !';
    }
$conn->close();   
?>
