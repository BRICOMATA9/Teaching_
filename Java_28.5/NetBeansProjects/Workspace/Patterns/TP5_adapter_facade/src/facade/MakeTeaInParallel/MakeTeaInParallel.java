package facade.MakeTeaInParallel;

import java.util.concurrent.*;

public class MakeTeaInParallel {
/*
* Résumé du rôle de la méthode.
* Commentaires détaillés sur le role de la methode
* @param val la valeur a traiter
* @return la valeur calculée
* @since 1.0
* @deprecated Utiliser la nouvelle methode main
*/
	@Deprecated
	public static void main(String[] args) {

		//ExecutorService service = Executors.newSingleThreadExecutor();

		TeaCup blueCup = new TeaCup();
		Water water = new Water();
		final TeaInfuser infuser = new TeaInfuser();
		final Tea tea = new Tea("Earl Grey");
				
		Runnable runnable = new Runnable() { 
			public void run() { 
				infuser.addTea(tea);
			}
		};
		Thread t3 = new Thread(runnable);
		t3.start();
		//service.execute(runnable);
		//infuser.addTea(tea);

		water.boilWater();
		
		blueCup.addWater(water);
		
		blueCup.setSteepTime(15);
		
		blueCup.steep();
	}

}

