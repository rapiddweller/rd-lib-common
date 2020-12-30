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
package com.rapiddweller.common.xml;

/**
 * Represents an alias for an XML namespace like xmlns:xs="http://www.w3.org/2001/XMLSchema".
 * Created: 23.03.2008 08:49:44
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class NamespaceAlias {
    
    private final String aliasName;
    private final String namespaceURI;
    
    public NamespaceAlias(String aliasName, String fullName) {
        super();
        this.aliasName = aliasName;
        this.namespaceURI = fullName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public String getNamespaceURI() {
        return namespaceURI;
    }
    
}
