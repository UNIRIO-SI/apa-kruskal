package br.com.etyllica.graph;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder={"firstNode", "secondNode", "value"})
public class EdgeInfo {

	private Integer firstNode;
	private Integer secondNode;
	private Integer value;
	
	public Integer getFirstNode() {
		return firstNode;
	}
	public void setFirstNode(Integer firstNode) {
		this.firstNode = firstNode;
	}
	public Integer getSecondNode() {
		return secondNode;
	}
	public void setSecondNode(Integer secondNode) {
		this.secondNode = secondNode;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
}
