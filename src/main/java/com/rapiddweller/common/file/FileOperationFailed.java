/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.file;

import com.rapiddweller.common.OperationFailed;

/**
 * Signals failure of a file operation.<br/><br/>
 * Created: 19.11.2021 14:03:50
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FileOperationFailed extends OperationFailed {

  public FileOperationFailed(String errorId, int exitCode, String message) {
    this(errorId, exitCode, message, null);
  }

  public FileOperationFailed(String errorId, int exitCode, String message, Throwable cause) {
    super(message, cause, errorId, exitCode);
  }

}
