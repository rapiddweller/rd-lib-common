/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Signals the failure of a database query.<br/><br/>
 * Created: 25.05.2022 20:07:32
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DBQueryFailed extends QueryFailed {

	public DBQueryFailed(String message) {
		super(message);
	}

	public DBQueryFailed(String message, Throwable cause) {
		super(message, cause);
	}

	public DBQueryFailed(String message, Throwable cause, String errorId) {
		super(message, cause, errorId);
	}

}
