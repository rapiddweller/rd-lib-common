/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

import com.rapiddweller.common.OperationFailed;

/**
 * Signals that the initialization of a component failed.<br/><br/>
 * Created: 20.11.2021 19:03:38
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ComponentInitializationFailure extends OperationFailed {

  private String componentName;

  public ComponentInitializationFailure(String message) {
    this(message, null);
  }

  public ComponentInitializationFailure(String componentName, Throwable cause) {
    this(null, componentName, cause);
  }

  public ComponentInitializationFailure(String errorId, String componentName, Throwable cause) {
    super("Component initialization failed for " + componentName, cause, errorId, ExitCodes.INTERNAL_SOFTWARE_ERROR);
    this.componentName = componentName;
  }

  public String getComponentName() {
    return componentName;
  }

}
