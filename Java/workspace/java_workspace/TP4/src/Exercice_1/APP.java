package Exercice_1;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class APP {
		
		public static IntStream msb (BigInteger i) {
			int length = i.bitLength();
			int[] tab = new int[length];
			for(int j = 0;j<=length-1;j++)
				tab[length-1-j] =  i.testBit(j)?1:0;
			return 	Arrays.stream(tab);
			
		}

  public static void main(String[] args) {
	  Stream<BigInteger> pairs = Stream.iterate(BigInteger.ZERO, BigInteger.valueOf(2)::add);
	  Stream<BigInteger> pairs1000 = pairs.limit(1000);
	  IntStream bits = pairs1000.flatMapToInt(s -> IntStream.concat(APP.msb(s).map(i-> i==0?'0':'1'),IntStream.of(' ')));
	  bits.forEach(x -> {System.out.println((char)x);});
  }
}