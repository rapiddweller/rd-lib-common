/*
 * Copyright (C) 2004-2022 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides String related utility operations.
 * Created: 05.07.2006 22:45:12
 * @author Volker Bergmann
 * @since 0.1
 */
public final class StringUtil {

  /** Bell character (BEL, 0x07) */
  public static final String BEL = String.valueOf((char) 7);

  /** Backspace character (BS, 0x08) */
  public static final String BS = String.valueOf((char) 8);

  /** Horizontal Tab character (HT, 0x09) */
  public static final String HT = String.valueOf('\t');

  /** Line Feed character (LF, 0x0A) */
  public static final String LF = String.valueOf('\n');

  /** Vertical Tab character (VT, 0x0B) */
  public static final String VT = String.valueOf('\u000B');

  /** character (FF, 0x0C) */
  public static final String FF = String.valueOf('\f');

  /** character (CR, 0x0D) */
  public static final String CR = String.valueOf('\r');

  private static final Set<Character> REGEX_ESCAPABLES = CollectionUtil.toSet('*', '-', '.', ',', '\\', '|');

  private StringUtil() {
    // private constructor to prevent instantiation of this utility class
  }

  public static String shortOrdinal(int number) {
    switch (number) {
      case 1:
        return "1st";
      case 2:
        return "2nd";
      case 3:
        return "3rd";
      default:
        return number + "th";
    }
  }

  /** Tells if a String is null or isEmpty.
   *  @param s the string argument to check
   *  @return true if the String is null or isEmpty, otherwise false. */
  public static boolean isEmpty(CharSequence s) {
    return s == null || s.length() == 0;
  }

  /**  Returns the suffix of a String. If the last character is a
   *  separator, or if no separator was found, the string is assumed
   *  to have no suffix.
   *  @param name      the String to check
   *  @param separator the character that separates name from suffix
   *  @return a suffix if one exists, else null. */
  public static String suffix(String name, char separator) {
    if (name == null) {
      return null;
    }
    int separatorIndex = name.lastIndexOf(separator);
    if (separatorIndex < 0 || separatorIndex == name.length() - 1) {
      return null;
    } else {
      return name.substring(separatorIndex + 1);
    }
  }

  /** Returns the last token of a list in string representation.
   *  If the string does not contain the separator, the string itself
   *  is the token. If the string ends with a separator, the token is
   *  null.
   *  @param name      the name to parse
   *  @param separator the character that separates the tokens
   *  @return the last token */
  public static String lastToken(String name, char separator) {
    if (name == null) {
      return null;
    }
    int separatorIndex = name.lastIndexOf(separator);
    if (separatorIndex < 0) {
      return name;
    } else if (separatorIndex == name.length() - 1) {
      return null;
    } else {
      return name.substring(separatorIndex + 1);
    }
  }

  /** Splits a list's String representation into tokens.
   *  @param text      the String representation of a list.
   *  @param separator the character used to separate tokens
   *  @return an array of tokens. */
  public static String[] tokenize(String text, char separator) {
    if (text == null) {
      return null;
    }
    if (text.length() == 0) {
      return new String[] {""};
    }
    int i = 0;
    int sep;
    List<String> list = new ArrayList<>();
    while ((sep = text.indexOf(separator, i)) >= 0) {
      if (sep == i) {
        list.add("");
        i++;
      } else {
        list.add(text.substring(i, sep));
        i = sep + 1;
      }
    }
    if (i < text.length()) {
      list.add(text.substring(i));
    } else if (text.endsWith("" + separator)) {
      list.add("");
    }
    String[] tokens = new String[list.size()];
    for (i = 0; i < tokens.length; i++) {
      tokens[i] = list.get(i);
    }
    return tokens;
  }

