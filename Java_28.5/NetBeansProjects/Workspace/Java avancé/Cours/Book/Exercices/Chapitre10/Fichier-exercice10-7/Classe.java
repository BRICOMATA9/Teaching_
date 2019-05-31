/*
#	Le livre de Java, premier langage
#	A. Tasso
#	Chapitre 10 : Collectionner un nombre ind�termin� d'objets 
#	Exercice   : 10.7 
#	Fichier    : Classe.java
#	Class      : Classe
*/

import java.util.*;
import java.io.*;
public class Classe implements Serializable {
 private HashMap<String, Etudiant> listeClass�e;
 public Classe()   {
	listeClass�e = new HashMap<String, Etudiant> ();		
 }
  public String cr�erUneCl�(Etudiant e) {
  	 String tmp;
  	 tmp = (e.quelPr�nom()).charAt(0)+ e.quelNom();
  	 tmp.toUpperCase();
  	 return tmp;
  }
  public String cr�erUneCl�(String p, String n ) {
  	 String tmp;
  	 tmp = p.charAt(0)+ n;
  	 tmp.toUpperCase();
  	 return tmp;
  }	
  public void ajouteUnEtudiant() {
	 Etudiant e = new Etudiant();
 	 String cl� = cr�erUneCl�(e);	
 	 Etudiant nouveau = (Etudiant) listeClass�e.get(cl�);
 	 if (nouveau == null) listeClass�e.put(cl�, e);
  }	
  public void rechercheUnEtudiant(String p, String n) {
 	 String cl� = cr�erUneCl�(p, n);
 	 Etudiant e = (Etudiant) listeClass�e.get(cl�);
 	 if (e != null)  	e.afficheUnEtudiant();
   else System.out.println(p + " " + n + " est inconnu ! ");
  }
 	public void supprimeUnEtudiant(String p, String n) {
	  	String cl� = cr�erUneCl�(p, n);
    	Etudiant e = (Etudiant) listeClass�e.get(cl�);
 	  if (e != null)  {
 	   	listeClass�e.remove(cl�);
 	  	  	System.out.println(p + " " + n + " a �t� supprim� ");
 	  } 
 	  else System.out.println(p + " " + n + " est inconnu ! ");
  }
  public void afficheLesEtudiants() 	{
	if(listeClass�e.size() != 0)
	{
           Collection<Etudiant> c = listeClass�e.values();
 
           for (  Etudiant e : c) {
  		  e.afficheUnEtudiant(); 	
        }
	}
	else System.out.println("Il n'y a pas d'etudiant dans cette liste");
 }
}