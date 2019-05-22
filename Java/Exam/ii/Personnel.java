import java.util.ArrayList;

public class Personnel {

private ArrayList<Employe> employes;
public Personnel(){
	employes = new ArrayList<Employe>();
}

public void ajouteEmploye(Employe e){
	employes.add(e);
}

public void calculerHoraires(int semaine){
	String f=null;
	for(Employe e : employes){
		try{
			f= e.getNom() + " " + e.calculerHoraire(semaine) + "heures.";
		}catch(ExceptionChargeEmploye ex){
			System.out.println(f);
		}
	}
}

public int HoraireMensuel(){
	int somme = 0;
	for (int semaine = 1; semaine <= 4; semaine++)
		for(Employe e : employes)
			try{
				somme += e.calculerHoraire(semaine);
			}catch(ExceptionChargeEmploye ex){
				System.out.println("");
			}
	return somme/4;
}


public static void main(String[] args) {
	Personnel p = new Personnel();
	p.ajouteEmploye(new Vente("Pierre", "Business", 45));
	p.ajouteEmploye(new Vente("Leon", "Vendtout", 25));
	p.ajouteEmploye(new Production("Yves", "Bosseur", 28));
	p.ajouteEmploye(new Manutention("Jeanne", "Stocketout", 32));
	p.calculerHoraires(2);
	System.out.println("L'horaire moyen dans l'entreprise est de " +
	p.HoraireMensuel() + " heures.");
}
}
