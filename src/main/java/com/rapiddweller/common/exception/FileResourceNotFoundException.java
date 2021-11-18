/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals that a file resource has not been found.<br/><br/>
 * Created: 17.11.2021 23:17:11
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FileResourceNotFoundException extends ApplicationException {

  public FileResourceNotFoundException(String errorCode, String message) {
    this(errorCode, message, null);
  }

  public FileResourceNotFoundException(String errorCode, String message, Throwable cause) {
    super(errorCode, ExitCodes.FILE_NOT_FOUND, message, cause);
  }

}
