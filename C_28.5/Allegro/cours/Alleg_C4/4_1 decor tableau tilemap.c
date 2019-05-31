/**************************************************************************
DECOR TILEMAP: Ce programme construit un decor � l'�cran
							 en utilisant des �l�ments graphiques standardis�s
							 � partir d'une matrice 2D d�crivant le terrain
**************************************************************************/

#include <allegro.h>

// Pour plus de simplicit� : constantes des tuiles utilis�es en globale
// ( pour g�rer plusieurs ensembles de tuiles il faudrait
//	 mettre ces informations dans une structure ... )

#define NTUILE 6		// Nombre de tuiles graphiques utilis�es
#define TXTUILE 100 // Largeur des tuiles
#define TYTUILE 100 // Hauteur des tuiles
#define NCOLTUILE 3 // Nombre de colonnes de tuiles dans le fichier image

#define NXMAP 8 // Nombre de tuiles en largeur sur le terrain
#define NYMAP 6 // Nombre de tuiles en hauteur sur le terrain

// Sur cet exemple on a un seul ensemble de tuiles
// ( on s'autorise une globale, l� encore pour simplifier )
BITMAP *tableTuiles;

// Un seul terrain de jeu
int terrainExemple[NYMAP][NXMAP] = {
		{1, 2, 1, 2, 1, 2, 1, 2}, {2, 0, 3, 3, 3, 3, 0, 1},
		{1, 3, 4, 5, 4, 5, 3, 2}, {2, 3, 5, 4, 5, 4, 3, 1},
		{1, 0, 3, 3, 3, 3, 0, 2}, {2, 1, 2, 1, 2, 1, 2, 1},
};

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

// Dessine un terrain complet sur une bitmap
// (en g�n�ral on dessinera sur le buffer, ici on utilise directement screen)
void dessineTerrain(BITMAP *bmp, int terrain[NYMAP][NXMAP]) {
	int xmap, ymap;

	for (ymap = 0; ymap < NYMAP; ymap++)
		for (xmap = 0; xmap < NXMAP; xmap++)
			dessineTuile(bmp, terrain[ymap][xmap], xmap, ymap);
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

	// Dessiner le terrain d'exemple
	dessineTerrain(screen, terrainExemple);

	// Boucle de jeu (rien pour l'instant)
	while (!key[KEY_ESC]) {
	}

	return 0;
}
END_OF_MAIN();
