/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.exception.SyntaxError;

/**
 * Abstract {@link Parser} implementation.<br/><br/>
 * Created: 08.12.2021 14:45:21
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class AbstractParser<E> implements Parser<E> {

  private final String description;

  protected AbstractParser(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public E parse(String spec) {
    try {
      return parseImpl(spec);
    } catch (Exception e) {
      throw syntaxError(spec, e);
    }
  }

  protected abstract E parseImpl(String spec);

  @Override
  public String toString() {
    return description;
  }

  protected SyntaxError syntaxError(String text, Exception e) {
    return ExceptionFactory.getInstance().syntaxErrorForText("Not a " + getDescription(), e, text);
  }

}
