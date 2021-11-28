/**
 * 
 */
package pro;

/**
 * @author Lukas Runt
 *
 */
public class Hrana {
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
	public Hrana(int vertex, int weight) {
		this.vertex = vertex;
		this.weight = weight;
	}

}
