/**************************************************************************
COLLISIONS DECOR ET SCROLLING:
						Un sprite (vaisseau spatial) se d�place vers la souris (cliquer)
						Le d�placement se fait sur un d�cor plus grand que l'�cran,
						donc l'�cran r�el "bouge" sur ce d�cor (en fait ce sont les
						coordonn�es d'affichage du d�cor qui sont modifi�es � l'�cran...)

						Les deux variables � surveiller de pr�s dans ce programme
						sont screenx et screeny qui sont les coordonn�es de l'�cran
						(fen�tre visible) dans le rep�re du d�cor.
						Les coordonn�es acteur sont aussi dans ce rep�re du d�cor.
						En fait on ne passe en rep�re �cran qu'avec ce qui est
						sur le buffer ou en rapport avec l'�cran :

						Il faut modifier le blit du d�cors sur le buffer
						Il faut modifier le versSourisActeur
						(car la souris est dans le rep�re �cran)
						Il faut modifier l'affichage du vaisseau sur le buffer
						Il faut bien s�r prendre en compte la position de
						l'acteur par rapport � l'�cran r�el pour ne pas
						le perdre de vu : le scrolling doit suivre l'acteur.

						Les collisions sont g�r�es comme pour le programme 2_1
						sur une carte de collision

**************************************************************************/

#include <allegro.h>

/****************************/
/*		 STRUCTURE ACTEUR		 */
/****************************/

// donn�es personnelles de chaque acteur qui se d�place
typedef struct acteur {
	int x, y;				 // coordonn�e (du coin sup. gauche)
	int precx, precy; // coordonn�es pr�c�dent le d�placement
	int dx, dy;			 // vecteur deplacement
	int tx, ty;			 // tailles : horizontal/vertical
	BITMAP *img;			// image de l'acteur
} t_acteur;

/****************************/
/*		 FONCTIONs UTILEs		 */
/****************************/

// Fonction d�tectant un contact avec couleur sur
// l'ensemble des pixels du rectangle encadrant l'acteur
// Retourne le nombre de pixels du bord en contact avec couleur
//	ce qui peut s'interpr�ter comme un bool�en au niveau de l'appelant
//	ou �tre utilis� pour �valuer quantitativement la longueur du contact.
int contactBordActeur(t_acteur *acteur, BITMAP *bmp, int couleur) {
	int ncontact;
	int xb, yb;

	ncontact = 0;

	// Bords gauches et droits
	for (yb = 0; yb < acteur->ty; yb++) {
		if (getpixel(bmp, acteur->x, acteur->y + yb) == couleur)
			ncontact++;
		if (getpixel(bmp, acteur->x + acteur->tx, acteur->y + yb) == couleur)
			ncontact++;
	}

	for (xb = 0; xb < acteur->tx; xb++) {
		if (getpixel(bmp, acteur->x + xb, acteur->y) == couleur)
			ncontact++;
		if (getpixel(bmp, acteur->x + xb, acteur->y + acteur->ty) == couleur)
			ncontact++;
	}

	return ncontact;
}

