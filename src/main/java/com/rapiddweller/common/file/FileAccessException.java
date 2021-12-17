/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.file;

import com.rapiddweller.common.exception.ApplicationException;
import com.rapiddweller.common.exception.ExitCodes;

/**
 * Signals a general file I/O error.<br/><br/>
 * Created: 18.11.2021 15:18:23
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FileAccessException extends ApplicationException {

  public FileAccessException(String message) {
    this(message, null);
  }

  public FileAccessException(String message, Throwable cause) {
    super(message, cause, null, ExitCodes.I_O_ERROR);
  }

}
