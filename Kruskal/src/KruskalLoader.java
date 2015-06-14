import java.util.List;

import br.com.etyllica.graph.kruskal.KruskalGraph;
import br.com.etyllica.graph.loader.GraphLoader;
import br.com.etyllica.linear.graph.common.IntegerGraph;
import br.com.etyllica.util.PathHelper;

public class KruskalLoader {
	
	public static void main(String[] args) {
 
		String path = PathHelper.currentDirectory().substring(5)+"../graphs/";
		
		path += "i600gs.txt";
		
		GraphLoader loader = new GraphLoader();
		List<IntegerGraph> graphs = loader.load(path);
		
		System.out.println("File: "+path);
		System.out.println("----------------------");
		
		for(IntegerGraph graph : graphs) {	
			KruskalGraph kruskal = new KruskalGraph(graph);
			runKruskal(kruskal, graph);
		}
	
	}
	
	private static void runKruskal(KruskalGraph k, IntegerGraph graph) {
		//Find minimun spanning tree using kruskal
		long beforeKruskal = System.currentTimeMillis();
		System.out.println("Before Kruskal");
		System.out.println("Time: "+beforeKruskal);
		System.out.println("Nodes: "+graph.getNodes().size());
		System.out.println("Edges: "+graph.getEdges().size());		
		
		IntegerGraph mg = k.minimunSpanningTree();

		long afterKruskal = System.currentTimeMillis();
		System.out.println("After Kruskal");
		System.out.println("Time: "+afterKruskal);
		System.out.println("Nodes: "+mg.getNodes().size());
		System.out.println("Edges: "+mg.getEdges().size());

		System.out.println("----------------------");
		long time = afterKruskal-beforeKruskal;
		System.out.println("Total Time: "+time+"ms");
		System.out.println("----------------------");
	}
}

