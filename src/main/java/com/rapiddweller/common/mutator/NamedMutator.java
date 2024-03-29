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

package com.rapiddweller.common.mutator;

import com.rapiddweller.common.Mutator;
import com.rapiddweller.common.Named;

/**
 * Mutator class to which a name is assigned.
 * Created at 04.11.2008 09:18:34
 *
 * @author Volker Bergmann
 * @since 0.4.6
 */
public interface NamedMutator extends Named, Mutator {
}
