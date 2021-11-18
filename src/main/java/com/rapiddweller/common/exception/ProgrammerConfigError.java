/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals an error induced by improper configuration used by a programmer (as opposed to the user).<br/><br/>
 * Created: 18.11.2021 15:09:10
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ProgrammerConfigError extends ProgrammerError {

  protected ProgrammerConfigError(String message) {
    this(message, null);
  }

  protected ProgrammerConfigError(String message, Throwable cause) {
    super(null, message, cause);
  }

}
