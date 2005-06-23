/* Created on 31.05.2005 */
package org.jalgo.module.avl.gui.components;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Queue;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import org.jalgo.module.avl.Controller;
import org.jalgo.module.avl.gui.DisplayModeChangeable;
import org.jalgo.module.avl.gui.GUIConstants;
import org.jalgo.module.avl.gui.GUIController;
import org.jalgo.module.avl.gui.Settings;

/**
 * The class <code>LogPane</code> represents a scrollable logbook for logging
 * actions occured during algorithms. The last entry is each highlighted for better
 * recognition of the current action.
 *   
 * @author Alexander Claus
 */
public class LogPane
extends JComponent
implements DisplayModeChangeable, GUIConstants {

	private Controller controller;
	private JTextPane textPane;
	private DefaultStyledDocument doc;

	private String lineSeparator;
	private Queue<String> lastLogDescriptions;
	private int lastHighlightedParagraphOffset = 0;
	private int lastHighlightedParagraphLength = 0;

	/**
	 * Constructs a <code>LogPane</code> object with the given references.
	 * 
	 * @param gui the <code>GUIController</code> instance of the AVL module
	 * @param controller the <code>Controller</code> instance of the AVL module
	 */
	public LogPane(final GUIController gui, Controller controller) {
		this.controller = controller;
		lineSeparator = System.getProperty("line.separator");

		textPane = new JTextPane();
		textPane.setMargin(new Insets(2, 4, 2, 4));
		textPane.setEditable(false);
		doc = new DefaultStyledDocument();
		textPane.setDocument(doc);

		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(
			textPane,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);

		//the status line updater
		textPane.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				gui.setStatusMessage(null);
			}
			public void mouseEntered(MouseEvent e) {
				gui.setStatusMessage("Gibt erfolgte Aktionen logbuchartig wieder");
			}
		});
	}

	/**
	 * Takes the last occured actions as queue from the <code>Controller</code>.
	 * This actions are prepared as strings for easy displaying.
	 * Highlights the last entry, sets the pre-last entry back to normal style.
	 */
	public void update() {
		doc.setCharacterAttributes(
			lastHighlightedParagraphOffset, lastHighlightedParagraphLength,
			Settings.NORMAL_STYLE[Settings.getDisplayMode()], true);
		
		lastLogDescriptions = controller.getLogDescriptions();
		for (String logDesc : lastLogDescriptions) {
			if (logDesc == null || logDesc.equals("")) continue;
			lastHighlightedParagraphOffset = doc.getLength();
			try {
				doc.insertString(doc.getLength(), logDesc+lineSeparator,
					Settings.NORMAL_STYLE[Settings.getDisplayMode()]);
			}
			catch (BadLocationException ex) {
				System.err.println("Fehler beim Logbuch-Update");
			}
			lastHighlightedParagraphLength =
				logDesc.length()+lineSeparator.length();
		}
		doc.setCharacterAttributes(
			lastHighlightedParagraphOffset, lastHighlightedParagraphLength,
			Settings.HIGHLIGHTED_STYLE[Settings.getDisplayMode()], true);
		textPane.setCaretPosition(
			lastHighlightedParagraphOffset+lastHighlightedParagraphLength);
	}

	/**
	 * Clears the logbook.
	 */
	public void reset() {
		try {doc.remove(0, doc.getLength());}
		catch (BadLocationException ex) {
			System.err.println("Sollte nicht auftreten...");
		}
		lastHighlightedParagraphLength = 0;
		lastHighlightedParagraphOffset = 0;
	}

	/**
	 * This method is called, when the display mode has changed between pc mode
	 * and beamer mode. As a result of this the size of the font is changed.
	 */
	public void displayModeChanged() {
		if (lastHighlightedParagraphOffset+lastHighlightedParagraphLength == 0)
			return;
		doc.setCharacterAttributes(
			0, lastHighlightedParagraphOffset,
			Settings.NORMAL_STYLE[Settings.getDisplayMode()], true);
		doc.setCharacterAttributes(
			lastHighlightedParagraphOffset, lastHighlightedParagraphLength,
			Settings.HIGHLIGHTED_STYLE[Settings.getDisplayMode()], true);
		//scroll to end of text
		Rectangle visibleRect = textPane.getVisibleRect();
		//force recalculating of height
		textPane.getParent().doLayout();
		visibleRect.y = textPane.getHeight()-visibleRect.height;
		textPane.scrollRectToVisible(visibleRect);
	}
}