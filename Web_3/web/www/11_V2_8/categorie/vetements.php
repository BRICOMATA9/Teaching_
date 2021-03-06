<!DOCTYPE html>
<!--
	Auteurs : Clément LARIVIERE	TD10
			  Théo MERCIER		TD04
			  Antoine PAINCHAUX TD01
-->
<html lang="fr">

<!-- Header -->
<?php include("header.php");?>

<?php session_start(); ?>

<style type="text/css">
	#all_blocks
	{
		display : block;
		float : center;
		align-items:center;
	}

	#itemAff
	{
		background-color:#e9ecef;
		display : block;
		text-align:left;
		margin:10px;
		padding:5px;
		border: solid grey;
		width: 350px;
		height: auto;
		min-height : 360px;
		float:left;
		p 
		{
			font-size: 80%;
		}
		
	}
	#block_bouton
	{
		display:block;
		position : absolute;
		bottom : 0; 
		margin:10px;
	}
	#attributs
	{
		font-size: 90%;
	}
	
	footer
	{
		display : block;
	}
	
</style>

<?php
function subMyString( $contenu, $limite, $separateur = '...' ) {
    if( strlen($contenu) >= $limite ) {
        $contenu = substr( $contenu, 0, $limite );
        $contenu = substr( $contenu, 0, strrpos($contenu, ' ') );
        $contenu .= $separateur;
    }
     
    return $contenu;
}
?>

<body>
	<main role="main">

		<div class="jumbotron">
			<div class="container">
			  <h1 class="display-3">Vêtements</h1>
			  <p>Achetez vos vêtements parmi plus d'1 million d'articles : t-shirt, jean, chemise, casquette, sac, chaussures, lunettes, veste, pulll... <br>Dans cette e-boutique, nous nous efforçons de vous proposer les meilleurs articles aux meilleurs prix ! </p>
			</div>
		</div>

		<div class="container">

			<div id="all_blocks">
				<?php

				$database = "ece_amazon";

				try
				{
				  $bdd = new PDO('mysql:host=localhost;dbname=ece_amazon;charset=utf8', 'root', 'root');
				}
				catch (Exception $e)
				{
					die('Erreur : ' . $e->getMessage());
				}
				  
				$reponse = $bdd->query('SELECT * FROM items INNER JOIN clothing ON items.id=clothing.id_item');	

				while ($donnees = $reponse->fetch())
				{
					?>
					<div class="col-md-4" id="itemAff">
						<p>
							<div id="block_titre" class = "container">
								<h4><?php echo subMyString($donnees['name'],40,'...'); ?></h4>
							</div>
							<div id="block_principal" class = "container">
								<table>
									<tr>
									<td id="attributs">Prix :</td>
									<td><?php echo $donnees['price']; echo "€"?></td>
									</tr>

									<tr>
									<td id="attributs">Type :</td>
									<td id="attributs"><?php echo $donnees['type']; ?></td>
									</tr>

									<tr>
									<td id="attributs">Taille :</td>
									<td id="attributs"><?php echo $donnees['size']; ?></td>
									</tr>

									<tr>
									<td id="attributs">Etat :</td>
									<td id="attributs"><?php echo $donnees['shape']; ?></td>
									</tr>
									
									<tr>
									<td id="attributs"><br></td>
									</tr>

								</table>
								<?php echo subMyString( $donnees['description'], 150, '...' ); ?>
							</div>
							<div id="block_bouton" class = "container">
								<a class="btn btn-warning" id="bouton_annonce" href="../Article/pageArticleClothings.php?id=<?php echo $donnees['id'] ?>" role="button">Voir l'offre &raquo;</a>
							</div>
						</p>
					</div>

				<?php
					}
					$reponse->closeCursor(); // Termine le traitement de la requête
				?>
			</div>
			<hr>
		</div> <!-- /container -->
	</main>
</body>

<!-- Footer -->
<!--<=?php include("footer.php");?>-->

</html>
