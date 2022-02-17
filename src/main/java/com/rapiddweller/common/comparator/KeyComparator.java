/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.comparator;

import java.util.Comparator;
import java.util.Map;

/**
 * Comparator for {@link java.util.Map} keys.<br/><br/>
 * Created: 16.02.2022 11:37:29
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class KeyComparator<E> implements Comparator<Map.Entry<E, ?>> {

	private final Comparator<E> realComparator;

	public KeyComparator(Comparator<E> realComparator) {
		this.realComparator = realComparator;
	}

	@Override
	public int compare(Map.Entry<E, ?> e1, Map.Entry<E, ?> e2) {
		return realComparator.compare(e1.getKey(), e2.getKey());
	}

}
