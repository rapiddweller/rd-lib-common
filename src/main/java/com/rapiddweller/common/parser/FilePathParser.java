/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

import com.rapiddweller.common.FileUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Parses a file path.<br/><br/>
 * Created: 29.03.2022 08:48:03
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FilePathParser extends AbstractTypedParser<String> {

	private final boolean supportingDirectories;

	public FilePathParser(String description, boolean supportingDirectories) {
		super(description, String.class);
		this.supportingDirectories = supportingDirectories;
	}

	@Override
	protected String parseImpl(String spec) {
		if (!supportingDirectories && FileUtil.isDirectoryPath(spec)) {
			throw ExceptionFactory.getInstance().syntaxErrorForText(
				"This must be a file but denotes a directory", spec);
		}
		return spec;
	}

}
