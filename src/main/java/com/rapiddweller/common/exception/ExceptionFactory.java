/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.AssertionError;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.DeploymentError;
import com.rapiddweller.common.ImportFailedException;
import com.rapiddweller.common.ObjectNotFoundException;
import com.rapiddweller.common.OperationFailedException;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.cli.CLIIllegalArgumentException;
import com.rapiddweller.common.cli.CLIIllegalOptionException;
import com.rapiddweller.common.cli.CLIMissingArgumentException;
import com.rapiddweller.common.cli.CLIMissingOptionValueException;
import com.rapiddweller.common.file.FileAccessException;
import com.rapiddweller.common.file.FileCreationFailedException;
import com.rapiddweller.common.file.FileResourceNotFoundException;

import java.io.FileNotFoundException;

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

  public AssertionError assertionFailed(String message) {
    return new AssertionError(message);
  }

  public SyntaxError syntaxError(String message, Throwable cause) {
    return new SyntaxError(message, cause);
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

  public FileCreationFailedException fileCreationFailed(String message, Exception cause) {
    return new FileCreationFailedException(message, cause);
  }

  public CLIIllegalOptionException illegalCommandLineOption(String message) {
    return new CLIIllegalOptionException(message);
  }

  public CLIMissingArgumentException missingCommandLineArgument() {
    return new CLIMissingArgumentException("Missing command argument.");
  }

  public CLIIllegalArgumentException illegalCommandLineArgument(String message) {
    return new CLIIllegalArgumentException(message);
  }

  public CLIMissingOptionValueException missingCommandLineOptionValue(String name) {
    return new CLIMissingOptionValueException("Value missing for command line option: '" + name + "'.");
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

  public FileAccessException fileAccessException(String message, Throwable cause) {
    return new FileAccessException(message, cause);
  }

  public UnexpectedQueryResultException unexpectedQueryResult(String message, Throwable cause) {
    return new UnexpectedQueryResultException(message, cause);
  }

  public IllegalArgumentError illegalArgument(String message) {
    return illegalArgument(message, null);
  }

  public IllegalArgumentError illegalArgument(String message, Throwable cause) {
    return new IllegalArgumentError(message, cause);
  }

  public ConnectFailedException connectFailed(String message, Throwable cause) {
    return new ConnectFailedException(message, cause);
  }

  public ServiceUnavailableException serviceUnavailable(String s, Throwable e) {
    return new ServiceUnavailableException(s, e);
  }

  public InternalError internalError(String message, Throwable cause) {
    return new InternalError(message, cause);
  }

  public ServiceFailedException serviceFailed(String message, Throwable cause) {
    return new ServiceFailedException(message, cause);
  }

  public ConfigurationError configurationError(String message) {
    return configurationError(message, null);
  }

  public ConfigurationError configurationError(String message, Throwable cause) {
    return new ConfigurationError(message, cause);
  }

  public MutationFailedException mutationFailed(String message, Throwable cause) {
    return new MutationFailedException(message, cause);
  }

  public AccessFailedException accessFailed(String message, Throwable cause) {
    return new AccessFailedException(message, cause);
  }
  public ConversionException conversionFailed(String message, Throwable cause) {
    return new ConversionException(message, cause);
  }

  public ImportFailedException importFailed(String message, Throwable cause) {
    return new ImportFailedException(message, cause);
  }

  public DeploymentError deploymentFailed(String message, Throwable cause) {
    return new DeploymentError(message, cause);
  }

  public CloningFailedException cloningFailed(String message, Throwable cause) {
    return new CloningFailedException(message, cause);
  }

  public OperationCancelledException operationCancelled(String message) {
    return new OperationCancelledException(message);
  }

  public OperationFailedException operationFailed(String message, Throwable cause) {
    return new OperationFailedException(null, ExitCodes.MISCELLANEOUS_ERROR, message, cause);
  }

  public QueryFailedException queryFailed(String message, Throwable cause) {
    return new QueryFailedException(message, cause);
  }

  public ComponentInitializationFailedException componentInitializationFailed(String message, Throwable cause) {
    return new ComponentInitializationFailedException(message, cause);
  }

}
