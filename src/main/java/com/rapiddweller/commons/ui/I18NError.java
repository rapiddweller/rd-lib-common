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
package com.rapiddweller.commons.ui;

/**
 * Error with localizable message.
 * Created at 14.12.2008 13:23:51
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class I18NError extends RuntimeException {
	
	private static final long serialVersionUID = -7876200178254927951L;

	private Object[] parameters;

	public I18NError() {
		super();
	}

	public I18NError(String code) {
		super(code);
	}

	public I18NError(Throwable cause) {
		super(cause);
	}

	public I18NError(String code, Throwable cause, Object... parameters) {
		super(code, cause);
		this.parameters = parameters;
	}
	
	public String renderMessage(I18NSupport i18n) {
		String message = getMessage();
		return renderMessage(message, i18n, parameters);
	}

	public static String renderMessage(String message, I18NSupport i18n, Object... parameters) {
		return i18n.format("error." + message, parameters);
	}
	
}
