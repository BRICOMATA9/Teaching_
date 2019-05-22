public class Manutention extends Employe {

private final int HORAIRE = 35;
public Manutention(String _prenom, String _nom, int _age, int _recrutement){
	super(_prenom, _nom, _age, _recrutement);
}

public int calculerHoraire(int s){
return HORAIRE;
}

public String getNom(){
	String res = super.getNom();
	res = res.replace("employe","manutentionnaire");
	return res;
}
}
