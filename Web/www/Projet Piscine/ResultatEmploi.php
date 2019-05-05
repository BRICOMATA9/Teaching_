	<!DOCTYPE html>
<html>
<head>
  <title>Accueil</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

<nav class="navbar navbar-inverse" action="ResultatEmploi.php">
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
      <ul class="nav navbar-nav navbar-right">
       
        <li><a href="PageAccueil.html">Accueil</a></li>
        <li><a href="Amis.html">Mon Reseau</a></li>
        <li><a href="MonProfil.html">Vous</a></li>
        <li><a href="Notifications.html">Notifications</a></li>
        <li><a href="Messages.html">Messagerie</a></li>
        <li class="active"><a href="#">Emplois</a></li>
      </ul>
</nav>

    <div class="container-fluid text-left">  
		<p class="text-primary"><h1><strong>Resultat de votre recherche d'Emplois</strong><br><br></h1> </p>
	
		<?php
    	$mysqli = new mysqli('localhost', 'root', '', 'piscine');
        $requete = 'SELECT * FROM emploi';
        $resultat = $mysqli->query($requete);
        while ($ligne = $resultat->fetch_assoc()) 
        {
            echo $ligne['Titre'].'<br>';
			      echo $ligne['Entreprise'].' - '.$ligne['Lieu'].'<br><br>';
            print "Description: ";
            echo $ligne['Description'].'<br><br>';
            print "Formation: ";
            echo $ligne['Formation'].'<br>';
            print "Experience: ";
            echo $ligne['Experience'].'<br>';
            print "Mission: ";
            echo $ligne['Missions'].'<br><br><br>';
           // $sql = SELECT * FROM Emploi WHERE Type = 'CDI';
        } 
        $mysqli->close();
        ?>
   
    </div>
    $sql = "SELECT Type FROM Emploi WHERE Type = 'CDI';
	$result = mysqli_query($emploi, $sql);
  </div>


</body>
<footer>
  <div id="footer" align="center">Copyright &copy; 2018 My Professionnal Network<br />
        <a href="mailto:mpn.contact@gmail.com"> Contactez nous </a>
    </div>
  </footer>
</html>