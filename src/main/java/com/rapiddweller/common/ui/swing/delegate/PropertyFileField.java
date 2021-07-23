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

package com.rapiddweller.common.ui.swing.delegate;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.bean.ObservableBean;
import com.rapiddweller.common.ui.FileOperation;
import com.rapiddweller.common.ui.FileTypeSupport;
import com.rapiddweller.common.ui.swing.FileField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * {@link FileField} implementation that serves as delegate of a property of a JavaBean object.
 * Created at 30.11.2008 00:22:14
 *
 * @author Volker Bergmann
 * @since 0.5.13
 */
public class PropertyFileField extends FileField {

  // attributes ------------------------------------------------------------------------------------------------------

  private static final long serialVersionUID = -3970525222511845399L;

  private Object bean;
  private String propertyName;

  /**
   * The Locked.
   */
  boolean locked;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Property file field.
   *
   * @param bean         the bean
   * @param propertyName the property name
   * @param length       the length
   * @param typeSupport  the type support
   * @param operation    the operation
   */
  public PropertyFileField(Object bean, String propertyName, int length, FileTypeSupport typeSupport, FileOperation operation) {
    super(length, null, typeSupport, operation);
    init(bean, propertyName);
  }

  private void init(Object bean, String propertyName) {
    this.bean = bean;
    this.propertyName = propertyName;
    this.locked = true;
    refresh();
    Listener listener = new Listener();
    if (bean instanceof ObservableBean) {
      ((ObservableBean) bean).addPropertyChangeListener(propertyName, listener);
    }
    this.addActionListener(listener);
    this.locked = false;
    File value = (File) BeanUtil.getPropertyValue(bean, propertyName);
    if (value != null) {
      setFile(value);
    }
  }

  // event handlers --------------------------------------------------------------------------------------------------

  /**
   * reads the current property value and writes it to the file field.
   */
  void refresh() {
    if (!locked) {
      locked = true;
      Object propertyValue = BeanUtil.getPropertyValue(bean, propertyName);
      File file = (File) propertyValue;
      if (!NullSafeComparator.equals(getFile(), file)) {
        setFile(file);
      }
      locked = false;
    }
  }

  /**
   * writes the current file field content to the property.
   */
  void update() {
    if (!locked) {
      locked = true;
      File file = getFile();
      if (!NullSafeComparator.equals(file, BeanUtil.getPropertyValue(bean, propertyName))) {
        BeanUtil.setPropertyValue(bean, propertyName, file);
      }
      locked = false;
    }
  }

  /**
   * The type Listener.
   */
  class Listener implements PropertyChangeListener, ActionListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      refresh();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      update();
    }

  }
}
