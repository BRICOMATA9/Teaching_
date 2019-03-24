#include "projet.h"

/// Programme principal
int main(int argc, char *argv[])
{
    /// DECLARATIONS DES VARIABLES DU MAIN

    BITMAP *decor;
    t_tabObjet *tabObj;
    t_perso *monLapin;

    /// INITIALISATION ALLEGRO et services
    lancerToutAllegro(800,600);

    /// AVANT BOUCLE JEU : initialisations et chargement des donn�es

    // charger les images des "objets" (valable pour tous le jeu)
    chargerTabImageObjet();

    // charger image de fond
    decor=chargerImage("images/decor_scene1.bmp");

    // charger les donn�es des "objets" pour la sc�ne
    tabObj=chargerTabObjet("donnees/objets_scene1.txt");

    // cr�er le perso et charger son image
    monLapin=creerPerso();

    // Positionner le perso en d�marrage de sc�ne
    monLapin->x=100;
    monLapin->y=400;
    monLapin->orient=1;

    /// BOUCLE JEU
    while (!key[KEY_ESC])
    {
        /// Mise � jour des positions pour les objets anim�s
        bougerPerso(monLapin);

        /// Assemblage de la sc�ne sur le BUFFER

        //  En posant le d�cor sur le buffer on �crase son ancien contenu
        blit(decor,page,0,0,0,0,SCREEN_W,SCREEN_H);
        //  On pose les diff�rents �l�ments derri�re le perso
        afficherTabObjet(tabObj, 0, monLapin->y-1);
        //  On dessine le perso
        afficherPerso(monLapin);
        //  On pose les diff�rents �l�ments devant le perso
        afficherTabObjet(tabObj, monLapin->y, SCREEN_H);

        /// affichage du buffer � l'�cran
        blit(page,screen,0,0,0,0,SCREEN_W,SCREEN_H);

        /// une pause pour temporiser l'encha�nement des images
        rest(20);        // ( 20 ms -> � peu pr�s 50 images/s )
    }

    return 0;
}
END_OF_MAIN();
