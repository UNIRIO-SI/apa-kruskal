package br.com.etyllica.graph;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.graphics.SVGColor;
import br.com.etyllica.graph.kruskal.Kruskal;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.linear.graph.common.IntegerEdge;
import br.com.etyllica.linear.graph.common.IntegerGraph;
import br.com.etyllica.util.PathHelper;

public class KruskalXMLGraphExample extends Application{

	public KruskalXMLGraphExample(int w, int h) {
		super(w, h);
	}

	private Kruskal k = new Kruskal();

	private Node<Integer> root;
	
	private Set<Node<Integer>> visited = new HashSet<Node<Integer>>();

	private IntegerGraph graph;

	private final double nodeDistance = 350;
	
	@Override
	public void load(){
		
		clearBeforeDraw = false;

		graph = new IntegerGraph();

		//Open file in graphs folder
		String graphFile = "complete_graph_100.xml";

		GraphRepository gr = loadGraph(graphFile);

		if (gr != null) {
			for (GraphInfo graphInfo:gr.getGraphInfo()) {
				Map<Integer, Node<Integer>> nodes = new HashMap<Integer, Node<Integer>>();

				for (int i = 0; i <graphInfo.getNodeName().size(); i++){
					Node<Integer> node = new Node<Integer>(graphInfo.getNodeName().get(i));
					nodes.put(graphInfo.getNodeName().get(i), node);
					
					loading = (i*100)/graphInfo.getNodeName().size();
				}
				for (EdgeInfo edgeInfo: graphInfo.getEdgeInfo()) {
					Node<Integer> node1 = nodes.get(edgeInfo.getFirstNode());
					Node<Integer> node2 = nodes.get(edgeInfo.getSecondNode());
					addEdge(new IntegerEdge(node1, node2, edgeInfo.getValue()));
				}
				
				root = nodes.get(0);
				root.setLocation(w/2, h/2);
				
				System.out.println("Start moving nodes: "+nodes.size());
				moveNodes(root);
				System.out.println("End moving nodes");
				visited.clear();

				loading = 100;
				//Find minimun spanning tree using kruskal
				long beforeKruskal = System.currentTimeMillis();
				System.out.println("Before: "+beforeKruskal);

				IntegerGraph mg = k.minimunSpanningTree(graph);

				long afterKruskal = System.currentTimeMillis();
				System.out.println("After: "+afterKruskal);

				long time = afterKruskal-beforeKruskal;
				System.out.println("Time: "+time);
			}
		}
	}

	private GraphRepository loadGraph(String graphFile) {

		String path = PathHelper.currentDirectory().substring(5)+"../graphs/";
		
		GraphRepository gr = null;

		try {
			File file = new File(path+graphFile);
			JAXBContext jc = JAXBContext.newInstance(GraphRepository.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			gr = (GraphRepository) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gr;
	}

	private void addEdge(IntegerEdge edge) {
		k.addEdge(edge);
		graph.addEdge(edge);
	}

	@Override
	public void draw(Graphic g) {
		//Fill background
		g.setColor(Color.WHITE);
		g.fillRect(this);
		
		drawNode(g, root);
		visited.clear();
	}

	private void drawLeaf(Graphic g, Node<Integer> node) {
		Point2D point = node.getPoint();

		int radius = 12;

		g.setColor(SVGColor.BLACK);
		g.fillCircle(point, radius);
		g.setColor(SVGColor.WHITE);

		int x = (int)point.getX()-radius;
		int y = (int)point.getY()-radius;
		int w = radius*2;
		int h = radius*2;

		g.drawStringBorder(Integer.toString(node.getData()), x, y, w, h);
		g.setColor(SVGColor.BLACK);
	}

	private void drawNode(Graphic g, Node<Integer> node) {
		if(visited.contains(node)) {
			return;
		}
		
		visited.add(node);
		
		//Draw Children
		drawEdges(g, node);

		//Draw Node itself
		drawLeaf(g, node);
	}

	private void drawEdges(Graphic g, Node<Integer> node) {

		List<IntegerEdge> edges = graph.getEdges(node);

		for(IntegerEdge edge: edges) {

			if(k.selectedEdges.contains(edge)) {
				g.setColor(Color.GREEN);
			}

			Point2D origin = edge.getOrigin().getPoint();
			Point2D destination = edge.getDestination().getPoint();

			g.drawLine(origin, destination);

			int wx = (int)((origin.getX()+destination.getX())/2);
			int wy = (int)((origin.getY()+destination.getY())/2);

			g.setColor(SVGColor.BLACK);

			g.drawString(Integer.toString(edge.getWeight()), wx-4, wy+3);

			drawNode(g, edge.getDestination());
		}

	}

	public void moveNodes(Node<Integer> root) {
		moveChildrenNodes(root, 0);
	}

	private void moveChildrenNodes(Node<Integer> node, double initialAngle) {

		if(visited.contains(node)) {
			return;
		}
		
		visited.add(node);
		
		double maxAngle = 360;
		
		List<IntegerEdge> edges = graph.getEdges(node);

		int size = edges.size()+1;

		double theta = maxAngle / size;

		int i = 0;
		
		for(IntegerEdge edge: edges) {

			i++;

			Node<Integer> destination = edge.getDestination();

			double angle = (theta * i);

			if(initialAngle>maxAngle/2) {
				angle += initialAngle-maxAngle/2;
			} else {
				angle -= initialAngle;
			}

			double x = node.getPoint().getX() + nodeDistance * Math.cos(Math.toRadians(angle));
			double y = node.getPoint().getY() + nodeDistance * Math.sin(Math.toRadians(angle));

			destination.setLocation(x, y);

			moveChildrenNodes(destination, angle);
		}
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
