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
 *
 * @param <E> the type of the tree nodes
 * @author Volker Bergmann
 */
public interface TreeModel<E> {

  /**
   * Gets root.
   *
   * @return the root
   */
  E getRoot();

  /**
   * Gets parent.
   *
   * @param child the child
   * @return the parent
   */
  E getParent(E child);

  /**
   * Gets child.
   *
   * @param parent the parent
   * @param index  the index
   * @return the child
   */
  E getChild(E parent, int index);

  /**
   * Gets child count.
   *
   * @param parent the parent
   * @return the child count
   */
  int getChildCount(E parent);

  /**
   * Is leaf boolean.
   *
   * @param node the node
   * @return the boolean
   */
  boolean isLeaf(E node);

  /**
   * Gets index of child.
   *
   * @param parent the parent
   * @param child  the child
   * @return the index of child
   */
  int getIndexOfChild(E parent, E child);
}
