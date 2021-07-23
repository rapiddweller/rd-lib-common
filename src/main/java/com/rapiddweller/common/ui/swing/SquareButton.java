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

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.Dimension;

/**
 * Provides a square button.<br><br>
 * Created: 25.05.2016 17:28:12
 *
 * @author Volker Bergmann
 * @since 1.0.10
 */
public class SquareButton extends JButton {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Square button.
   */
  public SquareButton() {
    super();
  }

  /**
   * Instantiates a new Square button.
   *
   * @param action the action
   */
  public SquareButton(Action action) {
    super(action);
  }

  /**
   * Instantiates a new Square button.
   *
   * @param text the text
   */
  public SquareButton(String text) {
    super(text);
  }

  /**
   * Instantiates a new Square button.
   *
   * @param icon the icon
   */
  public SquareButton(Icon icon) {
    super(icon);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension preferredSize = super.getPreferredSize();
    preferredSize.width = preferredSize.height;
    return preferredSize;
  }

}
