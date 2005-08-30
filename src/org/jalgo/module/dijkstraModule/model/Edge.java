/* j-Algo - j-Algo is an algorithm visualization tool, especially useful for students and lecturers of computer sience. It is written in Java and platform independant. j-Algo is developed with the help of Dresden University of Technology.
 *
 * Copyright (C) 2004-2005 j-Algo-Team, j-algo-development@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*
 * Created on 07.05.2005
 *
 */
package org.jalgo.module.dijkstraModule.model;

import java.io.Serializable;

/**
 * @author Hannes Strass
 * 
 * Represents an weighted edge between two nodes.
 *
 */
public class Edge extends GraphElement implements Serializable, Comparable {

	private static final long serialVersionUID = 18238242054685159L;

	private Node startNode;

	private Node endNode;

	private int weight;

	/**
	 * <code>reversed</code> specifies whether an edge needs to be
	 * drawn not from <code>startNode</code> to
	 * <code>endNode</node>, but the other way round.
	 *
	 */
	private boolean reversed = false;

	/**
	 * Get the Reversed value.
	 * @return the Reversed value.
	 */
	public boolean isReversed() {
		return reversed;
	}

	/**
	 * Set the Reversed value.
	 * @param newReversed The new Reversed value.
	 */
	public void setReversed(boolean newReversed) {
		this.reversed = newReversed;
	}

	/** creates a new Edge with default weight, sets changed-flag true
	 * 
	 * @param startNode the start Node
	 * @param endNode the end Node
	 */
	public Edge(Node startNode, Node endNode) {
		/**
		 * if index of startNode is greater than index of endNode, swap Nodes
		 */

		if (startNode.getIndex() > endNode.getIndex()) {
			this.startNode = endNode;
			this.endNode = startNode;

			//this.startNodeIndex = endNode.getIndex();
			//this.endNodeIndex = startNode.getIndex();
		} else {
			this.startNode = startNode;
			this.endNode = endNode;

			//this.startNodeIndex = startNode.getIndex();
			//this.endNodeIndex = endNode.getIndex();			
		}
		this.weight = 5;

		this.setChanged(true);
	}

	/** creates a new Edge, sets changed-flag true
	 * 
	 * @param startNode the start Node
	 * @param endNode the end Node
	 * @param weight the weight
	 */
	public Edge(Node startNode, Node endNode, int weight) {
		/**
		 * if index of startNode is greater than index of endNode, swap Nodes
		 */

		if (startNode.getIndex() > endNode.getIndex()) {
			this.startNode = endNode;
			this.endNode = startNode;

			//this.startNodeIndex = endNode.getIndex();
			//this.endNodeIndex = startNode.getIndex();
		} else {
			this.startNode = startNode;
			this.endNode = endNode;

			//this.startNodeIndex = startNode.getIndex();
			//this.endNodeIndex = endNode.getIndex();			
		}
		setWeight(weight);

		this.setChanged(true);
	}

	/**
	 * @param anotherEdge
	 * @return true, if both start- and endNode are equal and weight matches as well
	 */
	public boolean equals(Edge anotherEdge) {
		return (this.startNode.getIndex() == anotherEdge.getStartNode().getIndex()
		//&&
		//this.weight == anotherEdge.getWeight()
		&& this.endNode.getIndex() == anotherEdge.getEndNode().getIndex());
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object anotherObject) {
		try {
			Edge anotherEdge = (Edge) anotherObject;

			if (this.startNode.getIndex() < anotherEdge.getStartNode().getIndex())
				return -1;
			if (this.startNode.getIndex() > anotherEdge.getStartNode().getIndex())
				return 1;
			if (this.endNode.getIndex() < anotherEdge.getEndNode().getIndex())
				return -1;
			if (this.endNode.getIndex() > anotherEdge.getEndNode().getIndex())
				return 1;
			return 0;
		} catch (ClassCastException e) {
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Edge(" + startNode + ", " + weight + ", " + endNode + ")";
	}

	/**
	 * @return the start Node
	 */
	public Node getStartNode() {
		return startNode;
	}

	/**
	 * @return the end Node
	 */
	public Node getEndNode() {
		return endNode;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @return index of start Node
	 */
	public int getStartNodeIndex() {
		return startNode.getIndex();
	}

	/**
	 * @return index of end Node
	 */
	public int getEndNodeIndex() {
		if (endNode == null)
			throw new RuntimeException("This edge is without end node.");
		return endNode.getIndex();
	}

	/**
	 * @return Edge as string according to Prof. Vogler's script: (startNodeIndex, weight, endNodeIndex)
	 */
	public String getText() {
		if (!reversed)
			return "(" + startNode.getIndex() + ", " + weight + ", " + endNode.getIndex() + ")";
		return "(" + endNode.getIndex() + ", " + weight + ", " + startNode.getIndex() + ")";
	}

	/** Creates a String suitable as description for the Algorithm stuff. Edges are represented
	 *  as (to, distance/weight, form).
	 * 
	 * @param distancep if true display the distance rather than weight.
	 * @return a string describing the edge.
	 */
	public String getAlgoText(boolean distancep, BorderState bdstate) {
		boolean rev = false;

		Node fStart = bdstate.getGraph().findNode(startNode.getIndex());
		Node fEnd = bdstate.getGraph().findNode(endNode.getIndex());

		if (fEnd.getPredecessor() == null) {
			rev = true;
		} else {
			rev = !(fStart.getIndex() == fEnd.getPredecessor().getIndex());
		}

		Node start = rev ? fEnd : fStart;
		Node end = rev ? fStart : fEnd;
		int dis = distancep ? end.getDistance() : weight;

		return "(" + end.getIndex() + "," + dis + "," + start.getIndex() + ")";
	}

	/**
	 * @param newStartNode the start Node to set
	 */
	public void setStartNode(Node newStartNode) {
		this.startNode = newStartNode;
	}

	/**
	 * @param newEndNode the end Node to set
	 */
	public void setEndNode(Node newEndNode) {
		this.endNode = newEndNode;
	}

	/**
	 * @param newWeight the weight to set
	 */
	public void setWeight(int newWeight) {
		this.weight = newWeight;
		if (newWeight < 0)
			this.weight = 0;
		if (newWeight > 99)
			this.weight = 99;
	}
}
