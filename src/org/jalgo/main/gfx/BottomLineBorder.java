/* j-Algo - j-Algo is an algorithm visualization tool, especially useful for students and lecturers of computer sience. It is written in Java and platform independant. j-Algo is developed with the help of Dresden University of Technology.
 *
 * Copyright (C) 2004 j-Algo-Team, j-algo-development@lists.sourceforge.net
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
 * Created on Jun 15, 2004
 */
 
package org.jalgo.main.gfx;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;

/**
 * @author Malte Blumberg
 */
public class BottomLineBorder extends AbstractBorder {
	/*
		private int textWidth;
		
		public BottomLineBorder(int textWidth)
		{
			this.textWidth=textWidth;
		}
	*/
	public Insets getInsets(IFigure arg0) {
		return new Insets(0);
	}

	public void paint(IFigure figure, Graphics graphics, Insets insets) {

		graphics.setLineWidth(1);
		Insets inset = new Insets(0);
		Point startPoint =
			new Point(
				getPaintRectangle(figure, insets).getBottomLeft().x,
				getPaintRectangle(figure, insets).getBottom().y - 1);
		Point endPoint =
			new Point(
				getPaintRectangle(figure, insets).getBottomRight().x,
				getPaintRectangle(figure, insets).getBottom().y - 1);

		graphics.drawLine(startPoint, endPoint);

	}

}