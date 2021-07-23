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
import java.util.regex.Pattern;

/**
 * {@link FileFilter} that can be configured to accepted files and/or folders
 * based on a regular expression.
 * Created: 24.02.2010 07:09:52
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class PatternFileFilter implements FileFilter {

  private final Pattern pattern;
  private final boolean acceptingFiles;
  private final boolean acceptingFolders;

  /**
   * Instantiates a new Pattern file filter.
   *
   * @param regex            the regex
   * @param acceptingFiles   the accepting files
   * @param acceptingFolders the accepting folders
   */
  public PatternFileFilter(String regex, boolean acceptingFiles, boolean acceptingFolders) {
    this.pattern = (regex != null ? Pattern.compile(regex) : null);
    this.acceptingFiles = acceptingFiles;
    this.acceptingFolders = acceptingFolders;
  }

  @Override
  public boolean accept(File file) {
    if (pattern != null && !pattern.matcher(file.getName()).matches()) {
      return false;
    }
    return (acceptingFiles && file.isFile()) || (acceptingFolders && file.isDirectory());
  }

}
