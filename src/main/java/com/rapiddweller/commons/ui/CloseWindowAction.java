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
package com.rapiddweller.commons.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * {@link Action} that closes a Window.
 * Created: 10.07.2014 15:23:20
 * @since 0.5.33
 * @author Volker Bergmann
 */

public class CloseWindowAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private final Window window;

	public CloseWindowAction(Window window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (window == null)
			return;
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}

}
