package org.jalgo.module.avl.algorithm;
import org.jalgo.module.avl.*;
import org.jalgo.module.avl.datastructure.*;

import java.util.*;

/**
 * @author Ulrike Fischer
 * 
 * The class <code>UpdateBalance</code> gets a start node and calculates all
 * balances using <code>CalculateBalance</code> beginning from this node.
 */

public class UpdateBalance extends MacroCommand implements Constants {

	private WorkNode wn;
	private AVLNode currentNode=null;
	private Set<Node> updatednodes;
	private String oldsection;
	
	/**
	 * @param wn reference to the position in the tree, holds the <code>nextToMe</code>
	 * node where <code>UpdateBalance</code> begins
	 */
	
	public UpdateBalance(WorkNode wn) {
		super();
		this.wn = wn;
		updatednodes = new HashSet<Node>();
		commands.add(CommandFactory.createCalcBalance(wn));
	}
	
	/**
	 * Calculates all balances from the inserted node until it reaches the root or
	 * the balance of a node is 2 or -2.
	 */
	
	public void perform() {	
		results.clear();
		results.add(0,"");
		results.add(1,"absatz");
		
		updatednodes.add((AVLNode) wn.getNextToMe());
		
		Command c = (Command) commands.get(currentPosition);
		c.perform();
		currentPosition++;
	
		if (c instanceof NoOperation) {
			if (wn.getNextToMe().getParent()==null)
				results.add(2, ROOT);
			else 
				results.add(2, DONE);
			results.set(1,oldsection);
			return;
		}
		int balanceresult = (Integer) c.getResults().get(2);
		
//		if (currentNode==null)
//			results.set(0,"Balance auf 0 gesetzt");
//		else
//			results.set(0,"Balance aktualisiert: " + balanceresult);
		
		results.set(0, "Balance auf " + balanceresult + " gesetzt");
		
		currentNode = (AVLNode) wn.getNextToMe();
		
		if (balanceresult==2) {
			AVLNode r = (AVLNode) currentNode.getRightChild();
			if (r.getBalance()==-1) {
				results.add(2, DOUBLEROTATE);
				results.add(3, RIGHT);
				results.add(4, r);
				results.add(5, LEFT);
				results.add(6, currentNode);
			}
			else {
				results.add(2, ROTATE);
				results.add(3, LEFT);
			}
		}
		
		else if (balanceresult==-2) {
			AVLNode l = (AVLNode) currentNode.getLeftChild();
			if (l.getBalance() == 1) {
				results.add(2,DOUBLEROTATE);
				results.add(3,LEFT);
				results.add(4,l);
				results.add(5,RIGHT);
				results.add(6,currentNode);
			}
			else {
				results.add(2,ROTATE);
				results.add(3,RIGHT);
			}
		}
		else if (balanceresult==0 && currentPosition !=1) {
			oldsection = (String) c.getResults().get(1);
			results.add(2, WORKING);
			if (currentPosition==commands.size()) {
				commands.add(CommandFactory.createNoOperation());
			}
		}
		else if (wn.getNextToMe().getParent()==null) {
			oldsection = (String) c.getResults().get(1);
			results.add(2, WORKING);
			if (currentPosition==commands.size()) {
				commands.add(CommandFactory.createNoOperation());
			}
		}
		else {
			results.add(2, WORKING);
			wn.setNextToMe(wn.getNextToMe().getParent());
			if (currentPosition==commands.size())
				commands.add(CommandFactory.createCalcBalance(wn));
		}
		
		results.set(1, c.getResults().get(1));
	}
	
	/**
	 * Sets all balances back to there previous ones.
	 */
	
	public void undo() {
		if (currentPosition==0) {
			throw new NullPointerException();
		}
	
		else {
			results.clear();
			results.add(0, "Schritt r�ckg�ngig gemacht");
			
			//necessary for DoubleRotation, sets RotateVisualisationStatus back to normal
			if (!hasNext()) {
				for (Node n: updatednodes) {
					n.setVisualizationStatus(Visualizable.BALANCE | Visualizable.NORMAL);
				}
			}
			currentPosition--;
			Command c = (Command) commands.get(currentPosition);
			
			if (c instanceof NoOperation) {
				for (Node n: updatednodes) {
					n.setVisualizationStatus(Visualizable.NORMAL | Visualizable.BALANCE);
				}
				results.add(1, oldsection);
				results.add(2,WORKING);
			}
	
			else {
				c.undo();
				results.add(1, c.getResult(1));
				if (currentPosition==0) {
					results.add(2,LASTUNDO);
					currentNode=null;
				}
				else results.add(2,DONE);
			}
		}
	}
	
	public void performBlockStep() {
		while (this.hasNext()) {
			perform();
		}
	}
	
	public void undoBlockStep() {
		while (this.hasNext()) {
			undo();
		}
	}
	
	public Set<Node> getUpdatednodes() {
		return updatednodes;
	}
	
}