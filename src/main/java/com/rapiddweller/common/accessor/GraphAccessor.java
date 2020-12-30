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
package com.rapiddweller.common.accessor;

import com.rapiddweller.common.Accessor;

/**
 * Accesses object graphs by splitting a path names into tokens by a dot separator('.').
 * Created: 12.06.2007 18:29:19
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GraphAccessor implements Accessor {

    private final Accessor realAccessor;

    public GraphAccessor(String path) {
        int separatorIndex = path.lastIndexOf('.');
        if (separatorIndex < 0)
            realAccessor = new FeatureAccessor(path);
        else {
            realAccessor = new FetchingAccessor(
                    new GraphAccessor(path.substring(0, separatorIndex)),
                    new FeatureAccessor(path.substring(separatorIndex + 1))
            );
        }
    }
    
    
    // Accessor interface implementation -------------------------------------------------------------------------------

    @Override
	public Object getValue(Object o) {
        return realAccessor.getValue(o);
    }
    
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + realAccessor + ']';
    }
    
    
    // static utility methods ------------------------------------------------------------------------------------------
    
    public static Object getValue(String path, Object o) {
    	return new GraphAccessor(path).getValue(o);
    }
    
}
