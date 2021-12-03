/**
 * 
 */
package pro;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Lukas Runt
 * @version 1.0 (26-11-2021)
 */
public class Main {
	public static long start, end;
	
	/**
	 * Matoda vytvori graf ze souboru
	 * @param jmenoSouboru jmeno souboru, ze ktereho nacitame graf
	 * @return nacteny graf
	 */
	public static Graph createGraph(String jmenoSouboru) {
		Graph graph = null;
		try {
            List<String> seznamRadek = Files.readAllLines(Paths.get(jmenoSouboru));
            graph = new Graph(Integer.parseInt(seznamRadek.get(0)));
            int pocetHran = 0;
            for(int i = 1; i < seznamRadek.size(); i++) {
            	String[] retezec = seznamRadek.get(i).split(" ");
            	if(!graph.adj[Integer.parseInt(retezec[2]) - 1].stream().anyMatch(h -> h.vertex == Integer.parseInt(retezec[1]) - 1)) {
            		graph.addEdge(Integer.parseInt(retezec[1]) - 1, Integer.parseInt(retezec[2]) - 1, 1);
            		pocetHran++;
            	}
            }
            System.out.printf("Testovany graf s %d vrcholy a %d hranami\n", Integer.parseInt(seznamRadek.get(0)), pocetHran);
        }catch (Exception e) {
        	System.err.println("Doslo k chybe pri cteni souboru: " + jmenoSouboru);
        }
		return graph;
	}
	
	/**
	 * Metoda vygeneruje graf s jednoduchou cestou
	 * @param pocetVrcholu pocet vrcholu v grafu
	 * @return graf s jednoduchou cestou
	 */
	public static Graph generateGraph(int pocetVrcholu) {
		int pocetHran = 0;
		Graph graph = new Graph(pocetVrcholu);
		for(int i = 0; i < pocetVrcholu - 1; i++) {
			graph.addEdge(i, i+1, 1);
			pocetHran++;
		}
		graph.addEdge(0, pocetVrcholu - 1, 1);
		pocetHran++;
		System.out.printf("Testovany graf s %d vrcholy a %d hranami\n", pocetVrcholu, pocetHran);
		return graph;
	}
	

	/**
	 * Metoda vygeneruje graf s jednoduchou cestou
	 * @param pocetVrcholu pocet vrcholu v grafu
	 * @return graf s jednoduchou cestou
	 */
	public static Graph generateGraph2(int pocetVrcholu) {
		int pocetHran = 0;
		Graph graph = new Graph(pocetVrcholu);
		for(int i = 0; i < pocetVrcholu - 1; i++) {
			graph.addEdge(i, i+1, 1);
			pocetHran++;
		}
		System.out.printf("Testovany graf s %d vrcholy a %d hranami\n", pocetVrcholu, pocetHran);
		return graph;
	}
	
	/**
	 * Metoda vygeneruje graf s hranami mezi vsemi vrcholy
	 * @param pocetVrcholu pocet vrcholu v grafu
	 * @return graf s hranami mezi vsemi vrcholy
	 */
	public static Graph generateGraph3(int pocetVrcholu) {
		int pocetHran = 0;
		Graph graph = new Graph(pocetVrcholu);
		for(int i = 0; i < pocetVrcholu - 1; i++) {
			for(int j = i + 1; j < pocetVrcholu; j++) {
				graph.addEdge(i, j, 1);
				pocetHran++;
			}
		}
		System.out.printf("Testovany graf s %d vrcholy a %d hranami\n", pocetVrcholu, pocetHran);
		return graph;
	}
	
	/**
	 * Matoda testuje casy ruznych metod pro nalezeni cetsty Eulerovskoho cyklu
	 * @param g testovany graf
	 * @throws CloneNotSupportedException vyjimka pokod se nepodari vytvorit klon
	 */
	public static void testTime(Graph g) throws CloneNotSupportedException {
		g.test();
		start = System.currentTimeMillis();
		//g.startEulerTour();
		end = System.currentTimeMillis();
		System.out.println("Fleuryho algoritmus: " + (end - start));
		start = System.currentTimeMillis();
		//g.startHierholzer();
		end = System.currentTimeMillis();
		//System.out.println("Hierholzeruv algoritmus: " + (end - start));
		start = System.currentTimeMillis();
		g.startMyAlgorithm();
		end = System.currentTimeMillis();
		System.out.println("Muj algoritmus: " + (end - start));
	}
	
	/**
	 * Metoda pro vypis na zacatku testu
	 * @param p cislo testovaneho grafu
	 */
	public static void printIntro(int p) {
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.printf("                                    Graf %d\n", p);
		System.out.println("------------------------------------------------------------------------------------------");
	}

	/**
	 * Vstumni metoda
	 * @param args parametry pri spusteni
	 * @throws CloneNotSupportedException 
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
		Graph g1 = new Graph(5);
		g1.addEdge(0, 1, 2);
		g1.addEdge(0, 2, 2);
		g1.addEdge(1, 2, 1);
		g1.addEdge(1, 3, 1);
		g1.addEdge(1, 4, 3);
		g1.addEdge(2, 3, 3);
		g1.addEdge(2, 4, 1);
		g1.addEdge(3, 4, 1);
		//Kvuli optimalizovani kodu za behu nebudu prvni test pocitat
		testTime(g1);
		g1.reset();
		printIntro(1);
		testTime(g1);
		
		printIntro(2);
		Graph g2 = new Graph(10);
		g2.addEdge(0, 1, 1);
		g2.addEdge(1, 2, 1);
		g2.addEdge(0, 3, 1);
		g2.addEdge(3, 7, 3);
		g2.addEdge(3, 4, 2);
		g2.addEdge(4, 7, 1);
		g2.addEdge(4, 6, 5);
		g2.addEdge(4, 5, 2);
		g2.addEdge(5, 6, 2);
		g2.addEdge(6, 7, 2);
		g2.addEdge(6, 8, 10);
		g2.addEdge(9, 8, 1);
		g2.addEdge(3, 8, 6);
		g2.addEdge(9, 5, 7);
		testTime(g2);
		
		printIntro(3);
		Graph g3 = createGraph("data/graf1.txt");
		testTime(g3);
		
		printIntro(4);
		Graph g4 = generateGraph(50);
		testTime(g4);
		
		printIntro(5);
		Graph g5 = generateGraph2(50);
		testTime(g5);
		
		printIntro(6);
		Graph g6 = generateGraph3(50);
		testTime(g6);
		
		
		
		/*printIntro(7);
		Graph g7 = createGraph("data/graf2.txt");
		testTime(g7);*/
		
		printIntro(8);
		Graph g8 = createGraph("data/graf5.txt");
		testTime(g8);
		
		/*printIntro(9);
		Graph g9 = createGraph("data/graf6.txt");
		testTime(g9);*/
		
		/*printIntro(10);
		Graph g10 = createGraph("data/graf7.txt");
		testTime(g10);*/
		
	}

}
