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
 * Created on 30.04.2004
 */
 
package org.jalgo.module.synDiaEBNF;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.jalgo.main.gfx.MarkStyle;
import org.jalgo.main.gui.TextCanvas;
import org.jalgo.main.gui.widgets.StackCanvas;
import org.jalgo.main.util.GfxUtil;
import org.jalgo.main.util.Stack;
import org.jalgo.module.synDiaEBNF.gfx.InitialFigure;
import org.jalgo.module.synDiaEBNF.gfx.SynDiaColors;
import org.jalgo.module.synDiaEBNF.gfx.SynDiaFigure;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaAlternative;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaConcatenation;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaElement;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaEpsilon;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaInitial;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaRepetition;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaSystem;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaTerminal;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaVariable;
import org.jalgo.module.synDiaEBNF.synDia.SynDiaVariableBack;

/**
 * Offers the methods to control if a word is recognized by a language
 * shown by a syntax diagram system. Within the possiblity to go step by step 
 * through the system, even backwards.
 * The algorithm can be aborted all the time. 
 * 
 * @author Babett Schaliz
 * @author Benjamin Scholz
 * @author Marco Zimmmerling
 * @author Michael Pradel
 * @version %I%, %G%
 */
public class RecognizeWord
	extends SynDiaBacktracking
	implements SynDiaColors, Serializable {
	private ModuleController moduleController;
	private StackCanvas stackCanvas; // the Canvas of the graphical stack
	private TextCanvas algoTxtCanvas; // this Canvas display the algorithm
	private TextCanvas outputCanvas; // this Canvas display the generated word
	private Figure synDiaCanvas;

	private Stack stack; // the internal stack
	private String generatedWord = ""; // the generated word //$NON-NLS-1$
	private SynDiaSystem synDiaDef; // the SynDiaSystem to work with

	private BackTrackHistory history; // save the steps

	private SynDiaElement currentElement = null;
	// current SynDiaElement to go through
	private SynDiaInitial currentInitial;
	// help diagram which worked at the moment

	private String word = ""; //$NON-NLS-1$
	private int wordPointer;
	// always points to the current character in the word (String)

	/**
	* Constructor gets the Figure, Stack, the StackCanavas, the TextCanvases and
	* also the syntaxtical diagram SynDiaSystem, to work with. It also fill the 
	* TextCanvas and show the backtracking labels, set the startelement and the 
	* inputCanvas opens a dialog to get word-to-recognize from user
	* using wordInputDialog() 
	* @param figure	The <code> Figure </code> include the diagram system
	* @param stackCanvas	The <code> StackCanvas </code> of the graphical stack
	* @param algoTxtCanvas	The <code> TextCanvas </code> where the algorithm is displayed
	* @param generatedWordCanvas	The <code> TextCanvas </code> to display the generated word
	* @param synDiaDef	The <code> SynDiaSystem </code> to work with
	*/
	public RecognizeWord(
		ModuleController moduleController,
		Figure figure,
		StackCanvas stackCanvas,
		TextCanvas algoTxtCanvas,
		TextCanvas generatedWordCanvas,
		SynDiaSystem synDiaDef) {

		synDiaCanvas = figure;
		this.moduleController = moduleController;
		this.stackCanvas = stackCanvas;
		this.algoTxtCanvas = algoTxtCanvas;
		outputCanvas = generatedWordCanvas;
		this.synDiaDef = synDiaDef;

		stack = new Stack();

		//trick to set right backgrounds
		SynDiaVariableBack help =
			new SynDiaVariableBack(null, synDiaDef.getStartElem());
		stack.push(help);

		// go to first SynDiaElement to work with
		// lay backtracking labels on stack 
		stack.push(synDiaDef.getStartElem());

		// algorithm written on page 22 in the script
		algoTxtCanvas.setTextSegments(
			new String[] {
				Messages.getString("RecognizeWord.Algo_title_3"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Algo_Description_1_4") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_2_5"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Algo_Description_3_6"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Algo_Description_4_7") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_5_8") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_6_9"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Algo_Description_7_10") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_8_11") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_9_12") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_10_13") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_11_14") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_12_15"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Algo_Description_13_16") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_14_17") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_15_18") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_16_19") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_17_20"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Algo_Description_18_21") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_19_22") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_20_23"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Algo_Description_21_24") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Algo_Description_22_25"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Algo_Description_23_26") }); //$NON-NLS-1$
		algoTxtCanvas.markFirst();
		algoTxtCanvas.setMarkStyle(new MarkStyle(normal, diagramNormal, 2));
		algoTxtCanvas.demarkAll();
		algoTxtCanvas.setMarkStyle(
			new MarkStyle(textHighlight, diagramNormal, 3));

		outputCanvas.setTextSegments(
			new String[] { Messages.getString("RecognizeWord.Algo_Description_24_27") }); //$NON-NLS-1$

		//show backtracking labels
		for (int k = 0; k < this.synDiaDef.getInitialDiagrams().size(); k++) {
			BacktrackingLabels(synDiaDef.getInitialDiagram(k), true);
		}
		history = new BackTrackHistory();
		wordInputDialog();
	}

	/**
	* Test if there is a valid previous element in the history, to go there.
	* @return 	true, if there is a previous element in the history, so you can go a step back;
	* 			false if not
	*/
	public boolean hasPreviousHistStep() {
		return (history.getPointer() > 0);
	}

	/**
	* Test if there is a valid next element in the history, to go there
	* @return 	true, if there is a next element, so you can go a step forward in history; 
	* 			false if not
	*/
	public boolean hasNextHistStep() {
		return (history.getPointer() < history.getSize());
	}

	/**
	* Test if there is a valid next step, to go there. Else the algorithm is ready.
	* @return 	true if there is a next element, so you can go a NextStep();
	* 			false if not, therefore the algorithm is ready
	*/
	public boolean hasNextStep() {
		return !(stack.isEmpty());
	}

	/**
	* This method is called if the forwardInHistoryButton on the GUI is pushed, 
	* it restore the next step with the history.
	* 
	* @exception IndexOutOfBounds   if there is no further step to go
	*/
	public void nextHistStep() throws IndexOutOfBoundsException {
		//throw exception
		if (!hasNextHistStep()) {
			throw new IndexOutOfBoundsException("there is no further history step to go"); //$NON-NLS-1$
		} else {

			restoreStep(history.getNextHistoryStep());
			refreshGeneratedWord(generatedWord);

			//MAKE ready and controll if it works
			if (currentElement instanceof SynDiaTerminal) {
				redoNextTerm((SynDiaTerminal) currentElement);
			} else if (
				currentElement instanceof SynDiaVariable) { //SynDiaVariable
				redoNextVariable((SynDiaVariable) currentElement);
			} else if (currentElement instanceof SynDiaVariableBack) {
				//SynDiaVariable
				redoNextVariableBack((SynDiaVariableBack) currentElement);
			} else {
				nextHistStep();
			}
		}
	}

	/**
	* this method is called if the backwardButton on the GUI is pushed
	* and should restore the last saved step of the visualisation
	* 
	* @exception IndexOutOfBounds   if there is no previous step to go
	*/
	public void previousHistStep() throws IndexOutOfBoundsException {
		//throw exeption
		if (!hasPreviousHistStep()) {
			throw new IndexOutOfBoundsException("there is no further history step to go back"); //$NON-NLS-1$
		} else {
			restoreStep(history.getLastHistoryStep());
			refreshGeneratedWord(generatedWord);

			stack.push(currentElement);

			//mark the right algorithm Text-field
			algoTxtCanvas.demarkAll();
			algoTxtCanvas.mark(3); //legal way

			if (currentElement instanceof SynDiaTerminal) {
				// mark the SynDiaTerminal
				remark((SynDiaTerminal) currentElement, false);

				// refresh the generatedWord
				refreshGeneratedWord(generatedWord);
			} else if (currentElement instanceof SynDiaVariable) {
				//SynDiaVariable Jump in in the next step

				// mark the currentElem
				remark((SynDiaVariable) currentElement, false);

				// remove correspondent backtracking label on StackCanvas
				stackCanvas.pop();

				// restore Backtracking diagram to set Background
				colorTheDiagram(
					((SynDiaVariable) currentElement)
						.getHelpCopy()
						.getParentInitial()
						.getGfx());

				// MAKE first Connection??? 
			} else if (currentElement instanceof SynDiaVariableBack) {
				//SynDiaVariableBackjump out!

				// display correspondent backtracking label on StackCanvas
				if (((SynDiaVariableBack) currentElement).getOriginal()
					!= null) {
					stackCanvas.push(
						"" //$NON-NLS-1$
							+ (((SynDiaVariableBack) currentElement)
								.getOriginal()
								.getBacktrackingLabel()));
				}
			} else {
				previousHistStep();
			}
		}
	}

	/**
	 * this method is called if the "do algorithm" button on the GUI is pushed
	 * realize the backtracking algorithm
	 * has to find the next element in the syntactical diagram and...
	 */
	public void performNextStep() {
		if (hasPreviousHistStep()) {
			remark(history.getStepElem(history.getPointer() - 1), true);
		}

		// check, if it is actually possible to perform a further step
		if ((stack.peak() != null)) {

			//fetch the new SynDiaElement to work with
			currentElement = (SynDiaElement) stack.pop();

			//initialize BackTrackStep to save and restore later!
			history.addNewPosStep(stack, currentElement, generatedWord);

			//detect type of currentElem and go on accordingly
			if (currentElement instanceof SynDiaInitial) {
				doNextInitial((SynDiaInitial) currentElement);
			} else if (currentElement instanceof SynDiaEpsilon) {
				//go on Stack, this is an Epsilon f.e. in a Alternative,
				performNextStep();
			} else if (currentElement instanceof SynDiaTerminal) { //terminal?
				doNextTerm((SynDiaTerminal) currentElement);
			} else if (currentElement instanceof SynDiaVariable) { //variable?
				doNextVariable((SynDiaVariable) currentElement);
			} else if (
				currentElement instanceof SynDiaVariableBack) { //variable?
				doNextVariableBack((SynDiaVariableBack) currentElement);
			} else { // Composite
				algoTxtCanvas.demarkAll();
				algoTxtCanvas.mark(3); //Search the right Way
				if (currentElement instanceof SynDiaRepetition) { //repetition?
					doNextRepetition((SynDiaRepetition) currentElement);
				} else if (currentElement instanceof SynDiaAlternative) {
					doNextAlternative((SynDiaAlternative) currentElement);
				} else if (currentElement instanceof SynDiaConcatenation) {
					doNextConcatenation((SynDiaConcatenation) currentElement);
				}
			}
		} else { // if null on stack
			stack.pop();
			performNextStep();
		}
		if (!hasNextStep()) {
			//mark the right algorithm text field
			algoTxtCanvas.demarkAll();
			algoTxtCanvas.mark(3);
			// dialog that the Algorithmen is Empty
			readyDialog();
		}
	}

	/**
	* this method is called if the algorithm is closed, aborted or finished
	* and should hide the no longer needful Backtrackinglabels
	*/
	public void hideBacktrackingLabels() {
		//hide backtracking labels
		for (int k = 0; k < this.synDiaDef.getInitialDiagrams().size(); k++) {
			BacktrackingLabels(synDiaDef.getInitialDiagram(k), false);
		}
		//	reset all diagrams white
		List list = this.synDiaDef.getGfx().getSynDias();
		for (int k = 0; k < list.size(); k++) {
			((SynDiaFigure) list.get(k)).setBackgroundColor(diagramNormal);
		}
	}

	private void BacktrackingLabels(SynDiaElement help, boolean bool) {
		if (!bool) {
			remark(help, false);
		}
		if (help instanceof SynDiaInitial) {

			currentInitial = (SynDiaInitial) help;
			BacktrackingLabels(((SynDiaInitial) help).getInnerElem(), bool);
		}
		if (help instanceof SynDiaVariable) {
			SynDiaVariableBack elem =
				new SynDiaVariableBack((SynDiaVariable) help, currentInitial);
			((SynDiaVariable) help).setHelpCopy(elem);
			((SynDiaVariable) help).getGfx().setIndexText(
				"" + ((SynDiaVariable) help).getBacktrackingLabel()); //$NON-NLS-1$
			((SynDiaVariable) help).getGfx().setIndexVisible(bool);
		}
		if (help instanceof SynDiaRepetition) {
			BacktrackingLabels(
				((SynDiaRepetition) help).getStraightAheadElem(),
				bool);
			BacktrackingLabels(
				((SynDiaRepetition) help).getRepeatedElem(),
				bool);
		}
		if (help instanceof SynDiaAlternative) {
			for (int i = 0;
				i < (((SynDiaAlternative) help).getNumOfOptions());
				i++) {
				BacktrackingLabels(
					((SynDiaAlternative) help).getOption(i),
					bool);
			}
		}
		if (help instanceof SynDiaConcatenation) {
			for (int j = 0;
				j < ((SynDiaConcatenation) help).getNumOfElements();
				j++) {
				BacktrackingLabels(
					((SynDiaConcatenation) help).getContent(j),
					bool);
			}
		}
	}

	private void colorTheDiagram(InitialFigure current) {
		// reset all diagrams white
		List list = this.synDiaDef.getGfx().getSynDias();

		for (int k = 0; k < list.size(); k++) {
			((SynDiaFigure) list.get(k)).setBackgroundColor(diagramNormal);
		}

		//set the one
		current.setBackgroundColor(diagramHighlight);
	}

	private void remark(SynDiaElement markobj, boolean bool) {
		if (markobj instanceof SynDiaTerminal) {
			((SynDiaTerminal) markobj).remarkObject(bool);
		} else if (markobj instanceof SynDiaVariable) { //SynDiaVariable
			 ((SynDiaVariable) markobj).remarkObject(bool);
		} else if (markobj instanceof SynDiaVariableBack) {
			//SynDiaVariable
			 ((SynDiaVariableBack) markobj).remarkObject(bool);
		}
	}

	//----------------------PerformNextStep()-----------------------------------

	private void doNextInitial(SynDiaInitial currentElem) {
		colorTheDiagram(currentElem.getGfx());

		//actualize StackConfiguration
		stack.push(currentElem.getInnerElem());
	}

	private void doNextTerm(SynDiaTerminal currentElem) {
		// mark the right algorithmen Text-field
		algoTxtCanvas.demarkAll();
		algoTxtCanvas.mark(4); //SynDiaTerminal

		// mark the SynDiaTerminal
		 ((SynDiaTerminal) currentElem).markObject();

		// refresh the generatedWord
		generatedWord =
			generatedWord + (((SynDiaTerminal) currentElem).getLabel());
		if (!(word.startsWith(generatedWord))) {
			if (missMatchDialog()) {
				previousHistStep();
			} else {
				finalTasks();
				moduleController.algoFinished();
			}
		} else {
			refreshGeneratedWord(generatedWord);
		}
	}

	private void doNextVariable(SynDiaVariable currentElem) { //jump in
		// mark the right algorithmen Text-field
		algoTxtCanvas.demarkAll();
		algoTxtCanvas.mark(5); //SynDiaVariable

		// mark the currentElem
		currentElem.markObject();

		// set internal Stack config 
		stack.push(currentElem.getHelpCopy()); // save to go back
		stack.push(((SynDiaVariable) currentElem).getStartElem());

		// display correspondent backtracking label on StackCanvas
		stackCanvas.push(
			"" + ((SynDiaVariable) currentElem).getBacktrackingLabel()); //$NON-NLS-1$

		// change diagram colors in Initial

	}

	private void doNextVariableBack(SynDiaVariableBack currentElem) { //jump out
		// mark the right algorithmen Text-field
		algoTxtCanvas.demarkAll();
		algoTxtCanvas.mark(6); //Backtracking

		// color last connection in the left diagram

		// restore Backtracking diagram to set Background
		colorTheDiagram(currentElem.getParentInitial().getGfx());

		// set new internal StackConfig 
		// Variable removed in pop...

		// remove correspondent backtracking label on StackCanvas
		stackCanvas.pop();
	}

	private void doNextRepetition(SynDiaRepetition currentElem) {
		if (!(currentElem.isStraightAheadElemDone())) {
			// set new Stack Configuration
			currentElem.setStraightAheadElemDone(true);
			stack.push(currentElem);
			stack.push(currentElem.getStraightAheadElem());
		} else { //StraightAheadElem already done
			currentElem.setStraightAheadElemDone(false);
			if (repetionDialog((SynDiaRepetition) currentElem)) {
				// go into the Repetition 
				stack.push(currentElem);
				stack.push(currentElem.getRepeatedElem());
			}
		}
		performNextStep();
	}

	private void doNextAlternative(SynDiaAlternative currentElem) {
		stack.push(
			currentElem.getOption(
				alternativeDialog((SynDiaAlternative) currentElem)));
		performNextStep();
	}

	private void doNextConcatenation(SynDiaConcatenation currentElem) {
		LinkedList list = currentElem.getContent();
		for (int i = list.size() - 1; i >= 0; i--) {
			stack.push(list.get(i));
		}
		performNextStep();
	}

	//----------------------------redoNextTerm()--------------------------------

	private void redoNextTerm(SynDiaTerminal currentElem) {
		// mark the right algorithmen Text-field
		algoTxtCanvas.demarkAll();
		algoTxtCanvas.mark(4); //SynDiaTerminal

		// mark the SynDiaTerminal
		 ((SynDiaTerminal) currentElem).markObject();

		// refresh the generatedWord
		generatedWord =
			generatedWord + (((SynDiaTerminal) currentElem).getLabel());
		if (!(word.startsWith(generatedWord))) {
			if (missMatchDialog()) {
				previousHistStep();
			} else {
				finalTasks();
				moduleController.algoFinished();
			}
		} else {
			refreshGeneratedWord(generatedWord);
		}
	}

	private void redoNextVariable(SynDiaVariable currentElem) {
		//jump in
		// mark the right algorithmen Text-field
		algoTxtCanvas.demarkAll();
		algoTxtCanvas.mark(5); //SynDiaVariable

		// mark the currentElem
		currentElem.markObject();

		// set internal Stack config 
		stack.push(currentElem); // save to go back
		stack.push(((SynDiaVariable) currentElem).getStartElem());

		// display correspondent backtracking label on StackCanvas
		stackCanvas.push(((SynDiaVariable) currentElem).getLabel());

		// change diagram colors in Initial
	}

	private void redoNextVariableBack(SynDiaVariableBack currentElem) {
		//jump out
		// mark the right algorithmen Text-field
		algoTxtCanvas.demarkAll();
		algoTxtCanvas.mark(6); //backtracking

		// restore Backtracking diagram to set Background
		colorTheDiagram(currentElem.getParentInitial().getGfx());

		// remove correspondent backtracking label on StackCanvas
		stackCanvas.pop();
	}

	private void refreshGeneratedWord(String output) {
		outputCanvas.addSegment(
			Messages.getString("RecognizeWord.the_word_to_recognize_is___n_33") //$NON-NLS-1$
				+ word
				+ Messages.getString("RecognizeWord._nthe_word_generated_until_here_is___n_34") //$NON-NLS-1$
				+ output);

	}
	/**
	* opens a dialog to get a word, that should be tested from the user and 
	* stores the word in the variable "word"
	*/
	private void wordInputDialog() {
		InputDialog inDialog =
			new InputDialog(
				GfxUtil.getAppShell(),
				Messages.getString("RecognizeWord.WordInput_35"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.Word_to_recognize__36"), //$NON-NLS-1$
				"", //$NON-NLS-1$
				null);
		String result = ""; //$NON-NLS-1$
		if (inDialog.open() != Window.CANCEL) {
			result = inDialog.getValue();
			word = result;
		} else {
			MessageDialog.openError(
				null,
				Messages.getString("RecognizeWord.Warning_39"), //$NON-NLS-1$
				Messages.getString("RecognizeWord.You_must_enter_a_proper_string_40") //$NON-NLS-1$
					+ Messages.getString("RecognizeWord.Now_the_Algo_will_be_finished_!_41")); //$NON-NLS-1$
			finalTasks();
			moduleController.algoFinished();
		}
	}

	/**
	* Say the user that the word couldn't be recognized on this way 
	* @return boolean false if the want abort
	*/
	private boolean missMatchDialog() {
		return MessageDialog.openQuestion(
			stackCanvas.getShell(),
			Messages.getString("RecognizeWord.recognize_missmatch_1_42"), //$NON-NLS-1$
			Messages.getString("RecognizeWord.recognize_missmatch_2_43") //$NON-NLS-1$
				+ word
				+ Messages.getString("RecognizeWord.recognize_missmatch_3_44") //$NON-NLS-1$
				+ Messages.getString("RecognizeWord.recognize_missmatch_4_45")); //$NON-NLS-1$
	}

	/**
	* opens a dialog to show the algorithm is ready
	*/
	private void readyDialog() {
		if (generatedWord.equals(word)) {
			boolean result =
				MessageDialog.openQuestion(
					stackCanvas.getShell(),
					Messages.getString("RecognizeWord.Algo_finished_unsuccessfull_1_46"), //$NON-NLS-1$
					Messages.getString("RecognizeWord.Algo_finished_unsuccessfull_2_47") //$NON-NLS-1$
						+ generatedWord
						+ Messages.getString("RecognizeWord.Algo_finished_unsuccessfull_3_48")); //$NON-NLS-1$
			if (result) {
				finalTasks();
				moduleController.algoFinished();
			}
		} else {
			missMatchDialog();
		}
	}

	private int alternativeDialog(SynDiaAlternative alternative) {
		LinkedList list;
		int way;
		list = alternative.getOptions();
		way = list.size(); // int of posibile ways
		// ask the user, which way to go on
		//return the list index of the choosen way
		int result = 0;

		while (result == 0) {
			InputDialog inDialog =
				new InputDialog(
					GfxUtil.getAppShell(),
					Messages.getString("RecognizeWord.Alternative_Dialog_1_49"), //$NON-NLS-1$
					Messages.getString("RecognizeWord.Alternative_Dialog_2_50") //$NON-NLS-1$
						+ way
						+ Messages.getString("RecognizeWord.Alternative_Dialog_3_51"), //$NON-NLS-1$
					"", //$NON-NLS-1$
					null);
			if (inDialog.open() != Window.CANCEL) {
				try {
					result = (Integer.valueOf(inDialog.getValue())).intValue();
				} catch (NumberFormatException e) {
					//				MessageDialog.openError(
					//					null,
					//					"Warning",
					//					"Please use a integer value. Using default value now: 1.");
					result = 0;
				}
			}
			if ((result > 0) && (result <= way)) {
				return result - 1;
			} else {
				MessageDialog.openError(
					null,
					Messages.getString("RecognizeWord.Warning_53"), //$NON-NLS-1$
					Messages.getString("RecognizeWord.Please_use_a_value_between_1_and__54") + way + "."); //$NON-NLS-1$ //$NON-NLS-2$
				result = 0;
			}
		}

		return 0;
	}

	private boolean repetionDialog(SynDiaRepetition repetition) {
		// ask the user, if the repetition should makes!
		// return boolean if or not
		return MessageDialog.openQuestion(
			GfxUtil.getAppShell(),
			Messages.getString("RecognizeWord.Repetition_Dialog__1_56"), //$NON-NLS-1$
			Messages.getString("RecognizeWord.Repetition_Dialog__2_57")); //$NON-NLS-1$
	}

	private void restoreStep(BackTrackStep step) {
		generatedWord = step.getGeneratedWord();
		stack = step.getStackConfig();
		currentElement = step.getElem();
	}
	
	public void finalTasks() {
		hideBacktrackingLabels();
		Iterator it = synDiaDef.getInitialDiagrams().iterator();
		while (it.hasNext()) 
			((SynDiaInitial)it.next()).getGfx().setBackgroundColor(diagramNormal);
	}
}