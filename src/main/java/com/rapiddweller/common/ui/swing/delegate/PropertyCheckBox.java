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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.bean.ObservableBean;

/**
 * {@link JCheckBox} implementation that serves as delegate of a property of a JavaBean object.
 * Created at 02.12.2008 15:03:32
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class PropertyCheckBox extends JCheckBox {

	private static final long serialVersionUID = 2502918170512919334L;
	
	private final Object bean;
	private final String propertyName;
	boolean locked;

	public PropertyCheckBox(Object bean, String propertyName, String label) {
		super(label);
		this.bean = bean;
		this.propertyName = propertyName;
		this.locked = true;
		Listener listener = new Listener();
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.getModel().addActionListener(listener);
		this.locked = false;
		refresh();
	}

	/**
	 * reads the current property value and writes it to the text field.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			boolean propertyValue = (Boolean) BeanUtil.getPropertyValue(bean, propertyName);
			boolean selected = isSelected();
			if (selected != propertyValue)
				setSelected(propertyValue);
			locked = false;
		}
	}
	
	/**
	 * writes the current text field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			Boolean propertyValue = (Boolean) BeanUtil.getPropertyValue(bean, propertyName);
			Boolean selected = isSelected();
			if (!NullSafeComparator.equals(selected, propertyValue))
				BeanUtil.setPropertyValue(bean, propertyName, selected);
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, ActionListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			refresh();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			update();
		}
	}
}
