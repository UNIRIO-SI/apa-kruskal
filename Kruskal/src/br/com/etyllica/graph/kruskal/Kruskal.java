package br.com.etyllica.graph.kruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.linear.graph.common.IntegerEdge;
import br.com.etyllica.linear.graph.common.IntegerGraph;

public class Kruskal {

	private List<IntegerEdge> edges = new ArrayList<IntegerEdge>();
	public Set<IntegerEdge> selectedEdges = new HashSet<IntegerEdge>();
	
	public Kruskal() {
		super();
	}
	
	public Kruskal(List<IntegerEdge> edges) {
		super();
		this.edges.addAll(edges);
	}
	
	public void addEdge(IntegerEdge edge) {
		edges.add(edge);
	}
	
	/**
	 * Find the Minimum Spanning Tree
	 * @param graph
	 * @return Minimum Spanning Tree Graph
	 */
	public IntegerGraph minimunSpanningTree(IntegerGraph graph) {
		
		IntegerGraph minimunGraph = new IntegerGraph();
		
		//Sort Edges
		Collections.sort(edges);
		
		Set<Node<Integer>> nodes = minimunGraph.getNodes();
		
		for (IntegerEdge edge :edges) {
			
			//If closes cycle
			if (!closesCycle(edge, nodes)) {
				minimunGraph.addNode(edge.getOrigin());
				minimunGraph.addNode(edge.getDestination());
				minimunGraph.addEdge(edge);
				
				selectedEdges.add(edge);
			}
			
			if (nodes.size() == graph.getNodes().size()) {
				break;
			}
		}
		
		return minimunGraph;
	}

	private boolean closesCycle(IntegerEdge edge, Set<Node<Integer>> nodes) {
		
		Node<Integer> origin = edge.getOrigin();
		Node<Integer> destination = edge.getDestination();
		
		return (nodes.contains(origin) && nodes.contains(destination));
	}
	
}
