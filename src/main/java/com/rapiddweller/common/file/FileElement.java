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
package com.rapiddweller.common.file;

import com.rapiddweller.common.Element;
import com.rapiddweller.common.Visitor;
import com.rapiddweller.common.visitor.WrapperElement;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * Element implementation (of the Visitor Design Pattern) for a File.
 * Created: 04.02.2007 08:10:05
 * @author Volker Bergmann
 */
public class FileElement extends WrapperElement<File> {

    public FileElement(File file) {
        super(file);
    }

    @Override
    protected Collection<Element<File>> getChildren(Visitor<File> visitor) {
        if (wrappedObject.isFile())
            return new ArrayList<>();
        File[] content = wrappedObject.listFiles();
        if (content == null)
        	content = new File[0];
        List<Element<File>> children = new ArrayList<>(content.length);
        for (File file : content)
            children.add(new FileElement(file));
        return children;
    }


}
