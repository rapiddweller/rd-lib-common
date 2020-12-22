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
package com.rapiddweller.commons.ui.swing;

import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.commons.file.FilenameFormat;
import com.rapiddweller.commons.ui.FileChooser;
import com.rapiddweller.commons.ui.FileOperation;
import com.rapiddweller.commons.ui.FileTypeSupport;
import com.rapiddweller.commons.ui.awt.AwtFileChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.io.File;
import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * Lets the user choose a {@link File} with {@link TextField} and {@link JFileChooser}.
 * Created: 05.05.2007 00:10:38
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class FileField extends Box {
	
	private static final long serialVersionUID = 2088339867450647264L;
	
    boolean fullPathDisplayed;
    private FilenameFormat filenameFormat;
    JTextField filenameField;
    FileChooser chooser;
    private List<ActionListener> actionListeners;
    FileOperation operation;
    String approveButtonText;
    JButton button;
    
    // constructors ----------------------------------------------------------------------------------------------------

    public FileField() {
        this(20);
    }

    public FileField(int columns) {
        this(columns, null, FileTypeSupport.filesOnly, FileOperation.OPEN);
    }

    public FileField(int columns, File file, FileTypeSupport fileTypeSupport) {
    	this(columns, file, fileTypeSupport, (FileOperation) null);
    }

    public FileField(int columns, File file, FileTypeSupport supportedTypes, FileOperation operation) {
    	super(BoxLayout.X_AXIS);
    	setBorder(null);
        this.fullPathDisplayed = true;
        this.operation = operation;
        filenameField = new JTextField(columns);
        if (SystemInfo.isMacOsx())
        	chooser = new AwtFileChooser(null, operation, supportedTypes);
        else
	        chooser = new SwingFileChooser(supportedTypes, operation);
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
        this.actionListeners = new ArrayList<ActionListener>();
    }

    // properties ------------------------------------------------------------------------------------------------------
    
    public File getFile() {
    	return new File(filenameField.getText());
    }

    public void setFile(File file) {
		chooser.setSelectedFile(file);
        filenameField.setText(file.getAbsolutePath());
    }

    public boolean isFullPathUsed() {
        return filenameFormat.isFullPathUsed();
    }

    public void setFullPathUsed(boolean fullPathUsed) {
        filenameFormat.setFullPathUsed(fullPathUsed);
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

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

    void fireAction(String command) {
        ActionEvent e = new ActionEvent(this, 0, command);
        for (int i = actionListeners.size() - 1; i >= 0; i--)
            actionListeners.get(i).actionPerformed(e);
    }

    class ButtonListener implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e) {
            File file = null;
            String path = filenameField.getText();
            if (path.length() > 0) {
                file = new File(path);
                if (!file.exists())
                    file = null;
            }
            if (file != null) {
                chooser.setCurrentDirectory(file.getParentFile());
                chooser.setSelectedFile(file);
            }
            File selectedFile = chooser.chooseFile(FileField.this);
            if (selectedFile != null) {
                filenameField.setText(selectedFile.getAbsolutePath());
                fireAction("files");
            }
        }
    }

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
			fireAction("files");
		}

	}

}
