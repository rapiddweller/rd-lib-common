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
package com.rapiddweller.commons.ui;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.commons.ui.FileChooser;
import com.rapiddweller.commons.ui.FileOperation;
import com.rapiddweller.commons.ui.FileTypeSupport;
import com.rapiddweller.commons.ui.awt.AwtFileChooser;
import com.rapiddweller.commons.ui.swing.SwingFileChooser;

/**
 * Provides GUI utility methods.
 * Created: 01.12.2010 13:55:23
 * @since 0.2.4
 * @author Volker Bergmann
 */
public class GUIUtil {

	public static FileChooser createFileChooser(
			File selectedFile, FileTypeSupport supportedTypes, FileOperation operation) {
		FileChooser chooser;
        if (SystemInfo.isMacOsx())
        	chooser = new AwtFileChooser(null, operation, supportedTypes);
        else
	        chooser = new SwingFileChooser(supportedTypes, operation);
        if (selectedFile != null && selectedFile.exists()) {
        	if (selectedFile.isDirectory())
        		chooser.setCurrentDirectory(selectedFile);
        	else
        		chooser.setSelectedFile(selectedFile);
        }
        return chooser;
	}
	
	public static void takeScreenshot(String fileName, String formatName) throws IOException, AWTException {
		Rectangle screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image = new Robot().createScreenCapture(screenBounds);
		ImageIO.write(image, formatName, new File(fileName));
	}
	
}
