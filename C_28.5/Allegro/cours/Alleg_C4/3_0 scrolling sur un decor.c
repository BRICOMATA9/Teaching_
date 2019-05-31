/**************************************************************************
DECOR SCROLLING:
						D�placement de l'�cran sur un d�cor plus grand.
						en fait ce sont les coordonn�es d'affichage du d�cor
						qui sont modifi�es � l'�cran...
						On se d�place avec les fl�ches du clavier

						Les deux variables � surveiller de pr�s dans ce programme
						sont screenx et screeny qui sont les coordonn�es de l'�cran
						(fen�tre visible) dans le rep�re du d�cor.

						Il faut modifier le blit du d�cors sur le buffer

**************************************************************************/

#include <allegro.h>

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

	BITMAP *page;				 // BITMAP buffer d'affichage
	BITMAP *decor;				// Image de fond, seulement pour visualiser
	int screenx, screeny; // Position de l'�cran r�el dans le rep�re du d�cor...

	// Lancer allegro et le mode graphique
	allegro_init();
	install_keyboard();

	set_color_depth(desktop_color_depth());
	if (set_gfx_mode(GFX_AUTODETECT_WINDOWED, 640, 480, 0, 0) != 0) {
		allegro_message("prb gfx mode");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// CREATION DU BUFFER D'AFFICHAGE � la taille de l'�cran
	page = create_bitmap(SCREEN_W, SCREEN_H);
	clear_bitmap(page);

	// charger image de fond
	decor = load_bitmap_check("images/grandfond_decor.bmp");

	// Ici initialisation du scrolling en haut � gauche du d�cor
	//	(on peut d�cider de partir autrement...)
	screenx = 0;
	screeny = 0;

	// Boucle de jeu
	while (!key[KEY_ESC]) {
		// GESTION DU SCROLLING AU CLAVIER (Touches de direction)
		if (key[KEY_RIGHT])
			screenx += 5;
		if (key[KEY_LEFT])
			screenx -= 5;
		if (key[KEY_DOWN])
			screeny += 5;
		if (key[KEY_UP])
			screeny -= 5;

		// Bloquer le scrolling si il est ammen� trop loin !
		if (screenx < 0)
			screenx = 0;
		if (screenx > decor->w - SCREEN_W)
			screenx = decor->w - SCREEN_W;
		if (screeny < 0)
			screeny = 0;
		if (screeny > decor->h - SCREEN_H)
			screeny = decor->h - SCREEN_H;

		// EFFACER BUFFER EN APPLIQUANT UNE PARTIE DU DECOR (TAILLE DE L'ECRAN)
		blit(decor, page, screenx, screeny, 0, 0, SCREEN_W, SCREEN_H);

		// ANIMER DES CHOSES... (ici rien)

		// AFFICHAGE NOUVELLEs POSITIONs SUR LE BUFFER
		// ici pour illustrer on se contente juste de graphismes simples

		// Affichages ancr�s au d�cor :
		// un changement de rep�re est n�cessaire
		// (soustraire screenx screeny � toutes les coordonn�es)
		circlefill(page, 540 - screenx, 50 - screeny, 25, makecol(255, 128, 128));
		textprintf_centre_ex(page, font, 540 - screenx, 100 - screeny,
												 makecol(255, 255, 255), -1, "AFFICHAGE FIXE AU DECOR");

		// Affichages ancr�s � l'�cran r�el : pas de prise en compte de screenx et
		// screeny
		circlefill(page, 100, 50, 25, makecol(128, 255, 128));
		textprintf_centre_ex(page, font, 100, 100, makecol(255, 255, 255), -1,
												 "AFFICHAGE FIXE A L'ECRAN");
		textprintf_centre_ex(page, font, 320, 10, makecol(255, 255, 255), -1,
												 "APPUYER SUR LES FLECHES POUR SCROLLER");

		// AFFICHAGE DU BUFFER MIS A JOUR A L'ECRAN
		blit(page, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

		// ON FAIT UNE PETITE PAUSE
		rest(10);
	}

	return 0;
}
END_OF_MAIN();
