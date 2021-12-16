/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Enumerates source types of {@link SyntaxError}s.<br/><br/>
 * Created: 26.11.2021 18:54:56
 * @author Volker Bergmann
 * @since 2.0.0
 */
public enum SourceType {
  URI,
  TEXT,
  MISSING,
  XML_ELEMENT,
  XML_ATTRIBUTE
}