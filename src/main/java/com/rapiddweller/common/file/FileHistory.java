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
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.CollectionUtil;

/**
 * Provides a file history using the Preferences API.<br><br>
 * Created: 27.03.2016 09:02:58
 * @since 1.0.8
 * @author Volker Bergmann
 */

public class FileHistory {
	
	public static final int HISTORY_LENGTH_LIMIT = 50;
	
	private static final String RECENT_FILE_PREFIX = "recent_file_";
	
	private final Class<?> clazz;
	private final ArrayDeque<File> files;
	private final boolean toleratingFailure;
	
	public FileHistory(Class<?> clazz, int length, boolean toleratingFailure) {
		this.clazz = clazz;
		Assert.lessOrEqual(length, HISTORY_LENGTH_LIMIT, "length");
		this.files = new ArrayDeque<>(length);
		this.toleratingFailure = toleratingFailure;
		load();
	}

	public File[] getFiles() {
		return CollectionUtil.toArray(files, File.class);
	}
	
	public File getMostRecentFolder(File defaultFolder) {
		if (files.isEmpty())
			return defaultFolder;
		else
			return files.getLast().getParentFile();
	}
	
	public void addFileAndSave(File file) {
		addFile(file);
		save();
	}
	
	public void addFile(File file) {
		try {
			file = normalize(file);
			remove(file);
			files.addFirst(file);
		} catch (IOException e) {
			if (!toleratingFailure)
				throw new RuntimeException("Failed to update file history", e);
		}
	}

	public void load() {
		Preferences node = Preferences.userNodeForPackage(clazz);
		for (int i = 0; i < HISTORY_LENGTH_LIMIT; i++) {
			String path = node.get(RECENT_FILE_PREFIX + i, null);
			if (path != null) {
				File file = new File(path);
				appendFile(file);
			}
		}
	}

	public void save() {
		Preferences node = Preferences.userNodeForPackage(clazz);
		Iterator<File> iterator = files.iterator();
		for (int i = 0; iterator.hasNext(); i++)
			node.put(RECENT_FILE_PREFIX + i, iterator.next().getAbsolutePath());
		try {
			node.flush();
		} catch (BackingStoreException e) {
			if (!toleratingFailure)
				throw new RuntimeException("Failed to save file history", e);
		}
	}
	
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private static File normalize(File file) throws IOException {
		return file.getCanonicalFile();
	}

	private void remove(File file) {
		files.removeIf(file::equals);
	}
	
	private void appendFile(File file) {
		try {
			file = normalize(file);
			remove(file);
			files.addLast(file);
		} catch (IOException e) {
			if (!toleratingFailure)
				throw new RuntimeException("Failed to update file history", e);
		}
	}

}
