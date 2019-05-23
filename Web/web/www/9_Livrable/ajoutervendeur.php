<?php 

session_start();

?>

<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
  <meta name="generator" content="Jekyll v3.8.5">
  <title>Ajouter vendeurs</title><!--Titre barre de navigation internet-->

  <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/checkout/">

  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


  <!--Style de la page internet-->
  <style>
  .bd-placeholder-img {
    font-size: 1.125rem;
    text-anchor: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }
  @media (min-width: 768px) {
    .bd-placeholder-img-lg {
      font-size: 3.5rem;
    }
  }
  /*Style du menu deroulant */         
  #menu-deroulant, #menu-deroulant ul {
    padding: 0;
    margin: 0;
    list-style: none;
  }
  #menu-deroulant {
    /* on centre le menu dans la page */
    text-align: center;
  }
  #menu-deroulant li {
    /* on place les liens du menu horizontalement */
    display: inline-block;
  }
  #menu-deroulant ul li {
    /* on enlève ce comportement pour les liens du sous menu */
    display: inherit;
  }
  #menu-deroulant a {
    text-decoration: none;
    display: block;
    /**color: #FFFFFF;*/
  }
  #menu-deroulant ul {
    position: absolute;
    /* on cache les sous menus complètement sur la gauche */
    left: -999em;
    text-align: left;
    z-index: 1000;
  }
  #menu-deroulant li:hover ul {
    /* Au survol des li du menu on replace les sous menus */
    left: auto;
  }
</style>
<!-- Custom styles for this template -->
<link href="album.css" rel="stylesheet">
</head>


<body>

  <header> <!-- tete de notre site -->
<!-- barre de navigation de notre site -->
    <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-info">
      <a href="index.html" class="navbar-brand d-flex align-items-center">
        <img src="img/pierre.png"></a>
        <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
          <span class="navbar-toggler-icon"></span>
        </button> <!-- Bouton qui revient a notre page d'accueil -->

        <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
          <ul class="navbar-nav mr-auto"> <!--Menu deroulant qui affiche les differentes categories-->
            <ul id="menu-deroulant">
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle text-white" href="categories.html" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Catégories</a>
                <ul>
                  <li><a class="text-dark" href="livres.php">Livres</a></li>
                  <li><a class="text-dark" href="musique.php">Musique</a></li>
                  <li><a class="text-dark"href="vetements.php">Vêtements</a></li>
                  <li><a class="text-dark" href="sport.php">Sport et Loisirs</a></li>
                </ul>

              </li>
            </ul> <!--Bouton de ventes flash-->
            <a class="nav-link text-white" href="ventesflash.php">Ventes Flash</a>
          </ul>

          <form class="form-inline my-2 my-lg-0">
            <a href="panier.php" class="btn btn-lg btn-info"><img src=img/panier.png></a> <!--Bouton panier-->
            <a class="nav-link text-white" href="deco.php">Se déconnecter</a>  <!--Bouton deconnexion-->
          </form>
        </div>
      </nav>
      
    </header>

    <br/> </br>

    <section class="jumbotron text-center">
      <!--Milieu de page de notre site internet-->


      <div class="py-5 text-center text-info">
        <img class="d-block mx-auto mb-4" src="img/creercompte.png" alt="" width="72" height="72">
        <h2>Ajout vendeurs</h2>
        <p class="lead">Vous êtes administrateur ? Vous pouvez ajouter un ou plusieurs vendeurs ! </p>
      </div>
    </section>



    <div class="container">
      <!--Formulaire pour ajouter des vendeurs-->
      <h4 class="mb-3">Formulaire d'ajout d'un vendeur </h4> 
      <form class="needs-validation" novalidate action="creationdecompte.php" method="POST">
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="Nom">Nom du vendeur</label> <!--Ajouter un nom et obligation de rentrer une valeur-->
            <input type="text" class="form-control" name = "Nom" id="Nom" placeholder="Nom du vendeur" value="" required>
            <div class="invalid-feedback">
              Un nom valide est requis. 
            </div>
          </div>
          <div class="col-md-6 mb-3">
            <label for="Prenom">Prénom du vendeur</label> <!--Ajouter un prenom et obligation de rentrer une valeur-->
            <input type="text" class="form-control" id="Prenom" name = "prenom" placeholder="Prénom du vendeur" value="" required>
            <div class="invalid-feedback">
              Un prénom valide est requis. 
            </div>
          </div>
        </div>

        <div class="mb-3"> <!--Ajouter un pseudo et obligation de rentrer une valeur-->
          <label for="pseudo">Pseudo du vendeur <span class="text-muted"></span></label>
          <input type="pseudo" class="form-control" name ="Pseudo" id="pseudo" placeholder="Pseudo du vendeur">
          <div class="invalid-feedback">
            Veuillez entrer un pseudo valide.
          </div>
        </div>

        <div class="mb-3">
          <label for="email">E-mail du vendeur</label> <!--Ajouter un email et obligation de rentrer une valeur-->
          <div class="input-group">
            <div class="input-group-prepend">
              <span class="input-group-text">@</span>
            </div>
            <input type="text" class="form-control" id="pseudo" name = "Email" placeholder="parexemple@gmail.com" required>
            <div class="invalid-feedback" style="width: 100%;">
              Une adresse mail valide est requise.
            </div>
          </div>
        </div>


        <div class="mb-3"> <!--Ajouter un mdp et obligation de rentrer une valeur-->
          <label for="motdepasse">Mot de passe du vendeur<span class="text-muted"></span></label>
          <input type="password" class="form-control" id="motdepasse" name = "mdp" placeholder="Mot de passe du vendeur">
        </div>

        <div class="mb-3"> <!--Ajouter un mdpt et obligation de rentrer une valeur-->
          <label for="confirmation">Confirmation du mot de passe du vendeur <span class="text-muted"></span></label>
          <input type="password" class="form-control" id="confirmation" placeholder="Confirmation du mot de pass du vendeur">
        </div>

        <hr class="mb-4">
        <button class="btn btn-outline-info btn-light btn-lg btn-block" type="submit">Valider l'ajout du vendeur</button> <!--Bouton qui permet l'ajout a la bdd-->
      </form>


    </div>

    <!--Pied de page avec toutes les informations necessaire-->
    <footer class="my-5 pt-5 text-muted text-center text-small">
      <p class="mb-1">Site A MA ZONE &copy; ECE A MA ZONE_2019</p>
      <ul class="list-inline">
        <li class="list-inline-item"><a href="#">Privacy</a></li>
        <li class="list-inline-item"><a href="#">Terms</a></li>
        <li class="list-inline-item"><a href="#">Support</a></li>
      </ul>
      <!--liens vers nos reseaux sociaux -->
      <p>Ou nous retrouver? Visiter notre<a href="https://getbootstrap.com/"> Facebook</a> ou notre page <a href="/docs/4.3/getting-started/introduction/">Instagram</a>.</p>
      <p class="float-right">
        <a href="#">Haut de page</a> <!--lien qui remonte en haut de la page -->
      </p>
    </footer>

  </div> 

  <script src="form-validation.js"></script></body>
  </html> <!--Fermeture du html-->

