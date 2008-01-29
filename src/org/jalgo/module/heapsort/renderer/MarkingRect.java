/*
 * j-Algo - a visualization tool for algorithm runs, especially useful for
 * students and lecturers of computer sience. j-Algo is written in Java and
 * thus platform independent. Development is supported by Technische Universität
 * Dresden.
 *
 * Copyright (C) 2004-2008 j-Algo-Team, j-algo-development@lists.sourceforge.net
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
package org.jalgo.module.heapsort.renderer;

import java.awt.Color;
import java.awt.Point;

/**
 * @author mbue
 *
 */
public class MarkingRect extends CanvasEntity {
	private Color color = Color.GRAY;
	private Point position = new Point();
	private float opacity = 1.0f;
	private int width;
	private int height;
	
	protected MarkingRect() {
		super();
	}

	/**
	 * @see Node#update()
	 *
	 */
	protected void update() {
	}	
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		if (!this.position.equals(position)) {
			this.position.setLocation(position);
			invalidate();
			trans.setToTranslation(position.x, position.y);
			invalidate();
		}		
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (height != this.height) {
			this.height = height;
			invalidate();
			bounds.setBounds(0, 0, width, height);
			invalidate();
		}
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width != this.width) {
			this.width = width;
			invalidate();
			bounds.setBounds(0, 0, width, height);
			invalidate();
		}
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
		invalidate();
		update();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		update();
	}

}
