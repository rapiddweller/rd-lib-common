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
import java.io.File;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.CollectionUtil;
import com.rapiddweller.commons.bean.ObservableBean;
import com.rapiddweller.commons.ui.I18NSupport;
import com.rapiddweller.commons.ui.swing.FileList;

/**
 * {@link FileList} implementation that serves as delegate of a property of a JavaBean object.
 * Created at 30.11.2008 15:06:20
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class PropertyFileList extends FileList {

	// attributes ------------------------------------------------------------------------------------------------------
	
	private static final long serialVersionUID = -1259803129031396860L;
	
	private final Object bean;
	private final String propertyName;

	boolean locked;
	
	// constructor -----------------------------------------------------------------------------------------------------
	
	public PropertyFileList(Object bean, String propertyName, I18NSupport i18n) {
		super(i18n);
		this.bean = bean;
		this.propertyName = propertyName;
		this.locked = true;
		refresh();
		Listener listener = new Listener();
		model.addListDataListener(listener);
		if (bean instanceof ObservableBean)
			((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
		this.locked = false;
	}
	
	// event handlers --------------------------------------------------------------------------------------------------

	/**
	 * reads the current property value and writes it to the file list.
	 */
	void refresh() {
		if (!locked) {
			locked = true;
			Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
			File[] files = (File[]) propertyValue;
			if (!CollectionUtil.ofEqualContent(getFiles(), files))
				setFiles(files);
			locked = false;
		}
	}
	
	/**
	 * writes the current file field content to the property.
	 */
	void update() {
		if (!locked) {
			locked = true;
			File[] modelFiles = (File[]) BeanUtil.getPropertyValue(bean, propertyName);
			List<File> viewFiles = getFiles();
			if (!CollectionUtil.ofEqualContent(viewFiles, modelFiles)) {
				File[] files = CollectionUtil.toArray(viewFiles, File.class);
				BeanUtil.setPropertyValue(bean, propertyName, files);
			}
			locked = false;
		}
	}
	
	class Listener implements PropertyChangeListener, ListDataListener {

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

	}
}
