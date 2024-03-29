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

import com.rapiddweller.common.exception.ExceptionFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * A panel that allows for easy component aligning by an underlying {@link GridBagLayout}.
 * Created: 20.03.2005 11:19:06
 * @author Volker Bergmann
 * @since 0.5.13
 */
public class AlignedPane extends JPanel implements SwingConstants {

  private final int orientation;
  private GridBagConstraints gbc;
  private final int columns;

  public static AlignedPane createHorizontalPane() {
    return new AlignedPane(HORIZONTAL, 0);
  }

  public static AlignedPane createVerticalPane() {
    return createVerticalPane(2);
  }

  public static AlignedPane createVerticalPane(int columns) {
    return new AlignedPane(VERTICAL, columns);
  }

  protected AlignedPane() {
    this(VERTICAL, 2);
  }

  protected AlignedPane(int orientation, int columns) {
    this.columns = columns;
    this.orientation = orientation;
    if (orientation == VERTICAL) {
      setLayout(new GridBagLayout());
    } else if (orientation == HORIZONTAL) {
      setLayout(new FlowLayout());
    } else {
      throw ExceptionFactory.getInstance().illegalArgument("Illeagl orientation: " + orientation);
    }
    removeAll();
  }

  // interface -------------------------------------------------------------------------------------------------------

  @Override
  public void removeAll() {
    super.removeAll();
    if (orientation == VERTICAL) {
      gbc = new GridBagConstraints(
          0, 0, 1, 1, 0, 0,
          GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
          new Insets(2, 2, 2, 2), 2, 2);
    }
  }

  public void addRow(String title, Component component) {
    if (orientation == HORIZONTAL) {
      add(new JLabel(title));
      add(component);
    } else {
      endRow();
      addElement(title, component, columns - 1);
      endRow();
    }
  }

  public void addRow(Component component) {
    if (orientation == HORIZONTAL) {
      add(component, gbc);
      gbc.gridx++;
    } else {
      endRow();
      gbc.weightx = 1;
      gbc.weighty = (component instanceof JTextArea ? 1 : 0);
      gbc.gridwidth = columns;
      add(component, gbc);
      newRow();
    }
  }

  public void addTallRow(Component component) {
    if (orientation == HORIZONTAL) {
      add(component);
    } else {
      endRow();
      gbc.weightx = 1;
      gbc.weighty = 1;
      gbc.gridwidth = columns;
      add(component, gbc);
      newRow();
    }
  }

  public void addElement(String title, Component component) {
    addElement(title, component, 1);
  }

  public void addElement(String title, Component component, int gridwidth) {
    JLabel label = new JLabel(title);
    if (orientation == HORIZONTAL) {
      add(label);
      add(component);
    } else {
      if (gbc.gridx > 0) {
        label.setHorizontalAlignment(RIGHT);
      }
      addElement(label, 1);
      addElement(component, gridwidth);
    }
  }

  public void addLabel(String labelText) {
    addElement(new JLabel(labelText), 1);
  }

  public void addElement(Component component) {
    addElement(component, 1);
  }

  public void addElement(Component component, int gridwidth) {
    if (orientation == HORIZONTAL) {
      add(component);
    } else {
      gbc.weightx = (component instanceof JLabel ? 0 : 1);
      gbc.weighty = (component instanceof JTextArea ? 1 : 0);
      gbc.gridwidth = gridwidth;
      add(component, gbc);
      gbc.gridx += gridwidth;
    }
  }

  public void endRow() {
    if (orientation == VERTICAL && gbc.gridx > 0) {
      newRow();
    }
  }

  private void newRow() {
    gbc.gridy++;
    gbc.gridx = 0;
  }

  public void addSeparator() {
    addLabelRow(" ");
  }

  public void addLabelRow(String text) {
    addRow(new JLabel(text));
  }

}
