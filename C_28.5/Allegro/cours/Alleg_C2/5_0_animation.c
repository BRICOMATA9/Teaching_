/**************************************************************************
ANIMATION : une primitive graphique (disque) et une image (.bmp charg�)
						bougent ensemble � l'�cran (rebonds sur les bords)

						1�re version : effacer ancienne position (pas de double buffer)
**************************************************************************/

#include <allegro.h>

int main() {
	// D�claration du pointeur sur BITMAP devant recevoir l'image
	BITMAP *image;

	// param�tres de l'�l�ment 1 � animer (disque)
	int posx1, posy1; // coordonn�es
	int rayon1;			 // taille (rayon du disque)
	int depx1, depy1; // vecteur d�placement effectif en x et y

	// param�tres de l'�l�ment 2 � animer (image)
	int posx2, posy2; // coordonn�es
	int tx2, ty2;		 // taille (largeur et hauteur)
	int depx2, depy2; // vecteur d�placement effectif en x et y

	// Lancer allegro et le mode graphique
	allegro_init();
	install_keyboard();

	set_color_depth(desktop_color_depth());
	if (set_gfx_mode(GFX_AUTODETECT_WINDOWED, 800, 600, 0, 0) != 0) {
		allegro_message("prb gfx mode");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// Chargement de l'image
	image = load_bitmap("earth.bmp", NULL);
	if (!image) {
		allegro_message("pas pu trouver/charger earth.bmp");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// Initialisation des param�tres de l'�l�ment 1
	rayon1 = 40;
	posx1 = rayon1;
	posy1 = rayon1;
	depx1 = 5;
	depy1 = 9;

	// Initialisation des param�tres de l'�l�ment 2
	tx2 = image->w;
	ty2 = image->h;
	posx2 = SCREEN_W / 2 - tx2 / 2;
	posy2 = SCREEN_H / 2 - ty2 / 2;
	depx2 = 9;
	depy2 = 5;

	// Boucle d'animation (pas d'interaction)
	while (!key[KEY_ESC]) {
		// 1) EFFACER POSITIONs ACTUELLEs (redessiner � la couleur du fond)
		circlefill(screen, posx1, posy1, rayon1, makecol(0, 0, 0));
		rectfill(screen, posx2, posy2, posx2 + tx2, posy2 + ty2, makecol(0, 0, 0));

		// 2) DETERMINER NOUVELLEs POSITIONs

		// contr�le des bords : ici on d�cide de rebondir sur les bords
		if ((posx1 - rayon1 < 0 && depx1 < 0) ||
				(posx1 + rayon1 > SCREEN_W && depx1 > 0))
			depx1 = -depx1;
		if ((posy1 - rayon1 < 0 && depy1 < 0) ||
				(posy1 + rayon1 > SCREEN_H && depy1 > 0))
			depy1 = -depy1;

		if ((posx2 < 0 && depx2 < 0) || (posx2 + tx2 > SCREEN_W && depx2 > 0))
			depx2 = -depx2;
		if ((posy2 < 0 && depy2 < 0) || (posy2 + ty2 > SCREEN_H && depy2 > 0))
			depy2 = -depy2;

		// calculer nouvelles positions
		// nouvelle position = position actuelle + deplacement
		posx1 = posx1 + depx1;
		posy1 = posy1 + depy1;

		posx2 = posx2 + depx2;
		posy2 = posy2 + depy2;

		// 3) AFFICHAGE NOUVELLEs POSITIONs
		circlefill(screen, posx1, posy1, rayon1, makecol(200, 255, 200));
		blit(image, screen, 0, 0, posx2, posy2, tx2, ty2);

		// 4) ON FAIT UNE PETITE PAUSE � chaque fois sinon �a va trop vite...
		rest(20);
	}

	return 0;
}
END_OF_MAIN();
