/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.ExceptionUtil;

import static com.rapiddweller.common.StringUtil.isEmpty;

/**
 * Represents an error that occurred when executing a script.<br/><br/>
 * Created: 17.12.2021 19:32:20
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ScriptException extends ApplicationException {

  public ScriptException(String message, Throwable cause) {
    this(message, cause, null, null);
  }

  public ScriptException(String message, Throwable cause, String errorId, String scriptText) {
    super(formatMessage(message, scriptText), cause, errorId, ExitCodes.SYNTAX_ERROR);
  }

  private static String formatMessage(String message, String scriptText) {
    String result = ExceptionUtil.endWithDotSpace(message);
    if (!isEmpty(scriptText)) {
      result += "Script text: '" + scriptText + "'";
    }
    return result;
  }

}