  public static String[][] splitMultiRowCells(String[] cells) {
    String[][] tmp = new String[cells.length][];
    int maxRows = 1;
    for (int colnum = 0; colnum < cells.length; colnum++) {
      String cell = cells[colnum];
      if (cell != null) {
        tmp[colnum] = cell.split("\n");
        if (tmp[colnum].length > maxRows) {
          maxRows = tmp[colnum].length;
        }
      } else {
        tmp[colnum] = new String[] { "" };
      }
    }
    String[][] result = new String[maxRows][];
    for (int rownum = 0; rownum < maxRows; rownum++) {
      result[rownum] = new String[cells.length];
      for (int colnum = 0; colnum < cells.length; colnum++) {
        if (rownum < tmp[colnum].length) {
          result[rownum][colnum] = tmp[colnum][rownum];
        } else {
          result[rownum][colnum] = "";
        }
      }
    }
    return result;
  }

  public static String[] splitAndTrim(String list, char separator) {
    return StringUtil.trimAll(split(list, separator));
  }

  public static String[] split(String list, char separator) {
    String separatorRegex = String.valueOf(separator);
    if (REGEX_ESCAPABLES.contains(separator)) {
      separatorRegex = '\\' + separatorRegex;
    }
    return list.split(separatorRegex);
  }

  public static String normalize(String s) {
    char[] srcBuffer = new char[s.length()];
    s.getChars(0, s.length(), srcBuffer, 0);
    char[] dstBuffer = new char[s.length()];
    int dstIndex = 0;
    for (char c : srcBuffer) {
      if (c >= 16) {
        dstBuffer[dstIndex++] = c;
      }
    }
    return new String(dstBuffer, 0, dstIndex);
  }

  public static StringBuilder appendLeftAligned(StringBuilder builder, String text, int columns) {
    builder.append(text);
    int columnsToInsert = columns - text.length();
    builder.append(" ".repeat(Math.max(0, columnsToInsert)));
    return builder;
  }

  public static String increment(String text) {
    if (text == null || text.length() == 0) {
      return "0";
    }
    char[] chars = new char[text.length()];
    text.getChars(0, text.length(), chars, 0);
    chars = increment(chars, chars.length - 1);
    return String.valueOf(chars);
  }

  public static char[] increment(char[] chars, int index) {
    char c = chars[index];
    switch (c) {
      case '9':
        chars[index] = 'a';
        break;
      case 'z':
      case 'Z':
        if (index > 0) {
          chars[index] = '0';
          chars = increment(chars, index - 1);
        } else {
          char[] result = new char[chars.length + 1];
          Arrays.fill(result, '0');
          result[0] = '1';
          return result;
        }
        break;
      default:
        chars[index]++;
    }
    return chars;
  }

  /** Interprets an nbsp as space character
   *  @param c the character to check
   *  @return true if the character is a whitespace, otherwise false. */
  public static boolean isWhitespace(char c) {
    return Character.isWhitespace(c) || c == 160;
  }

  /** Trims a String by removing white spaces (including nbsp) from left and right.
   *  @param s the String to trim
   *  @return the trimmed string */
  public static String trim(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    int beginIndex;
    for (beginIndex = 0; beginIndex < s.length() && isWhitespace(s.charAt(beginIndex)); beginIndex++) {
      // just count up the beginIndex
    }
    int endIndex;
    for (endIndex = s.length() - 1; endIndex > 0 && isWhitespace(s.charAt(endIndex)); endIndex--) {
      // just count down the endIndex
    }
    if (beginIndex > endIndex) {
      return "";
    }
    return s.substring(beginIndex, endIndex + 1);
  }

  /** Trims all String in the array.
   *  @param array an array of the Strings to trim
   *  @return the same array but with its elements trimmed
   *  @since 0.2.05 */
  public static String[] trimAll(String[] array) {
    for (int i = 0; i < array.length; i++) {
      array[i] = trim(array[i]);
    }
    return array;
  }

  /** Returns the platform dependent line separator
   *  @return the system's line separator */
  public static String lineSeparator() {
    return System.getProperty("line.separator");
  }

  public static boolean contains(String s, char c) {
    return s.indexOf(c) >= 0;
  }

  public static String remove(String s, String chars) {
    if (s == null) {
      return s;
    }
    StringBuilder result = new StringBuilder(s.length());
    for (int i = 0; i < s.length(); i++) {
      if (!(contains(chars, s.charAt(i)))) {
        result.append(s.charAt(i));
      }
    }
    return result.toString();
  }

