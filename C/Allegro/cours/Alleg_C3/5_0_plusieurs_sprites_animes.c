/**************************************************************************
PLUSIEURS SPRITES ANIMES

					ce programme illustre l'animation de plusieurs personnage (animaux)
					qui traversent l'�cran

					Pour avoir plusieurs s�quences anim�es
					il faut plusieurs tableaux de bitmaps
					correspondant aux s�quences d'images

					Ici, chaque structure de type t_sequence regroupe
					les images d'une s�quence, et on utilise un tableau
					global tabSequences de ces structures

					Pour �viter d'avoir � g�rer des quantit�s
					importantes de petits fichiers images, chaque
					s�quence image est regroup�e dans un m�me .bmp
					La proc�dure void chargerSequence(t_sequence * seq);
					permet de d�couper cette image de d�part en petite bitmaps
					(une pour chaque �tape de l'animation)
**************************************************************************/

#include <allegro.h>
#include <time.h>

/***************************************************/
/*									 CONSTANTES										*/
/*						devront aller dans un .h						 */
/***************************************************/

// sur cet exemple Nombre d'acteurs fixe :
//		le tableau d'acteurs sera d�clar� et utilis� en "automatique"
//		t_acteur tab[NACTEUR];
// Pour modifier cette valeur il faut adapter remplirTabActeurs
#define NACTEUR 6

// nombre total de s�quences d'animation du jeu
// ( le nombre d'acteurs peut �tre sup�rieur si plusieurs acteurs utilisent les
// m�me graphismes ) Pour modifier cette valeur il faut modifier le tableau
// initialis� tabSequences
#define NSEQUENCE 6

/*****************************/
/*			 STRUCTURES					*/
/*	devront aller dans un .h */
/*****************************/

// donn�es pour chaque s�quence d'animation charg�e initialement
//	( � ne faire qu'une seule fois au d�but du jeu )
typedef struct sequence {
	char *nomSource; // nom du fichier image contenant la s�quence
	int nimg;				// nombre d'images dans la s�quence
	int tx, ty;			// largeur et hauteur des images de la s�quence
	int ncol; // nbr images cotes � cotes horizontalement dans le fichier image
	BITMAP **img; // tableau de pointeurs pour indiquer les images
} t_sequence;

// donn�es personnelles de chaque acteur qui se d�place
// sur cet exemple on ne g�re que des d�placements horizontaux (pas de dy...)
typedef struct acteur {
	// g�om�trie et d�placements

	int x, y;	 // position du coin sup. gauche
	int dx;		 // deplacement
	int tmpdx;	// ralentir d�placements en x (1 pour ne pas ralentir)
	int cptdx;	// compteur pour ralentir d�placement
	int tx, ty; // largeur hauteur

	// s�quence d'images de l'animation

	int imgcourante; // indice de l'image courante
	int tmpimg;			// ralentir s�quence (image suivante 1 fois sur tmpimg)
	int cptimg;			// compteur pour ralentir s�quence

	// type = num�ro de la sequence � utiliser dans tabSequences
	// ( ici : 0 Dragon	1 Poisson	2 Crabe	3 Abeille	4 Moustique	5 Serpent )

	int type;

} t_acteur;

/*****************************/
/*			 PROTOTYPES					*/
/*	devront aller dans un .h */
/*****************************/

// Allouer et initialiser un acteur
t_acteur *creerActeur(int type, int x, int y, int dx, int tmpdx, int tmpimg);

// Pour remplir un tableau avec des acteurs cr��s
// Sur cet exemple on cr�e 6 acteurs, chacun associ� � une s�quence
void remplirTabActeurs(t_acteur *tab[NACTEUR]);

// Actualiser un acteur (bouger ...)
void actualiserActeur(t_acteur *acteur);

// G�rer l'�volution de l'ensemble des acteurs
void actualiserTabActeurs(t_acteur *tab[NACTEUR]);

// Dessiner un acteur sur une bitmap bmp
void dessinerActeur(BITMAP *bmp, t_acteur *acteur);

// Dessiner l'ensemble des acteurs sur une bitmap bmp
void dessinerTabActeurs(BITMAP *bmp, t_acteur *tab[NACTEUR]);

// Charger les images d'une s�quence d'animation
// D�coupe une image source en plusieurs vignettes
// (doivent �tre rang�es de gauche � droite et de haut en bas)
void chargerSequence(t_sequence *seq);

// Charger toutes les s�quences du tableau global tabSequence
void chargerTabSequences();

/***************************************************/
/*							VARIABLES GLOBALES								 */
/*	les d�clarations devront aller dans un .h			*/
/*	les d�finitions devront aller dans un .c			 */
/***************************************************/

