/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common;

/**
 * Represents XML location data.<br/><br/>
 * Created: 16.12.2021 14:00:53
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class TextFileLocation {

  public static final String LOCATION_DATA_KEY = "locationDataKey";

  private final String systemId;
  private final int startLine;
  private final int startColumn;
  private final int endLine;
  private final int endColumn;

  public TextFileLocation(String systemId, int startLine,
                          int startColumn, int endLine, int endColumn) {
    this.systemId = systemId;
    this.startLine = startLine;
    this.startColumn = startColumn;
    this.endLine = endLine;
    this.endColumn = endColumn;
  }

  public String getSystemId() {
    return systemId;
  }

  public int getStartLine() {
    return startLine;
  }

  public int getStartColumn() {
    return startColumn;
  }

  public int getEndLine() {
    return endLine;
  }

  public int getEndColumn() {
    return endColumn;
  }

  @Override
  public String toString() {
    return getSystemId() + "[line " + startLine + ":" + startColumn + " to line " + endLine + ":" + endColumn + "]";
  }
}
