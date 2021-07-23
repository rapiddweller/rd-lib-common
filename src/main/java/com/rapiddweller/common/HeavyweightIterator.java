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

package com.rapiddweller.common;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Models a heavyweight Iterator, that means an Iterator that needs to be closed,
 * e.g. for freeing allocated resources.
 * Created: 16.06.2007 10:21:25
 *
 * @param <E> the type of objects to iterate
 * @author Volker Bergmann
 */
public interface HeavyweightIterator<E> extends Iterator<E>, Closeable {
}
