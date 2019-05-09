public abstract class Employe {

private String nom;
private String prenom;
int age;
int recrutement;

public Employe(String _prenom, String _nom, int _age, int _recrutement){
	prenom = _prenom;
	nom = _nom;
	age = _age;
	recrutement = _recrutement;
}

public abstract int calculerHoraire(int semaine) throws ExceptionChargeEmploye;

public String getNom(){
	return "employe " + prenom + " " + nom;
}
}

