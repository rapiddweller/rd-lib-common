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

package com.rapiddweller.commons.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.rapiddweller.commons.MathUtil;

/**
 * Provides a ring buffer for double values.<br><br>
 * Created: 25.11.2017 19:27:12
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class DoubleRingBuffer {
	
	private double[] buffer;
	private int sampleCount;
	private int cursor;
	private boolean filled;
	private double lastValue;
	
	public DoubleRingBuffer(int capacity) {
		this.buffer = new double[capacity];
		Arrays.fill(this.buffer, Double.NaN);
		this.sampleCount = 0;
		this.cursor = 0;
		this.filled = false;
	}
	
	public int getSampleCount() {
		return sampleCount;
	}
	
	public int getCapacity() {
		return buffer.length;
	}
	
	public int size() {
		return buffer.length;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	public void add(double value) {
		lastValue = value;
		this.sampleCount++;
		this.buffer[cursor++] = value;
		if (cursor == buffer.length) {
			filled = true;
			cursor = 0;
		}
	}
	
	public double last() {
		return lastValue;
	}
	
	public double first() {
		return this.buffer[cursor];
	}
	
	/** @return the minimum value in the buffer or NaN if the buffer is empty */
	public double min() {
		double min = buffer[0];
		int n = (filled ? buffer.length : cursor);
		for (int i = 1; i < n; i++)
			if (!Double.isNaN(buffer[i]) && buffer[i] < min)
				min = buffer[i];
		return min;
	}
	
	/** @return the maximum value in the buffer or NaN if the buffer is empty */
	public double max() {
		double max = buffer[0];
		int n = (filled ? buffer.length : cursor);
		for (int i = 1; i < n; i++)
			if (!Double.isNaN(buffer[i]) && buffer[i] > max)
				max = buffer[i];
		return max;
	}

	public double average() {
		boolean contentFound = false;
		double sum = 0.;
		int count = 0;
		for (double d : buffer) {
			if (!Double.isNaN(d)) {
				contentFound = true;
				sum += d;
				count++;
			}
		}
		return (contentFound ? sum / count : Double.NaN);
	}

	public double median() {
		List<Double> list = new ArrayList<Double>();
		for (double d : buffer)
			if (!Double.isNaN(d))
				list.add(d);
		if (list.isEmpty())
			return Double.NaN;
		Collections.sort(list);
		return list.get(list.size() / 2);
	}

	public double sum() {
		double sum = 0.;
		for (double d : buffer)
			if (!Double.isNaN(d))
				sum += d;
		return sum;
	}
	
	public double[] getContent() {
		return buffer;
	}
	
	public double correctedStandardDeviation() {
		if (!filled)
			return Double.NaN;
		return MathUtil.correctedStandardDeviation(buffer);
	}

	public double standardDeviation() {
		if (!filled)
			return Double.NaN;
		return MathUtil.standardDeviation(buffer);
	}

	public double variance() { 
		if (!filled)
			return Double.NaN;
		return MathUtil.variance(buffer);
	}

}
