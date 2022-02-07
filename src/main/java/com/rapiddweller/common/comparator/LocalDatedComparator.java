/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.comparator;

import com.rapiddweller.common.LocalDated;

import java.util.Comparator;

/**
 * Compares instances of {@link LocalDated}.<br/><br/>
 * Created: 04.02.2022 17:09:31
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDatedComparator implements Comparator<LocalDated> {
	@Override
	public int compare(LocalDated o1, LocalDated o2) {
		return o1.getDate().compareTo(o2.getDate());
	}
}
