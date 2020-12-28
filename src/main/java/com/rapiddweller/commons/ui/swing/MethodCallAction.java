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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

import com.rapiddweller.commons.BeanUtil;

/**
 * {@link Action} implementation that calls a method on a target object using arguments.<br><br>
 * Created: 20.06.2016 10:29:05
 * @since 1.0.10
 * @author Volker Bergmann
 */

public class MethodCallAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final Object target;
	private final String methodName;
	private final Object[] arguments;
	
	public MethodCallAction(String label, Icon icon, Object target, String methodName, Object... arguments) {
		super(label, icon);
		this.target = target;
		this.methodName = methodName;
		this.arguments = arguments;
		putValue(SHORT_DESCRIPTION, getValue(NAME));
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		BeanUtil.invoke(target, methodName, arguments);
	}

}
