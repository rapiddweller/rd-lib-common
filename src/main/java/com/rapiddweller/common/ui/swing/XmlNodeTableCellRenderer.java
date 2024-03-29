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

import com.rapiddweller.common.converter.ToStringConverter;
import com.rapiddweller.common.xml.XMLUtil;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

/**
 * Renders {@link org.w3c.dom.Node} and its child classes in Swing table cells.<br><br>
 * Created: 23.06.2016 18:01:35
 *
 * @author Volker Bergmann
 * @since 1.0.11
 */
public class XmlNodeTableCellRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = 1L;

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus, int row, int column) {
    Node node = (Node) value;
    String text;
    if (node instanceof CDATASection) {
      text = "<![CDATA[" + node.getTextContent() + "]]>";
    } else if (node instanceof CharacterData) {
      text = node.getTextContent();
    } else if (node instanceof Element) {
      text = XMLUtil.formatStartTag((Element) node).trim();
    } else if (node instanceof Comment) {
      text = ((Comment) node).getData();
    } else if (node instanceof Document) {
      text = XMLUtil.format((Document) node);
    } else {
      text = ToStringConverter.convert(node, "");
    }
    return super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
  }

}
