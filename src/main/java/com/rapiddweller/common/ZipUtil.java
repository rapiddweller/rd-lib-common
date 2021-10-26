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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);

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
    try {
      compress(source, zipFile);
      source.delete();
    } catch (IOException e) {
      throw new RuntimeException("Unexpected error", e);
    }
  }

  public static void compress(File source, File zipFile) throws IOException {
    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
    out.setMethod(ZipOutputStream.DEFLATED);
    try {
      addFileOrDirectory(source, source, out);
      out.close();
    } catch (IOException e) {
      throw new RuntimeException("Zipping the report failed");
    }
  }

  public static void printContent(File zipFile) {
    ZipInputStream in = null;
    try {
      in = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
      ZipEntry entry;
      while ((entry = in.getNextEntry()) != null) {
        System.out.println(entry.getName());
      }
    } catch (IOException e) {
      LOGGER.error("Error listing archive content of file " + zipFile, e);
    } finally {
      IOUtil.close(in);
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static void addFileOrDirectory(File source, File root, ZipOutputStream out) throws IOException {
    if (source.isFile()) {
      addFile(source, root, out);
    } else if (source.isDirectory()) {
      addDirectory(source, root, out);
    }
  }

  private static void addDirectory(File source, File root, ZipOutputStream out) throws IOException {
    for (File file : source.listFiles()) {
      addFileOrDirectory(file, root, out);
    }
  }

  private static void addFile(File source, File root, ZipOutputStream out) throws IOException {
    byte[] buffer = new byte[BUFFER_SIZE];
    InputStream in = new BufferedInputStream(new FileInputStream(source));
    ZipEntry entry = new ZipEntry(FileUtil.relativePath(root, source));
    out.putNextEntry(entry);
    int count;
    while ((count = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
      out.write(buffer, 0, count);
    }
    in.close();
  }

}
