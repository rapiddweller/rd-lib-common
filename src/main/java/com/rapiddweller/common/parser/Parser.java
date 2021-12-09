/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parent interface for classes that parse a string into an individual object of type E.<br/><br/>
 * Created: 08.12.2021 14:38:11
 * @param <E> The target type of the parser
 * @author Volker Bergmann
 * @since 2.0.0
 */
public interface Parser<E> {
  E parse(String spec);
  String getDescription();
}
