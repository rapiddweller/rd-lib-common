/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.ExceptionUtil;
import com.rapiddweller.common.TextFileLocation;

import static com.rapiddweller.common.StringUtil.isEmpty;

/**
 * Represents an error that occurred when executing a script.<br/><br/>
 * Created: 17.12.2021 19:32:20
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ScriptException extends ApplicationException {

  private final String scriptText;
  private TextFileLocation location;

  public ScriptException(String message, Throwable cause) {
    this(message, cause, null, null);
  }

  public ScriptException(String message, Throwable cause, String errorId, String scriptText) {
    this(message, cause, errorId, scriptText, null);
  }

  public ScriptException(String message, Throwable cause, String errorId, String scriptText, TextFileLocation location) {
    super(message, cause, errorId, ExitCodes.SYNTAX_ERROR);
    this.scriptText = scriptText;
    this.location = location;
  }

  @Override
  public String getMessage() {
    return formatMessage(super.getMessage(), scriptText, location);
  }

  public TextFileLocation getLocation() {
    return location;
  }

  public void setLocation(TextFileLocation location) {
    this.location = location;
  }

  private static String formatMessage(String message, String scriptText, TextFileLocation location) {
    String result = ExceptionUtil.endWithDotSpace(message);
    if (!isEmpty(scriptText)) {
      result += "Script text: '" + scriptText + "'";
    }
    if (location != null) {
      result += " in ";
      if (location.getSystemId() != null) {
        result += " file " + location.getSystemId();
      }
      if (location.getStartLine() >= 0) {
        result += " line " + location.getStartLine();
        if (location.getStartColumn() >= 0) {
          result += " column " + location.getStartColumn();
        }
      }
    }
    return result;
  }

}
