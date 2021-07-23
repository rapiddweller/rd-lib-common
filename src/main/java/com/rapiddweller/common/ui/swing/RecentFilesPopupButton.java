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

import com.rapiddweller.common.Consumer;
import com.rapiddweller.common.file.FileHistory;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Button that opens a pop-up window with files provided by a {@link FileHistory},
 * lets the user click one and sends the clicked file to a {@link Consumer}.
 * Created: 20.06.2016 16:32:11
 *
 * @author Volker Bergmann
 * @since 1.0.11
 */
public class RecentFilesPopupButton extends JButton {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Recent files popup button.
   *
   * @param label    the label
   * @param icon     the icon
   * @param history  the history
   * @param consumer the consumer
   */
  public RecentFilesPopupButton(String label, Icon icon, final FileHistory history, final Consumer<File> consumer) {
    super(icon);
    setToolTipText(label);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JPopupMenu popup = new JPopupMenu();
        File[] recentFiles = history.getFiles();
        if (recentFiles.length > 0) {
          for (File recentFile : recentFiles) {
            popup.add(new ConsumerAction<>(recentFile.getName(), null, recentFile, consumer));
          }
          popup.show(RecentFilesPopupButton.this, e.getX(), e.getY());
        } else {
          Toolkit.getDefaultToolkit().beep();
        }
      }
    });
  }
}