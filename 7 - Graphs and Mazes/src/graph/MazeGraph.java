package graph;
import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	
	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and B) which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		super();
		// For every juncture in the maze, add juncture to graph.
		for(int x = 0; x < maze.getMazeWidth(); x++) {
			for(int y = 0; y < maze.getMazeHeight(); y++) {
				addVertex(new Juncture(x, y));
			}
		}
		 
		// For every juncture in the maze, check and add edges if needed.
		for(int x = 0; x < maze.getMazeWidth(); x++) {
			for(int y = 0; y < maze.getMazeHeight(); y++) {
				addEdgesToAdjacentJunctures(new Juncture(x, y), maze); // call private helper method
			}
		}
	}
	
	/**
	 * Adds edges to adjacent junctures by invoking methods from the Maze class
	 */
	private void addEdgesToAdjacentJunctures(Juncture junc, Maze maze) {
		// check and add above
		if(!(maze.isWallAbove(junc)) && containsVertex(new Juncture(junc.getX(), junc.getY() - 1))) {
			this.addEdge(junc, new Juncture(junc.getX(), junc.getY() - 1), maze.getWeightAbove(junc));
		}
		// check and add below
		if(!(maze.isWallBelow(junc)) && containsVertex(new Juncture(junc.getX(), junc.getY() + 1))) {
			this.addEdge(junc, new Juncture(junc.getX(), junc.getY() + 1), maze.getWeightBelow(junc));
		}
		// check and add left
		if(!(maze.isWallToLeft(junc)) && containsVertex(new Juncture(junc.getX() - 1, junc.getY()))) {
			this.addEdge(junc, new Juncture(junc.getX() - 1, junc.getY()), maze.getWeightToLeft(junc));
		}
		// check and add right
		if(!(maze.isWallToRight(junc)) && containsVertex(new Juncture(junc.getX() + 1, junc.getY()))) {
			this.addEdge(junc, new Juncture(junc.getX() + 1, junc.getY()), maze.getWeightToRight(junc));
		}
	}
}
