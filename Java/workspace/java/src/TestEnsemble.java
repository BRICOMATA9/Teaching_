import java.util.Scanner;
public class TestEnsemble {

	public static void main(String[] args){
      		TabEnsemble t=new TabEnsemble();
      		Scanner scan;
      		boolean b=false;
		while(!b){
      			try{
      				scan=new Scanner(System.in);
      				t.lireTabEnsemble(scan.nextInt());      		
      				b=true;
      			}
      			catch(Exception e){
      				System.out.println(e);
      			}
      		}
       
      		t.afficheTabEnsemble();
     
      		Ensemble e=new Ensemble();
      		System.out.println("Insertion"); 
      		e.lireEnsemble();
      		t.insereEnsemble(e);
      		t.afficheTabEnsemble();
	}

}
