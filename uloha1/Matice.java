package uloha1;

import java.util.Random;

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
	
	public static void main(String[] args) {
		int[][] matice = vytvorMatici();
		vypisMatice(matice);

	}

}
