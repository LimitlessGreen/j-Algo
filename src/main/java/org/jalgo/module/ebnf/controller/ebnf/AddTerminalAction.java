/* j-Algo - j-Algo is an algorithm visualization tool, especially useful for
 * students and lecturers of computer science. It is written in Java and
 * platform independent. j-Algo is developed with the help of Dresden
 * University of Technology.
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

package org.jalgo.module.ebnf.controller.ebnf;

import org.jalgo.module.ebnf.model.ebnf.DefinitionFormatException;
import org.jalgo.module.ebnf.model.ebnf.ETerminalSymbol;
import org.jalgo.module.ebnf.util.IAction;

/**
 * This is the action for adding a terminal symbol
 * 
 * @author Tom Kaimiers, Johannes Mey
 *
 */
public class AddTerminalAction implements IAction {
	
	// the new terminal symbol	
	private ETerminalSymbol terminal;
	
	// the controller that owns the definition
		private EbnfController controller;
		
		/**
		 * @param controller
		 * @param terminal
		 */
		public AddTerminalAction(EbnfController controller, ETerminalSymbol terminal) {
			this.terminal = terminal;
			this.controller = controller;
		}

		/* (non-Javadoc)
		 * @see org.jalgo.module.ebnf.util.IAction#perform()
		 */
		public void perform() throws DefinitionFormatException {
			controller.getDefinition().addTerminal(terminal);
		}

		/* (non-Javadoc)
		 * @see org.jalgo.module.ebnf.util.IAction#undo()
		 */
		public void undo() throws DefinitionFormatException {
			controller.getDefinition().removeTerminal(terminal);
		}
 }
