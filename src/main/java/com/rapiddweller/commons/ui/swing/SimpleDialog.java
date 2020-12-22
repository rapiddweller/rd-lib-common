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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

/**
 * {@link JDialog} which applies useful standard behaviour.
 * Created: 22.08.2012 07:31:08
 * @param <E> the component type to display in the dialog window
 * @since 0.5.18
 * @author Volker Bergmann
 */
@SuppressWarnings("serial")
public class SimpleDialog<E extends Component> extends JDialog {

	protected E mainComponent;
	protected boolean cancellable;
	private Component parentComponent;
	
	protected boolean cancelled;
	
	protected Box buttonBar;
	protected boolean completed;
	protected AbstractAction cancelAction;
	protected AbstractAction okAction;

	public SimpleDialog(Component parentComponent, String title, boolean modal, boolean cancellable, E mainComponent) {
		super(SwingUtil.getWindowForComponent(parentComponent), title, (modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS));
		this.parentComponent = parentComponent;
		this.cancellable = cancellable;
		this.completed = false;
		this.cancelled = false;
		
		// Set up main component
		this.mainComponent = mainComponent;
		add(mainComponent, BorderLayout.CENTER);
		
		// setup actions
		this.cancelAction = new AbstractAction("Cancel") {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelled = true;
				setVisible(false);
			}
		};
		this.okAction = new AbstractAction("OK") {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (handleOkClick()) {
					cancelled = false;
					setVisible(false);
				}
			}

		};
		this.buttonBar = Box.createHorizontalBox();
		add(this.buttonBar, BorderLayout.SOUTH);
		
		// assure that the dialog is closed if the user hits Escape
	    getRootPane().registerKeyboardAction(cancelAction, 
	    		KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			this.cancelled = false;
			if (!completed)
				completeButtonBar();
		}
		super.setVisible(visible);
	}

	public static <T extends Component> T showModalDialog(T mainComponent, String title, boolean cancellable, Component parentComponent) {
		SimpleDialog<T> dialog = new SimpleDialog<T>(parentComponent, title, true, cancellable, mainComponent);
		dialog.setVisible(true);
		dialog.dispose();
		return (dialog.wasCancelled() ? null : dialog.getMainComponent());
	}

	public E getMainComponent() {
		return mainComponent;
	}
	
	public boolean wasCancelled() {
		return cancelled;
	}

	public void addButton(AbstractAction action) {
		buttonBar.add(new JButton(action));
	}
	
	protected boolean handleOkClick() {
		return true;
	}
	
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private void completeButtonBar() {
		buttonBar.setBorder(new EmptyBorder(8, 8, 8, 8));
		buttonBar.add(Box.createHorizontalGlue());
		if (cancellable) {
			addButton(cancelAction);
			buttonBar.add(Box.createHorizontalStrut(8));
		}
		JButton okButton = new JButton(okAction);
		buttonBar.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    // pack and position the dialog 
		setResizable(false);
		pack();
		setLocationRelativeTo(parentComponent);
		this.completed = true;
	}

}
