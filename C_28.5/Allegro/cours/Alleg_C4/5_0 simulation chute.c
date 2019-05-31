/**************************************************************************
DECOR VERTICAL :
						Un 1er programme pour jouer avec la gravit� avec 2 modes de chute
						- chute simple avec vitesse verticale constante vers le bas
						- chute r�aliste avec int�gration d'une acc�l�ration de pesanteur


**************************************************************************/

#include <allegro.h>

/****************************/
/*		 STRUCTURE ACTEUR		 */
/****************************/

// donn�es personnelles de l'acteur
// ici on utilise des float � cause de la pr�cision requise
// pour faire l'int�gration de l'acc�l�ration de mani�re correcte
typedef struct acteur {
	float r;			// rayon (du disque)
	float x, y;	 // coordonn�e (du coin sup. gauche)
	float dx, dy; // vecteur deplacement

	int typechute; // 0 simulation simple	 1 simulation r�aliste
	float amorti;	// facteur d'amortissement des rebonds
	float visco;	 // facteur de viscosit� ambient

	// Donn�e sp�cifique pour la simulation simple
	float dychute; // vitesse de chute (quand rien ne le retient)

	// Donn�e sp�cifique pour la simulation r�aliste
	float ay; // acc�l�ration en y (gravit� quand rien ne le retient)

} t_acteur;

/****************************/
/*		 SOUS-PROGRAMMES			*/
/****************************/

// Retourne un acteur allou� et initialis�
// pos� sur le sol � vitesse nulle
t_acteur *creerActeur() {
	t_acteur *acteur;

	acteur = (t_acteur *)malloc(1 * sizeof(t_acteur));

	acteur->r = 50;
	acteur->x = SCREEN_W / 2;
	acteur->y = SCREEN_H - acteur->r;
	acteur->dx = 0;
	acteur->dy = 0;

	acteur->typechute = 0;
	acteur->amorti = 0.1;
	acteur->visco = 0;

	acteur->dychute = 10;

	acteur->ay = .2;

	return acteur;
}

// chute � vitesse constante
void chuteSimpleActeur(t_acteur *acteur) {

	// Si je suis au dessus du sol je tombe � vitesse dychute
	// (	je ne peux donc rebondir vers le haut
	//		car il faudrait aller au dessus du sol ! )
	if (acteur->y < SCREEN_H - acteur->r)
		acteur->dy = acteur->dychute;
	// Sinon je me pose sur le sol � vitesse nulle
	else {
		acteur->y = SCREEN_H - acteur->r;
		acteur->dy = 0;
	}
}

// chute en int�grant l'acc�l�ration de la pesanteur
void chuteRealisteActeur(t_acteur *acteur) {

	// Dans tous les cas on int�gre (qu'on monte ou pas)
	acteur->dy = acteur->dy + acteur->ay;
}

// gestion des bords par rebond et int�gration vitesse
void actualiserActeur(t_acteur *acteur) {

	// viscosit� ambiente
	acteur->dx *= (1.0 - acteur->visco);
	acteur->dy *= (1.0 - acteur->visco);

	// contr�le des bords rebond sur les bords avec amorti
	if ((acteur->x - acteur->r < 0 && acteur->dx < 0) ||
			(acteur->x + acteur->r > SCREEN_W && acteur->dx > 0)) {
		acteur->dx = -acteur->dx;
		acteur->dx *= (1.0 - acteur->amorti);
	}

	if ((acteur->y - acteur->r < 0 && acteur->dy < 0) ||
			(acteur->y + acteur->r > SCREEN_H && acteur->dy > 0)) {
		acteur->dy = -acteur->dy;
		acteur->dy *= (1.0 - acteur->amorti);
	}

	// calculer nouvelle position
	// nouvelle position = position actuelle + deplacement
	acteur->x = acteur->x + acteur->dx;
	acteur->y = acteur->y + acteur->dy;
}

// pour jouer avec les param�tres
void reglerActeur(BITMAP *bmp, t_acteur *acteur);

/******************************************/
/* PROGRAMME PRINCIPAL										*/
/* initialisation puis boucle de jeu			*/
/******************************************/

int main() {

	t_acteur *acteur; // Un acteur (� cr�er)
	BITMAP *page;		 // BITMAP buffer d'affichage
	int mmx, mmy;		 // Les mickeys de la souris

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

	show_mouse(screen);
	page = create_bitmap(SCREEN_W, SCREEN_H);

	acteur = creerActeur();

	// Boucle de jeu
	while (!key[KEY_ESC]) {
		clear_bitmap(page);

		reglerActeur(page, acteur);

		if (acteur->typechute == 0)
			chuteSimpleActeur(acteur);

		if (acteur->typechute == 1)
			chuteRealisteActeur(acteur);

		actualiserActeur(acteur);

		get_mouse_mickeys(&mmx, &mmy);
		if (mouse_b & 1) {
			acteur->x = mouse_x;
			acteur->y = mouse_y;
			acteur->dx = mmx;
			acteur->dy = mmy;
		}

		circlefill(page, acteur->x, acteur->y, acteur->r, makecol(255, 255, 255));

		blit(page, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

		rest(10);
	}

	return 0;
}
END_OF_MAIN();

/******************************************/
/* SOUS-PROGRAMMES ANNEXE								 */
/******************************************/

// Petite fonction annexe pour borner les param�tres
void borner(float *param, float min, float max) {
	if (*param < min)
		*param = min;
	if (*param > max)
		*param = max;
}

// Manipulation des param�tres
void reglerActeur(BITMAP *bmp, t_acteur *acteur) {

	textprintf_ex(bmp, font, 10, 10, makecol(255, 255, 255), 0, "ESPACE	 %s",
								acteur->typechute ? "REALISTE" : "SIMPLE");
	if (key[KEY_SPACE]) {
		acteur->typechute = 1 - acteur->typechute;
		while (key[KEY_SPACE])
			;
	}

	textprintf_ex(bmp, font, 10, 30, makecol(255, 255, 255), 0,
								"F1 - F2	AMORTI	%.05f", acteur->amorti);
	if (key[KEY_F1])
		acteur->amorti -= .01;
	if (key[KEY_F2])
		acteur->amorti += .01;
	borner(&acteur->amorti, 0.0, 1.0);

	textprintf_ex(bmp, font, 10, 50, makecol(255, 255, 255), 0,
								"F3 - F4	VISCO	 %.05f", acteur->visco);
	if (key[KEY_F3])
		acteur->visco -= .001;
	if (key[KEY_F4])
		acteur->visco += .001;
	borner(&acteur->visco, 0.0, 1.0);

	if (acteur->typechute) {
		textprintf_ex(bmp, font, 10, 70, makecol(255, 255, 255), 0,
									"F5 - F6	AY			%.05f", acteur->ay);
		if (key[KEY_F5])
			acteur->ay -= .02;
		if (key[KEY_F6])
			acteur->ay += .02;
		borner(&acteur->ay, 0.0, 10.0);
	} else {
		textprintf_ex(bmp, font, 10, 70, makecol(255, 255, 255), 0,
									"F5 - F6	DYCHUTE %.05f", acteur->dychute);
		if (key[KEY_F5])
			acteur->dychute -= .2;
		if (key[KEY_F6])
			acteur->dychute += .2;
		borner(&acteur->dychute, 0.0, 100.0);
	}
}
