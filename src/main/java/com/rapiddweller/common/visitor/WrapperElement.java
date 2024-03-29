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

import com.rapiddweller.common.Visitor;

import java.util.Objects;

/**
 * Element implementation that serves as proxy for another Element.
 * Created: 04.02.2007 08:17:20
 *
 * @param <E> The type of the wrapped element
 * @author Volker Bergmann
 */
public abstract class WrapperElement<E> extends AbstractElement<E> {

  /**
   * The Wrapped object.
   */
  protected E wrappedObject;

  /**
   * Instantiates a new Wrapper element.
   *
   * @param wrappedObject the wrapped object
   */
  protected WrapperElement(E wrappedObject) {
    this.wrappedObject = wrappedObject;
  }

  /**
   * Gets wrapped object.
   *
   * @return the wrapped object
   */
  public E getWrappedObject() {
    return wrappedObject;
  }

  @Override
  protected void acceptImpl(Visitor<E> visitor) {
    visitor.visit(wrappedObject);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final WrapperElement that = (WrapperElement) o;
    return Objects.equals(wrappedObject, that.wrappedObject);
  }

  @Override
  public int hashCode() {
    return (wrappedObject != null ? wrappedObject.hashCode() : 0);
  }

  @Override
  public String toString() {
    return wrappedObject.toString();
  }
}
