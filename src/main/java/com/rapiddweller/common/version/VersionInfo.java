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
package com.rapiddweller.common.version;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.DeploymentError;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.xml.XMLUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Provides a mechanism to access an application's version number and 
 * check its dependencies programmatically.
 * Created: 23.03.2011 10:38:31
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class VersionInfo {

	private static final Logger LOGGER = LogManager.getLogger(VersionInfo.class);
	
	private static final String VERSION_SUFFIX = "_version";

	private static final Map<String, VersionInfo> INSTANCES = new HashMap<>();
	
	private static final String VERSION_FILE_PATTERN = "com/rapiddweller/{0}/version.properties";

	private static boolean development;
	
	private final String name;
	private final String filePath;
	private String version;
	private final Map<String, String> dependencies;

	private String buildNumber;

	public static VersionInfo getInfo(@NotNull String name) {
		return getInfo(name, true);
	}

	public static VersionInfo getInfo(@NotNull String name, boolean parsingDependencies) {
		VersionInfo result = INSTANCES.get(name);
		if (result == null) {
			result = new VersionInfo(name, parsingDependencies);
			INSTANCES.put(name, result);
		}
		return result;
	}

	private VersionInfo(String name, boolean parsingDependencies) {
		Assert.notNull(name, "name");
		this.name = name;
		this.filePath = normalizedPath(name);
		this.dependencies = new HashMap<>();
		readVersionInfo(this, parsingDependencies);
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}
	
	public String getBuildNumber() {
		return buildNumber;
	}
	
	public Map<String, String> getDependencies() {
		return dependencies;
	}
	
	public void verifyDependencies() {
		if (VersionInfo.development)
			return;
		for (Map.Entry<String, String> dependency : dependencies.entrySet()) {
			String library = dependency.getKey();
			if (library.equals("build_number"))
				continue;
			VersionNumber expectedVersion = VersionNumber.valueOf(dependency.getValue());
			VersionNumber actualVersion = VersionNumber.valueOf(getInfo(library).getVersion());
			if (!VersionInfo.development && actualVersion.compareTo(expectedVersion) < 0)
				throw new DeploymentError(this + " requires at least " + library + ' ' + expectedVersion + ", " +
						"but found " + library + ' ' + actualVersion);
		}
	}
	
	
	
	// private helper methods ------------------------------------------------------------------------------------------

	private static String normalizedPath(String name) {
		if (name.contains("."))
			name = name.replace('.', '/');
		return name;
	}

	private static void readVersionInfo(VersionInfo versionInfo, boolean parsingDependencies) {
		versionInfo.version = "<unknown version>";
	    try {
			String versionFileName;
	    	if (versionInfo.filePath.contains("/"))
	    		versionFileName = versionInfo.filePath + "/version.properties";
	    	else
	    		versionFileName = VERSION_FILE_PATTERN.replace("{0}", versionInfo.name);
	    	boolean ok = readVersionInfo(versionInfo, versionFileName);
	        if (!ok)
	        	LOGGER.warn("Version number file '" + versionFileName + "' not found, falling back to POM");
	        if (versionInfo.version.startsWith("${") || versionInfo.version.startsWith("<unknown")) { // ...in Eclipse no filtering is applied,...
	        	VersionInfo.development = true;
	        	if (versionInfo.version.startsWith("${"))
	        		LOGGER.warn("Version number has not been resolved, falling back to POM info"); // ...so I fetch it directly from the POM!
	    		Document doc = XMLUtil.parse("pom.xml");
	    		Element versionElement = XMLUtil.getChildElement(doc.getDocumentElement(), false, true, "version");
	    		versionInfo.version = versionElement.getTextContent();
	    		if (parsingDependencies)
	    			parseDependencies(versionInfo, doc);
	        }
        } catch (IOException e) {
	        LOGGER.error("Error reading version info file", e);
        }
    }

	private static void parseDependencies(VersionInfo versionInfo, Document doc) {
		Element propsElement = XMLUtil.getChildElement(doc.getDocumentElement(), false, false, "properties");
		if (propsElement != null)
    		for (Element childElement : XMLUtil.getChildElements(propsElement)) {
    			String dependencyName = childElement.getNodeName();
    			String dependencyVersion = childElement.getTextContent();
    			if ("build_number".equals(dependencyName))
    				versionInfo.buildNumber = dependencyVersion;
    			else if (dependencyName.endsWith(VERSION_SUFFIX))
    				addDependency(dependencyName, dependencyVersion, versionInfo);
    		}
	}

	private static boolean readVersionInfo(VersionInfo versionInfo, String versionFileName) throws IOException {
        if (IOUtil.isURIAvailable(versionFileName)) {
    		Map<String, String> props = IOUtil.readProperties(versionFileName);
    		for (Entry<String, String> dependency : props.entrySet()) {
    			String dependencyName = dependency.getKey();
				String dependencyVersion = dependency.getValue();
				if ("build_number".equals(dependencyName))
    				versionInfo.buildNumber = dependencyVersion;
    			else
    				addDependency(dependencyName, dependencyVersion, versionInfo);
    		}
    		String versionKey = versionInfo.name.replace('.', '_') + VERSION_SUFFIX;
			versionInfo.version = props.get(versionKey);
	        if (versionInfo.version == null)
	        	throw new ConfigurationError("No version number (" + versionKey + ") defined in file " + versionFileName);
    		return true;
        } else
        	return false;
	}
	
	private static void addDependency(String dependencyName,
			String dependencyVersion, VersionInfo versionInfo) {
		dependencyName = dependencyName.substring(0, dependencyName.length() - VERSION_SUFFIX.length());
		if (!dependencyName.equals(versionInfo.name))
			versionInfo.dependencies.put(dependencyName, dependencyVersion);
	}
	
	@Override
	public String toString() {
		return name + ' ' + version + (buildNumber == null || ("${buildNumber}".equals(buildNumber)) ? "" : " build " + buildNumber);
	}
	
}
