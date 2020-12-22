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

import java.awt.Component;

/**
 * Adds convenience methods to the {@link javax.swing.ProgressMonitor}.
 * Created at 01.12.2008 09:46:04
 * @since 0.5.13
 * @author Volker Bergmann
 */

public class ProgressMonitor extends javax.swing.ProgressMonitor implements com.rapiddweller.commons.ui.ProgressMonitor {
	
	private int progress;

	public ProgressMonitor(Component parentComponent, Object message, String note, int min, int max) {
		super(parentComponent, message, note, min, max);
		this.progress = min;
	}

	@Override
	public void setProgress(int progress) {
		super.setProgress(progress);
		this.progress = progress;
	}
	
	@Override
	public int getProgress() {
		return progress;
	}
	
	@Override
	public void advance() {
		setProgress(progress + 1);
	}
}
