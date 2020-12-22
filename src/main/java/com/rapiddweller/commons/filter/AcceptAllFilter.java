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

package com.rapiddweller.commons.filter;

import com.rapiddweller.commons.Filter;

/**
 * {@link Filter} implementation which accepts any argument.<br><br>
 * Created: 25 Jun 2020 18:02:29
 * @since 1.0.12
 * @author Volker Bergmann
 * @param <E> 
 */

public class AcceptAllFilter<E> implements Filter<E> {

	@Override
	public boolean accept(E candidate) {
		return true;
	}

}