  public static String nullToEmpty(String text) {
    return (text != null ? text : "");
  }

  public static String substituteNull(String value, String substitution) {
    return (value != null ? value : substitution);
  }

  public static String normalizeSpace(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    s = trim(s);
    if (s.length() == 0) {
      return s;
    }
    char lastChar = s.charAt(0);
    StringBuilder result = new StringBuilder().append(lastChar);
    for (int i = 1; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!isWhitespace(c)) {
        result.append(c);
      } else if (!(isWhitespace(lastChar))) {
        result.append(' ');
      }
      lastChar = c;
    }
    return result.toString();
  }

  public static String trimEnd(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    int lastIndex = s.length() - 1;
    while (lastIndex >= 0 && isWhitespace(s.charAt(lastIndex))) {
      lastIndex--;
    }
    if (lastIndex < 0) {
      return "";
    }
    return s.substring(0, lastIndex + 1);
  }

  public static int countChars(String s, char c) {
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == c) {
        count++;
      }
    }
    return count;
  }

  public static String[] toArray(List<String> list) {
    String[] result = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      result[i] = list.get(i);
    }
    return result;
  }

  public static String[][] toArrayArray(List<List<String>> list) {
    String[][] result = new String[list.size()][];
    for (int i = 0; i < list.size(); i++) {
      result[i] = toArray(list.get(i));
    }
    return result;
  }

  public static String padLeft(String text, int length, char c) {
    if (text == null) {
      text = "";
    }
    int textLength = text.length();
    if (textLength > length) {
      throw ExceptionFactory.getInstance().illegalArgument("Text is too long (" + textLength + ") to be padded to " + length + " columns");
    }
    char[] chars = new char[length];
    int offset = length - textLength;
    fill(chars, 0, offset, c);
    getChars(0, textLength, text, chars, offset);
    return new String(chars);
  }

  public static String padRight(String text, int length, char c) {
    if (text == null) {
      text = "";
    }
    int textLength = text.length();
    if (textLength > length) {
      throw ExceptionFactory.getInstance().illegalArgument("Text is too long (" + textLength + ") to be padded to " + length + " columns");
    }
    char[] chars = new char[length];
    fill(chars, textLength, length, c);
    getChars(0, textLength, text, chars, 0);
    return new String(chars);
  }

  private static final int BUFFER_SIZE = 1024;
  private static final char[] WHITESPACE_BUFFER;

  static {
    WHITESPACE_BUFFER = new char[BUFFER_SIZE];
    Arrays.fill(WHITESPACE_BUFFER, ' ');
  }

  public static char[] fill(char[] chars, int fromIndex, int toIndex, char c) {
    int length = toIndex - fromIndex;
    if (length < 20) {
      for (int i = fromIndex; i < toIndex; i++) {
        chars[i] = c;
      }
    } else if (c != ' ' || length > WHITESPACE_BUFFER.length) {
      Arrays.fill(chars, fromIndex, toIndex, c);
    } else {
      System.arraycopy(WHITESPACE_BUFFER, 0, chars, fromIndex, length);
    }
    return chars;
  }

  public static char[] getChars(int srcBegin, int srcEnd, String text, char[] chars, int dstBegin) {
    int textLength = text.length();
    if (textLength >= 6) {
      text.getChars(srcBegin, srcEnd, chars, dstBegin);
    } else {
      for (int i = 0; i < textLength; i++) {
        chars[i + dstBegin] = text.charAt(i);
      }
    }
    return chars;
  }

  public static char[] getChars(String s) {
    char[] chars = new char[s.length()];
    return getChars(0, chars.length, s, chars, 0);
  }

  public static char[] getChars(StringBuilder builder) {
    char[] chars = new char[builder.length()];
    builder.getChars(0, builder.length(), chars, 0);
    return chars;
  }

  public static String padString(char c, int length) {
    if (length < 0) {
      throw ExceptionFactory.getInstance().illegalArgument("Negative pad length: " + length);
    }
    char[] chars = new char[length];
    Arrays.fill(chars, 0, length, c);
    return new String(chars);
  }

  public static String trimRight(String source) {
    if (source == null) {
      return null;
    }
    int i = source.length() - 1;
    while (i >= 0 && isWhitespace(source.charAt(i))) {
      i--;
    }
    return source.substring(0, i + 1);
  }

  public static String trimRight(String source, char padChar) {
    if (source == null) {
      return null;
    }
    int i = source.length() - 1;
    while (i >= 0 && source.charAt(i) == padChar) {
      i--;
    }
    return source.substring(0, i + 1);
  }

  public static String trimLeft(String source, char padChar) {
    if (source == null) {
      return null;
    }
    int i = 0;
    while (i < source.length() && source.charAt(i) == padChar) {
      i++;
    }
    return source.substring(i);
  }

  public static String trim(String source, char padChar) {
    if (source == null) {
      return null;
    }
    int i0 = 0;
    while (i0 < source.length() && source.charAt(i0) == padChar) {
      i0++;
    }
    if (i0 == source.length()) {
      return "";
    }
    int i1 = source.length() - 1;
    while (i1 > i0 && source.charAt(i1) == padChar) {
      i1--;
    }
    return source.substring(i0, i1 + 1);
  }

  public static String[] toLowerCase(String[] src) {
    String[] dst = new String[src.length];
    for (int i = 0; i < src.length; i++) {
      dst[i] = src[i].toLowerCase();
    }
    return dst;
  }

  public static int indexOfIgnoreCase(String searched, String... candidates) {
    for (int i = 0; i < candidates.length; i++) {
      if (searched.equalsIgnoreCase(candidates[i])) {
        return i;
      }
    }
    return -1;
  }

  public static char lastChar(String word) {
    return word.charAt(word.length() - 1);
  }

  /** Makes the first character of a String uppercase.
   *  @param text the text to convert
   *  @return a text that starts with a uppercase letter */
  public static String capitalize(String text) {
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }

  /** Makes the first character of a String lowercase.
   *  @param text the text to convert
   *  @return a text that starts with a lowercase letter
   *  @since 0.2.04 */
  public static String uncapitalize(String text) {
    return text.substring(0, 1).toLowerCase() + text.substring(1);
  }

  public static String[] splitOnFirstSeparator(String path, char separator) {
    if (path == null) {
      return new String[] {null, null};
    }
    if (path.indexOf(separator) < 0) {
      return new String[] {path, null};
    }
    int sepIndex = path.indexOf(separator);
    return splitAroundSeparator(path, sepIndex);
  }

  public static String[] splitOnLastSeparator(String path, char separator) {
    if (path == null) {
      return new String[] {null, null};
    }
    int sepIndex = path.lastIndexOf(separator);
    return splitAroundSeparator(path, sepIndex);
  }

  public static String[] splitAroundSeparator(String path, int sepIndex) {
    if (sepIndex < 0) {
      return new String[] {null, path};
    } else if (sepIndex == 0) {
      return new String[] {"", path.substring(1)};
    } else if (sepIndex == path.length() - 1) {
      return new String[] {path.substring(0, path.length() - 1), ""};
    } else {
      return new String[] {path.substring(0, sepIndex), path.substring(sepIndex + 1)};
    }
  }

  public static String concat(Character separator, String... parts) {
    if (parts == null) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    for (String part : parts) {
      if (!isEmpty(part)) {
        if (builder.length() > 0 && separator != null) {
          builder.append(separator);
        }
        builder.append(part);
      }
    }
    return builder.toString();
  }

  public static boolean equalsIgnoreCase(String s1, String s2) {
    return (s1 != null ? s1.equalsIgnoreCase(s2) : s2 == null);
  }

  public static boolean equalsIgnoreCase(String[] a1, String[] a2) {
    if (a1.length != a2.length) {
      return false;
    }
    for (int i = 0; i < a1.length; i++) {
      if (!equalsIgnoreCase(a1[i], a2[i])) {
        return false;
      }
    }
    return true;
  }

  public static boolean equalsIgnoreCase(Set<String> set1, Set<String> set2) {
    if (set1.size() != set2.size()) {
      return false;
    }
    for (String s1 : set1) {
      boolean found = false;
      for (String s2 : set2) {
        if (equalsIgnoreCase(s1, s2)) {
          found = true;
          break;
        }
      }
      if (!found) {
        return false;
      }
    }
    return true;
  }

  public static boolean containsIgnoreCase(String searchedWord, Collection<String> words) {
    for (String name : words) {
      if (name.equalsIgnoreCase(searchedWord)) {
        return true;
      }
    }
    return false;
  }

  public static boolean containsIgnoreCase(String searchedWord, String[] words) {
    for (String name : words) {
      if (name.equalsIgnoreCase(searchedWord)) {
        return true;
      }
    }
    return false;
  }

  public static boolean startsWithIgnoreCase(String text, String prefix) {
    if (text == null) {
      return (prefix == null);
    }
    if (prefix == null) {
      return false;
    }
    return text.toLowerCase().startsWith(prefix.toLowerCase());
  }

  public static boolean endsWithIgnoreCase(String text, String suffix) {
    if (text == null) {
      return (suffix == null);
    }
    if (suffix == null) {
      return false;
    }
    return text.toLowerCase().endsWith(suffix.toLowerCase());
  }

  public static String normalizeName(final String name) {
    if (StringUtil.isEmpty(name)) {
      return name;
    }
    final int NONE = -1;
    final int WS = 0;
    final int SPECIAL = 1;
    final int INITIAL = 2;
    final int SUBSEQUENT = 3;
    StringBuilder builder = new StringBuilder(name.length());
    StringCharacterIterator iterator = new StringCharacterIterator(name);
    iterator.skipWhitespace();
    int prevType = NONE;
    while (iterator.hasNext()) {
      char c = iterator.next();
      int type;
      if (Character.isWhitespace(c)) {
        type = WS;
      } else if (!Character.isLetter(c)) {
        type = SPECIAL;
      } else if (prevType == INITIAL) {
        type = SUBSEQUENT;
      } else if (prevType == NONE || prevType == WS || prevType == SPECIAL) {
        type = INITIAL;
      } else {
        type = prevType;
      }
      if (prevType == WS && (type == INITIAL || type == SPECIAL)) {
        builder.append(' ');
      }
      switch (type) {
        case INITIAL:
          builder.append(Character.toUpperCase(c));
          break;
        case SUBSEQUENT:
          builder.append(Character.toLowerCase(c));
          break;
        case SPECIAL:
          builder.append(c);
          break;
        case WS:
          break;
        default:
          throw ExceptionFactory.getInstance().programmerUnsupported(
              "Internal error: Not a supported type: " + type);
      }
      prevType = type;
    }
    return builder.toString();
  }

  public static String escape(String text) {
    return escape(text, false, false);
  }

  /** Escapes a string in C/C++/Java style.
   *  @param text               the text to escape
   *  @param escapeSingleQuotes true if single quotes shall be escaped, otherwise false
   *  @param escapeDoubleQuotes true if double quotes shall be escaped, otherwise false
   *  @return the escaped string
   *  @see "http://en.wikipedia.org/wiki/ASCII" */
  public static String escape(String text, boolean escapeSingleQuotes, boolean escapeDoubleQuotes) {
    if (text == null) {
      return null;
    }
    text = text.replace("\\", "\\\\"); // keep this first, otherwise all other escapes will be doubled
    text = text.replace(BEL, "\\u0007");
    text = text.replace(BS, "\\u0008");
    text = text.replace(CR, "\\r");
    text = text.replace(LF, "\\n");
    text = text.replace(HT, "\\t");
    text = text.replace(VT, "\\u000B");
    text = text.replace(FF, "\\f");
    if (escapeSingleQuotes) {
      text = text.replace("'", "\\'");
    }
    if (escapeDoubleQuotes) {
      text = text.replace("\"", "\\\"");
    }
    return text;
  }

  /** Unescapes a string in C/C++/Java style.
   *  @param text the text to unescape
   *  @return the unescaped text
   *  @see "http://en.wikipedia.org/wiki/ASCII" */
  public static String unescape(String text) {
    if (text == null) {
      return null;
    }
    StringBuilder builder = new StringBuilder(text.length());
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (c != '\\') {
        builder.append(c);
      } else if (i < text.length() - 1) {
        c = text.charAt(++i);
        switch (c) {
          case '\'':
            builder.append('\'');
            break;
          case '"':
            builder.append('"');
            break;
          case '{':
            builder.append('{');
            break;
          case '}':
            builder.append('}');
            break;
          case 'a':
            builder.append(BEL);
            break;
          case 'b':
            builder.append(BS);
            break;
          case 'r':
            builder.append(CR);
            break;
          case 'n':
            builder.append(LF);
            break;
          case 't':
            builder.append(HT);
            break;
          case 'f':
            builder.append(FF);
            break;
          case 'u':
            long n = Long.parseLong(text.substring(i + 1, i + 5), 16);
            builder.append((char) n);
            i += 4;
            break;
          default:
            builder.append(c);
            break;
        }
      } else {
        builder.append('\\');
      }
    }
    return builder.toString();
  }

  public static String replaceTokens(String src, String token, String... values) {
    StringBuilder builder = new StringBuilder();
    int paramIndex = 0;
    int srcIndex = 0;
    while (srcIndex < src.length()) {
      int i = src.indexOf(token, srcIndex);
      if (i >= 0) {
        builder.append(src, srcIndex, i);
        builder.append(values[paramIndex++]);
        srcIndex = i + token.length();
      } else {
        builder.append(src.substring(srcIndex));
        break;
      }
    }
    return builder.toString();
  }

  public static String emptyToNull(String s) {
    if (s == null || s.length() == 0) {
      return null;
    }
    String trimmed = trim(s);
    return (trimmed.length() != 0 ? s : null);
  }

  public static String trimmedEmptyToNull(String s) {
    if (s == null || s.length() == 0) {
      return null;
    }
    String trimmed = trim(s);
    return (trimmed.length() != 0 ? trimmed : null);
  }

  public static String removeSection(String text, String beginMark, String endMark) {
    if (StringUtil.isEmpty(text)) {
      return text;
    }
    int beginIndex = text.indexOf(beginMark);
    int endIndex = text.indexOf(endMark);
    if (beginIndex < 0 || endIndex < 0 || beginIndex + beginMark.length() > endIndex + endMark.length()) {
      return text;
    }
    return text.substring(0, beginIndex) + text.substring(endIndex + endMark.length());
  }

  public static String normalizeLineSeparators(String text, String lineSeparator) {
    if (StringUtil.isEmpty(text)) {
      return text;
    }
    StringBuilder builder = new StringBuilder();
    StringCharacterIterator iterator = new StringCharacterIterator(text);
    while (iterator.hasNext()) {
      char c = iterator.next();
      if (c != '\r' && c != '\n') {
        builder.append(c);
      } else {
        // swallow the \n part of of \r\n
        if (c == '\r' && iterator.hasNext()) {
          char c2 = iterator.next();
          if (c2 != '\n') // oops, it was only a \r
          {
            iterator.pushBack();
          }
        }
        builder.append(lineSeparator);
      }
    }
    return builder.toString();
  }

  public static String extract(String text, String beginMark, String endMark) {
    int beginIndex = (beginMark != null ? text.indexOf(beginMark) + beginMark.length() : 0);
    if (endMark != null) {
      int endIndex = text.indexOf(endMark, beginIndex + 1);
      return (beginIndex >= 0 && endIndex >= 0 && endIndex > beginIndex ? text.substring(beginIndex, endIndex) : null);
    } else {
      return (beginIndex >= 0 ? text.substring(beginIndex) : null);
    }
  }

  public static String buildPhrase(String... parts) {
    if (parts == null) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    for (String part : parts) {
      if (!StringUtil.isEmpty(part)) {
        if (builder.length() > 0) {
          builder.append(' ');
        }
        builder.append(part);
      }
    }
    return builder.toString();
  }

  public static String trimLineSeparators(String text) {
    if (text == null) {
      return null;
    }
    int start = 0;
    while (start < text.length() && isLineSeparatorChar(text.charAt(start))) {
      start++;
    }
    int end = text.length();
    while (end > 0 && (text.charAt(end - 1) == '\r' || text.charAt(end - 1) == '\n')) {
      end--;
    }
    return text.substring(start, end);
  }

  public static List<String> splitLines(String text) {
    if (text == null) {
      return null;
    }
    List<String> lines = new ArrayList<>();
    final int TEXT = 0;
    final int CR = 1;
    final int LF = 2;
    int mode = TEXT;
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (c == '\r') {
        lines.add(builder.toString());
        builder.delete(0, builder.length());
        mode = CR;
      } else if (c == '\n') {
        if (mode != CR) {
          // only add a line if it is a standalone LF (no CRLF)
          lines.add(builder.toString());
          builder.delete(0, builder.length());
        }
        mode = LF;
      } else {
        mode = TEXT;
        builder.append(c);
      }
    }
    if (text.length() == 0 || mode != TEXT || builder.length() > 0) {
      lines.add(builder.toString());
    }
    return lines;
  }

  public static String removeEmptyLines(String text) {
    if (text == null) {
      return null;
    }
    List<String> lines = splitLines(text);
    lines.removeIf(s -> s.trim().length() == 0);
    String sep = lineSeparatorUsedIn(text);
    return ArrayFormat.format(sep, lines.toArray());
  }

  private static String lineSeparatorUsedIn(String text) {
    int i = 0;
    while (i < text.length() && !isLineSeparatorChar(text.charAt(i))) // search the first line separator char
    {
      i++;
    }
    if (i == text.length()) // if no line sep was found, then return null
    {
      return null;
    }
    char c1 = text.charAt(i);
    if (i == text.length() - 1) {
      return String.valueOf(c1); // if the found char is the last one in the string, return it, ...
    }
    char c2 = text.charAt(i + 1);  // ... otherwise check the char that follows
    if (isLineSeparatorChar(c2) && c1 != c2) {
      return "" + c1 + c2; // the line separator consists of two characters
    }
    return String.valueOf(c1);
  }

  public static boolean isLineSeparatorChar(char c) {
    return c == '\r' || c == '\n';
  }

  public static String quoteIfContainsSpecialChars(String text, char quote, String specialChars) {
    if (text == null) {
      return null;
    }
    boolean foundSpecialChar = false;
    for (int i = 0; i < specialChars.length(); i++) {
      if (text.indexOf(' ') >= 0) {
        foundSpecialChar = true;
        break;
      }
    }
    if (foundSpecialChar) {
      return quote + text + quote;
    } else {
      return text;
    }
  }

  public static String quoteIfNotNull(String text) {
    return (text != null ? "'" + text + "'" : text);
  }

  public static String getLeadingWhitespace(String line) {
    int i;
    for (i = 0; i < line.length() && Character.isWhitespace(line.charAt(i)); i++) {
      // empty iteration for index counting
    }
    return line.substring(0, i);
  }

  public static String limitLength(String text, int maxLength) {
    if (text == null) {
      return null;
    } else if (text.length() <= maxLength) {
      return text;
    } else {
      return text.substring(0, maxLength);
    }
  }

  public static String limitLengthWithEllipsis(String text, int maxLength) {
    if (text == null) {
      return null;
    } else if (text.length() <= maxLength) {
      return text;
    } else if (maxLength > 3) {
      return text.substring(0, maxLength - 3) + "...";
    } else if (maxLength == 3) {
      return text.charAt(0) + "..";
    } else if (maxLength > 0) {
      return text.substring(0, maxLength - 1) + ".";
    } else {
      return "";
    }
  }

  public static String replaceOptionalSuffix(String text, String suffix, String replacement) {
    if (text.endsWith(suffix)) {
      return text.substring(0, text.length() - suffix.length()) + replacement;
    } else {
      return text;
    }
  }

  public static String removeSuffixIfPresent(String suffix, String name) {
    if (name != null && name.endsWith(suffix)) {
      return name.substring(0, name.length() - suffix.length());
    }
    return name;
  }

  public static String removePrefixIfPresent(String prefix, String name) {
    if (name != null && name.startsWith(prefix))
      return name.substring(prefix.length());
    else
      return name;
  }

  public static String substringAfter(String marker, String s) {
    int index = s.indexOf(marker);
    if (index < 0) {
      return null;
    }
    return s.substring(index + marker.length());
  }

  public static String substringBefore(String marker, String s) {
    int index = s.indexOf(marker);
    if (index < 0) {
      return null;
    }
    return s.substring(0, index);
  }

  public static int maxLength(String[] strings) {
    int result = 0;
    if (strings != null) {
      for (String s : strings) {
        int length = s.length();
        if (length > result)
          result = length;
      }
    }
    return result;
  }

  public static<E> int maxLength(List<E> objects, Accessor<E, Object> valueAccessor, Format format) {
    int result = 0;
    if (objects != null) {
      for (E item : objects) {
        Object value = valueAccessor.getValue(item);
        String label = (format != null ? format.format(value) : String.valueOf(value));
        int length = label.length();
        if (length > result)
          result = length;
      }
    }
    return result;
  }

  public static byte[] toBytes(String text, String encoding) {
    try {
      return text.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      throw ExceptionFactory.getInstance().configurationError(e.getMessage(), e);
    }
  }

  public static String toString(byte[] bytes, String encoding) {
    try {
      return new String(bytes, encoding);
    } catch (UnsupportedEncodingException e) {
      throw ExceptionFactory.getInstance().configurationError(e.getMessage(), e);
    }
  }

  public static String withLineBreaks(String text, int maxWidth) {
    // TODO test and finalize method
    if (StringUtil.isEmpty(text)) {
      return text;
    }
    Assert.positive(maxWidth, "maxWidth");
    StringBuilder builder = new StringBuilder();
    String rest = text;
    //System.out.println(builder + " - " + rest);
    while (rest.length() > maxWidth) {
      int i = maxWidth;
      while (i > 1 && (i >= rest.length() || !Character.isWhitespace(rest.charAt(i + 1))) && Character.isWhitespace(rest.charAt(i))) {
        i--;
      }
      builder.append(rest.substring(0, i)).append(SystemInfo.LF);
      rest = rest.substring(i);
      //System.out.println(builder + " - " + rest);
    }
    builder.append(rest);
    return builder.toString();
  }

  public static String replaceIgnoreCase(String text, Map<String, String> replacements, boolean keepCaps) {
    Capitalization caps = Capitalization.mixed;
    String lcText = text.toLowerCase();
    if (keepCaps) {
      if (text.equals(lcText)) {
        caps = Capitalization.lower;
      } else if (text.equals(text.toUpperCase())) {
        caps = Capitalization.upper;
      }
    }

    String result = text;
    for (Map.Entry<String, String> entry : replacements.entrySet()) {
      String orig = entry.getKey().toLowerCase();
      int index = lcText.indexOf(orig);
      if (index >= 0) {
        String replacement = applyCaps(caps, entry);
        result = result.substring(0, index) + replacement + result.substring(index + orig.length());
        lcText = result.toLowerCase();
      }
    }
    return result;
  }

  public static String replace(String text, Map<String, String> replacements) {
    String result = text;
    for (Map.Entry<String, String> entry : replacements.entrySet()) {
      String orig = entry.getKey();
      int index = result.indexOf(orig);
      if (index >= 0) {
        result = result.substring(0, index) + entry.getValue() + result.substring(index + orig.length());
      }
    }
    return result;
  }

  public static boolean isUpperCase(String text) {
    return text.toUpperCase().equals(text);
  }

  private static String applyCaps(Capitalization caps, Map.Entry<String, String> entry) {
    String replacement = entry.getValue();
    if (caps == Capitalization.upper) {
      replacement = replacement.toUpperCase();
    } else if (caps == Capitalization.lower) {
      replacement = replacement.toLowerCase();
    }
    return replacement;
  }

}
