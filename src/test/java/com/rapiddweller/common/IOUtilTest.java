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
import com.rapiddweller.common.exception.InternalErrorException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link IOUtil} class.
 * Created: 21.06.2007 08:31:28
 * @author Volker Bergmann
 * @since 0.1
 */
public class IOUtilTest {

  private static final Logger logger = LoggerFactory.getLogger(IOUtilTest.class);

  @Test
  public void testLocalFilename() {
    assertEquals("product-info.jsp", IOUtil.localFilename("http://localhost:80/shop/product-info.jsp"));
    assertEquals("Uri", IOUtil.localFilename("Uri"));
  }

  @Test
  public void testIsURIAvailable_directory() {
    assertFalse(IOUtil.isURIAvailable("Uri"));
    assertTrue(IOUtil.isURIAvailable("string://"));
    assertTrue(IOUtil.isURIAvailable("file:"));
    assertTrue(IOUtil.isURIAvailable("file://"));
    assertTrue(IOUtil.isURIAvailable("/"));
  }

  @Test
  public void testIsURIAvailable_file() {
    assertTrue(IOUtil.isURIAvailable("file://com/rapiddweller/common/names.csv"));
    assertTrue(IOUtil.isURIAvailable("file:com/rapiddweller/common/names.csv"));
    assertTrue(IOUtil.isURIAvailable("com/rapiddweller/common/names.csv"));
    assertFalse(IOUtil.isURIAvailable("com/rapiddweller/common/not.an.existing.file"));
  }

  @Test
  public void testGetContentOfURI() {
    logger.info(String.format("OS is using following file encoding : %s", SystemInfo.getFileEncoding()));
    logger.info(String.format("OS is using following file line seperator : %s", SystemInfo.getLineSeparator()));
    logger.info(String.format("OS is using following file file seperator : %s", SystemInfo.getFileSeparator()));
    String expected = "Alice\nBob";
    logger.info(String.format("The following String is expected : %s", expected));
    String result1 = IOUtil.getContentOfURI("file:com/rapiddweller/common/names.csv");
    logger.info(String.format("The following String we got : %s", result1));
    String result2 = IOUtil.getContentOfURI("file://com/rapiddweller/common/names.csv");
    logger.info(String.format("The following String we got : %s", result2));
    String result3 = IOUtil.getContentOfURI("com/rapiddweller/common/names.csv");
    logger.info(String.format("The following String we got : %s", result3));
    assertEquals(expected, result1);
    assertEquals(expected, result2);
    assertEquals(expected, result3);
  }

  @Test
  public void testGetContentOfURI2() {
    assertThrows(ConfigurationError.class, () -> IOUtil.getContentOfURI("Uri"));
  }

  @Test
  public void testGetContentOfURI3() {
    assertEquals("", IOUtil.getContentOfURI("string://"));
  }

  @Test
  public void testGetContentOfURI4() {
    assertEquals("com\nlog4j2.xml\n", IOUtil.getContentOfURI("file:"));
  }

  @Test
  public void testGetContentOfURI5() {
    assertThrows(ConfigurationError.class, () -> IOUtil.getContentOfURI("Uri", "UTF-8"));
  }

  @Test
  public void testGetContentOfURI6() {
    assertEquals("", IOUtil.getContentOfURI("string://", "UTF-8"));
  }

  @Test
  public void testGetContentOfURI7() {
    assertEquals("com\nlog4j2.xml\n", IOUtil.getContentOfURI("file:", "UTF-8"));
  }

  @Test
  public void testGetContentOfURI8() {
    assertThrows(ConfigurationError.class, () -> IOUtil.getContentOfURI("Uri", null));
  }

  @Test
  public void testReadAndClose() {
    assertEquals("", IOUtil.readAndClose(Reader.nullReader()));
    assertEquals("S", IOUtil.readAndClose(new StringReader("S")));
  }

  @Test
  public void testReadTextLines() {
    assertThrows(ConfigurationError.class, () -> IOUtil.readTextLines("Uri", true));
    assertEquals(0, IOUtil.readTextLines("string://", true).length);
    assertEquals(2, IOUtil.readTextLines("file:", true).length);
    assertEquals(2, IOUtil.readTextLines("file://", false).length);
  }

