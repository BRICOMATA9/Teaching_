/*
#	Le livre de Java 1er langage
#	A. Tasso
#	Chapitre 12 :Java et Internet
#	Section  : Projet  
#	Fichier  : Compte.java
#	Class    : Compte
*/

public class Compte {
 private String typeCpte ;
 private double val_courante;
 private String num�roCpte ;
 public Compte (double valeur, String numero, char t ) 	{ 
	switch (t)	{
		case 'C' : case 'c' : typeCpte = "Courant";
		break;
		case 'J' : case 'j' : typeCpte = "Joint";
		break;
		case 'E' : case 'e' : typeCpte = "Epargne";
		break;
	}
	num�roCpte = numero;
	val_courante = valeur;
 }
 public String getNum�roCpte() {
	return num�roCpte;
 }
 public  String afficherCpte() {
	String message = " <BR> Compte n� : " + num�roCpte + " <br>";
	message = message + "Type du compte : "+typeCpte +"<br>";
	message = message +"Valeur courante : " + val_courante + " &euro;";
	return message;
 }
}
