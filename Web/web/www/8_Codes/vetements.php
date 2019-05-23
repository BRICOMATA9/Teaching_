<!-- PHP USER CONNECTE -->
<?php 
    session_start();
    
    //Partie USER
    //Partie USER
    if(isset($_SESSION['user'])){
        $user = $_SESSION['user'];
    }
    else{
        $user = "";
    }

    //identifier votre BDD
    $database = "ece_amazon";
    //connectez-vous dans votre BDD
    $db_handle = mysqli_connect('localhost', 'root', '');
    $db_found = mysqli_select_db($db_handle, $database);
    //Si la BDD est trouvée
    if($db_found){
        
    //recherche d'une connexion
    //on recherche dans les acheteurs
    $sql0 = "SELECT * FROM acheteurs WHERE connexion = 1";
    $result = mysqli_query($db_handle,$sql0);
    if(mysqli_num_rows($result) == 0){ //si ce n'est pas un acheteur
        //on recherche dans les vendeurs
        $sql0 = "SELECT * FROM vendeurs WHERE connexion = 1";
        $result = mysqli_query($db_handle,$sql0);
        if(mysqli_num_rows($result) != 0){ //si c'est un vendeur
            
            //on recherche dans les vendeurs
            $data = mysqli_fetch_array($result);
            $user_vendeur = $data['email'];
            
            //fond
            $fond = $data['fond'];

            //on recherche si c'est un admin
            if($data['ID'] == 6){
                $user_admin = $data['email'];
                
            } else {
                $user_admin = "";
            }
            
        } else {
            $user_vendeur = "";
        }
    
        $user_acheteur = "";
        
    } else { //si c'est un acheteur
        $data = mysqli_fetch_array($result);
        $user_acheteur = $data['email'];
        
        //donc ce n'est ni un vendeur ni un admin
        $user_vendeur = "";
        $user_admin = "";
    }
?>
<!DOCTYPE html>

<!-- OUVERTURE DE LA BDD POUR ENSUITE UTILISER PHP DANS LE CODE -->
<?php
    //identifier votre BDD
    $database = "ece_amazon";
    //connectez-vous dans votre BDD
    $db_handle = mysqli_connect('localhost', 'root', '');
    $db_found = mysqli_select_db($db_handle, $database);
    //Si la BDD est trouvée
    if($db_found){
?>

<html>
<head>
    <!-- TITRE A CHANGER POUR CHAQUE PAGE -->
    <title>Vêtements | ECE Amazon</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- LIEN VERS LA PAGE DE CSS A CHANGER SELON LA PAGE -->
    <link rel="stylesheet" type="text/css" href="menu.css">
    <link rel="stylesheet" type="text/css" href="livres.css">
    <script type="text/javascript"> 
        $(document).ready(function(){
            $('body').css('background-image','url(<?php echo $fond; ?>)');
            $('.fondheader').css('background-color','white');
        });
    </script>

</head>
    
<body>
    <!-- BARRE DE NAVIGATION A CONSERVER POUR CHAQUE PAGE -->
    <nav class="navbar navbar-expand-md">
        <!-- Logo du site -->
        <a class="navbar-brand" href="menu.php"><img src="logo.png" alt="logo" height="70px" width="70px"></a>
        <!-- Hamburger des items lorsque la fenêtre rétrécit -->
        <button class="navbar-toggler navbar-dark" type="button" data-toggle="collapse" data-target="#navbarCategories">
            <span class="navbar-toggler-icon"></span>
        </button>
        <!-- Pages du site accessibles via le menu -->
        <div class="collapse navbar-collapse" id="navbarCategories">
            <ul class="navbar-nav mr-auto">
                <!-- item Catégories qui se déroule pour laisser paraître ses sous-items -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown">Catégories</a>
                    <!-- Sous-items -->
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="livres.php?click0=1">Livres</a>
                        <a class="dropdown-item" href="musique.php?click0=1">Musique</a>
                        <a class="dropdown-item" href="vetements.php?click0=1">Vêtements</a>
                        <a class="dropdown-item" href="sports_et_loisir.php?click0=1">Sports et Loisir</a>
                    </div>
                </li>
                <!-- item Ventes Flash -->
                <li class="nav-item">
                    <a class="nav-link" href="ventes_flash.php">Ventes Flash</a>
                </li>
                <!-- item Vendre -->
                <li class="nav-item">
                    <?php
                       if($user == ""){
                           echo '<a class="nav-link" href="connexion.php">';
                       } else if(($user == $user_vendeur) && ($user != $user_admin)){
                           echo '<a class="nav-link" href="ajout_item.php">'; 
                       } else if(($user == $user_acheteur) || ($user == $user_admin)){
                           echo '<a class="nav-link" href="#">';
                       }
                        echo 'Vendre</a>';
                    ?>
                </li>
                <!-- item Votre compte -->
                <li class="nav-item">
                    <?php
                       if($user == ""){
                           echo '<a class="nav-link" href="connexion.php">';
                       } else if($user == $user_acheteur){
                          echo '<a class="nav-link" href="votre_compte.php">'; 
                       } else if($user == $user_vendeur){
                            echo '<a class="nav-link" href="#">'; 
                       }
                        echo 'Votre compte</a>';
                    ?>
                </li>
                <!-- item Mon profil-->
                <li class="nav-item">
                    <?php
                       if($user == ""){
                           echo '<a class="nav-link" href="connexion.php">';
                       } else if($user == $user_vendeur){
                            echo '<a class="nav-link" href="mon_profil.php">'; 
                       } else if($user == $user_acheteur){
                            echo '<a class="nav-link" href="#">';
                       }
                        
                        echo 'Mon profil</a>';
                    ?>
                </li>
            </ul>
            <!-- item Connexion/Inscription à gauche -->
            <p class="nav-item left">
                <?php
                    if($user == ""){
                        echo '<a class="nav-link" href="connexion.php">Connexion | Inscription</a>';
                    } else {
                        echo '<form method="post" class="deco"><input style="background-color:#A02831;border:none" type="submit" class="nav-link" name="deconnect" value="'.$user.' | Déconnexion"></form>'; 
                    }
                ?>
                <!-- DECONNEXION -->
                <?php
                    if(isset($_POST['deconnect'])){
                        $sql="UPDATE vendeurs SET connexion = 0 WHERE email = '$user'";
                        $result = mysqli_query($db_handle,$sql);
                        
                        $sql="UPDATE acheteurs SET connexion = 0 WHERE email = '$user'";
                        $result = mysqli_query($db_handle,$sql);
                        
                        $_SESSION['user'] = "";
                        $user = $_SESSION['user'];
                        echo '<script language="Javascript">document.location.replace("menu.php");</script>';
                    }
                ?>
            <!-- item Mon panier -->
             <p class="nav-item left">
                 <?php
                    if(($user == "")||($user==$user_acheteur)){
                        echo '<a class="nav-link" href="mon_panier.php">';
                    
                    } else {
                        echo '<a class="nav-link" href="#">'; 
                    }
               
                    echo '<img src="panier.png" alt="logo" height="30px" width="25px"></a>';
                 ?>
            </p>
        </div>
    </nav> <!-- FIN DE LA BARRE DE NAVIGATION -->

    <header>
        <div class="container-fluid">
            <!-- Première Ligne pour la bannière -->
            <!-- 1ère colonne pour afficher les catégories -->
            <div class="row">
                <div class="col-md-2">
                    <ul class="navbar-nav mr-auto">
                        <br>
                    <li class="nav-item dropdown">
                    <div class="list-group" id="list-group" aria-labelledby="navbarDropdown2">
                        <a style="color:#f6745a" class="list-group-item" href="vetements.php?click0=1" clicked>Tout voir</a><br>
                        <a class="nav-link dropdown-toggle" id="navbarDropdown2" role="button" data-toggle="dropdown">Femme</a>
                            <div class="list-group" id="list-group" aria-labelledby="navbarDropdown2">
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click=1">Haut</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click1=1">Bas</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click2=1" >Chaussures</a>
                            </div><br>
                        <a class="nav-link dropdown-toggle" id="navbarDropdown2" role="button" data-toggle="dropdown">Homme</a>
                            <div class="list-group" id="list-group" aria-labelledby="navbarDropdown2">
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click3=1" >Haut</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click4=1" >Bas</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click5=1" >Chaussures</a>
                            </div><br>
                        <a class="nav-link dropdown-toggle" id="navbarDropdown2" role="button" data-toggle="dropdown">Couleur</a>
                            <div class="list-group" id="list-group" aria-labelledby="navbarDropdown2">
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click6=1">Blanc</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click7=1">Bleu</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click8=1">Gris</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click9=1">Jaune</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click10=1">Marron</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click11=1">Noir</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click12=1">Orange</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click13=1">Rouge</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click14=1">Rose</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click15=1">Vert</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click16=1">Violet</a>
                            </div><br>
                        <a class="nav-link dropdown-toggle" id="navbarDropdown2" role="button" data-toggle="dropdown">Matière</a>
                            <div class="list-group" id="list-group" aria-labelledby="navbarDropdown2">
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click17=1">Coton</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click18=1">Cuir</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click19=1">Daim</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click20=1">Fausse fourrure</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click21=1">Jean</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click22=1">Lin</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click23=1">Plastique</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click24=1">Polyester</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click25=1">Soie</a>
                                <a style="color:#f6745a" class="list-group-item" href="vetements.php?click26=1">Toile</a>
                            </div><br>
                    </div>
                    </li>                
                    </ul>   
                </div>

            <!-- 2ème colonne pour afficher les derniers produits mis en vente -->
            <div class="col-md-10">
                <div class="row">
                <!-- Affichage de la bannière -->
                    <img src="vetements.jpg" alt="Vêtements" class="img-fluid">
                </div>
                <br><br>
                <div class="row">
                <?php
                    
                  if(isset($_GET["click0"])){
                      
                        //recherche tous les vetements
                        $sql = "SELECT * FROM vetements" ;
                        $result = mysqli_query($db_handle, $sql);

                        while($data = mysqli_fetch_array($result)){

                            //Affichage de l'image des produits
                            echo '<div class="col-md-4">';
                            echo '<div class="product-grid">';
                            echo '<div class="product-image">';
                            print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                            echo '<span class="product-trend-label"></span>';
                            echo '<ul class="social">
                                    <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                    <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                </ul>';
                            echo '</div>';
                            echo '<div class="product-content">';
                            echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                            echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                            echo '</div>';
                            echo '</div>';
                            echo '</div>';
                        }
                  }
                ?>
                <?php
            
                    //recherche tous les hauts de femme
                    
                    if (isset($_GET["click"])) {
                                 
                    $sql = "SELECT * FROM `vetements` WHERE genre ='femme' AND type='haut'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        
                        } 
                ?>
                <?php
                    
                    //recherche tous les bas de femme
                    if (isset($_GET["click1"])) {
                    $sql = "SELECT * FROM `vetements` WHERE genre ='femme' AND type='bas'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        } 
                ?>
                <?php
                    //recherche toutes les chaussures de femme

                    if (isset($_GET["click2"])) {
                    $sql = "SELECT * FROM `vetements` WHERE genre ='femme' AND type='chaussures' " ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les hauts d'homme
                    if (isset($_GET["click3"])) {
                    $sql = "SELECT * FROM `vetements` WHERE genre ='homme' AND type='haut'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les bas d'homme

                    if (isset($_GET["click4"])) {
                    $sql = "SELECT * FROM `vetements` WHERE genre ='homme' AND type='bas' " ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche toutes les chaussures d'homme

                    if (isset($_GET["click5"])) {
                    $sql = "SELECT * FROM `vetements` WHERE genre ='homme' AND type='chaussures'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements blancs

                    if (isset($_GET["click6"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='blanc'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements bleu

                    if (isset($_GET["click7"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='bleu'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements gris

                    if (isset($_GET["click8"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='gris' " ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements jaune

                    if (isset($_GET["click9"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='jaune'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>
                <?php
                    //recherche tous les vêtements marron

                    if (isset($_GET["click10"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='marron'";
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements noir

                    if (isset($_GET["click11"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='noir'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous vêtements orange

                    if (isset($_GET["click12"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='orange'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements rouges

                    if (isset($_GET["click13"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='rouge'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements roses

                    if (isset($_GET["click14"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='rose'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements verts

                    if (isset($_GET["click15"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='vert'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements violets

                    if (isset($_GET["click16"])) {
                    $sql = "SELECT * FROM `vetements` WHERE couleur='violet'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en coton

                    if (isset($_GET["click17"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='coton'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>


                <?php
                    //recherche tous les vêtements en cuir

                    if (isset($_GET["click18"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='cuir'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en daim

                    if (isset($_GET["click19"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='daim'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en fausse fourrure

                    if (isset($_GET["click20"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='fausse fourrure'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en jean

                    if (isset($_GET["click21"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='jean'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en lin

                    if (isset($_GET["click22"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='lin'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en plastique

                    if (isset($_GET["click23"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='plastique'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en coton

                    if (isset($_GET["click24"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='polyester'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';;
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en soie

                    if (isset($_GET["click25"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='soie'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

                <?php
                    //recherche tous les vêtements en toile

                    if (isset($_GET["click26"])) {
                    $sql = "SELECT * FROM `vetements` WHERE matiere='toile'" ;
                    $result = mysqli_query($db_handle, $sql);

                            while($data = mysqli_fetch_array($result)){
                                
                                //Affichage de l'image des produits
                                echo '<div class="col-md-4">';
                                echo '<div class="product-grid">';
                                echo '<div class="product-image">';
                                print '<img src="'. $data['photos'] .'" alt="' . $data['photos']. '" class="img-fluid"';
                                echo '<span class="product-trend-label"></span>';
                                echo '<ul class="social">
                                        <li><a href="" data-tip="Add to cart"><i class="fa fa-shopping-cart"></i></a></li>
                                        <li><a href="redirection_vetements.php" data-tip="Quick view"><i class="fa fa-search"></i></a></li>
                                    </ul>';
                                echo '</div>';
                                echo '<div class="product-content">';
                                echo '<center><h4 style="color:#f6745a" class="title">'.$data['nom'].'</h4></center>';
                                echo '<center><h5><B>'  . $data['prix'] . '€ </B></h5></center></br></br>';
                                echo '</div>';
                                echo '</div>';
                                echo '</div>';
                            }
                        }

                ?>

            </div>
            </div>
            </div>    
        </div>
    </header>
    
<!-- BARRE DE FOOTER A CONSERVER POUR CHAQUE PAGE -->
    <footer class="footer_page">
        <div class="container">
            <div class="row">
                <!-- Informations supplémentaires -->
                <div class="col-lg-4 col-md-4 col-sm-12">
                    <h6 class="text-uppercase font-weight-bold">Informations additionnelles</h6> 
                    <p><b>Livraison</b> <br/> Sous 3 - 5 jours ouvrables, à domicile ou en point relais, sous presentation d'un justificatif de la commande et d'une pièce d'identité. </p> 
                    <p><b>Remboursement</b> <br/> Retour et échange gratuit sous un délais de 30 jours à partir de la réception du colis.</p>
                </div>
                <!-- Contact et connexion administration -->
                <div class="col-lg-4 col-md-4 col-sm-12">
                    <h6 class="text-uppercase font-weight-bold">Nous contacter</h6>
                    <p>
                        37, quai de Grenelle, 75015 Paris, France <br>
                        <a class="bottom" href="mailto:contact@ece-amazon.fr">contact@ece-amazon.fr</a><br>
                        +330102030405<br>
                    </p>
                    <h6>
                        <?php
                           if($user == ""){
                                echo '<a class="bottom" href="connexion.php">';
                           } else if($user == $user_admin){
                                echo '<a class="bottom" href="administration.php">'; 
                           } else {
                                echo '<a class="bottom" href="#">'; 
                           }
                            echo 'Connexion Administrateur</a>';
                        ?>
                     </h6>
                </div>
                <!-- Formulaire de contact -->
                <div class="col-lg-4 col-md-4 col-sm-12">
                    <h6 class="text-uppercase font-weight-bold">Des questions ? Contactez-nous !</h6>

                    <form method="POST" action="" >
                        <table>
                            <td><tr><div class="form-group"><input type="text" class="form-control" placeholder="Votre nom:" name="nom"> </div></tr></td>
                            <td><tr><div class="form-group"><input type="email" class="form-control" placeholder="Courriel:" name="email"></div></tr></td>
                            <td><tr><div class="form-group"><textarea class="form-control" rows="4" name="commentaires" placeholder="Vos commentaires"></textarea></div></tr> </td>
                            <td><tr><input type="submit" class="envoie btn btn-secondary btn-block" name="button_commentaires" value="Envoyer" ></tr></td>
                        </table>
                    </form>
                    <?php
                        if (isset($_POST['button_commentaires'])) {
                            //Verification si des cases sont vides
                            if(empty($_POST["nom"]) AND empty($_POST["email"]) AND empty($_POST["commentaires"])) {
                                $erreur = '<font color="red">Tous les champs nont pas été remplis. </font>';
                            }
                            else{
                                // on vérifie si le champ "nom" n'est pas vide
                                if(empty($_POST["nom"])) {
                                    $erreur = '<font color="red">Le champ nom est vide.</font>';
                                }
                                else {
                                    // on vérifie si le champ "Email" n'est pas vide
                                    if(empty($_POST["email"])) {
                                        $erreur = '<font color="red">Le champ Email est vide.</font>';
                                    } 
                                    else{
                                        // on vérifie si le champ "Commentaires" n'est pas vide
                                        if(empty($_POST["commentaires"])) {
                                            $erreur = '<font color="red">Le champ Commentaires est vide.<font>';
                                        }
                                        else{
                                            $erreur = '<font color="green">Votre message a bien été envoyé !</font';
                                        }
                                    }
                                }
                            }
                        }
                        if(isset($erreur)) {
                            echo $erreur;
                        }
                    ?>
                </div>
            </div>
            <!-- Copyright -->
            <div class="footer-copyright text-center">&copy; 2019 Copyright | Droit d'auteur: Equipe 36 Projet Web Dynamique
            </div>
        </div>
    </footer> <!-- FIN BARRE DE FOOTER -->
</body>
</html>
<!-- FERMETURE DE LA BDD -->
<?php    
    //affichage d'erreur si la BDD non trouvée et fermeture de la BDD
    } else {
         echo "Database not found.";
    }

    mysqli_close($db_handle);
?>
<!-- FERMETURE DE LA BDD -->
<?php    
    //affichage d'erreur si la BDD non trouvée et fermeture de la BDD
    } else {
         echo "Database not found.";
    }

    mysqli_close($db_handle);
?>