// tableau global de toutes les s�quences anim�es du jeu
// on s'autorise � utiliser un tableau global car ces donn�es
// n'existent qu'en un seul exemplaire � l'�chelle du programme
t_sequence tabSequences[NSEQUENCE] = {
		//					nomSource					 , nimg,	tx,	ty, ncol
		{"images/dragon/dragon.bmp", 6, 128, 64, 3},
		{"images/dragon/poisson.bmp", 3, 64, 32, 3},
		{"images/dragon/crabe.bmp", 4, 64, 32, 4},
		{"images/dragon/abeille.bmp", 6, 50, 40, 6},
		{"images/dragon/moustique.bmp", 6, 50, 40, 6},
		{"images/dragon/serpent.bmp", 7, 100, 50, 4}};

/******************************************/
/* PROGRAMME PRINCIPAL										*/
/* initialisation puis boucle d'animation */
/******************************************/

int main() {
	// Le tableau regroupant tous les acteurs
	// c'est un tableau de pointeurs sur structures t_acteurs
	t_acteur *mesActeurs[NACTEUR];

	// BITMAP servant de buffer d'affichage (double buffer)
	BITMAP *page;

	// Image de fond
	BITMAP *decor;

	// On va utiliser du hasard
	srand(time(NULL));

	// Lancer allegro et le mode graphique
	allegro_init();
	install_keyboard();

	set_color_depth(desktop_color_depth());
	if (set_gfx_mode(GFX_AUTODETECT_WINDOWED, 640, 480, 0, 0) != 0) {
		allegro_message("prb gfx mode");
		allegro_exit();
		exit(EXIT_FAILURE);
	}

	// Cr�ation du buffer d'affichage � la taille de l'�cran
	page = create_bitmap(SCREEN_W, SCREEN_H);
	clear_bitmap(page);

	// charger image de fond
	decor = load_bitmap("images/dragon/decor.bmp", NULL);
	if (!decor) {
		allegro_message("pas pu trouver images/dragon/decor.bmp");
		exit(EXIT_FAILURE);
	}

	// Chargement des images des s�quences anim�es
	chargerTabSequences();

	// Initialisation al�atoire des param�tres des acteurs :
	// remplir le tableau avec des acteurs allou�s et initialis�s
	remplirTabActeurs(mesActeurs);

	// Boucle d'animation (pas d'interaction)
	while (!key[KEY_ESC]) {

		// 1)	EFFACER BUFFER, en appliquant d�cor	(pas de clear_bitmap)
		blit(decor, page, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

		// 2) DETERMINER NOUVELLEs POSITIONs
		actualiserTabActeurs(mesActeurs);

		// 3) AFFICHAGE NOUVELLEs POSITIONs SUR LE BUFFER
		dessinerTabActeurs(page, mesActeurs);

		// 4) AFFICHAGE DU BUFFER MIS A JOUR A L'ECRAN
		blit(page, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

		// 5) ON FAIT UNE PETITE PAUSE � chaque fois sinon �a va trop vite...
		rest(20);
	}

	return 0;
}
END_OF_MAIN();

/************************************************/
/*		 DEFINITIONS DES SOUS-PROGRAMMES					*/
/*	devront aller dans un autre .c : acteurs.c	*/
/************************************************/

// Allouer et initialiser un acteur
t_acteur *creerActeur(int type, int x, int y, int dx, int tmpdx, int tmpimg) {
	// pointeur sur l'acteur qui sera cr�� (et retourn�)
	t_acteur *acteur;

	// Cr�ation (allocation)
	acteur = (t_acteur *)malloc(1 * sizeof(t_acteur));

	// Initialisation g�om�trie et d�placement
	acteur->x = x;
	acteur->y = y;
	acteur->dx = dx;
	acteur->tmpdx = tmpdx;
	acteur->cptdx = 0;
	acteur->tx = tabSequences[type].tx;
	acteur->ty = tabSequences[type].ty;

	// Initialisation s�quence d'images de l'animation
	acteur->imgcourante = 0;
	acteur->tmpimg = tmpimg;
	acteur->cptimg = 0;

	// num�ro de s�quence
	acteur->type = type;

	// on retourne cet acteur fraichement cr��
	return acteur;
}

// Pour remplir un tableau avec des acteurs cr��s
// Sur cet exemple on cr�e 6 acteurs, chacun associ� � une s�quence
void remplirTabActeurs(t_acteur *tab[NACTEUR]) {
	// Appeler NACTEUR fois creerActeur avec les param�tres souhait�s :
	//								(type,	 x,	 y,	dx, tmpdx, tmpimg )
	tab[0] = creerActeur(0, 500, 0, -5, 1, 5);
	tab[1] = creerActeur(1, 300, 400, 3, 1, 8);
	tab[2] = creerActeur(2, 300, 212, 2, 6, 20);
	tab[3] = creerActeur(3, 100, 122, -3, 1, 8);
	tab[4] = creerActeur(4, 500, 70, 4, 1, 2);
	tab[5] = creerActeur(5, 350, 200, -2, 1, 4);
}

// Actualiser un acteur (bouger ...)
void actualiserActeur(t_acteur *acteur) {

	// gestion des bords "� la pac man"
	// sur cet exemple seulement sur l'axe x (car pas de dy)
	if (acteur->x + acteur->tx < 0)
		acteur->x = SCREEN_W;
	if (acteur->x > SCREEN_W)
		acteur->x = -acteur->tx;

	// calculer nouvelle position
	// nouvelle position = position actuelle + deplacement seulement une fois sur
	// tmpdx sur cet exemple seulement sur l'axe x (car pas de dy)
	acteur->cptdx++;
	if (acteur->cptdx >= acteur->tmpdx) {
		acteur->cptdx = 0;
		acteur->x = acteur->x + acteur->dx;
	}

	// gestion enchainement des images
	// incr�menter imgcourante une fois sur tmpimg
	acteur->cptimg++;
	if (acteur->cptimg >= acteur->tmpimg) {
		acteur->cptimg = 0;
		acteur->imgcourante++;
		// quand l'indice de l'image courante arrive � nimg de la s�quence
		// on recommence la s�quence � partir de 0
		if (acteur->imgcourante >= tabSequences[acteur->type].nimg)
			acteur->imgcourante = 0;
	}
}

// G�rer l'�volution de l'ensemble des acteurs
void actualiserTabActeurs(t_acteur *tab[NACTEUR]) {
	int i;

	for (i = 0; i < NACTEUR; i++)
		actualiserActeur(tab[i]);
}

// Dessiner un acteur sur une bitmap bmp
void dessinerActeur(BITMAP *bmp, t_acteur *acteur) {
	// Pointeur sur la s�quence concern�e (prise en compte du type de l'acteur)
	t_sequence *seq;
	seq = &tabSequences[acteur->type];

	//	Prise en compte du num�ro d'image courante de l'acteur dans cette s�quence
	draw_sprite(bmp, seq->img[acteur->imgcourante], acteur->x, acteur->y);
}

// Dessiner l'ensemble des acteurs sur une bitmap bmp
void dessinerTabActeurs(BITMAP *bmp, t_acteur *tab[NACTEUR]) {
	int i;

	for (i = 0; i < NACTEUR; i++)
		dessinerActeur(bmp, tab[i]);
}

// Charger les images d'une s�quence d'animation
// D�coupe une image source en plusieurs vignettes
// (doivent �tre rang�es de gauche � droite et de haut en bas)
void chargerSequence(t_sequence *seq) {
	BITMAP *source; // la bitmap qui charge l'image de s�quence (temporairement)
	int i;					// indice de l'image dans la s�quence
	int ix, iy; // indices (horizontal et vertical) dans le "tableau" des images
	int sx, sy; // coordonn�es correspondantes (en pixels)

	// Charger l'image de s�quence
	source = load_bitmap(seq->nomSource, NULL);
	if (!source) {
		allegro_message("pas pu trouver %s", seq->nomSource);
		exit(EXIT_FAILURE);
	}

	// Allouer le tableau de pointeur sur les images de l'animation
	seq->img = (BITMAP **)malloc(seq->nimg * sizeof(BITMAP *));

	// Allouer les images de l'animation et les r�cup�rer sur l'image source
	ix = 0;
	iy = 0;
	for (i = 0; i < seq->nimg; i++) {
		// allouer image
		seq->img[i] = create_bitmap(seq->tx, seq->ty);

		// r�cup�rer image
		sx = ix * seq->tx;
		sy = iy * seq->ty;
		blit(source, seq->img[i], sx, sy, 0, 0, seq->tx, seq->ty);

		// pr�parer indices pour l'image suivante
		ix++;								// colonne suivante
		if (ix >= seq->ncol) // si je suis � droite de la derni�re colonne alors...
		{
			ix = 0; // repartir sur la colonne 0
			iy++;	 // � la ligne en dessous
		}
	}

	// On a fini de r�cup�rer s�par�ment chaque �tape (image) de l'animation
	// on n'a donc plus besoin de l'image source qui les regroupe
	destroy_bitmap(source);
}

// Charger toutes les s�quences du tableau global tabSequence
void chargerTabSequences() {
	int i;

	for (i = 0; i < NSEQUENCE; i++)
		chargerSequence(&tabSequences[i]);
}
