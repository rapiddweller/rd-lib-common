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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * Provides convenience methods for the Java number types.
 * Created: 12.02.2005 18:24:47
 * @since 0.1
 * @author Volker Bergmann
 */
@SuppressWarnings("unchecked")
public class NumberUtil {
	
	// constants -------------------------------------------------------------------------------------------------------
	
	private static final Map<Class<? extends Number>, ? extends Number> MIN_VALUES = 
		CollectionUtil.buildMap(
				byte.class,       Byte.MIN_VALUE, 
				Byte.class,       Byte.MIN_VALUE, 
				short.class,      Short.MIN_VALUE, 
				Short.class,      Short.MIN_VALUE, 
				int.class,        Integer.MIN_VALUE, 
				Integer.class,    Integer.MIN_VALUE, 
				long.class,       Long.MIN_VALUE, 
				Long.class,       Long.MIN_VALUE, 
				float.class,      Float.MIN_VALUE, 
				Float.class,      Float.MIN_VALUE, 
				double.class,     - Double.MAX_VALUE, 
				Double.class,     - Double.MAX_VALUE, 
				BigDecimal.class, new BigDecimal(- Double.MAX_VALUE), 
				BigInteger.class, BigInteger.valueOf(Long.MIN_VALUE) 
		);
	
	private static final Map<Class<? extends Number>, ? extends Number> MAX_VALUES = 
		CollectionUtil.buildMap(
				byte.class,       Byte.MAX_VALUE, 
				Byte.class,       Byte.MAX_VALUE, 
				short.class,      Short.MAX_VALUE, 
				Short.class,      Short.MAX_VALUE, 
				int.class,        Integer.MAX_VALUE, 
				Integer.class,    Integer.MAX_VALUE, 
				long.class,       Long.MAX_VALUE, 
				Long.class,       Long.MAX_VALUE, 
				float.class,      Float.MAX_VALUE, 
				Float.class,      Float.MAX_VALUE, 
				double.class,     Double.MAX_VALUE, 
				Double.class,     Double.MAX_VALUE, 
				BigDecimal.class, new BigDecimal(Double.MAX_VALUE), 
				BigInteger.class, BigInteger.valueOf(Long.MAX_VALUE) 
		);
	
	public static <T extends Number> byte toByte(T value) {
		return (value != null ? value.byteValue() : 0);
    }
	
	public static <T extends Number> Integer toInteger(T value) {
	    return (value != null ? value.intValue() : null);
    }
	
	public static <T extends Number> Long toLong(T value) {
	    return (value != null ? value.longValue() : null);
    }
	
	public static <T extends Number> Float toFloat(T value) {
	    return (value != null ? value.floatValue() : null);
    }
	
	public static <T extends Number> Double toDouble(T value) {
	    return (value != null ? value.doubleValue() : null);
    }
	
	public static <T extends Number> BigDecimal toBigDecimal(T value) {
	    return (value != null ? BigDecimal.valueOf(value.doubleValue()) : null);
    }
	
	public static <T extends Number> BigInteger toBigInteger(T value) {
	    return (value != null ? BigInteger.valueOf(value.longValue()) : null);
    }
	
    public static String formatHex(int value, int digits) {
        String tmp = Integer.toHexString(value);
        if (tmp.length() > digits)
            return tmp.substring(tmp.length() - digits);
        return StringUtil.padLeft(tmp, digits, '0');
    }

    public static int bitsUsed(long value) {
        if (value < 0)
            return 64;
        for (int i = 62; i > 0; i--)
            if (((value >> i) & 1) == 1)
                return i + 1;
        return 1;
    }

    public static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++)
            result = (result << 8) - Byte.MIN_VALUE + bytes[i];
        return result;
    }

    public static String format(double number, int fractionDigits) {
        return numberFormat(fractionDigits, Locale.US).format(number);
    }

	public static NumberFormat numberFormat(int fractionDigits, Locale locale) {
		NumberFormat nf = NumberFormat.getInstance(locale);
        nf.setMinimumFractionDigits(fractionDigits);
        nf.setMaximumFractionDigits(fractionDigits);
		return nf;
	}
    
    public static boolean isLimited(Class<? extends Number> numberType) {
    	return (numberType != BigDecimal.class && numberType != BigInteger.class);
    }

    public static <T extends Number> T minValue(Class<T> numberType) {
    	Number value = MIN_VALUES.get(numberType);
    	if (value == null)
    		throw new IllegalArgumentException("Not a supported number type: " + numberType);
		return (T) value;
    }
    
    public static <T extends Number> T maxValue(Class<T> numberType) {
    	Number value = MAX_VALUES.get(numberType);
    	if (value == null)
    		throw new IllegalArgumentException("Not a supported number type: " + numberType);
		return (T) value;
    }
    
    public static <T extends Number> int totalDigits(Class<T> numberType) {
    	if (isLimited(numberType))
    		return 1 + (int) Math.log10(maxValue(numberType).doubleValue());
    	else
    		return 1 + (int) Math.log10(maxValue(double.class));
    }
    
}
