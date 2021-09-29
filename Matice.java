package uloha1;

import java.util.Random;
import java.util.Arrays;

public class Matice {
	
	private static final int N = 5;
	private static final Random r = new Random();

	static int[][] vytvorMatici() {
		int[][] matice = new int[N][N];
		for(int i = 0; i < matice.length; i++) {
			for(int j = 0; j < matice[i].length; j++) {
				matice[i][j] = r.nextInt(100);
			}
		}
		return matice;
	}
	
	static void vypisMatice(int[][] matice) {
		for(int i = 0; i < matice.length; i++) {
			for(int j = 0; j < matice[i].length; j++) {
				System.out.printf("%02d, ", matice[i][j]);
			}
			System.out.println();
		}
	}
	
	static void metoda1(int[][] matice) {
		for(int i = 0; i < matice.length; i++) {
			for(int j = 0; j < matice[i].length; j++) {
				for(int k = 0; k < matice.length; k++) {
					
				}
			}
		}
	}
	
	static void vypisPole(int[] pole) {
		for(int i = 0; i < pole.length; i++) {
			System.out.print(pole[i] + ", ");
		}
	}
	
	public static void main(String[] args) {
		int[][] matice = vytvorMatici();
		vypisMatice(matice);
		metoda1(matice);
		System.out.println("------------------------------");
		System.out.println("------------------------------");
		vypisMatice(matice);
	}
}
