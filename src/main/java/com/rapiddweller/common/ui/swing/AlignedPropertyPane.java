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

import com.rapiddweller.common.ui.I18NSupport;
import com.rapiddweller.common.ui.swing.delegate.PropertyCheckBox;
import com.rapiddweller.common.ui.swing.delegate.PropertyComboBox;
import com.rapiddweller.common.ui.swing.delegate.PropertyPasswordField;
import com.rapiddweller.common.ui.swing.delegate.PropertyTextArea;
import com.rapiddweller.common.ui.swing.delegate.PropertyTextField;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;

/**
 * {@link AlignedPane} implementation that supports property-based input elements.
 * Created: 22.08.2010 07:09:34
 *
 * @param <E> The type of the bean displayed
 * @author Volker Bergmann
 * @since 0.5.13
 */
public class AlignedPropertyPane<E> extends AlignedPane {

  private static final long serialVersionUID = 4787396114405246065L;

  private static final int WIDE = 30;

  /**
   * The Bean.
   */
  protected final E bean;
  /**
   * The 18 n.
   */
  protected final I18NSupport i18n;

  /**
   * Instantiates a new Aligned property pane.
   *
   * @param orientation the orientation
   * @param columns     the columns
   * @param bean        the bean
   * @param i18n        the 18 n
   */
  public AlignedPropertyPane(int orientation, int columns, E bean, I18NSupport i18n) {
    super(orientation, columns);
    this.bean = bean;
    this.i18n = i18n;
  }

  /**
   * Gets bean.
   *
   * @return the bean
   */
  public E getBean() {
    return bean;
  }

  /**
   * Create i 18 n label row.
   *
   * @param keys the keys
   */
  public void createI18NLabelRow(String... keys) {
    for (String key : keys) {
      this.addElement(new JLabel(i18n.getString(key)));
    }
    endRow();
  }

  /**
   * Create text field row j text field.
   *
   * @param propertyName the property name
   * @return the j text field
   */
  public JTextField createTextFieldRow(String propertyName) {
    JTextField textfield = new PropertyTextField(bean, propertyName, WIDE);
    String label = i18n.getString(propertyName);
    this.addRow(label, textfield);
    return textfield;
  }

  /**
   * Create text field j text field.
   *
   * @param propertyName the property name
   * @return the j text field
   */
  public JTextField createTextField(String propertyName) {
    return createTextField(propertyName, 1, true);
  }

  /**
   * Create text field j text field.
   *
   * @param propertyName the property name
   * @param useLabel     the use label
   * @return the j text field
   */
  public JTextField createTextField(String propertyName, boolean useLabel) {
    return createTextField(propertyName, 1, useLabel);
  }

  /**
   * Create text field j text field.
   *
   * @param propertyName the property name
   * @param gridwidth    the gridwidth
   * @param useLabel     the use label
   * @return the j text field
   */
  public JTextField createTextField(String propertyName, int gridwidth, boolean useLabel) {
    JTextField textfield = new PropertyTextField(bean, propertyName, WIDE / 2);
    if (useLabel) {
      String label = i18n.getString(propertyName);
      this.addElement(label, textfield, gridwidth);
    } else {
      this.addElement(textfield, gridwidth);
    }
    return textfield;
  }

  /**
   * Create text area j text area.
   *
   * @param propertyName the property name
   * @return the j text area
   */
  public JTextArea createTextArea(String propertyName) {
    JTextArea textArea = new PropertyTextArea(bean, propertyName);
    String label = i18n.getString(propertyName);
    this.addLabelRow(label);
    this.addTallRow(new JScrollPane(textArea));
    return textArea;
  }

  /**
   * Create check box j check box.
   *
   * @param propertyName the property name
   * @return the j check box
   */
  public JCheckBox createCheckBox(String propertyName) {
    PropertyCheckBox checkBox = new PropertyCheckBox(bean, propertyName, i18n.getString(propertyName));
    this.addElement(checkBox);
    return checkBox;
  }

  /**
   * Create combo box row j combo box.
   *
   * @param <O>          the type parameter
   * @param propertyName the property name
   * @param options      the options
   * @return the j combo box
   */
  @SafeVarargs
  public final <O> JComboBox<O> createComboBoxRow(String propertyName, O... options) {
    JComboBox<O> comboBox = createComboBox(propertyName, true, true, options);
    this.endRow();
    return comboBox;
  }

  /**
   * Create combo box j combo box.
   *
   * @param <O>          the type parameter
   * @param propertyName the property name
   * @param useLabel     the use label
   * @param contentIi18n the content ii 18 n
   * @param options      the options
   * @return the j combo box
   */
  @SafeVarargs
  public final <O> JComboBox<O> createComboBox(String propertyName, boolean useLabel, boolean contentIi18n, O... options) {
    JComboBox<O> comboBox = new PropertyComboBox<>(bean, propertyName, (contentIi18n ? i18n : null), propertyName + ".", options);
    if (useLabel) {
      String label = this.i18n.getString(propertyName);
      this.addElement(label, comboBox);
    } else {
      this.addElement(comboBox);
    }
    return comboBox;
  }

  /**
   * Create password field j text field.
   *
   * @param propertyName the property name
   * @return the j text field
   */
  public JTextField createPasswordField(String propertyName) {
    PropertyPasswordField pwfield = new PropertyPasswordField(bean, propertyName, WIDE / 2);
    String label = i18n.getString(propertyName);
    this.addElement(label, pwfield);
    return pwfield;
  }

  /**
   * Create button j button.
   *
   * @param label    the label
   * @param listener the listener
   * @return the j button
   */
  public JButton createButton(String label, ActionListener listener) {
    JButton button = new JButton(i18n.getString(label));
    button.addActionListener(listener);
    return button;
  }

}
