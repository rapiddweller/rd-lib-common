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

package com.rapiddweller.common.version;

import java.io.Serializable;

/**
 * Abstract super type for all version number components, e.g. 0-9 or alpha/beta/RC.
 * Created at 22.12.2008 16:31:02
 *
 * @author Volker Bergmann
 * @since 0.5.7
 */
public abstract class VersionNumberComponent implements Comparable<VersionNumberComponent>, Serializable {

  private static final long serialVersionUID = -916818799515194470L;

}
