#include "projet.h"

// Tableau des images (BITMAPS) utilis�es par les objets
BITMAP *tabImageObjet[NIMAGE_OBJET];

// Chargement des images des objets
void chargerTabImageObjet()
{
    tabImageObjet[0]=chargerImage("images/arbre.bmp");
    tabImageObjet[1]=chargerImage("images/maison.bmp");
    tabImageObjet[2]=chargerImage("images/soleil.bmp");
}

// Affichage d'un objet (sur le buffer)
void afficherObjet(t_objet *obj)
{
    BITMAP *img;   // Adresse de l'image de l'objet

    // Ici il ne s'agit pas d'une copie de l'image !
    // on veut juste mettre son adresse dans une variable temporaire
    // pour manipuler plus facilement le code apr�s
    img=tabImageObjet[ obj->iImg ];

    // On dessine l'image sur le buffer
    // en calculant les coordonn�es du coin sup�rieur gauche
    // en fonction des coordonn�es du "pied" de l'objet et de la taille image
    draw_sprite(page, img, obj->x - img->w/2, obj->y - img->h);
}

// Affichage de tous les objets de la sc�ne entre ymin et ymax (inclus)
void afficherTabObjet(t_tabObjet *tabObj, int ymin, int ymax)
{
    int i;
    t_objet *obj;

    // Pour chaque objet de la sc�ne
    for (i=0; i < tabObj->nobj; i++)
    {
        // On r�cup�re l'adresse du i�me objet...
        //  note : les acc�s aux champs et [] sont prioritaires sur le &
        //         la ligne suivante �quivaut donc �  obj= &( tabObj->obj[i] );
        obj= &tabObj->obj[i];

        // Si il est dans l'intervalle voulu alors on l'affiche
        if ( obj->y >= ymin  &&  obj->y <= ymax )
            afficherObjet(obj);
    }
}

// Allocation et remplissage d'un ensemble d'objets � partir d'un fichier
t_tabObjet *chargerTabObjet(char *nomFichier)
{
    FILE *fp;
    int i, nobj;
    t_tabObjet *tabObj;
    t_objet *obj;

    // Ouverture en lecture du fichier de donn�es d�crivant
    // les coordonn�es et les indices images associ�s � chaque objet
    fp=fopen(nomFichier,"r");
    if (fp==NULL)
    {
        allegro_message("Catastrophe, j'ai un besoin vital du fichier %s\nmais je n'arrive pas a le trouver ou a l'ouvrir", nomFichier);
        allegro_exit();
        exit(EXIT_FAILURE);
    }

    // OK, on alloue la structure qui va contenir le tableau...
    tabObj=(t_tabObjet *)malloc(1*sizeof(t_tabObjet));

    // La premi�re donn�e du fichier sera le nombre d'objets
    fscanf(fp,"%d",&nobj);

    // On peut remplir le champ correspondant
    tabObj->nobj=nobj;

    // Et allouer le tableau proprement dit
    tabObj->obj=(t_objet *)malloc(nobj*sizeof(t_objet));

    // Maintenant on charge tous les objets...
    for (i=0; i<nobj; i++)
    {
        // r�cup adresse de l'objet courant (pour plus de commodit�)
        obj= &tabObj->obj[i];
        fscanf(fp,"%d",&obj->x);
        fscanf(fp,"%d",&obj->y);
        fscanf(fp,"%d",&obj->iImg);
    }
    // Tout est en place !

    // On ferme le fichier
    fclose(fp);

    // On retourne les donn�es compl�tes
    return tabObj;
}
