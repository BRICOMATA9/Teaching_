/*******************************************************************
BITMAP : Charger une image depuis un fichier .bmp vers une BITMAP
				 et afficher cette BITMAP au milieu de l'�cran
				 puis animer cette image (effacer ancienne position...)

				 L'image s'apppelle "mon_image.bmp" et doit �tre mise
				 au niveau du r�pertoire de projet (avec le main.c)
				 � moins de modifier le chemin d'acc�s ci dessous...
*******************************************************************/

#include <allegro.h>

int main() {
	// D�claration du pointeur sur BITMAP devant recevoir l'image
	BITMAP *image;

	// param�tres de l'�l�ment � animer
	int posx, posy; // coordonn�es
	int tx, ty;		 // taille (largeur et hauteur)
	int depx, depy; // vecteur d�placement effectif en x et y

	// Lancer allegro et le mode graphique
	allegro_init();
	install_keyboard();

	set_color_depth(desktop_color_depth());
	if (set_gfx_mode(GFX_AUTODETECT_WINDOWED, 800, 600, 0, 0) != 0) {
		allegro_message("prb gfx mode");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// Chargement de l'image (l'allocation a lieu en m�me temps)
	image = load_bitmap("mon_image.bmp", NULL);
	if (!image) {
		allegro_message("pas pu trouver/charger mon_image.bmp");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// Initialisation des param�tres de l'�l�ment � animer

	// La taille est directement r�cup�r�e dans les champs w et h
	tx = image->w;
	ty = image->h;

	// Position initiale au centre
	posx = SCREEN_W / 2 - tx / 2;
	posy = SCREEN_H / 2 - ty / 2;

	// mouvements vers la droite et vers le bas
	// on pourrait initialiser al�atoirement ici...
	depx = 9;
	depy = 5;

	// Boucle d'animation (pas d'interaction)
	while (!key[KEY_ESC]) {
		// 1) EFFACER POSITION ACTUELLE (redessiner � la couleur du fond)
		rectfill(screen, posx, posy, posx + tx, posy + ty, makecol(0, 0, 0));

		// 2) DETERMINER NOUVELLE POSITION

		// contr�le des bords : ici on d�cide de rebondir sur les bords
		if ((posx < 0 && depx < 0) || (posx + tx > SCREEN_W && depx > 0))
			depx = -depx;
		if ((posy < 0 && depy < 0) || (posy + ty > SCREEN_H && depy > 0))
			depy = -depy;

		// calculer nouvelle position
		// nouvelle position = position actuelle + deplacement
		posx = posx + depx;
		posy = posy + depy;

		// 3) AFFICHAGE NOUVELLE POSITION
		//		On blit l'image en position posx posy
		blit(image, screen, 0, 0, posx, posy, tx, ty);

		// 4) ON FAIT UNE PETITE PAUSE � chaque fois sinon �a va trop vite...
		rest(20);
	}

	return 0;
}
END_OF_MAIN();
