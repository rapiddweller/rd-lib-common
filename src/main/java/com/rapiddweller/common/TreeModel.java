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
package com.rapiddweller.common;

/**
 * Models a typed tree logic.
 * Created: 08.05.2007 18:30:55
 * @param <E> the type of the tree nodes
 * @author Volker Bergmann
 */
public interface TreeModel<E> {

    E getRoot();
    E getParent(E child);
    E getChild(E parent, int index);
    int getChildCount(E parent);
    boolean isLeaf(E node);
    int getIndexOfChild(E parent, E child);
}
