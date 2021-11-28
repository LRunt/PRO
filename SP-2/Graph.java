/**
 * 
 */
package pro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.Popup;

/**
 * @author Lukas Runt
 * @version 1.0 (26.11.2021)
 */
public class Graph{
	/** pocet vrcholu */ 
	private int vertexCount;
	/*List hra grafu*/
    private List<Hrana> adj[];
    /** clone grafu (aby se nezmìnil pùvodní graf)*/
    private List<Hrana> clone[];
	 
	/**
	 * Konstruktor grafu
	 * @param vertexCount pocet vrcholu grafu
	 */
	public Graph(int vertexCount) {
		this.vertexCount = vertexCount;
		initGraph();
	}
	
	/**
	 * Inicializace grafu
	 */
	private void initGraph() {
		adj = new ArrayList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adj[i] = new ArrayList<Hrana>();
        }
	}
	
	/**
	 * Pridavani hrany do grafu
	 * @param v id vrcholu
	 * @param w id vrcholu
	 */
    public void addEdge(int v, int w, int weight){
        adj[v].add(new Hrana(w, weight));// Add w to v's list.
        adj[w].add(new Hrana(v, weight)); //The graph is undirected
    }
    
    // This function removes edge u-v from graph.
    public void removeEdge(int u, int v)
    {
    	adj[u] = Stream.concat(
                adj[u].stream().filter(a -> a.vertex != v),
                adj[u].stream().filter(a -> a.vertex == v).skip(1))
            .collect(Collectors.toList());
    	adj[v] = Stream.concat(
                adj[v].stream().filter(a -> a.vertex != u),
                adj[v].stream().filter(a -> a.vertex == u).skip(1))
            .collect(Collectors.toList());
    }
    
    //---------------------------------------------------------------------------------------------------------------------------
    //Test zda graf obsahuje eulerovsky cyklus
    //---------------------------------------------------------------------------------------------------------------------------
    
   /**
    * Metoda testuje zda je graf spojity
    * @return true - graf je spojity, false - graf neni spojity 
    */
    public boolean isConnected()
    {
        //Oznaceni vsech vrcholu jako neprosle
        boolean visited[] = new boolean[vertexCount];
        int i;
 
        // Hledani vrcholu s nenulovym stupnem
        for (i = 0; i < vertexCount; i++)
            if (adj[i].size() != 0)
                break;
 
        // Kdyz nani v grafu zadna hrana vrat true
        if (i == vertexCount)
            return true;
 
        // Zacatek DFS u rvrcholu s nenulovym stupnem
        DFSUtil(i, visited);
 
        // Kontrola, zda jsou prosle vsechny vrcholy s nenulovym stupnem
        for (i = 0; i < vertexCount; i++)
           if (visited[i] == false && adj[i].size() > 0)
                return false;
 
        return true;
    }
    
    /**
     * Metoda prohledavani do sirky
     * @param v id vrcholu
     * @param visited identifikator, zda byl vrchol navstiven
     */
    void DFSUtil(int v,boolean visited[]){
        // Oznaceni vrcholu jako navstiveny
        visited[v] = true;
 
        // Rekurzivni prochazeni
        Iterator<Hrana> i = adj[v].listIterator();
        while (i.hasNext())
        {
            Hrana n = i.next();
            if (!visited[n.vertex])
                DFSUtil(n.vertex, visited);
        }
    }
    
    /* The function returns one of the following values
    0 --> If graph is not Eulerian
    1 --> If graph has an Euler path (Semi-Eulerian)
    2 --> If graph has an Euler Circuit (Eulerian)  */
    int isEulerian(){
    	// Check if all non-zero degree vertices are connected
    	if (isConnected() == false)
    		return 0;

    	// Count vertices with odd degree
    	int odd = 0;
    	for (int i = 0; i < vertexCount; i++)
    		if (adj[i].size()%2!=0)
    			odd++;

    	// If count is more than 2, then graph is not Eulerian
    	if (odd > 2)
    		return 0;

    	// If odd count is 2, then semi-eulerian.
    	// If odd count is 0, then eulerian
    	// Note that odd count can never be 1 for undirected graph
    	return (odd==2)? 1 : 2;
    }

    // Function to run test cases
    boolean test(){
    	int res = isEulerian();
    	if (res == 0) {
    		System.out.println("Graf neni Eulerovsky");
    		return false;
         	}
    	else if (res == 1)
    		System.out.println("Graf obsahuje Eulerovsky tah");
    	else
    		System.out.println("Graf obsahuje Eulerovsky cyklus");
    	return true;
 }
    
    //-------------------------------------------------------------------------------------------------------------------------------
    //Vytvareni eulerovskeho grafu
    //-------------------------------------------------------------------------------------------------------------------------------
    /**
     * Metoda udela z neulerovskeho cyklu eulerovsky
     */
    public void makeEuler() {
    	List<Integer> oddVertex = new ArrayList<>();
    	//Zjisteni lichych vrcholu
    	for(int i = 0; i < vertexCount; i++) {
    		if(adj[i].size() % 2 != 0) {
    			oddVertex.add(i);
    		}
    		System.out.printf("Vrchol %d stupen: %d\n",i, adj[i].size());
    	}
	 
    	while(oddVertex.size() != 0) {
    		int[] distance = new int[oddVertex.size()];
    		List<Integer>[] path = new List[oddVertex.size()];
    		List<Integer>[] weight = new List[oddVertex.size()];
    		
    		// Vypocet nejkratsich vzdalenosti
    		for(int i = 1; i < oddVertex.size(); i++) {
    			List<Integer> pth = new ArrayList<>();
    			path[i] = pth;
    			List<Integer> wgh = new ArrayList<>();
    			weight[i] = wgh;
    			distance[i] = dijkstra(oddVertex.get(0), oddVertex.get(i), pth, wgh);
    		}
    		
    		//zjisteni nejkratsi vzdalenosti
    		int minimum = Integer.MAX_VALUE;
    		int index = 0;
    		for(int i = 1; i < distance.length; i++) {
    			if(minimum > distance[i]) {
    				minimum = distance[i];
    				index = i;
    			}
    		}
    		
    		for(int i = 0; i < path[index].size() - 1; i++) {
    			addEdge(path[index].get(i), path[index].get(i + 1), weight[index].get(i));
    		}
    		oddVertex.remove(index);
    		oddVertex.remove(0);
    	}
	
    }
 
    /**
     * Dijkstruv algorimus na hledani nejkratsi cesty
     * @param start 
     * @param end
     * @return
     */
    public int dijkstra(int start, int end, List path, List edgeWeight) {
    	int[] distance = fieldInit(Integer.MAX_VALUE);
    	int[] predecessor = fieldInit(-1);
    	int[] weight = new int[vertexCount];
    	byte[] finite = new byte[vertexCount];
	 
    	distance[start] = 0;
    	predecessor[start] = 0;
    	finite[start] = 1;
		int current = start;
	 
		while(finite[end] == 0) {
			for(int i = 0; i < adj[current].size(); i++) {
				if(distance[adj[current].get(i).vertex] > distance[current] + adj[current].get(i).weight) {
					distance[adj[current].get(i).vertex] = distance[current] + adj[current].get(i).weight;
					predecessor[adj[current].get(i).vertex] = current;
					weight[adj[current].get(i).vertex] = adj[current].get(i).weight;
				}
			}
			int minimum = Integer.MAX_VALUE;
		 	int index = - 1;
		 	for(int i = 0; i < distance.length; i++) {
		 		if(finite[i] == 0) {
		 			if(minimum > distance[i]) {
		 				minimum = distance[i];
		 				index = i;
		 			}
		 		}
		 	}
		 	if(index == -1) break;
		 	current = index;
		 	finite[current] = 1;
		}

		current = end;
		while(current != start){
			path.add(current);
			edgeWeight.add(weight[current]);
			current = predecessor[current]; 
		}
		path.add(current);
	 
		path.stream().forEach(a -> System.out.print(a + " "));
		System.out.println();
		edgeWeight.stream().forEach(a -> System.out.print(a + " "));
		System.out.println();
	 
		return distance[end];
    }
 
    /**
     * Inicializace pole vybranou hodnotou
     * @param value hodnota, kterou se naplni pole
     * @return inicializovane pole vzdalenosti
     */
    private int[] fieldInit(int value) {
    	int[] field = new int[vertexCount];
    	
    	for(int i = 0; i < field.length; i++) {
    		field[i] = value;
    	}
	 
    	return field;
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------
    //Fleuryho algoritmus
    //-----------------------------------------------------------------------------------------------------------------------------
    public void startEulerTour() throws CloneNotSupportedException {
    	clone = adj.clone();
    	printEulerTour();
    	adj = clone.clone();
    }
    
    /* The main function that print Eulerian Trail.
    It first finds an odd degree vertex (if there
    is any) and then calls printEulerUtil() to
    print the path */
    public void printEulerTour() {
    	// Find a vertex with odd degree
    	Integer u = 0;
    	for (int i = 0; i < vertexCount; i++) {
    		if (adj[i].size() % 2 == 1) {
    			u = i;
    			break;
    		}
    	}

    	// Print tour starting from oddv
    	List<Integer> cesta = new ArrayList<>();
    	cesta.add(u);
    	printEulerUtil(u, cesta);
    	for(int i = 0; i < cesta.size() - 1; i++) {
    		System.out.print(cesta.get(i) + " -> ");
    	}
    	System.out.print(cesta.get(cesta.size() - 1));
    	System.out.println();
    }
    
    // Print Euler tour starting from vertex u
    private void printEulerUtil(Integer u, List<Integer> cesta){
    	// Recur for all the vertices adjacent to this
    	// vertex
    	for (int i = 0; i < adj[u].size(); i++) {
    		int v = adj[u].get(i).vertex;
    		// If edge u-v is a valid next edge
    		if (isValidNextEdge(u, v)) {
    			cesta.add(v);

    			// This edge is used so remove it now
    			removeEdge(u, v);
    			printEulerUtil(v, cesta);
    		}
    	}
    }

    // The function to check if edge u-v can be
    // considered as next edge in Euler Tout
    private boolean isValidNextEdge(Integer u, Integer v)
    {
    	// The edge u-v is valid in one of the
    	// following two cases:

    	// 1) If v is the only adjacent vertex of u
    	// ie size of adjacent vertex list is 1
    	if (adj[u].size() == 1) {
    		return true;
    	}

    	// 2) If there are multiple adjacents, then
    	// u-v is not a bridge Do following steps
    	// to check if u-v is a bridge
    	// 2.a) count of vertices reachable from u
    	boolean[] isVisited = new boolean[this.vertexCount];
    	int count1 = dfsCount(u, isVisited);

    	// 2.b) Remove edge (u, v) and after removing
    	//  the edge, count vertices reachable from u
    	removeEdge(u, v);
    	isVisited = new boolean[this.vertexCount];
    	int count2 = dfsCount(u, isVisited);

    	// 2.c) Add the edge back to the graph
    	addEdge(u, v, 1);
    	return (count1 > count2) ? false : true;
    }

    // A DFS based function to count reachable
    // vertices from v
    private int dfsCount(Integer v, boolean[] isVisited)
    {
    	// Mark the current node as visited
    	isVisited[v] = true;
    	int count = 1;
    	// Recur for all vertices adjacent to this vertex
    	for (Hrana adj : adj[v]) {
    		if (!isVisited[adj.vertex]) {
    			count = count + dfsCount(adj.vertex, isVisited);
    		}
    	}
    	return count;
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    //Hierholzeruv algoritmus
    //-----------------------------------------------------------------------------------------------------------------------------
    public void startHierholzer() {
    	printEulerianCircuit(adj.clone());
    }
    
    private void printEulerianCircuit(List<Hrana> adj[]){
      // adj represents the adjacency list of
      // the directed graph
      // edge represents the number of edges emerging from a vertex
      
      Map<Integer,Integer> edges = new HashMap<Integer,Integer>();
    
      for (int i=0; i<adj.length ; i++)
      {
          //find the count of edges to keep track of unused edges
          edges.put(i,adj[i].size());
      }
      
      // Maintain a stack to keep vertices
      Stack<Integer> curr_path = new Stack<Integer>();
    
      // vector to store final circuit
      List<Integer> circuit = new ArrayList<Integer>();
    
      // We start from vertex 0
      curr_path.push(0);
      
      // Current vertex
      int curr_v = 0;
    
      while (!curr_path.empty()) {
          // If there's remaining edge
          if (edges.get(curr_v)>0) {
              // Push the vertex visited.
              curr_path.push(adj[curr_v].get(edges.get(curr_v) - 1).vertex);
    
              // and remove that edge or decrement the edge count.
              edges.put(curr_v, edges.get(curr_v) - 1);
              int prevous_vertex = curr_v;
              // Move to next vertex
              curr_v = curr_path.peek();
              
              adj[curr_v] = Stream.concat(
                      adj[curr_v].stream().filter(a -> a.vertex != prevous_vertex),
                      adj[curr_v].stream().filter(a -> a.vertex == prevous_vertex).skip(1))
                  .collect(Collectors.toList());
              edges.put(curr_v, edges.get(curr_v) - 1);
          }
    
          // back-track to find remaining circuit
          else{
          circuit.add(curr_path.pop());
          if(!curr_path.empty()) {
        	 curr_v = curr_path.peek();
          }
         
          }
      }
    
      // After getting the circuit, now print it in reverse
      for (int i=circuit.size()-1; i>=0; i--)
      {
          System.out.print(circuit.get(i));
          
          if(i!=0)
          System.out.print(" -> ");
      }
      System.out.println();
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    // Runtuv algoritmus (muj vlastni) - na zaklade backtrackingu
    //-----------------------------------------------------------------------------------------------------------------------------
    /**
     * Metoda spusti algoritmus pomoci klonu grafu
     */
    public void startMyAlgorithm(){
    	myAlgorithm(adj.clone());
    }
    
    /**
     * Muj (Runtuv) algoritmus na nalezeni cesty cinskeho listonose
     * Princip na bazi back-trackingu
     * @param adj clone pole
     */
    public void myAlgorithm(List<Hrana> adj[]) {
    	//pocet hran v grafu
    	int numberOfEdges = 0;
    	//pocet navstivenych hran
    	int edgesVisited = 0;
    	//zasobnik do ktereho se bude ukladat cesta
    	Stack<Integer> path = new Stack<>();
    	
    	//Spocitani hran
    	for(int i = 0; i < adj.length; i++) {
    		for(int j = 0; j < adj[i].size(); j++) {
    			numberOfEdges++;
    		}
    	}
    	//jelikoz jde o neorientovany graf bude potreba projit polovina hran
    	numberOfEdges = numberOfEdges/2;
    	
    	int currentVex = 0;
    	int[] numberOfVisit = new int[vertexCount];
    	//museime projit vsechny hrany
    	while(numberOfEdges > edgesVisited) {
    		//pokud vrcholu zbyvaji hrany
    		while(adj[currentVex].size() > numberOfVisit[currentVex]) {
    			//otestovani jestli byla hrana pouzita
    			if(!adj[currentVex].get(numberOfVisit[currentVex]).visited) {
    				adj[currentVex].get(numberOfVisit[currentVex]).visited = true;
    				numberOfVisit[currentVex]++;
    				path.add(currentVex);
    				currentVex = adj[currentVex].get(numberOfVisit[currentVex] - 1).vertex;
    				adj[currentVex].stream().filter(e -> e.vertex == path.peek()).filter(e -> e.visited == false).findFirst().get().visited = true;
    				edgesVisited++;
    				numberOfVisit[currentVex] = 0;
    				break;
    			//pokud byla zkusime dalsi hranu v poradi
    			}else {
    				numberOfVisit[currentVex]++;
    			}
    		}
    		//pokud nezbyva zadna hrana musime se vrati do predchoziho vrcholu a zkusit jinou hranu
    		if(adj[currentVex].size() == numberOfVisit[currentVex]) {
    			int placeholder = currentVex;
    			adj[path.peek()].stream().filter(e -> e.vertex == placeholder).filter(e -> e.visited == true).findFirst().get().visited = false;
    			adj[currentVex].stream().filter(e -> e.vertex == path.peek()).filter(e -> e.visited == true).findFirst().get().visited = false;
    			numberOfVisit[currentVex]--;
    			currentVex = path.pop();
    			edgesVisited--;
    		}
    	}
    	//neni potreba nikam chodit, vsechny hrany byli prosle, musime ale vlozit zaverecny vrchol
    	path.push(currentVex);
    	
    	//vypsani cesty
    	while(!path.empty()) {
    		System.out.print(path.pop());
    		if(!path.empty()) {
    			System.out.print(" -> ");
    		}
    	}
    	System.out.println();
    }
    
    
}
