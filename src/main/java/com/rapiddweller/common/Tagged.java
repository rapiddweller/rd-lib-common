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

import java.util.Set;

/**
 * Common interface for any classes that can have tags.
 * Created: 18.06.2012 10:17:25
 * @since 0.5.16
 * @author Volker Bergmann
 */
public interface Tagged {
	Set<String> getTags();
	boolean hasTag(String tag);
	void addTag(String tag);
	void removeTag(String tag);
}
