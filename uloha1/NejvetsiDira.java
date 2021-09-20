package uloha1;

import java.util.Random;
import java.util.Arrays;

public class NejvetsiDira {
	
	private static int n = 5;
	
	static int[] vytvorPole() {
		int[] pole = new int[n]; 
		Random r = new Random();
		for(int i = 0; i < pole.length; i++) {
			pole[i] = (r.nextInt(100) - 50);
		}
		return pole;
	}
	
	static void vypisPole(int[] pole) {
		for(int i = 0; i < pole.length; i++) {
			System.out.print(pole[i] + ", ");
		}
		System.out.println();
	}
	
	static void vypisNejvetsiDiru(int[] pole) {
		int index = 1;
		int velikostDiry = 0;
		for(int i = 1; i < pole.length; i++) {
			if(pole[i] - pole[i - 1] > velikostDiry) {
				velikostDiry = pole[i] - pole[i - 1];
				index = i;
			}
		}
		System.out.println("Nejvetsi dira je mezi cisly " + pole[index - 1] + " a " + pole[index]);
	}

	public static void main(String[] args) {
		int[] pole = vytvorPole();
		vypisPole(pole);
		Arrays.sort(pole);
		vypisPole(pole);
		vypisNejvetsiDiru(pole);
	}

}
