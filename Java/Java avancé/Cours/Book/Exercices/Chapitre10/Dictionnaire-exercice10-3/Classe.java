/*
#	Le livre de Java, premier langage
#	A. Tasso
#	Chapitre 10 : Collectionner un nombre ind�termin� d'objets 
#	Exercice   : 10.3 
#	Fichier    : Classe.java
#	Class      : Classe
*/
import java.util.*;
 public class Classe  {

 private HashMap<String, Etudiant> listeClass�e;
 public Classe()   {
	listeClass�e = new HashMap<String, Etudiant> ();		
 }
 
   private String cr�erUneCl�(Etudiant e) {
  	 String tmp;
  	 tmp = (e.quelPr�nom()).charAt(0)+ e.quelNom();
  	 tmp.toUpperCase();
  	 return tmp;
  }
 	
   private String cr�erUneCl�(String p, String n ) {
  	 String tmp;
  	 tmp = p.charAt(0)+ n;
  	 tmp.toUpperCase();
  	 return tmp;
  }
 	
 public void ajouteUnEtudiant() {
	Etudiant nouveau = new Etudiant();
	String cl� = cr�erUneCl�(nouveau);
	if (listeClass�e.get(cl�) == null) listeClass�e.put(cl�, nouveau);
 	else System.out.println("Cet �tudiant a deja ete saisi !");
 }
 	
  public void rechercheUnEtudiant(String p, String n) {
 	String cl� = cr�erUneCl�(p, n);
 	Etudiant eClass� = (Etudiant) listeClass�e.get(cl�);
 	if (eClass� != null)  
	   	eClass�.afficheUnEtudiant();
        else System.out.println(p + " " + n + " est inconnu ! ");
  }


  public void modifieUnEtudiant(String p, String n) {
 	String cl� = cr�erUneCl�(p, n);
 	if (listeClass�e.get(cl�) != null) {
 		 	Etudiant eModifi� = new Etudiant(p, n) ;
			listeClass�e.put(cl�, eModifi�);
 	}
 	else System.out.println(p + " " + n + " est inconnu ! ");
  }
 	

   public void supprimeUnEtudiant(String p, String n) {
  	String cl� = cr�erUneCl�(p, n);
    	Etudiant eClass� = (Etudiant) listeClass�e.get(cl�);
 	  if (eClass� != null)  {
 	   	listeClass�e.remove(cl�);
 	  	  	System.out.println(p + " " + n + " a �t� supprim� ");
 	  } 
 	  else System.out.println(p + " " + n + " est inconnu ! ");
  }


 public void afficheLesEtudiants() 	{
	if(listeClass�e.size() != 0)  {
           Collection<Etudiant> c = listeClass�e.values();
           for (  Etudiant e : c)   e.afficheUnEtudiant(); 	
	}
	else System.out.println("Il n'y a pas d'etudiant dans cette liste");
 }
 
}