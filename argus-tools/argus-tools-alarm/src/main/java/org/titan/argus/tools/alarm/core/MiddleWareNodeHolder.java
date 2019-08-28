package org.titan.argus.tools.alarm.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
public class MiddleWareNodeHolder {
	private List<Node> nodeList = new ArrayList<>();
	public void addNode(Node node) {
		this.nodeList.add(node);
	}
}
