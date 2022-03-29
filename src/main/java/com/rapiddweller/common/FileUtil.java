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

import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.file.DirectoryFileFilter;
import com.rapiddweller.common.file.PatternFileFilter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * File Utility class.
 * Created: 04.02.2007 08:22:52
 * @author Volker Bergmann
 * @since 0.1
 */
public final class FileUtil {

  private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

  private FileUtil() {
    // private constructor to prevent instantiation of this utility class
  }

  public static String getTextContentOf(File file) {
    return getTextContentOf(file, SystemInfo.getFileEncoding());
  }

  public static String getTextContentOf(File file, String encoding) {
    Reader reader = createFileReader(file, encoding);
    return IOUtil.readAndClose(reader);
  }

  public static BufferedReader createFileReader(File file, String encoding) {
    try {
      if (encoding == null) {
        encoding = SystemInfo.getFileEncoding();
      }
      InputStream is = createFileInputStream(file);
      PushbackInputStream in = new PushbackInputStream(is, 4);
      encoding = IOUtil.bomEncoding(in, encoding);
      return new BufferedReader(new InputStreamReader(in, encoding));
    } catch (UnsupportedEncodingException e) {
      throw ExceptionFactory.getInstance().configurationError("Not a supported encoding: " + encoding, e);
    }
  }

  @SuppressWarnings("unused")
  public static ZonedDateTime lastFileUpdateInZone(File file, ZoneId zone) {
    return JavaTimeUtil.toZonedDateTime(file.lastModified(), ZoneId.systemDefault()).withZoneSameInstant(zone);
  }

  public static File getOrCreateDirectory(File directory) {
    ensureDirectoryExists(directory);
    return directory;
  }

  public static File ensureDirectoryExists(File directory) {
    if (directory != null && !directory.exists()) {
      File parent = directory.getParentFile();
      if (parent != null) {
        ensureDirectoryExists(parent);
      }
      directory.mkdir();
    }
    return directory;
  }

  public static String[] splitNameAndSuffix(String filename) {
    return StringUtil.splitOnFirstSeparator(filename, '.');
  }

  public static boolean hasSuffix(File file, String suffix, boolean caseSensitive) {
    // don't use suffix(), because the extension may be like '.hbm.xml'
    if (caseSensitive) {
      return file.getName().endsWith(suffix);
    } else {
      return file.getName().toLowerCase().endsWith(suffix.toLowerCase());
    }
  }

  /** Extracts the filename part after the last dot. */
  public static String suffix(File file) {
    return suffix(file.getName());
  }

  /** Extracts the filename part after the last dot. */
  public static String suffix(String filename) {
    int dotIndex = filename.lastIndexOf('.');
    if (dotIndex < 0 || dotIndex == filename.length() - 1) {
      return "";
    }
    return filename.substring(dotIndex + 1);
  }

  public static String nativePath(String path) {
    return path.replace('/', SystemInfo.getFileSeparator());
  }

  @SuppressWarnings("unused")
  public static boolean isEmptyFolder(File folder) {
    String[] list = folder.list();
    return list == null || list.length == 0;
  }

  public static void copy(File srcFile, File targetFile, boolean overwrite) {
    copy(srcFile, targetFile, overwrite, null);
  }

  public static void copy(File srcFile, File targetFile, boolean overwrite, FileFilter filter) {
    try {
      if (filter != null && !filter.accept(srcFile.getCanonicalFile())) {
        return;
      }
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError(
          "Error calculating canonical path of " + srcFile, e);
    }
    if (!srcFile.exists()) {
      throw ExceptionFactory.getInstance().configurationError("Source file not found: " + srcFile);
    }
    if (!overwrite && targetFile.exists()) {
      throw ExceptionFactory.getInstance().configurationError("Target file already exists: " + targetFile);
    }
    if (srcFile.isFile()) {
      copyFile(srcFile, targetFile);
    } else {
      copyDirectory(srcFile, targetFile, overwrite, filter);
    }
  }

  public static String localFilename(String filePath) {
    if (filePath == null) {
      return null;
    }
    int i = filePath.lastIndexOf(File.separatorChar);
    if (File.separatorChar != '/') {
      i = Math.max(i, filePath.lastIndexOf('/'));
    }
    return (i >= 0 ? filePath.substring(i + 1) : filePath);
  }

  public static boolean equalContent(File file1, File file2) {
    long length = file1.length();
    if (length != file2.length()) {
      return false;
    }
    boolean equal = true;
    try (InputStream in1 = new BufferedInputStream(createFileInputStream(file1))) {
      logger.debug("Comparing content of {} and {}", file1, file2);
      try (InputStream in2 = new BufferedInputStream(createFileInputStream(file2))) {
        for (long i = 0; equal && i < length; i++) {
          if (in1.read() != in2.read()) {
            logger.debug("files unequal");
            equal = false;
          }
        }
      }
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError(
          "Error comparing files " + file1 + " and " + file2, e);
    }
    logger.debug("files equal");
    return equal;
  }

