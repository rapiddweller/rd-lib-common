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

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Container;

/**
 * Displays a text in a dialog window.
 * Created: 21.06.2016 13:52:18
 * @since 1.0.10
 * @author Volker Bergmann
 */

public class TextDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public TextDialog(String text) {
		Container contentPane = getContentPane();
		contentPane.add(new JScrollPane(new JTextArea(text)), BorderLayout.CENTER);
		JButton okButton = new JButton("OK");
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(okButton);
		buttonBox.add(Box.createHorizontalGlue());
		okButton.addActionListener(evt -> setVisible(false));
		contentPane.add(buttonBox, BorderLayout.SOUTH);
	}
	
	public static void show(String text) {
		TextDialog dialog = new TextDialog(text);
		dialog.setSize(600, 400);
		SwingUtil.center(dialog);
		dialog.setVisible(true);
	}
	
}
