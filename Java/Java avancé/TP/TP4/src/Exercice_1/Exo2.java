package Exercice_1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Exo2 {

	public static void créer_flux(){

    Stream.Builder<String> b = Stream.builder();
    b.accept("a");
    b.accept("b");
    b.accept("c");
    b.accept("d");
    b.accept("e");
    
    Stream<String> stream = b.build();

		//System.out.println(Arrays.toString(stream.toArray()));
    stream.forEach(System.out::println); 

  }

  public static void main(String[] args) {
		créer_flux();
  }
}
