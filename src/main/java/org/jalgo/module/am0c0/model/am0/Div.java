/*
 * j-Algo - j-Algo is an algorithm visualization tool, especially useful for
 * students and lecturers of computer science. It is written in Java and platform
 * independent. j-Algo is developed with the help of Dresden University of
 * Technology.
 * 
 * Copyright (C) 2004-2010 j-Algo-Team, j-algo-development@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jalgo.module.am0c0.model.am0;

import org.jalgo.main.util.Messages;
import org.jalgo.module.am0c0.model.LineAddress;

/**
 * Updates the AM after the specific command.
 * 
 * @author Max Leuth&auml;user
 * @author David Voigt
 */
public class Div extends ArithmeticStatement {

	public Div(LineAddress address) {
		super(address);
	}

	@Override
	public MachineConfiguration apply(MachineConfiguration configuration)
			throws IllegalArgumentException, ArithmeticException {
		if (configuration.getStack().getStackAsList().size() < 2) {
			throw new IllegalArgumentException(Messages.getString(
					"am0c0", "Div.0")); //$NON-NLS-1$
		}
		configuration.getProgramCounter().inc();
		int arg0 = configuration.getStack().pop();
		int arg1 = configuration.getStack().pop();
		// this way round because this is the way its
		// written in the script

		if (arg0 == 0) {
			throw new ArithmeticException(Messages.getString("am0c0", "Div.1")); //$NON-NLS-1$
		}

		configuration.getStack().push(arg1 / arg0);
		return configuration;
	}

	@Override
	public String getDescription() {
		return Messages.getString("am0c0", "Div.2"); //$NON-NLS-1$
	}

	@Override
	public String getCodeText() {
		return "DIV;"; //$NON-NLS-1$
	}
}
