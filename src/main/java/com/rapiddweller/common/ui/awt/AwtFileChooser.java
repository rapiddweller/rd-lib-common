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

package com.rapiddweller.common.ui.awt;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.ui.FileChooser;
import com.rapiddweller.common.ui.FileOperation;
import com.rapiddweller.common.ui.FileTypeSupport;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

/**
 * AWT based implementation of the {@link FileChooser} interface.
 * Created at 14.12.2008 14:36:28
 *
 * @author Volker Bergmann
 * @since 0.5.13
 */
public class AwtFileChooser extends FileDialog implements FileChooser {

  private static final long serialVersionUID = -3217299586317875276L;

  /**
   * The Supported types.
   */
  FileTypeSupport supportedTypes;

  /**
   * Instantiates a new Awt file chooser.
   *
   * @param prompt         the prompt
   * @param operation      the operation
   * @param supportedTypes the supported types
   */
  public AwtFileChooser(String prompt, FileOperation operation, FileTypeSupport supportedTypes) {
    super((Frame) null, prompt, (operation == FileOperation.OPEN ? FileDialog.LOAD : FileDialog.SAVE));
    Assert.notNull(supportedTypes, "supportedTypes");
    Assert.notNull(operation, "operation");
    this.supportedTypes = supportedTypes;
  }

  @Override
  public File chooseFile(Component owner) {
    if (supportedTypes == FileTypeSupport.directoriesOnly) {
      System.setProperty("apple.awt.fileDialogForDirectories", "true");
    }
    setVisible(true);
    System.setProperty("apple.awt.fileDialogForDirectories", "false");
    return getSelectedFile();
  }

  @Override
  public void setCurrentDirectory(File currentDirectory) {
    if (currentDirectory == null) {
      currentDirectory = new File(SystemInfo.getCurrentDir());
    }
    setDirectory(currentDirectory.getAbsolutePath());
  }

  @Override
  public void setSelectedFile(File file) {
    setDirectory(file.getParent());
    setFile(file.getName());
  }

  @Override
  public File getSelectedFile() {
    if (getFile() == null) {
      return null;
    }
    return new File(getDirectory(), getFile());
  }

}
