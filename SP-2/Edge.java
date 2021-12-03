/**
 * 
 */
package pro;

/**
 * @author Lukas Runt
 *
 */
public class Edge {
	/** Vrchol propojeni*/
	public int vertex;
	/** Vyha hrany */
	public int weight;
	public boolean visited = false;

	/**
	 * Konstruktor vahy
	 * @param vertex vrchol se se kterym je hrana
	 * @param weight vaha hrany
	 */
	public Edge(int vertex, int weight) {
		this.vertex = vertex;
		this.weight = weight;
	}

}
