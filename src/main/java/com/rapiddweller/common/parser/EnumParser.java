/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.converter.String2EnumConverter;
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Parses Strings which represent the name of an enumeration instance.<br/><br/>
 * Created: 09.12.2021 07:58:51
 * @author Volker Bergmann
 * @since 2.1.0
 */
public class EnumParser<E extends Enum<E>> extends AbstractTypedParser<E> {

  public EnumParser(Class<E> resultType) {
    super("instance of ", resultType);
  }

  @Override
  protected E parseImpl(String spec) {
    E result = String2EnumConverter.convert(spec, resultType);
    if (result == null && spec != null) {
      throw ExceptionFactory.getInstance().syntaxErrorForText("not a " + getDescription(), spec);
    }
    return result;
  }

}
