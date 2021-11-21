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
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Provides ZIP-related convenience methods.
 * Created: 20.10.2011 15:19:07
 * @author Volker Bergmann
 * @since 0.5.10
 */
public class ZipUtil {

  private static final int BUFFER_SIZE = 2048;

  private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

  private ZipUtil() {
    // private constructor to prevent instantiation of this utility class
  }

  public static byte[] decompress(byte[] data, boolean nowrap) throws IOException, DataFormatException {
    Inflater inflater = new Inflater(nowrap);
    inflater.setInput(data);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[BUFFER_SIZE];
    while (!inflater.finished()) {
      int count = inflater.inflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    outputStream.close();
    return outputStream.toByteArray();
  }

  public static void compressAndDelete(File source, File zipFile) {
    compress(source, zipFile);
    if (!source.delete()) {
      logger.error("Deletion failed for {}", source);
    }
  }

  public static void compress(File source, File zipFile) {
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(zipFile);
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(zipFile.getAbsolutePath(), e);
    }
    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(fos));
    out.setMethod(ZipOutputStream.DEFLATED);
    addFileOrDirectory(source, source, out);
    IOUtil.close(out);
  }

  public static void printContent(File zipFile) {
    printContent(zipFile, System.out);
  }

  public static void printContent(File zipFile, PrintStream printer) {
    try (ZipInputStream in = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)))) {
      ZipEntry entry;
      while ((entry = in.getNextEntry()) != null) {
        printer.println(entry.getName());
      }
    } catch (IOException e) {
      printer.println("Error listing archive content of file " + zipFile + ". " + e.getMessage());
      e.printStackTrace(printer);
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static void addFileOrDirectory(File source, File root, ZipOutputStream out) {
    if (source.isFile()) {
      addFile(source, root, out);
    } else if (source.isDirectory()) {
      addDirectory(source, root, out);
    }
  }

  private static void addDirectory(File source, File root, ZipOutputStream out) {
    File[] files = source.listFiles();
    if (files != null) {
      for (File file : files) {
        addFileOrDirectory(file, root, out);
      }
    }
  }

  private static void addFile(File source, File root, ZipOutputStream out) {
    byte[] buffer = new byte[BUFFER_SIZE];
    try (InputStream in = new BufferedInputStream(new FileInputStream(source))) {
      ZipEntry entry = new ZipEntry(FileUtil.relativePath(root, source));
      out.putNextEntry(entry);
      int count;
      while ((count = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
        out.write(buffer, 0, count);
      }
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(source.getAbsolutePath(), e);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().mutationFailed("Failed to add " + source + " to a zip", e);
    }
  }

}
