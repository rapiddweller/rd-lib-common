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
package com.rapiddweller.commons.ui.swing.delegate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.bean.ObservableBean;
import com.rapiddweller.commons.converter.ToStringConverter;

/**
 * {@link JTextField} implementation that serves as delegate of a property of a JavaBean object.
 * Created at 17.07.2008 14:38:14
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class PropertyTextField extends JTextField {
	
	/** uid for serialization */
	private static final long serialVersionUID = -6986676080916477887L;
	
	// attributes ------------------------------------------------------------------------------------------------------
	
	private final Object bean;
	private final String propertyName;

	private final ToStringConverter toStringConverter;
	boolean locked;
	
	// constructor -----------------------------------------------------------------------------------------------------
	
	public PropertyTextField(Object bean, String propertyName, int length) {
		super(length);
		this.bean = bean;
		this.propertyName = propertyName;
		this.toStringConverter = new ToStringConverter();
		this.locked = true;
		Listener listener = new Listener();
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.getDocument().addDocumentListener(listener);
		this.locked = false;
		refresh();
	}
	
	// event handlers --------------------------------------------------------------------------------------------------

	/**
	 * reads the current property value and writes it to the text field.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			String text = toStringConverter.convert(propertyValue);
			text = StringUtil.escape(text);
			if (!getText().equals(text))
				setText(text);
			locked = false;
		}
	}
	
	/**
	 * writes the current text field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			Document document = getDocument();
			String text;
			try {
				text = document.getText(0, document.getLength());
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
			text = StringUtil.unescape(text);
			if (!text.equals(BeanUtil.getPropertyValue(bean, propertyName)))
				BeanUtil.setPropertyValue(bean, propertyName, text);
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, DocumentListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			refresh();
		}

		@Override
		public void changedUpdate(DocumentEvent evt) {
			 update();
		}

		@Override
		public void insertUpdate(DocumentEvent evt) {
			 update();
		}

		@Override
		public void removeUpdate(DocumentEvent evt) {
			 update();
		}
		
	}
}
