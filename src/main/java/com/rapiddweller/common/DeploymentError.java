/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common;

/**
 * {@link ConfigurationError} sub class which indicates an incorrect deployment.
 * Created: 23.03.2011 11:50:42
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class DeploymentError extends ConfigurationError {

  private static final long serialVersionUID = 826096190591209925L;

  /**
   * Instantiates a new Deployment error.
   */
  public DeploymentError() {
    super();
  }

  /**
   * Instantiates a new Deployment error.
   *
   * @param message the message
   * @param cause   the cause
   */
  public DeploymentError(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new Deployment error.
   *
   * @param message the message
   */
  public DeploymentError(String message) {
    super(message);
  }

  /**
   * Instantiates a new Deployment error.
   *
   * @param cause the cause
   */
  public DeploymentError(Throwable cause) {
    super(cause);
  }

}
