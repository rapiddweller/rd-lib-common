/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parser implementation which holds the target type info.<br/><br/>
 * Created: 08.12.2021 19:46:21
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class AbstractTypedParser<E> extends AbstractParser<E> implements TypedParser<E> {

  protected final Class<E> resultType;

  protected AbstractTypedParser(String description, Class<E> resultType) {
    super(description);
    this.resultType = resultType;
  }

  public Class<E> getResultType() {
    return resultType;
  }

}
