public class Vente extends Employe {

private final int HORAIRE = 32;
private final int HORAIRE_MAX = 48;
public Vente(String _prenom, String _nom, int _age, int _recrutement){
	super(_prenom, _nom, _age, _recrutement);
}

public int calculerHoraire(int s){
	if (s >= 1 && s <= 3)
	return HORAIRE;
	if (s == 4)
	return HORAIRE_MAX;
	return 0;
}

public String getNom(){
String res = super.getNom();
res = res.replace("employe","vendeur");
return res;
}
}
