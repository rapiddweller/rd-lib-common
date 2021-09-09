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

package com.rapiddweller.common.web;

import com.rapiddweller.common.IOUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Provides file download and caches files in the file system.
 * Created: 15.08.2010 10:07:24
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class DownloadCache {

  private static final String DEFAULT_ROOT_FOLDER = "./cache";

  private static final Logger LOGGER = LoggerFactory.getLogger(DownloadCache.class);

  private final File rootFolder;

  /**
   * Instantiates a new Download cache.
   */
  public DownloadCache() {
    this(new File(DEFAULT_ROOT_FOLDER));
  }

  /**
   * Instantiates a new Download cache.
   *
   * @param rootFolder the root folder
   */
  public DownloadCache(File rootFolder) {
    this.rootFolder = rootFolder;
  }

  /**
   * Get.
   *
   * @param url the url
   * @throws IOException the io exception
   */
  public void get(URL url) throws IOException {
    File cacheSubDir = new File(rootFolder, url.getHost());
    String filename = url.getFile();
    if (filename.endsWith("/")) {
      filename = filename.substring(0, filename.length() - 1) + ".dir";
    }
    File cacheFile = new File(cacheSubDir, filename);
    if (!cacheFile.exists()) {
      IOUtil.download(url, cacheFile);
    } else {
      LOGGER.info("providing {} from cache file {}", url, cacheFile.getAbsolutePath());
    }
  }

}
