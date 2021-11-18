/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Parent class for unchecked application exceptions.<br/><br/>
 * Created: 17.11.2021 22:23:40
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ApplicationException extends RuntimeException {

  private String errorId;
  private final int exitCode;

  public ApplicationException(String errorId, int exitCode, String message) {
    this(errorId, exitCode, message, null);
  }

  public ApplicationException(String errorId, int exitCode, String message, Throwable cause) {
    super(message, cause);
    this.errorId = errorId;
    this.exitCode = exitCode;
  }

  public String getErrorId() {
    return errorId;
  }

  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }

  public int getExitCode() {
    return exitCode;
  }

}
