/**************************************************************************
COLLISIONS: 2 �l�ments rectangulaires bougent avec la souris (drag and throw)

						on d�tecte leur collision et l'�tat de collision
						est signal�e par un changement de couleur
						Ce code reprend des fonctions de 1_0 et 1_1 du cours 3
						on a enlev� la gestion du tableau d'acteurs (seulement 2 acteurs)

						La d�tection proprement dite des collisions est faite
						par une fonction auxilliaire : collisionActeurs
						Voir tout en bas du code.

**************************************************************************/

#include <allegro.h>
#include <time.h>

/****************************/
/*		 STRUCTURE ACTEUR		 */
/****************************/

// donn�es personnelles de chaque acteur qui se d�place
typedef struct acteur {
	int x, y;		// coordonn�e (du coin sup. gauche)
	int dx, dy;	// vecteur deplacement
	int tx, ty;	// tailles : horizontal/vertical
	int couleur; // couleur de l'�l�ment graphique
} t_acteur;

/*****************************/
/*		 PROTOTYPES						*/
/*****************************/

// Fonctions principales (appel�es depuis le main)

// Allouer et initialiser un acteur
t_acteur *creerActeur();

// Actualiser un acteur (bouger ...)
void actualiserActeur(t_acteur *acteur);

// Dessiner un acteur sur une bitmap bmp
void dessinerActeur(BITMAP *bmp, t_acteur *acteur);

// Fonctions annexes

// voir ci dessous pour des mani�res alternatives d'�crire la m�me fonction
int sousSourisActeur(t_acteur *acteur);

// D�terminer si les rectangles de 2 acteurs s'intersectent
// Fonction bool�enne : retourne 1 si collision	0 sinon
int collisionActeurs(t_acteur *a1, t_acteur *a2);

/******************************************/
/* PROGRAMME PRINCIPAL										*/
/* initialisation puis boucle de jeu			*/
/******************************************/

