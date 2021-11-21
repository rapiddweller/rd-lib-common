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
import com.rapiddweller.common.xml.XPathUtil;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.Color;

/**
 * Allows the user to enter an XPath definition, rendering illegal expressions in red.
 * Created: 25.05.2016 16:46:53
 * @author Volker Bergmann
 * @since 1.0.11
 */
public class XPathField extends JTextField {

  private static final long serialVersionUID = 1L;

  public XPathField(int columns) {
    this("", columns);
  }

  public XPathField(String text, int columns) {
    super(text, columns);
    getDocument().addDocumentListener(new XPathDocumentListener());
  }

  public boolean isXPathValid() {
    try {
      String text = getDocument().getText(0, getDocument().getLength());
      return XPathUtil.isValidXPath(text);
    } catch (BadLocationException e) {
      throw ExceptionFactory.getInstance().internalError("Internal error", e);
    }
  }

  protected class XPathDocumentListener implements DocumentListener {

    @Override
    public void changedUpdate(DocumentEvent evt) {
      checkValidity();
    }

    @Override
    public void insertUpdate(DocumentEvent evt) {
      checkValidity();
    }

    @Override
    public void removeUpdate(DocumentEvent evt) {
      checkValidity();
    }

    private void checkValidity() {
      setForeground(isXPathValid() ? Color.BLACK : Color.RED);
    }
  }

}
