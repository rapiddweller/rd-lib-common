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
package com.rapiddweller.commons;

import java.io.Closeable;
import java.io.IOException;

/**
 * Abstraction of a document writer.
 * Created: 06.06.2007 19:25:43
 * @param <E> the type of objects to persist
 * @author Volker Bergmann
 */
public interface DocumentWriter<E> extends Closeable {
    void setVariable(String name, Object value);
    void writeElement(E element) throws IOException;
}
