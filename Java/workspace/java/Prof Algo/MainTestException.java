import java.util.Scanner;


public class MainTestException {
public static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) throws ErreurValeurNegative {  // ce throws est rajoute � cause de test2
		ClassTestException T=new ClassTestException();
		System.out.println("donnez une 1�re valeur enti�re");
		try { T.test(sc.nextInt());
		}
		catch(Exception e){ System.out.println(e);
		
		}
		System.out.println("Fin test1");
		
		ClassTestException T2=new ClassTestException();
		System.out.println("donnez une 2�me valeur enti�re");
		T2.test2(sc.nextInt());
		System.out.println("Fin test2");
	}

}
