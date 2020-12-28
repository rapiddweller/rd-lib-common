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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.NullSafeComparator;
import com.rapiddweller.commons.bean.ObservableBean;
import com.rapiddweller.commons.converter.ToStringConverter;
import com.rapiddweller.commons.ui.I18NSupport;

/**
 * {@link JComboBox} implementation that serves as delegate of a property of a JavaBean object.
 * Created at 01.12.2008 07:44:32
 * @param <E> The item type of the {@link JComboBox}' elements
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class PropertyComboBox<E> extends JComboBox<E> {

	private static final long serialVersionUID = -5039037135360506124L;
	
	private final Object bean;
	private final String propertyName;
	boolean locked;
	

	@SafeVarargs
	public PropertyComboBox(Object bean, String propertyName, I18NSupport i18n, String prefix, E ... values) {
		super(values);
		this.bean = bean;
		this.propertyName = propertyName;
		this.locked = true;
		Listener listener = new Listener();
		this.addActionListener(listener);
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.getModel().addListDataListener(listener);
		this.setRenderer(new Renderer(i18n, prefix));
		this.locked = false;
		refresh();
	}

	/**
	 * reads the current property value and writes it to the text field.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			Object selectedItem = getSelectedItem();
			if (!NullSafeComparator.equals(selectedItem, propertyValue))
				setSelectedItem(propertyValue);
			locked = false;
		}
	}
	
	/**
	 * writes the current text field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			Object selectedItem = getSelectedItem();
			if (!NullSafeComparator.equals(selectedItem, propertyValue))
				BeanUtil.setPropertyValue(bean, propertyName, selectedItem);
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, ListDataListener, ActionListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			refresh();
		}

		@Override
		public void contentsChanged(ListDataEvent evt) {
			update();
		}

		@Override
		public void intervalAdded(ListDataEvent evt) {
			update();
		}

		@Override
		public void intervalRemoved(ListDataEvent evt) {
			update();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
	        update();
        }
		
	}
	
	static final class Renderer extends DefaultListCellRenderer {
		
		private static final long serialVersionUID = 8358429951305253637L;
		private final ToStringConverter converter = new ToStringConverter();
		private final I18NSupport i18n;
		private final String prefix;

		public Renderer(I18NSupport i18n, String prefix) {
			this.i18n = i18n;
			this.prefix = prefix;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			String text = (i18n != null ? i18n.getString(prefix + converter.convert(value)) : String.valueOf(value));
			return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
		}
	}

}
