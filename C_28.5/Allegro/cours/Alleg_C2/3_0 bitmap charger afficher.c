/*******************************************************************
BITMAP : Charger une image depuis un fichier .bmp vers une BITMAP
				 et afficher cette BITMAP au milieu de l'�cran

				 L'image s'apppelle "mon_image.bmp" et doit �tre mise
				 au niveau du r�pertoire de projet (avec le main.c)
				 � moins de modifier le chemin d'acc�s ci dessous...

				 Appuyer sur espace pour ajouter des copies multiples
*******************************************************************/

#include <allegro.h>
#include <time.h>

int main() {
	// D�claration du pointeur sur BITMAP devant recevoir l'image
	BITMAP *image;

	// Il y aura un peu de hasard...
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

	// Chargement de l'image (l'allocation a lieu en m�me temps)
	image = load_bitmap("mon_image.bmp", NULL);

	// V�rification que l'image est bien charg�e (dans le cas contraire image vaut
	// NULL) TOUJOURS LE FAIRE CAR ON N'EST JAMAIS CERTAIN DE BIEN TROUVER L'IMAGE
	if (!image) {
		allegro_message("pas pu trouver/charger mon_image.bmp");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// Affichage de l'image sur l'�cran au milieu
	blit(image, screen, 0, 0, (SCREEN_W - image->w) / 2,
			 (SCREEN_H - image->h) / 2, image->w, image->h);

	// Boucle interactive
	while (!key[KEY_ESC]) {
		if (key[KEY_SPACE])
			blit(image, screen, 0, 0, rand() % (SCREEN_W - image->w),
					 rand() % (SCREEN_H - image->h), image->w, image->h);
	}

	return 0;
}
END_OF_MAIN();
