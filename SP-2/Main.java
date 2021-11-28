/**
 * 
 */
package pro;

/**
 * @author Lukas Runt
 * @version 1.0 (26-11-2021)
 */
public class Main {

	/**
	 * @param args
	 * @throws CloneNotSupportedException 
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
		long start, end;
		/*Graph g1 = new Graph(5);
		g1.addEdge(0, 1, 2);
		g1.addEdge(0, 2, 2);
		g1.addEdge(1, 2, 1);
		g1.addEdge(1, 3, 1);
		g1.addEdge(1, 4, 3);
		g1.addEdge(2, 3, 3);
		g1.addEdge(2, 4, 1);
		g1.addEdge(3, 4, 1);
		g1.test();
		g1.makeEuler();
		g1.test();
		g1.myAlgorithm();*/
		/*start = System.currentTimeMillis();
		g1.startEulerTour();
		end = System.currentTimeMillis();
		System.out.println("Fleuryho algoritmus: " + (end - start));
		start = System.currentTimeMillis();
		g1.startHierholzer();
		end = System.currentTimeMillis();
		System.out.println("H... algoritmus: " + (end - start));*/
		
		Graph g2 = new Graph(8);
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
		g2.test();
		g2.makeEuler();
		g2.test();
		start = System.currentTimeMillis();
		g2.startEulerTour();
		end = System.currentTimeMillis();
		System.out.println("Fleuryho algoritmus: " + (end - start));
		start = System.currentTimeMillis();
		g2.startHierholzer();
		end = System.currentTimeMillis();
		System.out.println("Hierholzeruv algoritmus: " + (end - start));
		start = System.currentTimeMillis();
		g2.startMyAlgorithm();
		end = System.currentTimeMillis();
		System.out.println("Runtuv algoritmus: " + (end - start));
	}

}
