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

package com.rapiddweller.common.ui.swing;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Component;
import java.io.File;
import java.io.IOException;

/**
 * Renders the canonical path of a {@link File} in a {@link JList}.
 * Created at 30.11.2008 17:11:26
 * @author Volker Bergmann
 * @since 0.5.13
 */
public class FilePathListCellRenderer extends DefaultListCellRenderer {

  @Override
  public Component getListCellRendererComponent(
      JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    File file = (File) value;
    String text;
    try {
      text = file.getCanonicalPath();
    } catch (IOException e) {
      text = file.getAbsolutePath();
    }
    return super.getListCellRendererComponent(list, text, index, isSelected, hasFocus());
  }

}