  @Test
  public void testGetInputStreamForURIOfFileProtocol() throws Exception {
    InputStream stream = null;
    BufferedReader reader;
    try {
      stream = IOUtil.getInputStreamForURI("com/rapiddweller/common/names.csv");
      reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.ISO_8859_1));
      assertEquals("Alice", reader.readLine());
      assertEquals("Bob", reader.readLine());
      assertNull(reader.readLine());
    } finally {
      IOUtil.close(stream);
    }
  }

  @Test
  public void testGetInputStreamForURIOfFtpProtocol() throws Exception {
    if (!DatabeneTestUtil.isOnline()) {
      logger.info("offline mode: skipping test testGetInputStreamForURIOfFtpProtocol()");
      return;
    }
    InputStream stream;
    BufferedReader reader = null;
    try {
      stream = IOUtil.getInputStreamForURI(DatabeneTestUtil.ftpDownloadUrl());
      reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.ISO_8859_1));
      assertEquals("test", reader.readLine());
      assertNull(reader.readLine());
    } finally {
      IOUtil.close(reader);
    }
  }

  @Test
  public void testGetInputStreamForURIOfStringProtocol() throws Exception {
    InputStream stream = IOUtil.getInputStreamForURI("string://Alice,Bob\r\nCharly");
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, SystemInfo.getCharset()));
    assertEquals("Alice,Bob", reader.readLine());
    assertEquals("Charly", reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  @Test
  public void testStripOffProtocolFromUri() {
    assertEquals("Uri", IOUtil.stripOffProtocolFromUri("Uri"));
    assertEquals("", IOUtil.stripOffProtocolFromUri("://"));
    assertEquals("", IOUtil.stripOffProtocolFromUri("file:"));
  }

  @Test
  public void testIsFileUri() {
    assertTrue(IOUtil.isFileUri("Uri"));
    assertTrue(IOUtil.isFileUri("file:"));
    assertFalse(IOUtil.isFileUri("://"));
  }

  @Test
  public void testGetInputStreamForUriReference() {
    assertThrows(ConfigurationError.class,
        () -> IOUtil.getInputStreamForUriReference("Local Uri", "Context Uri", true));
    assertThrows(ConfigurationError.class, () -> IOUtil.getInputStreamForUriReference("://", "Context Uri", true));
    assertTrue(
        IOUtil.getInputStreamForUriReference("file:", "Context Uri", true) instanceof java.io.ByteArrayInputStream);
    assertThrows(ConfigurationError.class, () -> IOUtil.getInputStreamForUriReference("~", "Context Uri", true));
    assertThrows(ConfigurationError.class, () -> IOUtil.getInputStreamForUriReference("#", "Context Uri", true));
    assertThrows(ConfigurationError.class, () -> IOUtil.getInputStreamForUriReference("Local Uri", null, true));
    assertThrows(ConfigurationError.class, () -> IOUtil.getInputStreamForUriReference("Local Uri", "file:", true));
    assertThrows(ConfigurationError.class,
        () -> IOUtil.getInputStreamForUriReference("getInputStreamForURI({}, {})", null, true));
    assertTrue(IOUtil.getInputStreamForUriReference("string://", null, true) instanceof java.io.ByteArrayInputStream);
    assertTrue(IOUtil.getInputStreamForUriReference("", "http://", true) instanceof java.io.ByteArrayInputStream);
    assertTrue(
        IOUtil.getInputStreamForUriReference("file://", "http://", true) instanceof java.io.ByteArrayInputStream);
    assertThrows(InternalErrorException.class,
        () -> IOUtil.getInputStreamForUriReference("getInputStreamForURI({}, {})", "string://", true));
  }

  @Test
  public void testGetResourceAsStream() {
    assertThrows(ConfigurationError.class, () -> IOUtil.getResourceAsStream("Name", true));
    assertTrue(IOUtil.getResourceAsStream("/", true) instanceof java.io.ByteArrayInputStream);
    assertTrue(IOUtil.getResourceAsStream("", true) instanceof java.io.ByteArrayInputStream);
    assertNull(IOUtil.getResourceAsStream("Name", false));
  }

  @Test
  public void testResolveRelativeUri() {
    String SEP = File.separator;
    assertEquals("test.html", IOUtil.resolveRelativeUri("test.html", null));
    assertEquals("test.html", IOUtil.resolveRelativeUri("test.html", ""));
    assertEquals("http://test.com/main/test.html", IOUtil.resolveRelativeUri("test.html", "http://test.com/main/"));
    assertEquals("http://test.com/main/sub/test.html", IOUtil.resolveRelativeUri("sub/test.html", "http://test.com/main/"));
    assertEquals("http://test.com/test.html", IOUtil.resolveRelativeUri("/test.html", "http://test.com/main/"));
    assertEquals("http://test.com/main/test.html", IOUtil.resolveRelativeUri("./test.html", "http://test.com/main/"));
    assertEquals("http://test.com/other/test.html", IOUtil.resolveRelativeUri("../other/test.html", "http://test.com/main/"));
    assertEquals("http://test.com/other/test.html", IOUtil.resolveRelativeUri("/other/test.html", "http://test.com/main/"));
    assertEquals("sub/test.html", IOUtil.resolveRelativeUri("sub/test.html", ""));
    assertEquals("../test.html", IOUtil.resolveRelativeUri("../test.html", ""));
    assertEquals("file:///test.html", IOUtil.resolveRelativeUri("file:///test.html", "http://bla.txt"));
    assertEquals("file:/test.html", IOUtil.resolveRelativeUri("file:/test.html", "http://bla.txt"));
    assertEquals(SEP + "Users" + SEP + "name" + SEP + "text.txt", IOUtil.resolveRelativeUri("text.txt", "/Users/name/")); // SEP for Windows build
    assertEquals("/Users/user2/text.txt", IOUtil.resolveRelativeUri("/Users/user2/text.txt", "/Users/user1/"));
    assertEquals(SEP + "Users" + SEP + "temp" + SEP + "my.dtd", IOUtil.resolveRelativeUri("file:my.dtd", "/Users/temp/")); // SEP for Windows build
    assertEquals("~/temp/my.dtd", IOUtil.resolveRelativeUri("~/temp/my.dtd", "/Users/temp/"));
    assertEquals("c:\\temp\\test.txt", IOUtil.resolveRelativeUri("c:\\temp\\test.txt", "c:\\test\\base.txt"));
  }

  @Test
  public void testIsAbsoluteRef() {
    assertFalse(IOUtil.isAbsoluteRef("Uri", "Context Uri"));
    assertFalse(IOUtil.isAbsoluteRef("://", "Context Uri"));
    assertFalse(IOUtil.isAbsoluteRef("file:", "Context Uri"));
    assertTrue(IOUtil.isAbsoluteRef("/", "Context Uri"));
    assertTrue(IOUtil.isAbsoluteRef("~", "Context Uri"));
    assertTrue(IOUtil.isAbsoluteRef("Uri", null));
    assertFalse(IOUtil.isAbsoluteRef("Uri", "file:"));
    assertFalse(IOUtil.isAbsoluteRef("file://", "Context Uri"));
  }

  @Test
  public void testGetParentUri() {
    assertNull(IOUtil.getParentUri(null));
    assertNull(IOUtil.getParentUri(""));
    assertEquals("file://test/", IOUtil.getParentUri("file://test/text.txt"));
    assertEquals("test/", IOUtil.getParentUri("test/text.txt"));
    assertEquals("http://test.de/", IOUtil.getParentUri("http://test.de/text.txt"));
    char fileSeparator = SystemInfo.getFileSeparator();
    System.setProperty("file.separator", "\\");
    try {
      assertEquals("C:\\test\\", IOUtil.getParentUri("C:\\test\\bla.txt"));
    } finally {
      System.setProperty("file.separator", String.valueOf(fileSeparator));
    }
  }

  @Test
  public void testGetParentUri2() {
    assertEquals("./", IOUtil.getParentUri("Uri"));
  }

  @Test
  public void testGetParentUri3() {
    assertEquals("://", IOUtil.getParentUri("://"));
  }

  @Test
  public void testGetParentUri4() {
    assertNull(IOUtil.getParentUri(null));
  }

  @Test
  public void testGetParentUri5() {
    assertEquals("file./", IOUtil.getParentUri("file:"));
  }

  @Test
  public void testGetParentUri6() {
    assertEquals("file://", IOUtil.getParentUri("file://"));
  }

  @Test
  public void testGetProtocol() {
    assertNull(null, IOUtil.getProtocol(null));
    assertNull(null, IOUtil.getProtocol(""));
    assertNull(null, IOUtil.getProtocol("/test/text.txt"));
    assertEquals("http", IOUtil.getProtocol("http://files/index.dat"));
    assertEquals("file", IOUtil.getProtocol("file:///files/index.dat"));
    assertEquals("xyz", IOUtil.getProtocol("xyz:///files/index.dat"));
    assertNull(IOUtil.getProtocol("Uri"));
    assertNull(IOUtil.getProtocol(null));
    assertEquals("file", IOUtil.getProtocol("file:"));
    assertEquals("file", IOUtil.getProtocol("file://"));
  }

  @Test
  public void testGetReaderForURI_File() throws IOException {
    BufferedReader reader = IOUtil.getReaderForURI("com/rapiddweller/common/names.csv");
    assertEquals("Alice", reader.readLine());
    assertEquals("Bob", reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  @Test
  public void testGetReaderForURI_StringProtocol() throws Exception {
    BufferedReader reader = IOUtil.getReaderForURI("string://Alice,Bob\r\nCharly");
    assertEquals("Alice,Bob", reader.readLine());
    assertEquals("Charly", reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  @Test
  public void testGetReaderForURI_EmptyFile() throws IOException {
    BufferedReader reader = IOUtil.getReaderForURI("com/rapiddweller/common/empty.txt");
    assertEquals(-1, reader.read());
    reader.close();
  }

  @Test
  public void testTransferStream() {
    ByteArrayInputStream in = new ByteArrayInputStream("abcdefg".getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    IOUtil.transfer(in, out);
    assertArrayEquals("abcdefg".getBytes(), out.toByteArray());
  }

  @Test
  public void testTransferReaderWriter() {
    StringReader in = new StringReader("abcdefg");
    StringWriter out = new StringWriter();
    IOUtil.transfer(in, out);
    assertEquals("abcdefg", out.toString());
  }

  @Test
  public void testReadProperties() {
    Map<String, String> properties = IOUtil.readProperties("com/rapiddweller/common/test.properties");
    assertEquals(5, properties.size());
    assertEquals("b", properties.get("a"));
    assertEquals("z", properties.get("x.y"));
    assertEquals("ab", properties.get("z"));
    assertEquals("a bc", properties.get("q"));
    assertEquals("a\tb", properties.get("bla")); // unescaping
  }

  @Test
  public void testWriteProperties() throws IOException {
    File file = File.createTempFile("IOUtilTest", "properties");
    try {
      Map<String, String> properties = new HashMap<>();
      properties.put("a", "1");
      properties.put("b", "2");
      IOUtil.writeProperties(properties, file.getAbsolutePath());
      Properties check = new Properties();
      InputStream stream = new FileInputStream(file);
      check.load(stream);
      assertEquals(2, check.size());
      assertEquals("1", check.getProperty("a"));
      assertEquals("2", check.getProperty("b"));
      stream.close();
    } finally {
      FileUtil.deleteIfExists(file);
    }
  }

  @Test
  public void testGetBinaryContentOfUri() {
    assertThrows(ConfigurationError.class, () -> IOUtil.getBinaryContentOfUri("Uri"));
    assertEquals(0, IOUtil.getBinaryContentOfUri("string://").length);
  }

  @Test
  public void testGetBinaryContentOfUri2() {
    byte[] actualBinaryContentOfUri = IOUtil.getBinaryContentOfUri("file:");
    assertEquals(15, actualBinaryContentOfUri.length);
    assertEquals((byte) 99, actualBinaryContentOfUri[0]);
    assertEquals((byte) 111, actualBinaryContentOfUri[1]);
    assertEquals((byte) 109, actualBinaryContentOfUri[2]);
    assertEquals((byte) 10, actualBinaryContentOfUri[3]);
    assertEquals((byte) 108, actualBinaryContentOfUri[4]);
    assertEquals((byte) 111, actualBinaryContentOfUri[5]);
    assertEquals((byte) 50, actualBinaryContentOfUri[9]);
    assertEquals((byte) 46, actualBinaryContentOfUri[10]);
    assertEquals((byte) 120, actualBinaryContentOfUri[11]);
    assertEquals((byte) 109, actualBinaryContentOfUri[12]);
    assertEquals((byte) 108, actualBinaryContentOfUri[13]);
    assertEquals((byte) 10, actualBinaryContentOfUri[14]);
  }

  @Test
  public void testEncodeUrl() {
    assertEquals("https%3A%2F%2Fexample.org%2Fexample", IOUtil.encodeUrl("https://example.org/example"));
    assertEquals("https%3A%2F%2Fexample.org%2Fexample", IOUtil.encodeUrl("https://example.org/example", "UTF-8"));
    assertThrows(InternalErrorException.class,
        () -> IOUtil.encodeUrl("https://example.org/example", "https://example.org/example"));
  }

  @Test
  public void testEncoding() throws MalformedURLException {
    URL URL = new URL("http://databene.org/");
    String ENCODING = "iso-8859-15";
    URLConnection c1 = new URLConnectionMock(URL, ENCODING, null);
    assertEquals(ENCODING, IOUtil.encoding(c1, ""));
    URLConnection c2 = new URLConnectionMock(URL, null, "charset:" + ENCODING);
    assertEquals(ENCODING, IOUtil.encoding(c2, ""));
    URLConnection c3 = new URLConnectionMock(URL, null, null);
    assertEquals(ENCODING, IOUtil.encoding(c3, ENCODING));
    URLConnection c4 = new URLConnectionMock(URL, null, null);
    assertEquals(SystemInfo.getFileEncoding(), IOUtil.encoding(c4, null));
  }

  @Test
  public void testOpenOutputStreamForURI_FileProtocol() throws Exception {
    String filename = "target" + SystemInfo.getFileSeparator() + "testOpenOutputStreamForURI.txt";
    checkFileOutputStream(filename, IOUtil.openOutputStreamForURI(filename));
    checkFileOutputStream(filename, IOUtil.openOutputStreamForURI("file:" + filename));
  }

  @Test
  public void testOpenOutputStreamForURI_FtpProtocol() throws Exception {
    if (!DatabeneTestUtil.isOnline()) {
      logger.info("Offline mode: skipping testOpenOutputStreamForURI_FtpProtocol()");
      return;
    }
    String uri = DatabeneTestUtil.ftpUploadUrl();
    OutputStream out = IOUtil.openOutputStreamForURI(uri);
    try {
      out.write("test".getBytes());
      IOUtil.close(out);
      assertEquals("test", IOUtil.getContentOfURI(uri));
    } finally {
      IOUtil.close(out);
    }
  }

  @Test
  public void testCopy_jarUrl() {
    URL url = Test.class.getClassLoader().getResource("org/junit");
    File targetFolder = new File("target/IOUtilTest");
    FileUtil.ensureDirectoryExists(targetFolder);
    IOUtil.copyDirectory(url, targetFolder, null);
    assertTrue(ArrayUtil.contains("Test.class", targetFolder.list()));
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static void checkFileOutputStream(String filename, OutputStream out) throws IOException {
    try {
      out.write("test".getBytes());
      IOUtil.close(out);
      assertEquals("test", IOUtil.getContentOfURI(filename));
    } finally {
      IOUtil.close(out);
      FileUtil.deleteIfExists(new File(filename));
    }
  }

  private static class URLConnectionMock extends URLConnection {

    String encoding;
    String contentTypeHeader;

    protected URLConnectionMock(URL url, String encoding, String contentTypeHeader) {
      super(url);
      this.encoding = encoding;
      this.contentTypeHeader = contentTypeHeader;
    }

    @Override
    public void connect() {
      throw ExceptionFactory.getInstance().programmerUnsupported("connect() not implemented");
    }

    @Override
    public String getContentEncoding() {
      return encoding;
    }

    @Override
    public String getHeaderField(String name) {
      if ("Content-Type".equals(name)) {
        return contentTypeHeader;
      } else {
        return null;
      }
    }
  }

}
