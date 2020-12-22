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
package com.rapiddweller.commons.iterator;

import com.rapiddweller.commons.HeavyweightIterable;

/**
 * Special sub type of {@link Iterable} which creates {@link TabularIterator}s.
 * Created: 26.01.2012 17:59:58
 * @since 0.5.14
 * @author Volker Bergmann
 */
public interface TabularIterable extends HeavyweightIterable<Object[]> {
	@Override
	TabularIterator iterator();
}
