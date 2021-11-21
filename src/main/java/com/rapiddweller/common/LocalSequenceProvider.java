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

import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.file.FileResourceNotFoundException;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates consecutive sequence values and persists sequence state in a properties file.
 * Created: 09.11.2013 09:04:51
 * @author Volker Bergmann
 * @since 0.5.25
 */
public class LocalSequenceProvider implements Closeable {

  private static final Map<String, LocalSequenceProvider> INSTANCES = new HashMap<>();

  public static LocalSequenceProvider getInstance(String filename) {
    LocalSequenceProvider result = INSTANCES.get(filename);
    if (result == null) {
      result = new LocalSequenceProvider(filename);
      INSTANCES.put(filename, result);
    }
    return result;
  }


  private final String fileName;

  private boolean cached;

  private final Map<String, AtomicLong> counters;

  // Initialization --------------------------------------------------------------------------------------------------

  private LocalSequenceProvider(String fileName) {
    this(fileName, true);
  }

  private LocalSequenceProvider(String fileName, boolean cached) {
    this.fileName = fileName;
    this.cached = cached;
    this.counters = new HashMap<>();
    load();
  }

  // Properties ------------------------------------------------------------------------------------------------------

  public boolean isCached() {
    return cached;
  }

  public void setCached(boolean cached) {
    this.cached = cached;
  }


  // interface -------------------------------------------------------------------------------------------------------

  public long next(String sequenceName) {
    long result = counters.computeIfAbsent(sequenceName, k -> new AtomicLong()).incrementAndGet();
    if (!cached) {
      save();
    }
    return result;
  }

  @Override
  public void close() {
    save();
  }


  // static methods --------------------------------------------------------------------------------------------------

  public void save() {
    Map<String, String> values = new HashMap<>();
    for (Map.Entry<String, AtomicLong> entry : counters.entrySet()) {
      values.put(entry.getKey(), String.valueOf(entry.getValue().get()));
    }
    IOUtil.writeProperties(values, fileName);
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private void load() {
    if (IOUtil.isURIAvailable(fileName)) {
      try {
        Map<String, String> values = IOUtil.readProperties(fileName);
        for (Map.Entry<String, String> entry : values.entrySet()) {
          counters.put(entry.getKey(), new AtomicLong(Long.parseLong(entry.getValue())));
        }
      } catch (FileResourceNotFoundException e) {
        throw ExceptionFactory.getInstance().configurationError("Error loading configuration file " + fileName, e);
      }
    }
  }

}
