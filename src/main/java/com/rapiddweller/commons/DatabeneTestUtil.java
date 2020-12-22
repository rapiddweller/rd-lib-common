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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Provides test settings from a file <code>${user.home}/databene.test.properties</code>.
 * Created at 01.10.2009 15:30:51
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class DatabeneTestUtil {
	
    private static final String DATABENE_TEST_PROPERTIES = "test.properties";

	private static final Logger LOGGER = LogManager.getLogger(DatabeneTestUtil.class);
	
	private static Map<String, String> properties;

	static {
		init();
	}

    private static void init() {
	    File file = new File(SystemInfo.getUserHome() + File.separator + "databene", DATABENE_TEST_PROPERTIES);
	    if (file.exists()) {
	    	try {
	            properties = IOUtil.readProperties(file.getAbsolutePath());
            } catch (IOException e) {
	    		LOGGER.error("Error reading " + file.getAbsolutePath(), e);
	    		createDefaultProperties();
            }
	    } else {
	    	createDefaultProperties();
	    	try {
	    		IOUtil.writeProperties(properties, file.getAbsolutePath());
	    	} catch (Exception e) {
	    		LOGGER.error("Error writing " + file.getAbsolutePath(), e);
	    	}
	    }
    }
	
    private static void createDefaultProperties() {
	    properties = new HashMap<String, String>();
	    properties.put("online", "false");
    }

	public static boolean isOnline() {
		String setting = properties.get("online");
		if (StringUtil.isEmpty(setting))
			return false;
		else
			return setting.toLowerCase().equals("true");
	}
	
	public static String ftpDownloadUrl() {
		return properties.get("ftp.download.url");
	}
	
	public static String ftpUploadUrl() {
		return properties.get("ftp.upload.url");
	}

	public static Map<String, String> getProperties() {
		return properties;
	}
	
}
