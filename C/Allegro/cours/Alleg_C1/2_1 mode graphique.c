/********************************************************
Ouverture prot�g�e d'un mode graphique en 800 x 600
********************************************************/

#include <allegro.h>

int main(int argc, char *argv[]) {
	// initialisation allegro obligatoire
	allegro_init();

	// pour disposer du clavier
	install_keyboard();

	// d�finir un mode graphique
	// d�tection et sortie anticip�e en cas de probl�me
	set_color_depth(desktop_color_depth());
	if (set_gfx_mode(GFX_AUTODETECT_WINDOWED, 800, 600, 0, 0) != 0) {
		allegro_message("probleme mode graphique");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// attend une touche pour quitter (similaire getch() de conio.h)
	readkey();

	return 0;
}
// attention ne pas oublier !
END_OF_MAIN();
