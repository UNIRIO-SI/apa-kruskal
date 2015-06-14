package br.com.etyllica.graph;

import java.awt.Color;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.graphics.SVGColor;
import br.com.etyllica.graph.kruskal.KruskalGraph;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.linear.graph.common.IntegerEdge;
import br.com.etyllica.linear.graph.common.IntegerGraph;

public class KruskalGraphExample extends Application{

	public KruskalGraphExample(int w, int h) {
		super(w, h);
	}

	private KruskalGraph k;

	private Node<Integer> root;

	private Node<Integer> firstChild;

	private IntegerGraph graph;

	private final double nodeDistance = 70;

	@Override
	public void load() {

		loading = 10;

		loadGraph();
		k = new KruskalGraph(graph);

		//Find minimun spanning tree using kruskal
		long beforeKruskal = System.currentTimeMillis();
		System.out.println("Before: "+beforeKruskal);

		IntegerGraph mg = k.minimunSpanningTree();

		long afterKruskal = System.currentTimeMillis();
		System.out.println("After: "+afterKruskal);

		long time = afterKruskal-beforeKruskal;
		System.out.println("Time: "+time);

		loading = 100;
	}

	private void loadGraph() {
		graph = new IntegerGraph();

		root = new Node<Integer>(0);
		root.setLocation(380, 190);

		firstChild = new Node<Integer>(1);		
		Node<Integer> secondChild = new Node<Integer>(2);
		Node<Integer> thirdChild = new Node<Integer>(3);
		Node<Integer> firstChildSon = new Node<Integer>(4);

		//Other component
		Node<Integer> nodeA = new Node<Integer>(19);
		Node<Integer> nodeB = new Node<Integer>(20);
		Node<Integer> nodeC = new Node<Integer>(21);
		Node<Integer> nodeD = new Node<Integer>(22);
		Node<Integer> nodeE = new Node<Integer>(23);

		//Add three child nodes
		graph.addNode(root);
		graph.addNode(firstChild);
		graph.addNode(secondChild);
		graph.addNode(thirdChild);

		IntegerEdge thirdEdge = new IntegerEdge(root, thirdChild, 2);

		graph.addEdge(new IntegerEdge(root, firstChild, 5));
		graph.addEdge(new IntegerEdge(root, secondChild, 14));
		graph.addEdge(thirdEdge);

		graph.addEdge(new IntegerEdge(firstChild, firstChildSon, 99));
		graph.addEdge(new IntegerEdge(firstChild, new Node<Integer>(84), 50));

		graph.addEdge(new IntegerEdge(firstChild, secondChild, 20));

		graph.addEdge(new IntegerEdge(firstChildSon, nodeA, 20));
		graph.addEdge(new IntegerEdge(nodeA, nodeB, 6));
		graph.addEdge(new IntegerEdge(nodeA, nodeC, 9));
		graph.addEdge(new IntegerEdge(nodeC, nodeD, 10));
		graph.addEdge(new IntegerEdge(nodeC, nodeE, 8));

		moveNodes(root);
	}

	@Override
	public void draw(Graphic g) {
		drawNode(g, root);
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

		//Draw Children
		drawEdges(g, node);

		//Draw Node itself
		drawLeaf(g, node);
	}

	private void drawEdges(Graphic g, Node<Integer> node) {

		List<IntegerEdge> edges = graph.getEdges(node);

		for(IntegerEdge edge: edges) {

			if(k.getSelectedEdges().contains(edge)) {
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

		List<IntegerEdge> edges = graph.getEdges(node);

		int size = edges.size()+1;

		double theta = 180 / size;

		int i = 0;

		for(IntegerEdge edge: edges) {

			i++;

			Node<Integer> destination = edge.getDestination();

			double angle = (theta * i);

			if(initialAngle>90) {
				angle += initialAngle-90;
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
