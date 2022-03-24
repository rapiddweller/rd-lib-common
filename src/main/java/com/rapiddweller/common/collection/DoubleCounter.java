/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.collection;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link ObjectCounter} that counts the frequency of double values.<br/><br/>
 * Created: 19.03.2022 09:03:01
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DoubleCounter extends ObjectCounter<Double> {
	public double average() {
		double sum = 0;
		int totalCount = 0;
		for (Map.Entry<Double, AtomicInteger> entry : instances.entrySet()) {
			int count = entry.getValue().get();
			totalCount += count;
			sum += count * entry.getKey();
		}
		return (totalCount != 0 ? sum / totalCount : Double.NaN);
	}
}
