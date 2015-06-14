import java.util.List;

import br.com.etyllica.graph.loader.GraphLoader;
import br.com.etyllica.linear.graph.common.IntegerGraph;
import br.com.etyllica.util.PathHelper;

public class KruskalLoader {
	
	public static void main(String[] args) {
 
		String path = PathHelper.currentDirectory().substring(5)+"../graphs/";
		
		path += "i100gs.txt"; 
		
		GraphLoader loader = new GraphLoader();
		List<IntegerGraph> graphs = loader.load(path);
		
		for(IntegerGraph graph : graphs) {
			System.out.println("Found a graph with "+graph.getEdges().size()+" egdes.");
		}
 
	}
}

