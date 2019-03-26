/**************************************************************************
ANIMATION : 10 disques bougent � l'�cran (rebondissent sur les bords)
						avec double buffer (pas de clignotement)

ATTENTION : CECI EST UNE PREMIERE MANIERE D'AVOIR N ELEMENTS ANIMES
						EN MEME TEMPS EN UTILISANT DES TABLEAUX DE PARAMETRES.
						UNE MANIERE BEAUCOUP PLUS PROPRE ET EFFICACE UTILISANT
						DES TABLEAUX DE STRUCTURES SERA EXPOSE DANS LA SUITE...
**************************************************************************/

#include <allegro.h>
#include <time.h>

// Utilisation d'une constante pour le nombre d'�l�ment.
// Une constante est n�cessaire car on ne peut pas
// (en principe en C standard) utiliser une taille variable
// pour d�clarer un tableau automatique.
#define NELEM 10

int main() {
	// BITMAP servant de buffer d'affichage
	BITMAP *page;

	// param�tres des �l�ments � animer (disques) : tableaux
	int posx[NELEM], posy[NELEM]; // coordonn�es
	int rayon[NELEM];						 // tailles (rayons des disques)
	int depx[NELEM], depy[NELEM]; // vecteurs d�placement effectifs en x et y
	int couleur[NELEM];					 // couleurs des disques
	int i;

	// On va utiliser du hasard
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

	// CREATION DU BUFFER D'AFFICHAGE � la taille de l'�cran
	page = create_bitmap(SCREEN_W, SCREEN_H);
	clear_bitmap(page);

	// Initialisation al�atoire des param�tres des �l�ments
	for (i = 0; i < NELEM; i++) {
		rayon[i] = rand() % 40 + 1;
		posx[i] = rand() % (SCREEN_W - 2 * rayon[i]) + rayon[i];
		posy[i] = rand() % (SCREEN_H - 2 * rayon[i]) + rayon[i];

		// Vitesse al�atoire sym�trique
		// avec composantes horizontales et verticales non nulles
		do {
			depx[i] = rand() % 21 - 10;
			depy[i] = rand() % 21 - 10;
		} while (depx[i] == 0 || depy[i] == 0);

		// Couleur pas trop sombre
		couleur[i] =
				makecol(rand() % 128 + 128, rand() % 128 + 128, rand() % 128 + 128);
	}

	// Boucle d'animation (pas d'interaction)
	while (!key[KEY_ESC]) {
		// 1) EFFACER POSITIONs ACTUELLEs SUR LE BUFFER
		//		ON EFFACE TOUT LE BUFFER ! (c'est plus simple)
		clear_bitmap(page);

		// 2) DETERMINER NOUVELLEs POSITIONs
		for (i = 0; i < NELEM; i++) {
			// contr�le des bords : ici on d�cide de rebondir sur les bords
			if ((posx[i] - rayon[i] < 0 && depx[i] < 0) ||
					(posx[i] + rayon[i] > SCREEN_W && depx[i] > 0))
				depx[i] = -depx[i];
			if ((posy[i] - rayon[i] < 0 && depy[i] < 0) ||
					(posy[i] + rayon[i] > SCREEN_H && depy[i] > 0))
				depy[i] = -depy[i];

			// calculer nouvelle position
			// nouvelle position = position actuelle + deplacement
			posx[i] = posx[i] + depx[i];
			posy[i] = posy[i] + depy[i];
		}

		// 3) AFFICHAGE NOUVELLEs POSITIONs SUR LE BUFFER
		//		ON UTILISE page AU LIEU DE screen
		for (i = 0; i < NELEM; i++) {
			circlefill(page, posx[i], posy[i], rayon[i], couleur[i]);
		}

		// 4) AFFICHAGE DU BUFFER MIS A JOUR A L'ECRAN
		//		le nouveau contenu graphique remplace l'ancien
		//		sans que l'ancien ait �t� effac� � l'�cran (pas de clignotement)
		blit(page, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

		// 5) ON FAIT UNE PETITE PAUSE � chaque fois sinon �a va trop vite...
		rest(20);
	}

	return 0;
}
END_OF_MAIN();