// Donner � l'acteur un d�placement pour qu'il se rapproche de la souris
void versSourisActeur(t_acteur *acteur, int screenx, int screeny) {
	// vecteur d�placement = vecteur centre_vaisseau->souris
	acteur->dx = mouse_x + screenx - (acteur->x + acteur->tx / 2);
	acteur->dy = mouse_y + screeny - (acteur->y + acteur->ty / 2);
	// mais il faut limiter la vitesse : borner les valeurs obtenues
	if (acteur->dx > 5)
		acteur->dx = 5;
	if (acteur->dx < -5)
		acteur->dx = -5;
	if (acteur->dy > 5)
		acteur->dy = 5;
	if (acteur->dy < -5)
		acteur->dy = -5;
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

	t_acteur *acteur;		 // Un acteur (� cr�er)
	BITMAP *page;				 // BITMAP buffer d'affichage
	BITMAP *decor;				// Image de fond, seulement pour visualiser
	BITMAP *murs;				 // Image de fond, seulement pour les collisions
	int screenx, screeny; // Position de l'�cran r�el dans le rep�re du d�cor...

	// Lancer allegro et le mode graphique
	allegro_init();
	install_keyboard();
	install_mouse();

	set_color_depth(desktop_color_depth());
	if (set_gfx_mode(GFX_AUTODETECT_WINDOWED, 800, 600, 0, 0) != 0) {
		allegro_message("prb gfx mode");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// Montrer la souris � l'�cran
	show_mouse(screen);

	// CREATION DU BUFFER D'AFFICHAGE � la taille de l'�cran
	page = create_bitmap(SCREEN_W, SCREEN_H);
	clear_bitmap(page);

	// charger image de fond
	decor = load_bitmap_check("images/grandfond_decor.bmp");

	// charger image de murs
	murs = load_bitmap_check("images/grandfond_murs.bmp");

	// Allocation et initialisation des param�tres de l'acteur :
	acteur = (t_acteur *)malloc(1 * sizeof(t_acteur));
	acteur->tx = 99;
	acteur->ty = 38;
	acteur->x = 300;
	acteur->y = 260;
	acteur->dx = 0;
	acteur->dy = 0;
	acteur->img = load_bitmap_check("images/spaceship_small.bmp");

	// Initialisation du scrolling � gauche du d�cor
	screenx = 0;
	screeny = 0;

	// Boucle de jeu
	while (!key[KEY_ESC]) {
		// GESTION DU SCROLLING (suivi cam�ra)
		// ICI ON NE GERE QUE SUR L'AXE HORIZONTAL : A COMPLETER SI NECESSAIRE

		// Si le bord droit de l'acteur est � plus de 3/4 de largeur �cran
		// ( en coordonn�es �cran donc ) alors r�caler scrolling en 3/4
		if (acteur->x + acteur->tx - screenx > 3 * SCREEN_W / 4)
			screenx = acteur->x + acteur->tx - 3 * SCREEN_W / 4;

		// Si le bord gauche de l'acteur est � moins de 1/4 de largeur �cran
		// ( en coordonn�es �cran donc ) alors r�caler scrolling en 1/4
		if (acteur->x - screenx < SCREEN_W / 4)
			screenx = acteur->x - SCREEN_W / 4;

		// Bloquer le scrolling si il est ammen� trop loin !
		if (screenx < 0)
			screenx = 0;
		if (screenx > decor->w - SCREEN_W)
			screenx = decor->w - SCREEN_W;

		// EFFACER BUFFER EN APPLIQUANT UNE PARTIE DU DECOR (TAILLE DE L'ECRAN)
		blit(decor, page, screenx, screeny, 0, 0, SCREEN_W, SCREEN_H);

		// GESTION INTERFACE si cliquer alors le vaisseau part vers la souris
		if (mouse_b & 1)
			versSourisActeur(acteur, screenx, screeny);

		// ENREGISTRER POSITION ACTUELLE
		acteur->precx = acteur->x;
		acteur->precy = acteur->y;

		// DETERMINER NOUVELLE POSITION
		acteur->x += acteur->dx; // �quivalent � acteur->x = acteur->x + acteur->dx;
		acteur->y += acteur->dy; // �quivalent � acteur->y = acteur->y + acteur->dy;

		// SI COLLISION AVEC LE DECOR ALORS ON REVIENT SUR LA POSITION PRECEDENT LE
		// DEPLACEMENT
		//	ici on regard uniquement sur la carte de collision (murs) invisible pour
		//	l'utilisateur
		if (contactBordActeur(acteur, murs, makecol(255, 255, 255))) {
			acteur->x = acteur->precx;
			acteur->y = acteur->precy;

			// Souvent on accroche en dx ou dy mais on souhaite pouvoir "glisser" sur
			// l'autre axe donc on va essayer de voir si sur un seul axe �a ne passe
			// pas...
			acteur->x += acteur->dx; // juste dx ?
			if (contactBordActeur(acteur, murs, makecol(255, 255, 255)))
				acteur->x = acteur->precx; // �a ne passe toujours pas
			acteur->y += acteur->dy;		 // juste dy ?
			if (contactBordActeur(acteur, murs, makecol(255, 255, 255)))
				acteur->y = acteur->precy; // �a ne passe toujours pas
		}

		// AFFICHAGE NOUVELLEs POSITIONs SUR LE BUFFER
		draw_sprite(page, acteur->img, acteur->x - screenx, acteur->y - screeny);

		// AFFICHAGE DU BUFFER MIS A JOUR A L'ECRAN
		blit(page, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

		// ON FAIT UNE PETITE PAUSE
		rest(10);
	}

	return 0;
}
END_OF_MAIN();
