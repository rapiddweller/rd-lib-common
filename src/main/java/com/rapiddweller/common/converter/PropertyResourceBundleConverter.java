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
package com.rapiddweller.common.converter;

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.LocaleUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Converts key strings to localized texts by using a ResourceBundle.
 * Created: 07.06.2007 07:48:35
 * @author Volker Bergmann
 */
public class PropertyResourceBundleConverter extends ThreadSafeConverter<String, String> {

    private final ResourceBundle bundle;

    public PropertyResourceBundleConverter(String baseName, Locale locale) {
    	super(String.class, String.class);
        ResourceBundle.Control control = new UTF8Control();
        bundle = PropertyResourceBundle.getBundle(baseName, locale, control);
    }

    @Override
	public String convert(String sourceValue) {
        return bundle.getString(sourceValue);
    }
    
    static class UTF8Control extends PropertyResourceBundle.Control {
    	
    	@Override
    	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
    	        throws IOException {
    		String bundleName = toBundleName(baseName, locale);
    		String resourceName = toResourceName(bundleName, "properties");
    		InputStream stream = IOUtil.getInputStreamForURI(resourceName, true);
			Charset utf8 = StandardCharsets.UTF_8;
			return new PropertyResourceBundle(new InputStreamReader(stream, utf8)); 
    	}
    	
    	@Override
    	public Locale getFallbackLocale(String baseName, Locale locale) {
    		Locale fallback = LocaleUtil.getFallbackLocale();
    	    return (fallback.equals(locale) ? null : fallback);
    	}
    }
    
}
