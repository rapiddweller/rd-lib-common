/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.file;

import com.rapiddweller.common.exception.ExitCodes;

/**
 * Signals that the creation of a file failed.<br/><br/>
 * Created: 20.11.2021 18:41:07
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FileCreationFailed extends FileOperationFailed {

  public FileCreationFailed(String message) {
    this(message, null);
  }

  public FileCreationFailed(String message, Throwable cause) {
    super(null, ExitCodes.CANT_CREATE_FILE, message, cause);
  }

}
