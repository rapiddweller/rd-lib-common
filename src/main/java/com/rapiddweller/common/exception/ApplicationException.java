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
  private int exitCode;
  private String help;

  public ApplicationException(String message, String errorId, int exitCode) {
    this(message, null, errorId, exitCode);
  }

  public ApplicationException(String message, Throwable cause, String errorId, int exitCode) {
    super(message, cause);
    this.errorId = errorId;
    this.exitCode = exitCode;
  }

  public ApplicationException withHelp(String help) {
    this.help = help;
    return this;
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

  public void setExitCode(int exitCode) {
    this.exitCode = exitCode;
  }

  public String getHelp() {
    if (help != null) {
      return help;
    } else if (getCause() instanceof ApplicationException) {
      return ((ApplicationException) getCause()).getHelp();
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    return format(errorId, getMessage());
  }

  public static String format(String errorId, String message) {
    return "Error" + (errorId != null ? " " + errorId : "") + (message != null ? ": " + message : "");
  }

}
