package test;

import java.util.ArrayList;

class Pumpa{
	int cena;
	int vzdalenost;
	
	Pumpa(int vzdalenost, int cena){
		this.vzdalenost = vzdalenost;
		this.cena = cena;
	}
}

public class Main {
	
	public static ArrayList<Pumpa> pumpy;
	public static int poziceKamionu = 0;
	public static final int VELIKOST_NADRZE = 300; 
	public static int palivoVNadrzi = 300;
	public static int konecnaVzdalenost = 800;

	public static ArrayList<Pumpa> naplnPole() {
		ArrayList<Pumpa> pole = new ArrayList<>();
		pole.add(new Pumpa(200, 10));
		pole.add(new Pumpa(300, 40));
		pole.add(new Pumpa(500, 20));
		pole.add(new Pumpa(600, 10));
		return pole;
	}
	
	public static void main(String[] args) {
		pumpy = naplnPole();
		Pumpa predchoziPumpa = null;
		Pumpa aktPumpa = pumpy.stream().filter(p -> p.vzdalenost > poziceKamionu && p.vzdalenost <= poziceKamionu + VELIKOST_NADRZE).sorted((p1,p2) -> p1.cena - p2.cena).findFirst().get();
		while(palivoVNadrzi + poziceKamionu < konecnaVzdalenost) {
			palivoVNadrzi -= aktPumpa.vzdalenost - poziceKamionu;
			poziceKamionu = aktPumpa.vzdalenost;
			if(poziceKamionu + VELIKOST_NADRZE > konecnaVzdalenost) {
				System.out.printf("Tankovano %d litru, cena %d Kc/l\n", konecnaVzdalenost - (poziceKamionu + palivoVNadrzi), aktPumpa.cena);
				palivoVNadrzi += konecnaVzdalenost - (poziceKamionu + palivoVNadrzi);
			} else {
				predchoziPumpa = aktPumpa;
				aktPumpa = pumpy.stream().filter(p -> p.vzdalenost > poziceKamionu && p.vzdalenost <= poziceKamionu + VELIKOST_NADRZE).sorted((p1,p2) -> p1.cena - p2.cena).findFirst().get();
				if(predchoziPumpa.cena < aktPumpa.cena) {
					System.out.printf("Tankovano %d litru, cena %d Kc/l\n", VELIKOST_NADRZE - palivoVNadrzi, predchoziPumpa.cena);
					palivoVNadrzi = VELIKOST_NADRZE;
				}else {
					System.out.printf("Tankovano %d litru, cena %d Kc/l\n", aktPumpa.vzdalenost - (poziceKamionu + palivoVNadrzi), predchoziPumpa.cena);
					palivoVNadrzi += aktPumpa.vzdalenost - (poziceKamionu + palivoVNadrzi);
				}
				
			}
		}
	}

}
