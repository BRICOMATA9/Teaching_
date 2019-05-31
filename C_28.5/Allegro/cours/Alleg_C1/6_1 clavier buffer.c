/*************************************************************
Utilisation du clavier : buffer = comme en console avec getch
*************************************************************/

#include <allegro.h>

// Fonction g�n�rant une couleur al�atoire
int couleur_alea() { return makecol(rand() % 256, rand() % 256, rand() % 256); }

int main(int argc, char *argv[]) {
	int touche;
	char carac;
	int scancode;
	int fin;

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

	// Sp�cifique pour clavier via BUFFER ...
	// void set_keyboard_rate(int delay, int repeat);
	// permet de modifier la r�p�tition automatique des touches clavier :
	//		 apr�s "delay" temps en milliseconde
	//		 chaque r�p�tition a lieu tous les "repeat" millisecondes
	// passer 0,0 interdit la r�p�tition de touche pour le buffer clavier

	// set_keyboard_rate(1000,200);

	fin = 0;
	while (!fin) {

		// keypressed() : �quivalent allegro du kbhit()
		if (keypressed()) {
			// r�cup�rer la touche avec readkey() : �quivalent allegro du getch()
			touche = readkey();

			// a partir de l'info de touche on obtient le caract�re en castant en char
			carac = (char)touche;

			// tester si la touche appuy�e correspond � espace
			if (carac == ' ')
				textprintf_ex(screen, font, 360, 300, makecol(255, 255, 0),
											makecol(0, 0, 0), "	ESPACE	");
			else
				textprintf_ex(screen, font, 360, 300, makecol(0, 255, 255),
											makecol(0, 0, 0), "PAS ESPACE");

			// a partir de l'info de touche on obtient le scancode sur le 2�me octet
			// permet d'obtenir les touches non caract�res (directions, effacer ...)
			scancode = touche >> 8;

			// Si touche EFFACER alors effacer �cran
			if (scancode == KEY_BACKSPACE)
				clear_bitmap(screen);

			// on peut aiguiller selon le scancode avec un switch
			// ( ce qui est impossible avec l'utilisation directe key[KEY_...] )
			switch (scancode) {
			case KEY_UP:
				circlefill(screen, 300 + rand() % 200, 0 + rand() % 200, 20,
									 couleur_alea());
				break;

			case KEY_DOWN:
				circlefill(screen, 300 + rand() % 200, 400 + rand() % 200, 20,
									 couleur_alea());
				break;

			case KEY_LEFT:
				circlefill(screen, 0 + rand() % 200, 200 + rand() % 200, 20,
									 couleur_alea());
				break;

			case KEY_RIGHT:
				circlefill(screen, 600 + rand() % 200, 200 + rand() % 200, 20,
									 couleur_alea());
				break;

			case KEY_ESC:
				fin = 1;
				break;
			}
		}
	}

	return 0;
}
END_OF_MAIN();
