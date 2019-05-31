/*************************************************************
Utilisation du clavier : buffer = comme en console avec getch
*************************************************************/

#include <allegro.h>

int main(int argc, char *argv[]) {
	int touche;
	char carac;
	int fin;
	int poscarac;

	srand(time(NULL));

	allegro_init();

	// pour disposer du clavier
	install_keyboard();

	set_color_depth(desktop_color_depth());
	if (set_gfx_mode(GFX_AUTODETECT_WINDOWED, 800, 600, 0, 0) != 0) {
		allegro_message("prb gfx mode");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// A compl�ter si l'objectif est de saisir une chaine
	// (enregistrer les caract�res, position curseur mobile, effacer...)
	poscarac = 0;
	fin = 0;
	while (!fin) {

		// keypressed() : �quivalent allegro du kbhit()
		if (keypressed()) {
			// r�cup�rer la touche avec readkey() : �quivalent allegro du getch()
			touche = readkey();

			// a partir de l'info de touche on obtient le caract�re en castant en char
			carac = (char)touche;

			// afficher le caract�re toujours � la m�me position
			textprintf_ex(screen, font, 400, 60, makecol(255, 255, 255),
										makecol(0, 0, 0), "%c", carac);

			// afficher le caract�re � une position x croissante
			textprintf_ex(screen, font, 400 + poscarac, 100, makecol(255, 255, 255),
										makecol(0, 0, 0), "%c", carac);
			poscarac = poscarac + 8;

			// Terminer le programme � la validation...
			// le retour chariot n'est pas \n mais \r
			if (carac == '\r')
				fin = 1;
		}
	}

	return 0;
}
END_OF_MAIN();
