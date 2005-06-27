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

package org.jalgo.main.gui.actions;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

/*
 * Created on Apr 23, 2004
 */

/**
 * This action adds a Rectangle to a Figure.
 * 
 * @author Cornelius Hald
 * @author Hauke Menges
 */
public class AddRectangleAction extends Action {
	
	IFigure figure;

	/**
	 * Constructs a new Action.
	 * @param figure The <code>IFigure</code> to which the Rectangle should be added.
	 */
	public AddRectangleAction(IFigure figure) {
		this.figure = figure;
		setText("Rectangle");
		setToolTipText("Add rectangle.");
		setImageDescriptor(
			ImageDescriptor.createFromFile(null, "pix/rectangle.gif"));
	}

	public void run() {
		RectangleFigure fig = new RectangleFigure();
		figure.addMouseListener(new MouseListener.Stub() {
			public void mousePressed(MouseEvent arg0) {
			}
		});
		figure.add(fig);
	}
}
