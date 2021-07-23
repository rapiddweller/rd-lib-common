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

import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.file.FilenameFormat;
import com.rapiddweller.common.ui.FileChooser;
import com.rapiddweller.common.ui.FileOperation;
import com.rapiddweller.common.ui.FileTypeSupport;
import com.rapiddweller.common.ui.awt.AwtFileChooser;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Lets the user choose a {@link File} with {@link TextField} and {@link JFileChooser}.
 * Created: 05.05.2007 00:10:38
 *
 * @author Volker Bergmann
 * @since 0.5.13
 */
public class FileField extends Box {

  private static final long serialVersionUID = 2088339867450647264L;

  /**
   * The Full path displayed.
   */
  boolean fullPathDisplayed;
  private final FilenameFormat filenameFormat;
  /**
   * The Filename field.
   */
  JTextField filenameField;
  /**
   * The Chooser.
   */
  FileChooser chooser;
  private final List<ActionListener> actionListeners;
  /**
   * The Operation.
   */
  FileOperation operation;
  /**
   * The Approve button text.
   */
  String approveButtonText;
  /**
   * The Button.
   */
  JButton button;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new File field.
   */
  public FileField() {
    this(20);
  }

  /**
   * Instantiates a new File field.
   *
   * @param columns the columns
   */
  public FileField(int columns) {
    this(columns, null, FileTypeSupport.filesOnly, FileOperation.OPEN);
  }

  /**
   * Instantiates a new File field.
   *
   * @param columns         the columns
   * @param file            the file
   * @param fileTypeSupport the file type support
   */
  public FileField(int columns, File file, FileTypeSupport fileTypeSupport) {
    this(columns, file, fileTypeSupport, null);
  }

  /**
   * Instantiates a new File field.
   *
   * @param columns        the columns
   * @param file           the file
   * @param supportedTypes the supported types
   * @param operation      the operation
   */
  public FileField(int columns, File file, FileTypeSupport supportedTypes, FileOperation operation) {
    super(BoxLayout.X_AXIS);
    setBorder(null);
    this.fullPathDisplayed = true;
    this.operation = operation;
    filenameField = new JTextField(columns);
    if (SystemInfo.isMacOsx()) {
      chooser = new AwtFileChooser(null, operation, supportedTypes);
    } else {
      chooser = new SwingFileChooser(supportedTypes, operation);
    }
    if (file != null && file.exists()) {
      chooser.setSelectedFile(file);
      filenameField.setText(file.getAbsolutePath());
    }
    add(filenameField, BorderLayout.CENTER);
    filenameField.getDocument().addDocumentListener(new TextFieldListener());
    button = new JButton("...");
    add(button, BorderLayout.EAST);
    button.addActionListener(new ButtonListener());
    filenameFormat = new FilenameFormat(true);
    this.actionListeners = new ArrayList<>();
  }

  // properties ------------------------------------------------------------------------------------------------------

  /**
   * Gets file.
   *
   * @return the file
   */
  public File getFile() {
    return new File(filenameField.getText());
  }

  /**
   * Sets file.
   *
   * @param file the file
   */
  public void setFile(File file) {
    chooser.setSelectedFile(file);
    filenameField.setText(file.getAbsolutePath());
  }

  /**
   * Is full path used boolean.
   *
   * @return the boolean
   */
  public boolean isFullPathUsed() {
    return filenameFormat.isFullPathUsed();
  }

  /**
   * Sets full path used.
   *
   * @param fullPathUsed the full path used
   */
  public void setFullPathUsed(boolean fullPathUsed) {
    filenameFormat.setFullPathUsed(fullPathUsed);
  }

  /**
   * Add action listener.
   *
   * @param listener the listener
   */
  public void addActionListener(ActionListener listener) {
    actionListeners.add(listener);
  }

  /**
   * Remove action listener.
   *
   * @param listener the listener
   */
  public void removeActionListener(ActionListener listener) {
    actionListeners.remove(listener);
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    filenameField.setEnabled(enabled);
    button.setEnabled(enabled);
  }

  // private helpers -------------------------------------------------------------------------------------------------

  /**
   * Fire action.
   */
  void fireAction() {
    ActionEvent e = new ActionEvent(this, 0, "files");
    for (int i = actionListeners.size() - 1; i >= 0; i--) {
      actionListeners.get(i).actionPerformed(e);
    }
  }

  /**
   * The type Button listener.
   */
  class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      File file = null;
      String path = filenameField.getText();
      if (path.length() > 0) {
        file = new File(path);
        if (!file.exists()) {
          file = null;
        }
      }
      if (file != null) {
        chooser.setCurrentDirectory(file.getParentFile());
        chooser.setSelectedFile(file);
      }
      File selectedFile = chooser.chooseFile(FileField.this);
      if (selectedFile != null) {
        filenameField.setText(selectedFile.getAbsolutePath());
        fireAction();
      }
    }
  }

  /**
   * The type Text field listener.
   */
  public class TextFieldListener implements DocumentListener {

    @Override
    public void changedUpdate(DocumentEvent e) {
      update(e);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
      update(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      update(e);
    }

    private void update(DocumentEvent e) {
      fireAction();
    }

  }

}
