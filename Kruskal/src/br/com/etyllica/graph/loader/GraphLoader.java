package br.com.etyllica.graph.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.linear.graph.common.IntegerEdge;
import br.com.etyllica.linear.graph.common.IntegerGraph;

public class GraphLoader {

	private static final String END = "END";
	
	private static final String START_EDGES = "LIST_OF_EDGES";
	
	private static final String UNDIRECTED_GRAPH = "UNDIRECTED GRAPH";

	public List<IntegerGraph> load(String path) {

		List<IntegerGraph> graphs = new ArrayList<IntegerGraph>();

		BufferedReader br = null;

		try {

			String line;

			br = new BufferedReader(new FileReader(path));

			IntegerGraph currentGraph = new IntegerGraph();
			Map<Integer, Node<Integer>> nodes = new HashMap<Integer, Node<Integer>>();

			boolean readGraph = true;

			while ((line = br.readLine()) != null) {
				
				if(readGraph) {
					if(line.startsWith(START_EDGES)) {
						readGraph = false;
					}
				} else {
					if (line.startsWith(UNDIRECTED_GRAPH)) {
						graphs.add(currentGraph);
						readGraph = true;
						currentGraph = new IntegerGraph();
						nodes.clear();
					} else if (line.startsWith(END)) {
						graphs.add(currentGraph);
						nodes.clear();
						break;
					} else {
						String text = fixLineSpaces(line);
						String[] parts = text.split(" ");
						
						int originIndex = Integer.parseInt(parts[0]);
						int destinationIndex = Integer.parseInt(parts[1]);
						int cost = Integer.parseInt(parts[2]);
						
						nodes.put(originIndex, new Node<Integer>(originIndex));
						nodes.put(destinationIndex, new Node<Integer>(destinationIndex));
						
						Node<Integer> origin = nodes.get(originIndex);
						Node<Integer> destination = nodes.get(destinationIndex);
						
						currentGraph.addEdge(new IntegerEdge(origin, destination, cost));
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return graphs;
	}

	private String fixLineSpaces(String line) {
		String text = line.trim();
		text = text.replaceAll("	", "");
		text = text.replaceAll("    ", " ");
		text = text.replaceAll("   ", " ");
		text = text.replaceAll("  ", " ");
		return text;
	}

}
