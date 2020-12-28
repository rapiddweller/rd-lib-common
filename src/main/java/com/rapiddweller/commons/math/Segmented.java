/* (c) Copyright 2020 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.commons.math;

/**
 * Represents a segmented number series.<br/><br/>
 * Created: 27.02.2020 19:07:11
 * @since 1.0.12
 * @author Volker Bergmann
 */
public class Segmented {
	
	private final int segment;
    private final int offset;

	public Segmented(int segment, int offset) {
		this.segment = segment;
		this.offset = offset;
	}
	
	public int getSegment() {
		return segment;
	}
	
	public int getOffset() {
		return offset;
	}
	
	@Override
	public String toString() {
		return segment + "-" + offset;
	}

}
