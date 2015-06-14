package br.com.etyllica.graph.kruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.linear.graph.common.IntegerEdge;
import br.com.etyllica.linear.graph.common.IntegerGraph;

public class KruskalGraph {

	private List<IntegerEdge> edges = new ArrayList<IntegerEdge>();

	private Set<IntegerEdge> selectedEdges = new HashSet<IntegerEdge>();

	private Map<Node<Integer>, IntegerGraph> graphs = new HashMap<Node<Integer>, IntegerGraph>();

	private IntegerGraph completeGraph;

	private IntegerGraph candidateGraph;

	public KruskalGraph(IntegerGraph graph) {
		super();
		//Init all edges
		this.edges.addAll(graph.getEdges());
		
		//foreach v ∈ G.V:
		//MAKE-SET(v)
		for(Node<Integer> node: graph.getNodes()) {
			graphs.put(node, new IntegerGraph());
		}

		this.completeGraph = graph;
	}

	/**
	 * Find the Minimum Spanning Tree
	 * @param graph
	 * @return Minimum Spanning Tree Graph
	 */
	public IntegerGraph minimunSpanningTree() {

		Collections.sort(this.edges);
		
		int e = 0;
		for (IntegerEdge edge : edges) {
			
			//If do not closes cycle
			if (!closesCycle(edge)) {
				unionFind(edge);
				selectedEdges.add(edge);
			}
			
			if (candidateGraph.getNodes().size() == completeGraph.getNodes().size()) {
				graphs.clear();
				return candidateGraph;
			}
		}

		return completeGraph;
	}

	private boolean closesCycle(IntegerEdge edge) {
		Node<Integer> origin = edge.getOrigin();
		Node<Integer> destination = edge.getDestination();

		IntegerGraph originGraph = graphs.get(origin);
		IntegerGraph destinationGraph = graphs.get(destination);

		//If graphs are the same
		return originGraph == destinationGraph;
	}

	private void unionFind(IntegerEdge edge) {

		Node<Integer> origin = edge.getOrigin();
		Node<Integer> destination = edge.getDestination();

		IntegerGraph originGraph = graphs.get(origin);
		IntegerGraph destinationGraph = graphs.get(destination);

		IntegerGraph biggerGraph = originGraph;
		IntegerGraph smallerGraph = destinationGraph;

		if (smallerGraph.getNodes().size() > biggerGraph.getNodes().size()) {
			biggerGraph = destinationGraph;
			smallerGraph = originGraph;
			graphs.put(origin, biggerGraph);
		} else {
			//Point destinationGraph to the new Graph
			graphs.put(destination, biggerGraph);
		}

		//Add edge to biggerGraph
		biggerGraph.addEdge(edge);

		//Merge Graphs
		biggerGraph.mergeGraph(smallerGraph);
		
		candidateGraph = biggerGraph;
	}

	public List<IntegerEdge> getEdges() {
		return edges;
	}

	public Set<IntegerEdge> getSelectedEdges() {
		return selectedEdges;
	}

}
