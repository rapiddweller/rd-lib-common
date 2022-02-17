/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.filter;

import com.rapiddweller.common.Filter;

/**
 * Adapter pattern implementation for the {@link Filter} interface.<br/><br/>
 * Created: 10.02.2022 22:18:18
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class FilterAdapter<S, T> implements Filter<S> {

	private final Filter<T> realFilter;

	protected FilterAdapter(Filter<T> realFilter) {
		this.realFilter = realFilter;
	}

	@Override
	public boolean accept(S candidate) {
		return realFilter.accept(map(candidate));
	}

	protected abstract T map(S source);

}
