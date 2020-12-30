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

import java.util.Map;
import java.util.Set;

/**
 * Abstraction of a context that provides named items which can be set and retrieved.
 * Created: 23.08.2007 08:32:53
 * @since 0.3
 * @author Volker Bergmann
 */
public interface Context {
    Object get(String key);
    void set(String key, Object value);
    void remove(String key);
    Set<String> keySet();
	Set<Map.Entry<String, Object>> entrySet();
	boolean contains(String key);
}
