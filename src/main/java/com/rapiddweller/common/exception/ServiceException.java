/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Abstractions for exceptions that signal errors concerning an external service.<br/><br/>
 * Created: 19.11.2021 14:28:57
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class ServiceException extends ApplicationException {

  protected ServiceException(String errorId, int exitCode, String message) {
    super(message, errorId, exitCode);
  }

  protected ServiceException(String errorId, int exitCode, String message, Throwable cause) {
    super(message, cause, errorId, exitCode);
  }

}
