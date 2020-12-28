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
package com.rapiddweller.commons;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.io.IOException;

/**
 * Provides locale operations like determining the parent of a locale,
 * finding a locale by code, providing the characters of a locale
 * and a so-called 'fallback locale'.
 * Created: 26.09.2006 23:34:41
 * @author Volker Bergmann
 */
public final class LocaleUtil {

    /** The locale to use by default */
    private static final Locale FALLBACK_LOCALE = Locale.US;

    /** collects the special letters of locales as set of Characters, indexed by the locale. */
    private static Map<Locale, Set<Character>> specialLetters;

    /** Static initializer calls readConfigFile(). @see #readConfigFile() */
    static {
        readConfigFile();
    }

    // interface -------------------------------------------------------------------------------------------------------

    /**
     * Returns a set that contains all letters of the specified locale.
     * @param locale the locale of which the character set is requested
     * @return a set of characters that contains all letters of the specified locale
     * @exception UnsupportedOperationException if the locale is not supported
     */
    public static Set<Character> letters(Locale locale) {
        Set<Character> set = nullTolerantLetters(locale);
        if (set == null)
            throw new UnsupportedOperationException("Locale not supported: " + locale);
        return set;
    }

    /**
     * Determines a locale's parent, e.g. for a locale 'de_DE' it returns 'de'.
     * @param locale the locale of which to determine the parent
     * @return the locale's parent, or null if the locale has no parent.
     */
    public static Locale parent(Locale locale) {
        String variant = locale.getVariant();
        if (!StringUtil.isEmpty(variant)){
            if (variant.contains("_")) {
                String[] variantPath = StringUtil.tokenize(variant, '_');
                variant = ArrayFormat.formatPart("_", 0, variantPath.length - 1, variantPath);
                return new Locale(locale.getLanguage(), locale.getCountry(), variant);
            } else
                return new Locale(locale.getLanguage(), locale.getCountry());
        } else if (!StringUtil.isEmpty(locale.getCountry()))
            return new Locale(locale.getLanguage());
        else
            return null;
    }

    /**
     * Returns the fallback locale.
     * This differs from the default locale for cases in which it is desirable to
     * restrict the used character ranges to an unproblematic minimum.
     * @return the fallback locale.
     */
    public static Locale getFallbackLocale() {
        return FALLBACK_LOCALE;
    }

    /**
     * Maps the locale code to a locale, e.g. de_DE to Locale.GERMANY.
     * @param code the locale colde to map
     * @return a locale instance the represents the code
     */
    public static Locale getLocale(String code) {
        if (StringUtil.isEmpty(code))
            throw new IllegalArgumentException("code is empty");
        String[] path = StringUtil.tokenize(code, '_');
        switch (path.length) {
            case 1 : return new Locale(path[0]);
            case 2 : return new Locale(path[0], path[1]);
            case 3 : return new Locale(path[0], path[1], path[2]);
            default : return new Locale(path[0], path[1], ArrayFormat.formatPart("_", 2, path.length - 2, path));
        }
    }

    public static String availableLocaleUrl(String baseName, Locale locale, String suffix) {
        String localeString = locale.toString();
        do {
            String url = baseName;
            if (!StringUtil.isEmpty(localeString))
                url += "_" + localeString;
            url += suffix;
            if (IOUtil.isURIAvailable(url))
                return url;
            localeString = reduceLocaleString(localeString);
        } while (localeString != null);
        return null;
    }

    private static String reduceLocaleString(String localeString) {
        if (localeString == null || localeString.length() == 0)
            return null;
        int separatorIndex = localeString.lastIndexOf('_');
        if (separatorIndex < 0)
            return "";
        return localeString.substring(0, separatorIndex);
    }
    
	public static String getDefaultCountryCode() {
		String result = Locale.getDefault().getCountry();
		if (StringUtil.isEmpty(result))
			result = getFallbackLocale().getCountry();
		return result;
	}
	
	public static Locale language(Locale locale) {
		Locale result = locale;
		Locale parent;
		while ((parent = parent(result)) != null)
			result = parent;
		return result;
	}

    
    // private helpers -------------------------------------------------------------------------------------------------

    /**
     * @param locale the locale of which to get the letters.
     * @return the letters of a locale, null if the locale is unknown.
     */
    private static Set<Character> nullTolerantLetters(Locale locale) {
        if (locale == null)
            return null;
        Set<Character> set = specialLetters.get(locale);
        if (set != null)
            return set;
        Locale parent = locale;
        while ((parent = parent(parent)) != null) {
            set = specialLetters.get(parent);
            if (set != null)
                return set;
        }
        return latinSet();
    }

    /**
     * Reads the config file com/rapiddweller/commons/special-letters.properties from the file system or the path
     * and initializes the internal specialLetters map.
     */
    private static void readConfigFile() {
        try {
            specialLetters = new HashMap<>();
            Map<String, String> properties = IOUtil.readProperties(
            		"com/rapiddweller/commons/special-letters.properties", Encodings.UTF_8);
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                Locale locale = getLocale(String.valueOf(entry.getKey()));
                String specialChars = String.valueOf(entry.getValue());
                Set<Character> charSet = latinSet();
                for (int i = 0; i < specialChars.length(); i++)
                    charSet.add(specialChars.charAt(i));
                specialLetters.put(locale, charSet);
            }
        } catch (IOException e) {
            throw new ConfigurationError("Setup file for locale-specific letters is missing", e);
        }
    }

    private static Set<Character> latinSet() {
        return new CharSet('A', 'Z').addRange('a', 'z').getSet();
    }

	public static void runInLocale(Locale locale, Runnable task) {
	    Locale realLocale = Locale.getDefault();
	    try {
	    	Locale.setDefault(locale);
	    	task.run();
	    } finally {
	    	Locale.setDefault(realLocale);
	    }
    }

	public static <T> T callInLocale(Locale locale, Callable<T> task) throws Exception {
	    Locale realLocale = Locale.getDefault();
	    try {
	    	Locale.setDefault(locale);
	    	return task.call();
	    } finally {
	    	Locale.setDefault(realLocale);
	    }
    }

	public static char getDecimalSeparator() {
		return new DecimalFormat().getDecimalFormatSymbols().getDecimalSeparator();
    }

}
