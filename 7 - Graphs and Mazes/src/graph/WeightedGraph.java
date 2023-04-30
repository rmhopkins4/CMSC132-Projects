package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	private Map<V, Map<V, Integer>> graph;
	private final static int INFINITY_CONSTANT = 10000; // reflects node's cost prior to Dijkstra. 
	
	
	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing. 
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	

	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		graph = new HashMap<V, Map<V, Integer>>(); // implement using a HashMap
		observerList = new HashSet<GraphAlgorithmObserver<V>>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
		if(graph.containsKey(vertex)) { // vertex in graph
			throw new IllegalArgumentException("Vertex is already in the graph");
		}
		graph.put(vertex, new HashMap<V, Integer>()); // implement using a HashSet
	}
	
	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		return graph.containsKey(vertex);
	}

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if(!containsVertex(from) || !containsVertex(to)) {
			throw new IllegalArgumentException("One or more vertices are not in the graph");
		}
		if(weight < 0) {
			throw new IllegalArgumentException("Negative weight not permitted.");
		}
		graph.get(from).put(to, weight); // add vertex2 to map of vertex 1
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if(!containsVertex(from) || !containsVertex(to)) {
			throw new IllegalArgumentException("One or more vertices are not in the graph");
		}
		return graph.get(from).get(to);
	}

	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		for(GraphAlgorithmObserver<V> observer : observerList) { // notify of start of search
			observer.notifyBFSHasBegun(); 
		}
		
		Set<V> visitedSet = new HashSet<>(); // visitedSet = ∅
		Queue<V> discoveredCollection = new LinkedList<V>();
		discoveredCollection.add(start); // enqueue start vertex
		
		while(!discoveredCollection.isEmpty()) { // while Queue is not empty
			V currentVertex = discoveredCollection.poll(); // remove vertex from queue
			if(!visitedSet.contains(currentVertex)) { // if vertex is not in visitedSet
				
				for(GraphAlgorithmObserver<V> observer : observerList) { // notify of vertex visit
					observer.notifyVisit(currentVertex);
				}
				
				visitedSet.add(currentVertex); // add vertex to visitedSet
				for(V adjacentVertex : graph.keySet()) { // for each adjacency of vertex
					if(getWeight(currentVertex, adjacentVertex) != null) { // vertex is adjacent
						if(!visitedSet.contains(adjacentVertex)) { // if not in visitedSet
							discoveredCollection.add(adjacentVertex); // add adjacency to queue
						}
					}
				}
				
				if(currentVertex.equals(end)) {
					for(GraphAlgorithmObserver<V> observer : observerList) { // notify of end of search
						observer.notifySearchIsOver();
					}
					return;
				}
			}
		}
	}
	
	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoDFS(V start, V end) {
		for(GraphAlgorithmObserver<V> observer : observerList) { // notify of start of search
			observer.notifyDFSHasBegun(); 
		}
		
		Set<V> visitedSet = new HashSet<>(); // visitedSet = ∅
		Stack<V> discoveredCollection = new Stack<V>();
		discoveredCollection.add(start);
		
		while(!discoveredCollection.isEmpty()) { // while Stack is not empty
			V currentVertex = discoveredCollection.pop(); // remove vertex from queue
			if(!visitedSet.contains(currentVertex)) { // if vertex is not in visitedSet
				
				for(GraphAlgorithmObserver<V> observer : observerList) { // notify of vertex visit
					observer.notifyVisit(currentVertex);
				}
				
				visitedSet.add(currentVertex); // add vertex to visitedSet
				for(V adjacentVertex : graph.keySet()) { // for each adjacency of vertex
					if(getWeight(currentVertex, adjacentVertex) != null) { // vertex is adjacent
						if(!visitedSet.contains(adjacentVertex)) { // if not in visitedSet
							discoveredCollection.add(adjacentVertex); // add adjacency to queue
						}
					}
				}
				
				if(currentVertex.equals(end)) {
					for(GraphAlgorithmObserver<V> observer : observerList) { // notify of end of search
						observer.notifySearchIsOver();
					}
					return;
				}
			}
		}
	}
	
	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		for(GraphAlgorithmObserver<V> observer : observerList) { // notify of start of search
			observer.notifyDijkstraHasBegun();
		}
		
		Set<V> vertices = graph.keySet();
		Set<V> finishedSet = new HashSet<>(); // finishedSet = ∅
		
		// initialize predecessors
		Map<V, V> predecessors = new HashMap<>(); 
		for(V vertex : vertices) {
			predecessors.put(vertex, null);
		}
		
		// initialize costs
		Map<V, Integer> costs = new HashMap<>();
		for(V vertex : vertices) { // cost = infinity (100000)
			costs.put(vertex, INFINITY_CONSTANT);
		}
		costs.put(start, 0); // cost of start node is 0 
		
		// algorithm
		while(finishedSet.size() != vertices.size()) { // while not all vertices are in finished set
			// find vertex K not in finishedSet with smallest cost
			Integer lowestCost = INFINITY_CONSTANT;
			V minCostVertex = null;
			for(V vertex : vertices) {
				if(finishedSet.contains(vertex)) { // if vertex is finished, continue.
					continue;
				}
				if(costs.get(vertex).compareTo(lowestCost) < 0) {
					lowestCost = costs.get(vertex);
					minCostVertex = vertex;
				}
			}
			
			finishedSet.add(minCostVertex); // add vertex K to finishedSet
			
			// find cost of path back, and notify
			for(GraphAlgorithmObserver<V> observer : observerList) { // notify vertex is finished
				observer.notifyDijkstraVertexFinished(minCostVertex, costs.get(minCostVertex));
			}
			
			for(V adjacentVertex : graph.get(minCostVertex).keySet()) {
				if(finishedSet.contains(adjacentVertex)) { // for each neighbor of K not in finishedSet
					continue;
				}
				// if ( Cost[K] + weight(K, J) < Cost[J] )
				if(costs.get(minCostVertex) + getWeight(minCostVertex, adjacentVertex) < costs.get(adjacentVertex)) {
					costs.put(adjacentVertex, costs.get(minCostVertex) + getWeight(minCostVertex, adjacentVertex));
					predecessors.put(adjacentVertex, minCostVertex);
				}
			}
		}
		// all vertices are in finished set now.
		// find path
		V pathVertex = end;
		List<V> pathBack = new LinkedList<>(); // LinkedList makes insertion at front very fast!
		while(pathVertex != null) {
			pathBack.add(0, pathVertex); // add vertices to list at front.
			pathVertex = predecessors.get(pathVertex);
		}
		for(GraphAlgorithmObserver<V> observer : observerList) { // notify search is finished
			observer.notifyDijkstraIsOver(pathBack);
		}
	}
}
