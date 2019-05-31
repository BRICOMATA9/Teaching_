#ifndef AUXILIAIRE_H_INCLUDED
#define AUXILIAIRE_H_INCLUDED

// BUFFER d'affichage
extern BITMAP *page;

// Regroupe les initialisations d'allegro, mode graphique, clavier, souris
// Termine le programme (avec message) en cas d'�chec
void lancerToutAllegro(int largeur, int hauteur);

// Fonctionne comme load_bitmap sauf qu'il ne prend pas NULL en 2�me param�tre
// et qu'il termine le programme (avec message) en cas d'�chec
BITMAP *chargerImage(char *nomFichierImage);

#endif // AUXILIAIRE_H_INCLUDED
