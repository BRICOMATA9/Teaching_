package Exercice_1;

import java.lang.reflect.Field;

public class Exo2 {
	private int entier = 12;

	public int getEntier(){
		return entier;
	} 

	public static void modifier (Object o) throws IllegalAccessException{
		Class <?> c = o.getClass();
		Field [] fs = c.getDeclaredFields();
	  for (Field f:fs) {
			if (f.getType() == int.class) {
				//f.setAccessible (true);
				f.set (o,42);
			}
		}
	}

	public static void main (String [] args) throws IllegalAccessException{
		Exo2 o = new Exo2();	
		modifier(o);
		System.out.println(o.getEntier());
	}
}
