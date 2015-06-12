package br.com.etyllica.graph;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GraphRepository {

	private List<GraphInfo> graphInfo;

	@XmlElement(name="graphInfo")
	public List<GraphInfo> getGraphInfo() {
		return graphInfo;
	}

	public void setGraphInfo(List<GraphInfo> graphInfo) {
		this.graphInfo = graphInfo;
	}

	public void addGraphInfo(GraphInfo graphInfo) {
		if (this.graphInfo == null) {
			this.graphInfo = new ArrayList<GraphInfo>();
		}
		this.graphInfo.add(graphInfo);
	}
	
}
