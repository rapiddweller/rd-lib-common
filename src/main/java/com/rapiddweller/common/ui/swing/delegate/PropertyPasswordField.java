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
package com.rapiddweller.common.ui.swing.delegate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.bean.ObservableBean;
import com.rapiddweller.common.converter.ToStringConverter;

/**
 * {@link JPasswordField} implementation that serves as delegate of a property of a JavaBean object.
 * Created: 05.04.2010 11:14:19
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class PropertyPasswordField extends JPasswordField {
	
	/** UID for serialization */
	private static final long serialVersionUID = -4336295480697515456L;
	
	// attributes ------------------------------------------------------------------------------------------------------
	
	private final Object bean;
	private final String propertyName;

	private final ToStringConverter toStringConverter;
	boolean locked;
	
	// constructor -----------------------------------------------------------------------------------------------------
	
	public PropertyPasswordField(Object bean, String propertyName, int length) {
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
			getPassword();
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
			text = StringUtil.escape(text);
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