int main() {
	// 2 acteurs (� cr�er)
	t_acteur *actA, *actB;

	// BITMAP servant de buffer d'affichage (double buffer)
	BITMAP *page;

	// Mouvement Mouse en x et y	(mouvement = changement de position)
	int mmx, mmy;

	// On va utiliser du hasard
	srand(time(NULL));

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

	// Initialisation al�atoire des param�tres des acteurs :
	actA = creerActeur();
	actB = creerActeur();

	// Boucle de jeu
	while (!key[KEY_ESC]) {
		// EFFACER POSITIONs ACTUELLEs SUR LE BUFFER
		clear_bitmap(page);

		// GESTION INTERFACE

		// Le "mickey" est l'unit� de d�placement du curseur souris
		// la fonction suivante r�cup�re le vecteur d�placement depuis son dernier
		// appel
		get_mouse_mickeys(&mmx, &mmy);

		if (mouse_b & 1 && sousSourisActeur(actA)) {
			actA->dx = mmx;
			actA->dy = mmy;
		}

		if (mouse_b & 1 && sousSourisActeur(actB)) {
			actB->dx = mmx;
			actB->dy = mmy;
		}

		// DETERMINER NOUVELLEs POSITIONs
		actualiserActeur(actA);
		actualiserActeur(actB);

		// COLLISION ?
		if (collisionActeurs(actA, actB)) {
			actA->couleur = makecol(255, 80, 80);
			actB->couleur = makecol(255, 80, 80);
		} else {
			actA->couleur = makecol(80, 255, 80);
			actB->couleur = makecol(80, 255, 80);
		}

		// AFFICHAGE NOUVELLEs POSITIONs SUR LE BUFFER
		dessinerActeur(page, actA);
		dessinerActeur(page, actB);

		// AFFICHAGE DU BUFFER MIS A JOUR A L'ECRAN
		blit(page, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

		// ON FAIT UNE PETITE PAUSE
		rest(10);
	}

	return 0;
}
END_OF_MAIN();

/************************************************/
/*		 DEFINITIONS DES SOUS-PROGRAMMES					*/
/************************************************/

// Allouer et initialiser (al�atoirement) un acteur
t_acteur *creerActeur() {
	// pointeur sur l'acteur qui sera cr�� (et retourn�)
	t_acteur *acteur;

	// Cr�ation (allocation)
	acteur = (t_acteur *)malloc(1 * sizeof(t_acteur));

	// Initialisation

	acteur->tx = rand() % 100 + 100;
	acteur->ty = rand() % 100 + 100;

	// Position al�atoire (on tient compte de la taille...)
	acteur->x = rand() % (SCREEN_W - acteur->tx);
	acteur->y = rand() % (SCREEN_H - acteur->ty);

	// Pour ce programme on veut des acteurs initialement immobiles
	acteur->dx = 0;
	acteur->dy = 0;

	// Couleur blanche (mais ce sera modifi� dans le main...)
	acteur->couleur = makecol(255, 255, 255);

	// on retourne cet acteur fraichement cr��
	// ( en fait on retourne le POINTEUR sur lui )
	return acteur;
}

// Actualiser un acteur (bouger ...)
void actualiserActeur(t_acteur *acteur) {
	// contr�le des bords : ici on d�cide de rebondir sur les bords
	if ((acteur->x < 0 && acteur->dx < 0) ||
			(acteur->x + acteur->tx > SCREEN_W && acteur->dx > 0))
		acteur->dx = -acteur->dx;

	if ((acteur->y < 0 && acteur->dy < 0) ||
			(acteur->y + acteur->ty > SCREEN_H && acteur->dy > 0))
		acteur->dy = -acteur->dy;

	// calculer nouvelle position
	// nouvelle position = position actuelle + deplacement
	acteur->x = acteur->x + acteur->dx;
	acteur->y = acteur->y + acteur->dy;
}

// Dessiner un acteur sur une bitmap bmp
void dessinerActeur(BITMAP *bmp, t_acteur *acteur) {
	rectfill(bmp, acteur->x, acteur->y, acteur->x + acteur->tx,
					 acteur->y + acteur->ty, acteur->couleur);
}

// D�terminer si un acteur est sous la souris
// Fonction bool�enne : retourne 1 pour oui	0 pour non
// voir ci dessous pour des mani�res alternatives d'�crire la m�me fonction
int sousSourisActeur(t_acteur *acteur) {
	int retour = 0;

	if (mouse_x >= acteur->x && mouse_x <= acteur->x + acteur->tx &&
			mouse_y >= acteur->y && mouse_y <= acteur->y + acteur->ty)
		retour = 1;

	return retour;
}

// On pourrait aussi �crire (moins "rigoureux" car plusieurs return):
/*
int sousSourisActeur(t_acteur *acteur)
{
		if ( mouse_x >= acteur->x && mouse_x <= acteur->x + acteur->tx &&
				 mouse_y >= acteur->y && mouse_y <= acteur->y + acteur->ty )
				return 1;
		else
				return 0;
}
*/

// On pourrait aussi �crire (rigoureux et compact mais � r�server aux cas
// simples)
/*
int sousSourisActeur(t_acteur *acteur)
{
		// On peut retourner directement le r�sultat d'une expression bool�enne
		return	mouse_x >= acteur->x && mouse_x <= acteur->x + acteur->tx &&
						mouse_y >= acteur->y && mouse_y <= acteur->y + acteur->ty ;
}
*/

// D�terminer si les rectangles de 2 acteurs s'intersectent
// Fonction bool�enne : retourne 1 si collision	0 sinon
int collisionActeurs(t_acteur *a1, t_acteur *a2) {
	int retour = 0;

	// Si collision mettre valeur retour � 1
	if (a1->x <= a2->x + a2->tx && a2->x <= a1->x + a1->tx &&
			a1->y <= a2->y + a2->ty && a2->y <= a1->y + a1->ty)
		retour = 1;

	return retour;
}
