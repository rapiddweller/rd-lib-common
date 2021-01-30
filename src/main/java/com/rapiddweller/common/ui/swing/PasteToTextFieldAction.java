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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;

/**
 * Pastes the clipboard content to a {@link JTextField} component, replacing its current selection. 
 * Created: 10.04.2016 11:37:33
 * @since 1.0.9
 * @author Volker Bergmann
 */

public class PasteToTextFieldAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger(PasteToTextFieldAction.class);

	private final JTextField textField;
	
	public PasteToTextFieldAction(JTextField textField) {
		super("Paste");
		this.textField = textField;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = clipboard.getContents(null);
        if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
				String data = (String) t.getTransferData(DataFlavor.stringFlavor);
				textField.replaceSelection(data);
			} catch (Exception e1) {
				LOGGER.error("Paste failed", e1);
			}
        }
	}
	
}
