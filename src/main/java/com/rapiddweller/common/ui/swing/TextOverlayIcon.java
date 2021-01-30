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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * Paints a text over another icon.
 * Created: 19.05.2016 17:28:08
 * @since 1.0.10
 * @author Volker Bergmann
 */

public class TextOverlayIcon implements Icon {

	private final Icon   background;
	private final String text;
	private final Color  color;
	private final Font   font;
	private final FontMetrics metrics;
	
	private int backgroundXOffset;
	private int backgroundYOffset;
	private int textXOffset;
	private int textYOffset;

	private int width;
	private int height;

	// constructors ----------------------------------------------------------------------------------------------------

	public TextOverlayIcon(Icon background, String text) {
		this(background, text, Color.BLACK);
	}

	public TextOverlayIcon(Icon background, String text, Color color) {
		this(background, text, color, defaultFont());
	}

	public TextOverlayIcon(Icon background, String text, Color color, Font font) {
		this.background = background;
		this.text = text;
		this.color = color;
		this.font = font;
		this.metrics = new Canvas().getFontMetrics(font);

		int textWidth = metrics.stringWidth(text);
		int iconWidth = background.getIconWidth();
		setIconWidth(Math.max(textWidth, iconWidth));
		
		int textHeight = font.getSize();
		int iconHeight = background.getIconHeight();
		setIconHeight(Math.max(textHeight, iconHeight));
	}


	// properties ------------------------------------------------------------------------------------------------------

	public TextOverlayIcon withSize(int size) {
		setIconWidth(size);
		setIconHeight(size);
		return this;
	}
	
	@Override
	public int getIconWidth() {
		return width;
	}

	public void setIconWidth(int iconWidth) {
		this.width = iconWidth;
		int textWidth = metrics.stringWidth(text);
		this.textXOffset = (this.width - textWidth) / 2;
		this.backgroundXOffset = (this.width - background.getIconWidth()) / 2;
	}

	@Override
	public int getIconHeight() {
		return height;
	}

	public void setIconHeight(int iconHeight) {
		this.height = iconHeight;
		int textHeight = font.getSize();
		int ascent = metrics.getAscent();
		this.textYOffset = (iconHeight - textHeight) / 2  + ascent - 1;
		this.backgroundYOffset = (this.height - background.getIconHeight()) / 2;
	}


	// rendering methods -----------------------------------------------------------------------------------------------

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (background != null)
			background.paintIcon(c, g, x + backgroundXOffset, y + backgroundYOffset);
		g.setColor(color);
		Font origFont = g.getFont();
		g.setFont(font);
		g.drawString(text, x + textXOffset, y + textYOffset);
		g.setFont(origFont);
	}


	// private helpers -------------------------------------------------------------------------------------------------

	private static Font defaultFont() {
		Font tableFont = UIManager.getDefaults().getFont("Table.font");
		if (tableFont.isBold() != false)
			return new Font(tableFont.getFamily(), (false ? Font.BOLD : Font.PLAIN), tableFont.getSize());
		else
			return tableFont;
	}

}
