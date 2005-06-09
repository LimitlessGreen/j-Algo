/*
 * Created on 03.06.2005
 *
 */
package org.jalgo.tests.trees;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jalgo.main.trees.LeafFigure;
import org.jalgo.main.trees.NodeFigure;

/**
 * @author Michael Pradel
 *  
 */
public class GraphicalTree1 {

	public static void main(String[] args) {
		Display d = new Display();
		final Shell shell = new Shell(d);
		shell.setSize(400, 400);
		shell.setText("Tree test");
		LightweightSystem lws = new LightweightSystem(shell);
		Figure contents = new Figure();
		contents.setLayoutManager(new FlowLayout());

		LeafFigure l1 = new LeafFigure("NILLLLLPFERDDDDDDDDE");
		contents.add(l1);
		LeafFigure l2 = new LeafFigure();
		contents.add(l2);
		l2.setText("FLUSSPFERD");
		
		l2.setBackgroundColor(ColorConstants.cyan);
		
		NodeFigure n1 = new NodeFigure("13");
		contents.add(n1);
		NodeFigure n2 = new NodeFigure("13331313138");
		contents.add(n2);
		n2.setBackgroundColor(ColorConstants.green);
		n2.setTextColor(ColorConstants.white);
		NodeFigure n3 = new NodeFigure();
		contents.add(n3);
		NodeFigure n4 = new NodeFigure("a");
		contents.add(n4);
		
		//Arrows a = new Arrows();
		//contents.add(a);
		
		ImageFigure imf = new ImageFigure();
		imf.setImage(new Image(null, "pix/trees/arrows.png"));
		contents.add(imf);
		
		lws.setContents(contents);
		shell.open();
		while (!shell.isDisposed())
			while (!d.readAndDispatch())
				d.sleep();
	}
}
