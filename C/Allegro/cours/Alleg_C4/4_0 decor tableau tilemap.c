/**************************************************************************
DECOR TILEMAP: Ce programme construit al�atoirement un decor � l'�cran
							 en utilisant des �l�ments graphiques standardis�s
							 de taille 100x100 ( tuiles )

							 L'image finale est assembl�e en juxtaposant ces �l�ments,
							 s�lectionn�s au hasard, dans une table de tuiles.

							 Appuyer sur une touche pour refaire un nouveau labyrinthe.

**************************************************************************/

#include <allegro.h>
#include <time.h>

// Pour plus de simplicit� : constantes des tuiles utilis�es en globale
// ( pour g�rer plusieurs ensembles de tuiles il faudrait
//	 mettre ces informations dans une structure ... )

#define NTUILE 6		// Nombre de tuiles graphiques utilis�es
#define TXTUILE 100 // Largeur des tuiles
#define TYTUILE 100 // Hauteur des tuiles
#define NCOLTUILE 3 // Nombre de colonnes de tuiles dans le fichier image

// Sur cet exemple on a un seul ensemble de tuiles
// ( on s'autorise une globale, l� encore pour simplifier )
BITMAP *tableTuiles;

/******************************************/
/*				 SOUS-PROGRAMMES								*/
/******************************************/

// Dessine la tuile num�ro ituile sur la bitmap bmp au coordonn�es xmap ymap
// (unit� en nombre de tuiles, pas en pixels)
void dessineTuile(BITMAP *bmp, int ituile, int xmap, int ymap) {
	int lig, col; // ligne et colonne de la tuile source dans tableTuiles

	// Le num�ro de ligne et de colonne dans un tableau 2D
	// � partir de l'indice du i�me �l�ment (en comptant de gauche � droite
	// et de haut en bas) s'obtiennent avec le quotient et le reste
	// dans la division de l'indice par le nombre de colonnes du tableau
	lig = ituile / NCOLTUILE;
	col = ituile % NCOLTUILE;

	// on copie juste le morceau concern�
	blit(tableTuiles, bmp, col * TXTUILE, lig * TYTUILE, xmap * TXTUILE,
			 ymap * TYTUILE, TXTUILE, TYTUILE);
}

// Chargement "s�curis�" d'une image :
// interrompt le programme avec un message si probl�me...
BITMAP *load_bitmap_check(char *nomImage) {
	BITMAP *bmp;
	bmp = load_bitmap(nomImage, NULL);
	if (!bmp) {
		allegro_message("pas pu trouver %s", nomImage);
		exit(EXIT_FAILURE);
	}
	return bmp;
}

/******************************************/
/* PROGRAMME PRINCIPAL										*/
/* initialisation puis boucle de jeu			*/
/******************************************/

int main() {
	int xmap, ymap;

	srand(time(NULL));

	// Lancer allegro et le mode graphique
	allegro_init();
	install_keyboard();

	set_color_depth(desktop_color_depth());
	if (set_gfx_mode(GFX_AUTODETECT_WINDOWED, 800, 600, 0, 0) != 0) {
		allegro_message("prb gfx mode");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// Charger le fichier avec les tuiles
	tableTuiles = load_bitmap_check("images/tilemapLaby/tableTuiles.bmp");

	// Boucle de jeu : dessiner des labyrinthes
	while (!key[KEY_ESC]) {
		// Cr�er labyrinthe directement � l'�cran
		for (ymap = 0; ymap < 6; ymap++)
			for (xmap = 0; xmap < 8; xmap++)
				dessineTuile(screen, rand() % 6, xmap, ymap);

		// Attendre l'appui d'une touche
		readkey();
	}

	return 0;
}
END_OF_MAIN();
