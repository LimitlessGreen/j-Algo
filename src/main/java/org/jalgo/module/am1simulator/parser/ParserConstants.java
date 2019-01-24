/**
 * AM1 Simulator - simulating am1 code in an abstract machine based on the
 * definitions of the lectures 'Programmierung' at TU Dresden.
 * Copyright (C) 2010 Max Leuthäuser
 * Contact: s7060241@mail.zih.tu-dresden.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jalgo.module.am1simulator.parser;

/**
 * Set of constants that are used by the parser package.
 * 
 * @author Martin Morgenstern
 */
public final class ParserConstants {
	/**
	 * This class is not designed to be instantiated, therefore it's constructor
	 * is private.
	 */
	private ParserConstants() {
		throw new AssertionError();
	}

	/**
	 * Constant used as the value for end-of-file symbols (i.e. tokens).
	 */
	public static final String EOF = "end-of-file";

	/**
	 * Constant used as the value for end-of-line symbols (i.e. tokens).
	 */
	public static final String EOL = "end-of-line";
}