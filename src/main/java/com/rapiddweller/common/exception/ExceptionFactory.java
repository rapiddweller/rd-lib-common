/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.AssertionError;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.DeploymentError;
import com.rapiddweller.common.ImportFailedException;
import com.rapiddweller.common.ObjectNotFoundException;
import com.rapiddweller.common.OperationFailed;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.cli.CLIIllegalArgumentException;
import com.rapiddweller.common.cli.CLIIllegalOptionException;
import com.rapiddweller.common.cli.CLIIllegalOptionValueException;
import com.rapiddweller.common.cli.CLIMissingArgumentException;
import com.rapiddweller.common.cli.CLIMissingOptionValueException;
import com.rapiddweller.common.file.FileAccessException;
import com.rapiddweller.common.file.FileCreationFailed;
import com.rapiddweller.common.file.FileResourceNotFoundException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.io.FileNotFoundException;

/**
 * Creates {@link ApplicationException}s.<br/><br/>
 * Created: 18.11.2021 05:59:27
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ExceptionFactory {

  public static final String MISSING_ATTRIBUTE_FMT = "Attribute '%s' is missing in <%s>";

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

  public FileResourceNotFoundException fileNotFound(String uri, FileNotFoundException cause) {
    return new FileResourceNotFoundException("File not found: '" + uri + "'.", cause, null);
  }

  public ObjectNotFoundException sheetNotFound(String uri, String sheetName) {
    uri = StringUtil.lastToken(uri, '/');
    String message = "Sheet not found: '" + sheetName + "'";
    if (!StringUtil.isEmpty(uri)) {
      message += " in '" + uri + "'";
    } else {
      message += ".";
    }
    return new ObjectNotFoundException(message);
  }

  public FileCreationFailed fileCreationFailed(String message, Exception cause) {
    return new FileCreationFailed(message, cause);
  }

  public CLIIllegalOptionException illegalCommandLineOption(String optionName) {
    return new CLIIllegalOptionException(optionName);
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

  public CLIIllegalOptionValueException illegalCommandLineOptionValue(String name, String value) {
    return new CLIIllegalOptionValueException(name, value);
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
    return illegalArgument(message, cause, null);
  }

  public IllegalArgumentError illegalArgument(String message, Throwable cause, String errorId) {
    return new IllegalArgumentError(message, cause, errorId);
  }

  public ConnectFailedException connectFailed(String message, Throwable cause) {
    return new ConnectFailedException(message, cause);
  }

  public ServiceUnavailableException serviceUnavailable(String s, Throwable e) {
    return new ServiceUnavailableException(s, e);
  }

  public InternalErrorException internalError(String message, Throwable cause) {
    return new InternalErrorException(message, cause);
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

  public MutationFailed mutationFailed(String message, Throwable cause) {
    return new MutationFailed(message, cause);
  }

  public AccessFailed accessFailed(String message, Throwable cause) {
    return new AccessFailed(message, cause);
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

  public CloningFailed cloningFailed(String message, Throwable cause) {
    return new CloningFailed(message, cause);
  }

  public OperationCancelledException operationCancelled(String message) {
    return new OperationCancelledException(message);
  }

  public OperationFailed operationFailed(String message, Throwable cause) {
    return operationFailed(message, cause, null, ExitCodes.MISCELLANEOUS_ERROR);
  }

  public OperationFailed operationFailed(String message, Throwable cause, String errorId, int exitCode) {
    return new OperationFailed(message, cause, errorId, exitCode);
  }

  public QueryFailed queryFailed(String message, Throwable cause) {
    return new QueryFailed(message, cause);
  }

  public ComponentInitializationFailure componentInitializationFailed(String componentName, Throwable cause) {
    return new ComponentInitializationFailure(componentName, cause);
  }

  public IllegalOperationError illegalOperation(String message) {
    return new IllegalOperationError(message);
  }

  public ServicePermissionDenied servicePermissionDenied(String message) {
    throw new ServicePermissionDenied(message);
  }

  public IllegalAccess illegalAccess(String message) {
    return new IllegalAccess(message);
  }

  public SyntaxError missingInfo(String message) {
    return SyntaxError.forMissingInfo(message);
  }

  public SyntaxError syntaxErrorForText(String message, String text) {
    return SyntaxError.forText(message, null, null, text);
  }

  public SyntaxError syntaxErrorForText(String message, String text, int line, int column) {
    return SyntaxError.forText(message, null, null, text, line, column);
  }

  public SyntaxError syntaxErrorForText(String message, Throwable cause, String text, int line, int column) {
    return SyntaxError.forText(message, cause, null, text, line, column);
  }

  public SyntaxError syntaxErrorForText(String message, Throwable cause, String text) {
    return SyntaxError.forText(message + ". Text: '" + text + "'", cause, null, text);
  }

  public SyntaxError syntaxErrorForUri(String message, Throwable cause, String uri) {
    return SyntaxError.forXmlDocument(message, cause, uri, -1, -1);
  }

  public SyntaxError syntaxErrorForXmlDocument(String message, Throwable cause, String uri) {
    return SyntaxError.forXmlDocument(message, cause, uri, -1, -1);
  }

  public SyntaxError syntaxErrorForXmlElement(String message, Element element) {
    return syntaxErrorForXmlElement(message, null, null, element);
  }

  public SyntaxError syntaxErrorForXmlElement(String message, Throwable cause, String errorId, Element element) {
    return SyntaxError.forXmlElement(message, cause, errorId, element);
  }

  public SyntaxError illegalXmlAttributeValue(String message, Throwable cause, String errorId, Attr attribute) {
    if (errorId == null) {
      errorId = CommonErrorIds.XML_ATTR_ILLEGAL_VALUE;
    }
    return SyntaxError.forXmlAttribute(message, cause, errorId, attribute);
  }

  public SyntaxError illegalXmlAttributeName(String message, Throwable cause, String errorId, Attr attribute, Object source) {
    if (message == null) {
      message = "Illegal XML attribute: " + attribute.getOwnerElement().getNodeName() + "." + attribute.getName();
    }
    if (errorId == null) {
      errorId = CommonErrorIds.XML_ATTR_ILLEGAL_NAME;
    }
    return SyntaxError.forXmlAttribute(message, cause, errorId, attribute);
  }

  public SyntaxError missingXmlAttribute(String message, String errorId, String attributeName, Element owner) {
    if (message == null) {
      message = String.format(MISSING_ATTRIBUTE_FMT, attributeName, owner.getNodeName());
    }
    if (errorId == null) {
      errorId = CommonErrorIds.XML_ATTR_MISSING;
    }
    return syntaxErrorForXmlElement(message, null, errorId, owner);
  }

public SyntaxError illegalXmlElementText(String message, String errorId, String textContent, Element owner) {
  if (message == null) {
    message = "Element <" + owner.getNodeName() + "> has illegal text content: '" + textContent + "'";
  }
  if (errorId == null) {
    errorId = CommonErrorIds.XML_ILLEGAL_TEXT_CONTENT;
  }
  return syntaxErrorForXmlElement(message, null, errorId, owner);
}

  public ParseException parsingError(String message) {
    return parsingError(message, null);
  }

  public ParseException parsingError(String message, Throwable cause) {
    return new ParseException(message, cause, null, null, null, null);
  }

  public ApplicationException outOfMemory(Throwable e) {
    return new ApplicationException("Out of memory", e, CommonErrorIds.OUT_OF_MEMORY, ExitCodes.MISCELLANEOUS_ERROR);
  }

  public ScriptException scriptEvaluationFailed(String scriptText, Throwable cause) {
    return new ScriptException(cause.getMessage(), cause, CommonErrorIds.SCRIPT_EXCEPTION, scriptText);
  }

}
