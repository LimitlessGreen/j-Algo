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
 * Created on May 31, 2004
 */
 
package org.jalgo.main.gui.widgets;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jalgo.main.util.Stack;

/**
 * @author Cornelius Hald
 * @author Christopher Friedrich
 */
public class StackCanvas extends Composite {

	private Label label;

	private Stack stack;

	private Text textField;

	private void redrawStack() {
		
		textField.setText(""); //$NON-NLS-1$
		String str = new String();
		
		LinkedList l = stack.getContent();
		Iterator si = stack.getContent().iterator();
		while (si.hasNext()) {
			str = (String) si.next() + "\n" + str; //$NON-NLS-1$
		}
		
		textField.setText(str);
	}

	public StackCanvas(Composite parent, int style) {
		
		super(parent, style);
		
		setLayout(new FillLayout());
		textField = new Text(this, SWT.MULTI);
		stack = new Stack();
	}

	public void push(String element) {
		stack.push(element);
		redrawStack();
	}

	public String pop() {
		String obj = (String) stack.pop();
		redrawStack();
		return obj;
	}

	public void reset() {
		stack.clear();
	}


	public Stack getStack() {
		return stack;
	}


	public void setStack(Stack stack) {
		this.stack = stack;
	}

}
