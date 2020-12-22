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
package com.rapiddweller.commons.ui.swing;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Renders the canonical path of a {@link File} in a {@link JList}.
 * Created at 30.11.2008 17:11:26
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class FilePathListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -448916948052054554L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		try {
			String text = ((File) value).getCanonicalPath();
			return super.getListCellRendererComponent(list, text, index, isSelected, hasFocus());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
