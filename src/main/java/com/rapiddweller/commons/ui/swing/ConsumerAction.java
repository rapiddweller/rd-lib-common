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
import javax.swing.Icon;

import com.rapiddweller.commons.Consumer;

/**
 * {@link Runnable} implementation which consumes an object using a {@link Consumer}.
 * Created: 20.06.2016 16:40:19
 * @param <E> The generic type of the objects to consume
 * @since 1.0.11
 * @author Volker Bergmann
 */

public class ConsumerAction<E> extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	private final E object;
	private final Consumer<E> consumer;
	
	public ConsumerAction(String label, Icon icon, E object, Consumer<E> consumer) {
		super(label, icon);
		this.object = object;
		this.consumer = consumer;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		consumer.consume(object);
	}

}
