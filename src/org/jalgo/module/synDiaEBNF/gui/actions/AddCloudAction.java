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
 * Created on 18.06.2004
 *
 */
package org.jalgo.module.synDiaEBNF.gui.actions;

import java.util.ArrayList;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.jalgo.main.gfx.IClickAction;
import org.jalgo.main.util.ClickCollector;
import org.jalgo.main.util.Messages;
import org.jalgo.module.synDiaEBNF.gfx.AlternativeFigure;
import org.jalgo.module.synDiaEBNF.gfx.CloudFigure;
import org.jalgo.module.synDiaEBNF.gfx.CompositeSynDiaFigure;
import org.jalgo.module.synDiaEBNF.gfx.SynDiaException;
import org.jalgo.module.synDiaEBNF.gfx.SynDiaFigure;

/**
 * @author Hauke Menges
 *
 */
public class AddCloudAction extends Action implements IClickAction {
	IFigure figure;

	public AddCloudAction(IFigure figure) {
		this.figure = figure;
		setText(Messages.getString("synDiaEBNF",
			"AddCloudAction.Cloud_1")); //$NON-NLS-1$
		setToolTipText(Messages.getString("synDiaEBNF",
			"AddCloudAction.Add_cloud._2")); //$NON-NLS-1$
		setImageDescriptor(ImageDescriptor.createFromURL(
			getClass().getResource("/ebnf_pix/rectangle.gif")));
	}

	public void run() {
		ClickCollector.init(1, this);

	}

	public void performAction(ArrayList items) {
		try {
			IFigure help = ((Figure) items.get(0)).getParent();
			
			if (help instanceof CloudFigure)
				((CompositeSynDiaFigure) help.getParent()).replace((SynDiaFigure) help, new AlternativeFigure(2));
		} catch (SynDiaException e) {
			//TODO: handle e
		}

	}
}