package br.com.etyllica.graph;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder={"numberOfNodes", "numberOfEdges", "type", "nodeName", "edgeInfo"})
public class GraphInfo {

	private Integer numberOfNodes;
	private Integer numberOfEdges;
	private String type;
	private List<Integer> nodeName;
	private List<EdgeInfo> edgeInfo;

	public Integer getNumberOfNodes() {
		return numberOfNodes;
	}
	public void setNumberOfNodes(Integer numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}
	public Integer getNumberOfEdges() {
		return numberOfEdges;
	}
	public void setNumberOfEdges(Integer numberOfEdges) {
		this.numberOfEdges = numberOfEdges;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Integer> getNodeName() {
		return nodeName;
	}
	public void setNodeName(List<Integer> nodeName) {
		this.nodeName = nodeName;
	}
	public List<EdgeInfo> getEdgeInfo() {
		return edgeInfo;
	}
	public void setEdgeInfo(List<EdgeInfo> edgeInfo) {
		this.edgeInfo = edgeInfo;
	}

	public void addNodeName(Integer nodeName) {
		if (this.nodeName == null) {
			this.nodeName = new ArrayList<Integer>();
		}
		this.nodeName.add(nodeName);
	}

	public void addEdgeInfo(Integer firstNode, Integer secondNode, Integer value) {
		if (this.edgeInfo == null) {
			this.edgeInfo = new ArrayList<EdgeInfo>();
		}
		EdgeInfo edgeInfo = new EdgeInfo();
		edgeInfo.setFirstNode(firstNode);
		edgeInfo.setSecondNode(secondNode);
		edgeInfo.setValue(value);
		this.edgeInfo.add(edgeInfo);
	}
}