  public static void deleteIfExists(File file) {
    if (file.exists() && !file.delete()) {
      file.deleteOnExit();
    }
  }

  public static void deleteDirectoryIfExists(File folder) {
    if (folder.exists()) {
      deleteDirectory(folder);
    }
  }

  public static void deleteDirectory(File folder) {
    File[] files = folder.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          deleteDirectory(file);
        } else {
          if (!file.delete()) {
            logger.error("Failed to delete file {}", file);
          }
        }
      }
    }
    if (!folder.delete()) {
      logger.error("Failed to delete folder {}", folder);
    }
  }

  public static List<File> listFiles(File dir, String regex,
                                     boolean recursive, boolean acceptingFiles, boolean acceptingFolders) {
    PatternFileFilter filter = new PatternFileFilter(regex, acceptingFiles, acceptingFolders);
    return addFilenames(dir, filter, recursive, new ArrayList<>());
  }

  /** Checks if a path denotes a directory syntactically - without verifying its existence */
  public static boolean isDirectoryPath(String spec) {
    return ("..".equals(spec) || ".".equals(spec) || spec.endsWith("/")
        || spec.endsWith(String.valueOf(SystemInfo.getFileSeparator())));
  }

  public static String relativePath(File fromFile, File toFile) {
    return relativePath(fromFile, toFile, File.separatorChar);
  }

  public static String relativePath(File fromFile, File toFile, char separator) {
    File fromFolder = (fromFile.isDirectory() ? fromFile : fromFile.getParentFile());
    try {
      String[] from = StringUtil.tokenize(fromFolder.getCanonicalPath(), File.separatorChar);
      String[] to = StringUtil.tokenize(toFile.getCanonicalPath(), File.separatorChar);
      int i = 0;
      while (i < from.length && i < to.length && from[i].equals(to[i])) {
        i++;
      }
      StringBuilder builder = new StringBuilder();
      for (int j = from.length - 1; j >= i; j--) {
        if (builder.length() > 0) {
          builder.append(separator);
        }
        builder.append("..");
      }
      for (int j = i; j < to.length; j++) {
        if (builder.length() > 0) {
          builder.append(separator);
        }
        builder.append(to[j]);
      }
      if (builder.length() == 0) {
        builder.append(".");
      }
      return builder.toString();
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().operationFailed(
              "Error occurred while calculating relative path from " + fromFile + " to " + toFile + ": ", e);
    }
  }

  private static List<File> addFilenames(File dir, FileFilter filter, boolean recursive, List<File> buffer) {
    File[] matches = dir.listFiles(filter);
    if (matches != null) {
      buffer.addAll(Arrays.asList(matches));
    }
    if (recursive) {
      File[] subDirs = dir.listFiles(DirectoryFileFilter.instance());
      if (subDirs != null) {
        for (File subFolder : subDirs) {
          addFilenames(subFolder, filter, recursive, buffer);
        }
      }
    }
    return buffer;
  }

  public static String normalizeFilename(String rawName) {
    StringBuilder builder = new StringBuilder(rawName.length());
    StringCharacterIterator iterator = new StringCharacterIterator(rawName);
    while (iterator.hasNext()) {
      char c = iterator.next();
      if (Character.isLetterOrDigit(c) || c == '.' || c == '-' || c == '*' || c == '_' || c == '+' || c == ' ') {
        builder.append(c);
      } else if (c == '@') {
        builder.append("a");
      } else if (c == '$') {
        builder.append("s");
      } else if (c == '/') {
        builder.append("_");
      }
      // all other characters are ignored
    }
    return builder.toString().trim();
  }

  @SuppressWarnings("unused")
  public static File fileOfLimitedPathLength(File directory, String name, String suffix, boolean warn) {
    return fileOfLimitedPathLength(directory, name, suffix, 255, warn);
  }

  public static File fileOfLimitedPathLength(File directory, String name, String suffix, int maxLength, boolean warn) {
    try {
      String parentPath;
      parentPath = directory.getCanonicalPath();
      int consumedLength = parentPath.length() + 1 + suffix.length();
      int availableLength = maxLength - consumedLength;
      if (availableLength <= 0) {
        throw ExceptionFactory.getInstance().illegalArgument("Parent path name to long: " + parentPath);
      }
      String prefix = name;
      if (availableLength < prefix.length()) {
        prefix = prefix.substring(0, availableLength);
        if (warn && logger.isWarnEnabled()) {
          logger.warn("File name too long: {}, it was cut to {}",
              parentPath + SystemInfo.getFileSeparator() + name + suffix,
              parentPath + SystemInfo.getFileSeparator() + prefix + suffix);
        }
      }
      return new File(directory, prefix + suffix);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Error composing file path", e);
    }
  }

  public static File getFileIgnoreCase(File file, boolean required) {
    try {
      // if the file exists with the given capitalization use it as it is
      if (file.exists()) {
        return file;
      }
      // otherwise scan the folder for a file with the same name but different capitalization
      file = file.getCanonicalFile();
      File parentFile = file.getParentFile();
      if (parentFile == null) {
        return file;
      }
      File[] files = parentFile.listFiles();
      if (files == null) {
        return file; // parent directory is empty
      }
      for (File sibling : files) {
        if (sibling.getName().equalsIgnoreCase(file.getName())) {
          return sibling;
        }
      }
      // if no file of equal name has been found...
      if (required) {
        throw ExceptionFactory.getInstance().objectNotFound(
            "File not found: " + file); // ... then complain if one is required
      } else {
        return file; // otherwise use the predefined name
      }
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().operationFailed(
          "Error checking file " + file, e);
    }
  }

  /** Creates a {@link File} object from a path string, resolving Unix-style user home folder references. */
  public static File newFile(String path) {
    if (!SystemInfo.isWindows()) {
      if (path.startsWith("~/")) {
        return new File(SystemInfo.getUserHome(), path.substring(2));
      } else if (path.startsWith("~")) {
        return new File(new File(SystemInfo.getUserHome()).getParentFile(), path.substring(1));
      }
    }
    return new File(path);
  }

  public static boolean isXMLFile(String filePath) {
    return filePath.toLowerCase().endsWith(".xml");
  }

  public static String prependFilePrefix(String prefix, String path) {
    int sep = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
    String fileName = prefix + path.substring(sep + 1);
    return path.substring(0, sep + 1) + fileName;
  }

  @SuppressWarnings("unused")
  public static String readTextFileContent(File file) {
    return readTextFileContent(file, null);
  }

  public static String readTextFileContent(File file, String encoding) {
    return IOUtil.readAndClose(createFileReader(file, encoding));
  }

  @SuppressWarnings("unused")
  public static void writeTextFileContent(String content, File file) {
    writeTextFileContent(content, file, SystemInfo.getFileEncoding());
  }

  public static void writeTextFileContent(String content, File file, String encoding) {
    try {
      IOUtil.transferAndClose(new StringReader(content), new OutputStreamWriter(new FileOutputStream(file), encoding));
    } catch (UnsupportedEncodingException e) {
      throw ExceptionFactory.getInstance().internalError("Not a supported encoding: " + encoding, e);
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound("File not found: " + file.getPath(), e);
    }
  }

  @SuppressWarnings("unused")
  public static File backupWithTimestamp(File file, LocalDateTime timestamp, boolean overwrite) {
    File folder = file.getParentFile();
    String filename = file.getName();
    String backupName = addTimestampToFilename(filename, timestamp);
    File backupFile = new File(folder, backupName);
    copy(file, backupFile, overwrite);
    return backupFile;
  }


  // private helpers -------------------------------------------------------------------------------------------------

  static String addTimestampToFilename(String filename, LocalDateTime timestamp) {
    String[] nameParts = splitNameAndSuffix(filename);
    String backupName = nameParts[0] + timestamp.format(DateTimeFormatter.ofPattern("-yy-MM-dd'T'HH-mm-ss"));
    if (!StringUtil.isEmpty(nameParts[1])) {
      backupName += '.' + nameParts[1];
    }
    return backupName;
  }

  private static void copyFile(File srcFile, File targetFile) {
    try {
      try (InputStream in = new BufferedInputStream(createFileInputStream(srcFile))) {
        transfer(in, targetFile, srcFile.getPath());
      }
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(srcFile.getPath(), e);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileAccessException(srcFile.getPath(), e);
    }
  }

  private static void transfer(InputStream in, File targetFile, String srcPath) {
    try (OutputStream out = new FileOutputStream(targetFile)) {
      IOUtil.transfer(in, out);
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(targetFile.getPath(), e);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileAccessException(
          "Error copying " + srcPath + " to " + targetFile.getPath(), e);
    }
  }

  private static void copyDirectory(File srcDirectory, File targetDirectory, boolean overwrite, FileFilter filter) {
    ensureDirectoryExists(targetDirectory);
    File[] files = srcDirectory.listFiles();
    if (files != null) {
      for (File src : files) {
        File dstFile = new File(targetDirectory, src.getName());
        copy(src, dstFile, overwrite, filter);
      }
    }
  }

  public static FileInputStream createFileInputStream(File file) {
    try {
      return new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(file.getPath(), e);
    }
  }

}
