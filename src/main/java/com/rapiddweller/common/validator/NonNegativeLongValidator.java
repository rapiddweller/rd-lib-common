/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.validator;

import com.rapiddweller.common.Validator;

/**
 * {@link Validator} for long values greater or equals zero.<br/><br/>
 * Created: 08.12.2021 14:12:50
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class NonNegativeLongValidator implements Validator<String> {

  @Override
  public boolean valid(String spec) {
    try {
      long result = Long.parseLong(spec);
      return (result >= 0);
    } catch (NumberFormatException e) {
      return false;
    }
  }

}
