/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.common.ui.swing;

import javax.swing.Icon;
import javax.swing.UIManager;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * {@link Icon} implementation that renders a text.
 * Created: 13.12.2013 08:28:56
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class TextIcon implements Icon {

	private final String text;
	private final Color background;
	private final Color foreground;
	private final Font font;

	private int iconWidth;
	private int iconHeight;
	private final int textWidth;
	private final int ascent;


	// constructors ----------------------------------------------------------------------------------------------------

	public TextIcon(String text) {
		this(text, Color.BLACK, null);
	}

	public TextIcon(String text, Color foreground, Color background) {
		this(text, foreground, background, false, false);
	}

	public TextIcon(String text, Color foreground, Color background, boolean square, boolean bold) {
		this(text, foreground, background, square, defaultFont(bold));
	}

	public TextIcon(String text, Color foreground, Color background, boolean square, Font font) {
		this.text = text;
		this.font = font;
		FontMetrics metrics = new Canvas().getFontMetrics(font);
		this.textWidth = metrics.stringWidth(text);
		this.iconWidth = textWidth;
		this.iconHeight = font.getSize() + 2;
		if (square) {
			this.iconWidth = Math.max(this.iconWidth, this.iconHeight);
			this.iconHeight = this.iconWidth;
		}
		this.ascent = metrics.getAscent();
		this.foreground = foreground;
		this.background = background;
	}


	// properties ------------------------------------------------------------------------------------------------------

	@Override
	public int getIconHeight() {
		return iconHeight;
	}

	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	@Override
	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}


	// rendering methods -----------------------------------------------------------------------------------------------

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (background != null) {
			g.setColor(background);
			g.fillRect(x, y, iconWidth, iconHeight);
		}
		g.setColor(foreground);
		Font origFont = g.getFont();
		g.setFont(font);
		g.drawString(text, x + (iconWidth - textWidth) / 2, y + (iconHeight - font.getSize()) / 2  + ascent - 1);
		g.setFont(origFont);
	}


	// private helpers -------------------------------------------------------------------------------------------------

	private static Font defaultFont(boolean bold) {
		Font tableFont = UIManager.getDefaults().getFont("Table.font");
		if (tableFont.isBold() != bold)
			return new Font(tableFont.getFamily(), (bold ? Font.BOLD : Font.PLAIN), tableFont.getSize());
		else
			return tableFont;
	}

	public Icon withSize(int size) {
		this.iconWidth = this.iconHeight = size;
		return this;
	}

}
