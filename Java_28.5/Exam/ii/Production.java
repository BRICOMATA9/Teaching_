public class Production extends Employe {

private final int HORAIRE = 30;
private final int HORAIRE_MAX = 42;

public Production(String _prenom, String _nom, int _age, int _recrutement){
	super(_prenom, _nom, _age, _recrutement);
}

public int calculerHoraire(int s)throws ExceptionChargeEmploye{
	if (s==1)
		throw new ExceptionChargeEmploye();
	if (s == 1 || s == 2)
	return HORAIRE;
	return HORAIRE;
}

public String getNom(){
	String res = super.getNom();
	res = res.replace("employe","technicien");
	return res;
}
}
