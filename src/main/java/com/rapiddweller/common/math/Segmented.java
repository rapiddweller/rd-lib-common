/* (c) Copyright 2020 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.math;

/**
 * Represents a segmented number series.<br/><br/>
 * Created: 27.02.2020 19:07:11
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class Segmented {

  private final int segment;
  private final int offset;

  /**
   * Instantiates a new Segmented.
   *
   * @param segment the segment
   * @param offset  the offset
   */
  public Segmented(int segment, int offset) {
    this.segment = segment;
    this.offset = offset;
  }

  /**
   * Gets segment.
   *
   * @return the segment
   */
  public int getSegment() {
    return segment;
  }

  /**
   * Gets offset.
   *
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

  @Override
  public String toString() {
    return segment + "-" + offset;
  }

}
