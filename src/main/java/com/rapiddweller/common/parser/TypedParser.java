/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parent interface for {@link Parser}s which provide type info.<br/><br/>
 * Created: 13.12.2021 12:03:54
 * @author Volker Bergmann
 * @since 2.0.0
 */
public interface TypedParser<E> extends Parser<E> {
  Class<E> getResultType();
}
