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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.rapiddweller.common.converter.FormatHolder;
import com.rapiddweller.common.converter.ToStringConverter;

/**
 * Formats a Composite as a String.
 * Created: 14.03.2008 22:47:57
 * @author Volker Bergmann
 */
public class CompositeFormatter extends FormatHolder {
    
	private static final String INDENT_DELTA = "    ";
    
	boolean flat;
	boolean renderNames;

    public CompositeFormatter() {
    	this(true, true);
    }
    
    public CompositeFormatter(boolean flat, boolean renderNames) {
    	super("[null]");
    	this.flat = flat;
    	this.renderNames = renderNames;
    }
    
	public boolean isFlat() {
		return flat;
	}
	
	public void setFlat(boolean flat) {
		this.flat = flat;
	}
	
    // interface -------------------------------------------------------------------------------------------------------

   	public String render(String head, Composite composite, String tail) {
        if (flat)
            return renderFlat(head, composite, tail);
        else
            return renderHierarchical(head, composite, tail);
    }
    
    public String renderHierarchical(String head, Composite composite, String tail, String indent) {
        return head + renderComponentsHierarchical(composite, indent) + tail;
    }
    
    // private helpers -------------------------------------------------------------------------------------------------
    
	private String renderFlat(String head, Composite composite, String tail) {
        return head + renderComponentsFlat(composite) + tail;
    }
    
    private String renderHierarchical(String head, Composite composite, String tail) {
        return renderHierarchical(head, composite, tail, "");
    }
    
    private String renderComponentsFlat(Composite composite) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> components = composite.getComponents();
        Iterator<Map.Entry<String, Object>> iterator = components.entrySet().iterator();
        if (iterator.hasNext())
            renderComponent(builder, "", iterator.next());
        while (iterator.hasNext()) {
            builder.append(", ");
            renderComponent(builder, "", iterator.next());
        }
        return builder.toString();
    }

    private String renderComponentsHierarchical(Composite composite, String indent) {
        String lineSeparator = SystemInfo.getLineSeparator();
        StringBuilder builder = new StringBuilder();
        indent += INDENT_DELTA;
        Map<String, Object> components = composite.getComponents();
        for (Map.Entry<String, Object> stringObjectEntry : components.entrySet()) {
            builder.append(lineSeparator);
            renderComponent(builder, indent, stringObjectEntry);
        }
        indent = indent.substring(0, indent.length() - INDENT_DELTA.length());
        if (builder.length() > 1)
            builder.append(lineSeparator);
        return builder.toString();
    }

    void renderComponent(StringBuilder builder, String indent, Map.Entry<String, ?> component) {
        builder.append(indent);
        if (renderNames)
            builder.append(component.getKey()).append('=');
        Object value = component.getValue();
        if (value == null) {
            value = nullString;
        } else if (value instanceof Date) {
            Date date = (Date) value;
            if (TimeUtil.isMidnight(date))
                value = new SimpleDateFormat(datePattern).format((Date) value);
            else
                value = new SimpleDateFormat(dateTimePattern).format((Date) value);
        } else if (value instanceof Composite) {
            value = render("[", (Composite) value, "]") ;
        } else if (value.getClass().isArray()) {
        	value = "[" + ToStringConverter.convert(value, "null") + "]";
        } else {
            value = ToStringConverter.convert(value, "null");
        }
        builder.append(value);
    }

}
