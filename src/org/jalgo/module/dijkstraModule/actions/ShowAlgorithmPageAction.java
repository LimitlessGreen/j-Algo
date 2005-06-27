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
 * Created on 30.05.2005 10:00:39
 *
  */
package org.jalgo.module.dijkstraModule.actions;

import org.jalgo.module.dijkstraModule.gui.Controller;
import org.jalgo.module.dijkstraModule.model.Graph;
import org.jalgo.module.dijkstraModule.model.Node;
import org.jalgo.module.dijkstraModule.model.State;

/**
 * @author Frank
 *
 */
public class ShowAlgorithmPageAction extends SetEditingModeAction {
	
	/**
	 * @param ctrl
	 */
	Graph m_oldGraph = null;
	public ShowAlgorithmPageAction(Controller ctrl) throws Exception
	{
		super(ctrl, Controller.MODE_ALGORITHM,true);
		m_oldGraph = getController().getGraph();
	}
	
	/* (non-Javadoc)
	 * @see org.jalgo.module.dijkstraModule.actions.Action#Do()
	 */
	public boolean doAction() throws Exception
	{		
		getController().showAlgorithmPage();
		Graph gr = getController().getGraph();
		Node node = gr.getStartNode();
		if (node == null) {
			gr.findNode(1).setStart(true);
		}
		gr.getStartNode().getIndex();
		getController().setGraph(gr);
		super.doAction();
		State state = getController().getState(0);
		if(state != null)
		{
		    getController().setStatusbarText(state.getDescriptionEx());
		    getController().setModifiedFlag();
		}		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.jalgo.module.dijkstraModule.actions.Action#Undo()
	 */
	public boolean undoAction() throws Exception 
	{
		super.undoAction();
		getController().setGraph(m_oldGraph);
		getController().showEditingPage();
		return true;
	}
	
}
