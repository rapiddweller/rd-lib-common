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

package com.rapiddweller.common.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link TabularIterator} implementation that iterated through a list of object arrays.
 * Created: 27.10.2011 08:43:17
 *
 * @author Volker Bergmann
 * @since 0.5.11
 */
public class ListTableRowIterator extends AbstractTabularIterator {

  private final List<Object[]> rows;
  private int cursor;

  /**
   * Instantiates a new List table row iterator.
   *
   * @param columnLabels the column labels
   */
  public ListTableRowIterator(String... columnLabels) {
    this(columnLabels, new ArrayList<>());
  }

  /**
   * Instantiates a new List table row iterator.
   *
   * @param columnLabels the column labels
   * @param rows         the rows
   */
  public ListTableRowIterator(String[] columnLabels, List<Object[]> rows) {
    super(columnLabels);
    this.rows = rows;
    this.cursor = 0;
  }

  /**
   * Add row.
   *
   * @param row the row
   */
  public void addRow(Object[] row) {
    this.rows.add(row);
  }

  @Override
  public boolean hasNext() {
    return cursor < rows.size();
  }

  @Override
  public Object[] next() {
    return rows.get(cursor++);
  }

}
