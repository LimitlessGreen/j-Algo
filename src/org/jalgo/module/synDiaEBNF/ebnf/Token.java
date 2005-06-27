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
 * Created on May 3, 2004
 */
 
package org.jalgo.module.synDiaEBNF.ebnf;

/**
 * every recognized token is represented by a <code>Token</code> object
 * 
 * @author Stephan Creutz 
 */
public class Token {
	private Integer tokenName;
	private String tokenValue;
	private int position;

	/**
	 * constructor for the <code>Token</code> object
	 * @param tokenName
	 * @param tokenValue
	 * @param position
	 */
	public Token (Integer tokenName, String tokenValue, int position) {
		this.tokenName = tokenName;
		this.tokenValue = tokenValue;
		this.position = position;
	}
	
	/**
	 * 
	 * @return the token name
	 */
	public Integer getTokenName() {
		return tokenName;
	}

	/**
	 * 
	 * @param tokenName
	 */
	public void setTokenName(Integer tokenName) {
		this.tokenName = tokenName;
	}

	/**
	 * @return token value
	 */
	public String getTokenValue() {
		return tokenValue;
	}

	/**
	 * @param string token value
	 */
	public void setTokenValue(String string) {
		tokenValue = string;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

}
