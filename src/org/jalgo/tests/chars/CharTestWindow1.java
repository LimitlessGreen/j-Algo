/*
 * Created on 10.12.2004
 */
package org.jalgo.tests.chars;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author Michael Pradel
 */
public class CharTestWindow1 extends ApplicationWindow {
	
	Composite parent;
	
	public CharTestWindow1 () {
		super(null);
	}
	
	protected Control createContents(Composite parent) {
		parent.getShell().setText("Character Test 1");
		parent.getShell().setSize(400,250);
		
		Label label = new Label(parent, SWT.VERTICAL);
		label.setText("Umlaute:\n" +
			"ä - ae\n" +
			"ö - oe\n" +
			"griechische Buchstaben:\n" +

		return label;
	}
	
	/*
	 * -Djava.library.path=/home/michi/eclipse_fuer_sopra/eclipse/plugins/org.eclipse.swt.gtk_2.1.3/os/linux/x86
	 */

}