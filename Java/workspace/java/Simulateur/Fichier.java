import java.lang.String;
public final class Fichier implements Bloc{
	private String nom ;
	private final String data ;
	
	public Fichier (String nom , String data ) { 	// constructeur 
		this.nom=nom;
		this.data=data;
	}
 
	public String getNom(){		//retourne le nom du fichier
		return nom;
	}

	public String toString(){	//pour afficher la taille du fichier
		return ""+getTaille();
	} 
		
	public int getTaille(){		//retourne la taille du fichier 
		return data.length();
	}

}

