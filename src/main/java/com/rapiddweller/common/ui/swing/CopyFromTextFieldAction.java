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

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

/**
 * Copies the selected content of a {@link JTextField} component to the clipboard. 
 * Created: 10.04.2016 11:29:03
 * @since 1.0.9
 * @author Volker Bergmann
 */

public class CopyFromTextFieldAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	private final JTextField textField;
	
	public CopyFromTextFieldAction(JTextField textField) {
		super("Copy");
		this.textField = textField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String textToCopy = textField.getSelectedText();
		if (textToCopy == null)
			textToCopy = textField.getText();
		StringSelection selection = new StringSelection(textToCopy);
	    clipboard.setContents(selection, selection);
	}
	
}
