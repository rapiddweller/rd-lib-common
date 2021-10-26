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

package com.rapiddweller.common;

import com.rapiddweller.common.converter.ToStringConverter;
import com.rapiddweller.common.format.Alignment;

import java.util.Arrays;
import java.util.List;

/**
 * Provides text utilities.<br><br>
 * Created: 21.11.2019 11:53:38
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class TextUtil {

  public static String formatList(List<?> list) {
    StringBuilder text = new StringBuilder();
    for (Object item : list) {
      text.append(format(item)).append(SystemInfo.LF);
    }
    return text.toString();
  }

  public static String formatTable(Object[][] table, char separator) {
    StringBuilder text = new StringBuilder();
    for (Object[] row : table) {
      for (int i = 0; i < row.length; i++) {
        text.append(format(row[i]));
        if (i < row.length - 1) {
          text.append(separator);
        }
      }
      text.append(SystemInfo.LF);
    }
    return text.toString();
  }

  private static String format(Object object) {
    if (object instanceof Number) {
      return HF.format(((Number) object).doubleValue());
    } else {
      return ToStringConverter.convert(object, "");
    }
  }

  public static String formatLinedTable(String[] titleRows, Object[][] table) {
    return formatLinedTable(titleRows, table, null, true);
  }

  public static String formatLinedTable(String[] titleRows, Object[][] table, Alignment[] alignments, boolean hasColumnHeader) {
    // calculate title width and columns width
    int titleWidth = StringUtil.maxLength(titleRows) + 2;
    int[] colWidths = columnWidths(table);
    int bodyWidth = MathUtil.sum(colWidths) + colWidths.length - 1;

    // match title width and extend either titleWidth or with of column #0
    if (bodyWidth > titleWidth) {
      titleWidth = bodyWidth;
    } else {
      int diff = titleWidth - bodyWidth;
      colWidths[0] = colWidths[0] + diff;
    }

    // format table title
    StringBuilder text = new StringBuilder();
    if (titleRows != null && titleRows.length > 0) {
      text.append('+');
      appendChars('-', titleWidth, text);
      text.append("+\n");
      for (String titleRow : titleRows) {
        text.append("| ");
        text.append(titleRow);
        appendChars(' ', titleWidth - 2 - titleRow.length(), text);
        text.append(" |\n");
      }
    }

    // format table body
    appendSeparator(colWidths, text);
    for (int i = 0; i < table.length; i++) {
      Object[] row = table[i];
      Alignment[] alignmentsToUse = (hasColumnHeader && i == 0 ? centered(row.length) : alignments);
      appendRowContentWithLineBreak(row, alignmentsToUse, colWidths, text);
      appendSeparator(colWidths, text);
    }
    return text.toString();
  }

  private static Alignment[] centered(int length) {
    Alignment[] result = new Alignment[length];
    Arrays.fill(result, Alignment.CENTER);
    return result;
  }

  private static void appendRowContentWithLineBreak(Object[] cells, Alignment[] alignments, int[] colWidths, StringBuilder text) {
    String[] strings = new String[cells.length];
    for (int i = 0; i < cells.length; i++) {
      strings[i] = format(cells[i]);
    }
    String[][] rows = StringUtil.splitMultiRowCells(strings);
    for (String[] row : rows) {
      appendRowContentWithoutLineBreak(row, alignments, colWidths, text);
    }
  }

  private static void appendRowContentWithoutLineBreak(String[] row, Alignment[] alignments, int[] colWidths, StringBuilder text) {
    text.append("| ");
    for (int i = 0; i < row.length; i++) {
      Alignment cellAlignment = null;
      if (alignments != null && i < alignments.length) {
        cellAlignment = alignments[i];
      }
      String content = row[i];
      int padCount = colWidths[i] - 2 - content.length();
      if (cellAlignment == Alignment.RIGHT || (cellAlignment == null && isNumber(content))) {
        appendChars(' ', padCount, text);
        text.append(content);
      } else if (cellAlignment == Alignment.LEFT || cellAlignment == null) {
        text.append(content);
        appendChars(' ', padCount, text);
      } else {
        appendChars(' ', padCount / 2, text);
        text.append(content);
        appendChars(' ', padCount - padCount / 2, text);
      }
      text.append(" |");
      if (i < row.length - 1)
        text.append(' ');
    }
    text.append(SystemInfo.LF);
  }

  private static boolean isNumber(String content) {
    return RegexUtil.matches("(-)?[0-9]*(\\.([0-9]*)?)?", content);
  }

  static void appendSeparator(int[] colWidths, StringBuilder builder) {
    builder.append('+');
    for (int colWidth : colWidths) {
      appendChars('-', colWidth, builder);
      builder.append('+');
    }
    builder.append(SystemInfo.LF);
  }

  static void appendChars(char c, int n, StringBuilder builder) {
    for (int i = 0; i < n; i++) {
      builder.append(c);
    }
  }

  private static int[] columnWidths(Object[][] table) {
    int[] colWidths = new int[table[0].length];
    for (int col = 0; col < colWidths.length; col++) {
      colWidths[col] = 0;
      for (Object[] cells : table) {
        if (cells != null && cells.length > col) {
          String text = format(cells[col]);
          List<String> lines = StringUtil.splitLines(text);
          for (String line : lines) {
            if (line.length() > colWidths[col]) {
              colWidths[col] = line.length();
            }
          }
        }
      }
      colWidths[col] += 2;
    }
    return colWidths;
  }

}
