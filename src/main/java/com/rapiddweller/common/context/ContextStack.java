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
package com.rapiddweller.common.context;

import com.rapiddweller.common.Context;

/**
 * A Stack of Contexts, querying for items from top to bottom, 
 * setting and removing items only on the top.
 * @since 0.3.0
 * @author Volker Bergmann
 */
public interface ContextStack extends Context {
    void push(Context context);
    Context pop();
}
