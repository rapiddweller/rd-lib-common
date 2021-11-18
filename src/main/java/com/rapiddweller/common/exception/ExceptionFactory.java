/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.ObjectNotFoundException;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.cli.IllegaCommandLineArgumentException;
import com.rapiddweller.common.cli.IllegalCommandLineOptionException;
import com.rapiddweller.common.cli.MissingCommandLineArgumentException;
import com.rapiddweller.common.cli.MissingCommandLineOptionValueException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Creates {@link ApplicationException}s.<br/><br/>
 * Created: 18.11.2021 05:59:27
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ExceptionFactory {

  private static ExceptionFactory instance;

  public static ExceptionFactory getInstance() {
    if (instance == null) {
      instance = new ExceptionFactory();
    }
    return instance;
  }

  public static void setInstance(ExceptionFactory factory) {
    instance = factory;
  }

  public SyntaxError syntaxError(String uri, int lineNumber, int columnNumber, Throwable cause) {
    return new SyntaxError(StringUtil.removeSuffixIfPresent(".", cause.getMessage()), uri);
  }

  public SyntaxError syntaxError(String uri, int lineNumber, int columnNumber, String message) {
    return new SyntaxError(StringUtil.removeSuffixIfPresent(".", message), uri);
  }

  public FileResourceNotFoundException fileNotFound(String uri, FileNotFoundException cause) {
    return new FileResourceNotFoundException(null, "File not found: '" + uri + "'.", cause);
  }

  public IllegalCommandLineOptionException illegalCommandLineOption(String message) {
    return new IllegalCommandLineOptionException(message);
  }

  public MissingCommandLineArgumentException missingCommandLineArgument() {
    return new MissingCommandLineArgumentException("Missing command argument.");
  }

  public IllegaCommandLineArgumentException illegalCommandLineArgument(String message) {
    return new IllegaCommandLineArgumentException(message);
  }

  public MissingCommandLineOptionValueException missingCommandLineOptionValue(String name) {
    return new MissingCommandLineOptionValueException("Value missing for command line option: '" + name + "'.");
  }

  public ProgrammerValueUndefinedError programmerUndefinedValue(String message) {
    return new ProgrammerValueUndefinedError(message);
  }

  public ProgrammerStateError programmerStateError(String message) {
    return programmerStateError(message, null);
  }

  public ProgrammerStateError programmerStateError(String message, Throwable cause) {
    return new ProgrammerStateError(message, cause);
  }

  public ProgrammerUnsupportedError programmerUnsupported(String message) {
    return new ProgrammerUnsupportedError(message);
  }

  public ProgrammerConfigError programmerConfig(String message, Throwable cause) {
    return new ProgrammerConfigError(message, cause);
  }

  public ObjectNotFoundException objectNotFound(String message) {
    return new ObjectNotFoundException(message);
  }

  public FileAccessException fileAccessException(String message, IOException cause) {
    return new FileAccessException(message, cause);
  }

  public ConnectFailedException systemNotAvailable(String message, Throwable cause) {
    return new ConnectFailedException(message, cause);
  }

  public UnexpectedQueryResultException unexpectedQueryResult(String message, Throwable cause) {
    return new UnexpectedQueryResultException(message, cause);
  }

}
