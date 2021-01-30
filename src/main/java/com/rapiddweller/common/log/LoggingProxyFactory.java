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
package com.rapiddweller.common.log;

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Creates a proxy to a class which logs all invocations of parent interface methods.
 * Created at 17.09.2009 10:54:08
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class LoggingProxyFactory {
	
	private LoggingProxyFactory() {}

    public static <T> T createProxy(Class<T> interfaceClass, T realObject) {
		return createProxy(interfaceClass, realObject, Level.debug);
	}
	
    public static <T> T createProxy(Class<T> interfaceClass, T realObject, Level level) {
		return createProxy(interfaceClass, realObject, level, BeanUtil.getContextClassLoader());
	}
	
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> interfaceClass, T realObject, Level level, ClassLoader classLoader) {
		LoggingInvocationHandler handler = new LoggingInvocationHandler(realObject, level);
		return (T) Proxy.newProxyInstance(classLoader, new Class[] { interfaceClass }, handler);
	}
	
	protected static class LoggingInvocationHandler implements InvocationHandler {
		
		private final Object realObject;
		private final Logger logger;
		private final Level level;
		
        public LoggingInvocationHandler(Object realObject, Level level) {
        	this.realObject = realObject;
        	this.logger = LogManager.getLogger(realObject.getClass());
        	this.level = level;
        }

		@Override
		public Object invoke(Object object, Method method, Object[] args) {
			String message = method.getName() + '(' + ArrayFormat.format(args) + ')';
			switch (level) {
				case ignore: break;
				case trace: logger.trace(message); break;
				case debug: logger.debug(message); break;
				case info:  logger.info(message);  break;
				case warn:  logger.warn(message);  break;
				case error:
				case fatal:
					logger.error(message); break;
			}
	        return BeanUtil.invoke(realObject, method, args);
        }

	}
	
}
