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

/**
 * Indicates a failure in establishing a connection.
 * Created at 03.05.2008 09:27:34
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class ConnectFailedException extends Exception {

	private static final long serialVersionUID = -8060960690508842962L;

	public ConnectFailedException() {
		super();
	}

	public ConnectFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectFailedException(String message) {
		super(message);
	}

	public ConnectFailedException(Throwable cause) {
		super(cause);
	}
}
