/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.file;

import com.rapiddweller.common.exception.ExitCodes;

/**
 * Signals that the deletion of a file has failed.<br/><br/>
 * Created: 19.11.2021 14:08:07
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FileDeletionFailedException extends FileOperationFailedException {

  public FileDeletionFailedException(String message) {
    this(message, null);
  }

  public FileDeletionFailedException(String message, Throwable cause) {
    super(null, ExitCodes.I_O_ERROR, message, cause);
  }

}
