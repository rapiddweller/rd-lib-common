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

import javax.swing.JComboBox;

/**
 * Lets the user select a class.
 * Created: 19.05.2016 18:33:31
 * @param <E> The generic parent class type of the classes in the combo box
 * @since 1.0.11
 * @author Volker Bergmann
 */

public class ClassComboBox<E> extends JComboBox<Class<? extends E>> {

	private static final long serialVersionUID = 1L;

	public ClassComboBox(Class<? extends E>[] classes) {
		super(classes);
		setRenderer(new ClassListCellRenderer());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<E> getSelectedItem() {
		return (Class<E>) super.getSelectedItem();
	}
	
}
