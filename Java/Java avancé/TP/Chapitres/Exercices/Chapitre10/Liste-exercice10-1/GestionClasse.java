/*
#	Le livre de Java, premier langage
#	A. Tasso
#	Chapitre 10 : Collectionner un nombre ind�termin� d'objets 
#	Exercice   : 10.1 
#	Fichier    : GestionClasse.java
#	Class      : GestionClasse
*/

public class GestionClasse  {
	public static void main(String [] argument)  {
       Classe C = new Classe();
		System.out.println("------------------  R�capitulatif  ------------------");
		C.afficheLesEtudiants();
	//	C.classerParMoyenne();
		C.ajouteUnEtudiant();
	//	System.out.println("------------------  Classement  ------------------ ");
		C.afficheLesEtudiants();
	}
}