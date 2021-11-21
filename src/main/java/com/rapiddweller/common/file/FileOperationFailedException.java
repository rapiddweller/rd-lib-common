/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.file;

import com.rapiddweller.common.OperationFailedException;

/**
 * Signals failure of a file operation.<br/><br/>
 * Created: 19.11.2021 14:03:50
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FileOperationFailedException extends OperationFailedException {

  public FileOperationFailedException(String errorId, int exitCode, String message) {
    this(errorId, exitCode, message, null);
  }

  public FileOperationFailedException(String errorId, int exitCode, String message, Throwable cause) {
    super(errorId, exitCode, message, cause);
  }

}
