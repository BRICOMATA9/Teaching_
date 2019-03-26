/*****************************************************************************
BITMAP : Charger une image depuis un fichier .bmp vers une BITMAP puis
				 effectuer un traitement pixel par pixel (n�gatif) vers autre BITMAP
				 Afficher l'original et le r�sultat
*****************************************************************************/

#include <allegro.h>

int main() {
	// D�claration du pointeur sur BITMAP devant recevoir l'image
	BITMAP *image;

	// Pointeur sur BITMAP devant recevoir le r�sultat du traitement
	BITMAP *imgneg;

	int x, y;
	int cim, r, g, b;

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

	// Affichage de l'image originale sur l'�cran en haut � gauche
	blit(image, screen, 0, 0, 0, 0, image->w, image->h);

	// IMAGE EN NEGATIF

	// Cr�er
	imgneg = create_bitmap(image->w, image->h);

	// Remplir
	for (y = 0; y < image->h; y++)
		for (x = 0; x < image->w; x++) {
			// R�cup�rer la couleur de l'image en x,y
			cim = getpixel(image, x, y);

			// D�composer les 3 composantes rouge vert bleu
			r = getr(cim);
			g = getg(cim);
			b = getb(cim);

			// Inverser lin�airement	0 -> 255	et	255 -> 0
			r = 255 - r;
			g = 255 - g;
			b = 255 - b;

			// Mettre cette nouvelle couleur dans la BITMAP destination
			putpixel(imgneg, x, y, makecol(r, g, b));
		}

	// Afficher
	blit(imgneg, screen, 0, 0, image->w, 0, image->w, image->h);

	// Boucle interactive (vide sur cet exemple, juste pour attendre...)
	while (!key[KEY_ESC]) {
	}

	return 0;
}
END_OF_MAIN();
