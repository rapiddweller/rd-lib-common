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
package com.rapiddweller.commons.validator;

import com.rapiddweller.commons.Named;
import com.rapiddweller.commons.Validator;

/**
 * Combination of {@link Named} and {@link Validator} interfaces.
 * Created: 21.12.2011 07:32:40
 * @param <E> the object type to be validated
 * @since 0.5.14
 * @author Volker Bergmann
 */
public interface NamedValidator<E> extends Validator<E>, Named {

}
