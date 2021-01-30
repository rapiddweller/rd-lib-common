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
package com.rapiddweller.common.visitor;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.Visitor;

import java.util.List;

/**
 * Groups multiple visitors into the interface of a single one.
 * Created: 06.03.2011 14:39:23
 * @param <E> the type of the visited elements
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class MultiVisitor<E> implements Visitor<E> {

	protected List<Visitor<E>> realVisitors;
	
	@SafeVarargs
    public MultiVisitor(Visitor<E>... realVisitors) {
		this.realVisitors = CollectionUtil.toList(realVisitors);
	}
	
	@Override
	public <C extends E> void visit(C element) {
		for (Visitor<E> realVisitor : realVisitors)
			realVisitor.visit(element);
	}
	
}
