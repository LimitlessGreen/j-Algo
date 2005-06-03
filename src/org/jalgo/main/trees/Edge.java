/*
 * Created on 27.05.2005
 *
 */
package org.jalgo.main.trees;

/**
 * An edge is the connection between two nodes of a tree.
 * 
 * @author Michael Pradel
 *  
 */

public class Edge extends TreeComponent {

	private String text;

	private org.eclipse.draw2d.graph.Edge edge;

	private EdgeFigure figure;

	public Edge(TreeComponent parent, TreeComponent child) {
		edge = new org.eclipse.draw2d.graph.Edge(parent.getNode(), child.getNode());
		addOutgoing(child);
		setParent(parent);
	}

	public void addOutgoing(TreeComponent newOut) {
		if (newOut instanceof Edge) {
			throw new RuntimeException("Trying to add an edge to an edge.");
		}
		super.addOutgoing(newOut);
	}

	public void setParent(TreeComponent newParent) {
		if ((newParent instanceof Edge) || (newParent instanceof Leaf)) {
			throw new RuntimeException("Leaves and egdes can't be parent of an edge.");
		}
		super.setParent(newParent);
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public EdgeFigure getFigure() {
		return figure;
	}
	
	public org.eclipse.draw2d.graph.Edge getEdge() {
		return edge;
	}
	
}