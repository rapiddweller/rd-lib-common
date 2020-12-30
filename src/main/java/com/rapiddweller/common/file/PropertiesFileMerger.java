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
package com.rapiddweller.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.collection.TreeBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Merges properties files with priority and allows for override by VM parameters.
 * Created: 01.08.2013 10:37:30
 * @since 0.5.24
 * @author Volker Bergmann
 */

public class PropertiesFileMerger {
	
	private static final Logger LOGGER = LogManager.getLogger(PropertiesFileMerger.class);

	public static void merge(String targetPath, String... sourceFiles) throws IOException {
		merge(targetPath, true, sourceFiles);
	}

	public static void merge(String targetPath, boolean vmOverride, String... sourceFiles) throws IOException {
		LOGGER.debug("Merging the files {} into target file: {}", Arrays.toString(sourceFiles), targetPath);
		TreeBuilder tree = null;
		for (String sourceFile : sourceFiles) {
			tree = loadClasspathResourceIfPresent(sourceFile, tree); // find resource on class path
			tree = loadFileIfPresent(sourceFile, tree); // find resource on file system
		}
		if (tree != null) {
			if (vmOverride)
				overwritePropertiesWithVMParams(tree);
			// write properties in UTF-8
			if (targetPath.toLowerCase().endsWith(".xml"))
				tree.saveAsXML(new FileOutputStream(targetPath), Encodings.UTF_8);
			else
				tree.saveAsProperties(new FileOutputStream(targetPath));
		}
	}

	static TreeBuilder loadClasspathResourceIfPresent(String resourceName, TreeBuilder base) throws IOException {
		InputStream in = IOUtil.getResourceAsStream(resourceName, false);
		if (in != null) {
			LOGGER.debug("Loading and merging structure of classpath resource '{}' ", resourceName);
			try {
				TreeBuilder newTree = TreeBuilder.loadFromStream(in, resourceName);
				return overwriteProperties(base, newTree);
			} finally {
				IOUtil.close(in);
			}
		} else
			return base;
	}

	static TreeBuilder loadFileIfPresent(String sourceFile, TreeBuilder base) throws IOException {
		File file = new File(sourceFile);
		if (file.exists()) {
			LOGGER.debug("Loading and merging properties of file '{}' ", sourceFile);
			FileInputStream in = new FileInputStream(file);
			try {
				TreeBuilder newTree = TreeBuilder.loadFromStream(in, sourceFile);
				return overwriteProperties(base, newTree);
			} finally {
				IOUtil.close(in);
			}
		} else 
			return base;
	}

	static TreeBuilder overwriteProperties(TreeBuilder base, TreeBuilder overwrites) {
		if (base == null)
			return overwrites;
		if (overwrites == null)
			return base;
		overwrite(base.getRootNode(), overwrites.getRootNode());
		return base;
	}

	@SuppressWarnings("unchecked")
	private static void overwrite(Map<String, Object> baseNode, Map<String, Object> overwriteNode) {
		for (Map.Entry<String, Object> overEntry : overwriteNode.entrySet()) {
			String key = overEntry.getKey();
			Object oldValue = baseNode.get(key);
			if (oldValue == null) {
				baseNode.put(key, overEntry.getValue());
			} else if (oldValue instanceof Map) {
				overwrite((Map<String, Object>) oldValue, (Map<String, Object>) overEntry.getValue());
			} else {
				baseNode.put(key, overEntry.getValue());
			}
		}
	}

	static void overwritePropertiesWithVMParams(TreeBuilder tree) {
		LOGGER.debug("Checking properties against VM settings override");
		overwritePropertiesWithVMParams(tree.getRootNode(), tree.getRootName());
	}
	
	@SuppressWarnings("unchecked")
	private static void overwritePropertiesWithVMParams(Map<String, Object> node, String path) {
		for (Map.Entry<String, Object> entry : node.entrySet()) {
			String subPath = subPath(path, entry.getKey(), '.');
			Object child = entry.getValue();
			if (child instanceof Map) {
				overwritePropertiesWithVMParams((Map<String, Object>) child, subPath);
			} else if (child instanceof String) {
				String vmSetting = System.getProperty(subPath);
				if (vmSetting != null && vmSetting.length() > 0) {
					LOGGER.debug("Overwriting '{}' property with '{}'", subPath, vmSetting);
					entry.setValue(vmSetting);
				}
			}
		}
	}
	
	private static String subPath(String parentPath, String childName, char separator) {
		return (StringUtil.isEmpty(parentPath) ? "" : parentPath + separator)  + childName;
	}
	
}
