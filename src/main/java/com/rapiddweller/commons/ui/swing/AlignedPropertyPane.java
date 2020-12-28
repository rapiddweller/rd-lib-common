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

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.rapiddweller.commons.ui.I18NSupport;
import com.rapiddweller.commons.ui.swing.delegate.PropertyCheckBox;
import com.rapiddweller.commons.ui.swing.delegate.PropertyComboBox;
import com.rapiddweller.commons.ui.swing.delegate.PropertyPasswordField;
import com.rapiddweller.commons.ui.swing.delegate.PropertyTextArea;
import com.rapiddweller.commons.ui.swing.delegate.PropertyTextField;

/**
 * {@link AlignedPane} implementation that supports property-based input elements.
 * Created: 22.08.2010 07:09:34
 * @since 0.5.13
 * @author Volker Bergmann
 * @param <E> The type of the bean displayed
 */
public class AlignedPropertyPane<E> extends AlignedPane {
	
	private static final long serialVersionUID = 4787396114405246065L;

	private static final int WIDE = 30;
	
	protected final E bean; 
	protected final I18NSupport i18n;
	
	public AlignedPropertyPane(int orientation, int columns, E bean, I18NSupport i18n) {
		super(orientation, columns);
	    this.bean = bean;
	    this.i18n = i18n;
    }

	public E getBean() {
    	return bean;
    }

	public void createI18NLabelRow(String... keys) {
		for (String key : keys)
			this.addElement(new JLabel(i18n.getString(key)));
		endRow();
	}
	
	public JTextField createTextFieldRow(String propertyName) {
		JTextField textfield = new PropertyTextField(bean, propertyName, WIDE);
		String label = i18n.getString(propertyName);
		this.addRow(label, textfield);
		return textfield;
	}

    public JTextField createTextField(String propertyName) {
		return createTextField(propertyName, 1, true);
	}

	public JTextField createTextField(String propertyName, boolean useLabel) {
		return createTextField(propertyName, 1, useLabel);
	}
	
	public JTextField createTextField(String propertyName, int gridwidth, boolean useLabel) {
		JTextField textfield = new PropertyTextField(bean, propertyName, WIDE / 2);
		if (useLabel) {
			String label = i18n.getString(propertyName);
			this.addElement(label, textfield, gridwidth);
		} else
			this.addElement(textfield, gridwidth);
		return textfield;
	}
	
	public JTextArea createTextArea(String propertyName) {
		JTextArea textArea = new PropertyTextArea(bean, propertyName);
		String label = i18n.getString(propertyName);
		this.addLabelRow(label);
		this.addTallRow(new JScrollPane(textArea));
		return textArea;
	}
	
    public JCheckBox createCheckBox(String propertyName) {
		PropertyCheckBox checkBox = new PropertyCheckBox(bean, propertyName, i18n.getString(propertyName));
		this.addElement(checkBox);
		return checkBox;
	}

	@SafeVarargs
	public final <O> JComboBox<O> createComboBoxRow(String propertyName, O... options) {
		JComboBox<O> comboBox = createComboBox(propertyName, true, true, options);
		this.endRow();
		return comboBox;
	}

	@SafeVarargs
	public final <O> JComboBox<O> createComboBox(String propertyName, boolean useLabel, boolean contentIi18n, O... options) {
	    JComboBox<O> comboBox = new PropertyComboBox<>(bean, propertyName, (contentIi18n ? i18n : null), propertyName + ".", options);
	    if (useLabel) {
			String label = this.i18n.getString(propertyName);
			this.addElement(label, comboBox);
	    } else {
			this.addElement(comboBox);
	    }
	    return comboBox;
    }

	public JTextField createPasswordField(String propertyName) {
		PropertyPasswordField pwfield = new PropertyPasswordField(bean, propertyName, WIDE / 2);
		String label = i18n.getString(propertyName);
		this.addElement(label, pwfield);
		return pwfield;
	}
	
	public JButton createButton(String label, ActionListener listener) {
		JButton button = new JButton(i18n.getString(label));
		button.addActionListener(listener);
		return button;
	}
	
}
