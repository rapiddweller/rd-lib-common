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

import com.rapiddweller.common.converter.NoOpConverter;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.file.Paths;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Tests the {@link IOUtil} class.
 * Created: 21.06.2007 08:31:28
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class IOUtilTest {

    private static final Logger LOGGER = LogManager.getLogger(IOUtilTest.class);

    @Test
    public void testClose() {
        IOUtil.close(new ByteArrayInputStream(new byte[0]));
        IOUtil.close(new ByteArrayOutputStream());
        IOUtil.close(new StringWriter());
        IOUtil.close(new StringReader("abc"));
    }

    @Test
    public void testFlush() {
        IOUtil.flush(new StringWriter());
    }

    @Test
    public void testLocalFilename() {
        assertEquals("product-info.jsp", IOUtil.localFilename("http://localhost:80/shop/product-info.jsp"));
        assertEquals("Uri", IOUtil.localFilename("Uri"));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> IOUtil.localFilename("/"));
    }

    @Test
    public void testIsURIAvailable() {
        assertFalse(IOUtil.isURIAvailable("Uri"));
        assertTrue(IOUtil.isURIAvailable("string://"));
        assertTrue(IOUtil.isURIAvailable("file:"));
        assertFalse(IOUtil.isURIAvailable("://"));
        assertTrue(IOUtil.isURIAvailable("file://"));
        assertFalse(IOUtil.isURIAvailable("/"));
    }

    @Test
    public void testIsURIAvaliable() {
        assertTrue(IOUtil.isURIAvailable("file://com/rapiddweller/common/names.csv"));
        assertTrue(IOUtil.isURIAvailable("file:com/rapiddweller/common/names.csv"));
        assertTrue(IOUtil.isURIAvailable("com/rapiddweller/common/names.csv"));
        assertFalse(IOUtil.isURIAvailable("com/rapiddweller/common/not.an.existing.file"));
    }

    @Test
    public void testGetContentOfURI() throws IOException {
        LOGGER.info(String.format("OS is using following file encoding : %s", SystemInfo.getFileEncoding()));
        LOGGER.info(String.format("OS is using following file line seperator : %s", SystemInfo.getLineSeparator()));
        LOGGER.info(String.format("OS is using following file file seperator : %s", SystemInfo.getFileSeparator()));
        String expected = "Alice\nBob";
        LOGGER.info(String.format("The following String is expected : %s", expected));
        String result1 = IOUtil.getContentOfURI("file:com/rapiddweller/common/names.csv");
        LOGGER.info(String.format("The following String we got : %s", result1));
        String result2 = IOUtil.getContentOfURI("file://com/rapiddweller/common/names.csv");
        LOGGER.info(String.format("The following String we got : %s", result2));
        String result3 = IOUtil.getContentOfURI("com/rapiddweller/common/names.csv");
        LOGGER.info(String.format("The following String we got : %s", result3));
        assertEquals(expected, result1);
        assertEquals(expected, result2);
        assertEquals(expected, result3);
    }

    @Test
    public void testGetContentOfURI2() throws IOException {
        assertThrows(ConfigurationError.class, () -> IOUtil.getContentOfURI("Uri"));
    }

    @Test
    public void testGetContentOfURI3() throws IOException {
        assertEquals("", IOUtil.getContentOfURI("string://"));
    }

    @Test
    public void testGetContentOfURI4() throws IOException {
        assertEquals("com\nlog4j2.xml\n", IOUtil.getContentOfURI("file:"));
    }

    @Test
    public void testGetContentOfURI5() throws IOException {
        assertThrows(ConfigurationError.class, () -> IOUtil.getContentOfURI("Uri", "UTF-8"));
    }

    @Test
    public void testGetContentOfURI6() throws IOException {
        assertEquals("", IOUtil.getContentOfURI("string://", "UTF-8"));
    }

    @Test
    public void testGetContentOfURI7() throws IOException {
        assertEquals("com\nlog4j2.xml\n", IOUtil.getContentOfURI("file:", "UTF-8"));
    }

    @Test
    public void testGetContentOfURI8() throws IOException {
        assertThrows(ConfigurationError.class, () -> IOUtil.getContentOfURI("Uri", null));
    }

    @Test
    public void testReadAndClose() throws IOException {
        assertEquals("", IOUtil.readAndClose(Reader.nullReader()));
        assertEquals("S", IOUtil.readAndClose(new StringReader("S")));
    }

    @Test
    public void testReadTextLines() throws IOException {
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
            LOGGER.info("offline mode: skipping test testGetInputStreamForURIOfFtpProtocol()");
            return;
        }
        InputStream stream = null;
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
    public void testGetInputStreamForURL() throws IOException {
        assertTrue(IOUtil.getInputStreamForURL(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt")
                .toUri()
                .toURL()) instanceof java.io.ByteArrayInputStream);
    }

    @Test
    public void testGetInputStreamForUriReference() throws IOException {
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
        assertThrows(IllegalArgumentException.class,
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
    public void testResolveRelativeUri() throws Exception {
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
    public void testResolveRelativeUri10() {
        assertEquals("string://", IOUtil.resolveRelativeUri("string://", "file:"));
    }

    @Test
    public void testResolveRelativeUri11() {
        assertEquals("http:/://", IOUtil.resolveRelativeUri("://", "http://"));
    }

    @Test
    public void testResolveRelativeUri12() {
        assertEquals("/:", IOUtil.resolveRelativeUri("://", "file://"));
    }

    @Test
    public void testResolveRelativeUri2() {
        assertEquals("Context Uri/Relative Uri", IOUtil.resolveRelativeUri("Relative Uri", "Context Uri"));
    }

    @Test
    public void testResolveRelativeUri3() {
        assertEquals("Context Uri/:", IOUtil.resolveRelativeUri("://", "Context Uri"));
    }

    @Test
    public void testResolveRelativeUri4() {
        assertEquals("/", IOUtil.resolveRelativeUri("/", "Context Uri"));
    }

    @Test
    public void testResolveRelativeUri5() {
        assertEquals("~", IOUtil.resolveRelativeUri("~", "Context Uri"));
    }

    @Test
    public void testResolveRelativeUri6() {
        assertEquals("Relative Uri", IOUtil.resolveRelativeUri("Relative Uri", null));
    }

    @Test
    public void testResolveRelativeUri7() {
        assertEquals("/Relative Uri", IOUtil.resolveRelativeUri("Relative Uri", "file:"));
    }

    @Test
    public void testResolveRelativeUri8() {
        assertEquals("Relative Uri", IOUtil.resolveRelativeUri("Relative Uri", ""));
    }

    @Test
    public void testResolveRelativeUri9() {
        assertThrows(IllegalArgumentException.class, () -> IOUtil.resolveRelativeUri("://", "string://"));
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
        assertEquals(null, IOUtil.getParentUri(null));
        assertEquals(null, IOUtil.getParentUri(""));
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
        assertEquals(null, IOUtil.getProtocol(null));
        assertEquals(null, IOUtil.getProtocol(""));
        assertEquals(null, IOUtil.getProtocol("/test/text.txt"));
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
    public void testTransferStream() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("abcdefg".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtil.transfer(in, out);
        assertTrue(Arrays.equals("abcdefg".getBytes(), out.toByteArray()));
    }

    @Test
    public void testTransfer() throws IOException {
        InputStream in = InputStream.nullInputStream();
        assertEquals(0, IOUtil.transfer(in, OutputStream.nullOutputStream()));
    }

    @Test
    public void testTransfer2() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(
                new byte[]{65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65});
        assertEquals(24, IOUtil.transfer(in, OutputStream.nullOutputStream()));
    }

    @Test
    public void testTransfer3() throws IOException {
        assertEquals(0, IOUtil.transfer(new ByteArrayInputStream(new byte[]{}), (OutputStream) null));
    }

    @Test
    public void testTransfer4() throws IOException {
        Reader reader = Reader.nullReader();
        assertEquals(0, IOUtil.transfer(reader, Writer.nullWriter()));
    }

    @Test
    public void testTransfer5() throws IOException {
        StringReader reader = new StringReader("S");
        assertEquals(1, IOUtil.transfer(reader, Writer.nullWriter()));
    }

    @Test
    public void testTransfer6() throws IOException {
        StringReader stringReader = new StringReader("S");
        stringReader.read(new char[]{'\u0000', '\u0000', '\u0000', '\u0000'}, 0, 3);
        assertEquals(0, IOUtil.transfer(stringReader, (Writer) null));
    }

    @Test
    public void testTransferReaderWriter() throws IOException {
        StringReader in = new StringReader("abcdefg");
        StringWriter out = new StringWriter();
        IOUtil.transfer(in, out);
        assertEquals("abcdefg", out.toString());
    }

    @Test
    public void testReadProperties() throws IOException {
        Map<String, String> properties = IOUtil.readProperties("com/rapiddweller/common/test.properties");
        assertEquals(5, properties.size());
        assertEquals("b", properties.get("a"));
        assertEquals("z", properties.get("x.y"));
        assertEquals("ab", properties.get("z"));
        assertEquals("a bc", properties.get("q"));
        assertEquals("a\tb", properties.get("bla")); // unescaping
    }

    @Test
    public void testReadProperties10() throws IOException {
        assertTrue(IOUtil.<Object>readProperties("file:", new NoOpConverter(), "UTF-8").isEmpty());
    }

    @Test
    public void testReadProperties11() throws IOException {
        assertThrows(ConfigurationError.class, () -> IOUtil.<Object>readProperties("foo.txt", new NoOpConverter(), null));
    }

    @Test
    public void testReadProperties12() throws IOException {
        assertThrows(ConfigurationError.class, () -> IOUtil.readProperties("foo.txt", "UTF-8"));
    }

    @Test
    public void testReadProperties13() throws IOException {
        assertTrue(IOUtil.readProperties("string://", "UTF-8").isEmpty());
    }

    @Test
    public void testReadProperties14() throws IOException {
        assertTrue(IOUtil.readProperties("file:", "UTF-8").isEmpty());
    }

    @Test
    public void testReadProperties2() throws IOException {
        assertThrows(ConfigurationError.class, () -> IOUtil.readProperties("foo.txt"));
    }

    @Test
    public void testReadProperties3() throws IOException {
        assertTrue(IOUtil.readProperties("string://").isEmpty());
    }

    @Test
    public void testReadProperties4() throws IOException {
        assertTrue(IOUtil.readProperties("file:").isEmpty());
    }

    @Test
    public void testReadProperties5() throws IOException {
        assertThrows(ConfigurationError.class, () -> IOUtil.<Object>readProperties("foo.txt", new NoOpConverter()));
    }

    @Test
    public void testReadProperties6() throws IOException {
        assertTrue(IOUtil.<Object>readProperties("string://", new NoOpConverter()).isEmpty());
    }

    @Test
    public void testReadProperties7() throws IOException {
        assertTrue(IOUtil.<Object>readProperties("file:", new NoOpConverter()).isEmpty());
    }

    @Test
    public void testReadProperties8() throws IOException {
        assertThrows(ConfigurationError.class,
                () -> IOUtil.<Object>readProperties("foo.txt", new NoOpConverter(), "UTF-8"));
    }

    @Test
    public void testReadProperties9() throws IOException {
        assertTrue(IOUtil.<Object>readProperties("string://", new NoOpConverter(), "UTF-8").isEmpty());
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
            file.delete();
        }
    }

    @Test
    public void testGetBinaryContentOfUri() throws IOException {
        assertThrows(ConfigurationError.class, () -> IOUtil.getBinaryContentOfUri("Uri"));
        assertEquals(0, IOUtil.getBinaryContentOfUri("string://").length);
    }

    @Test
    public void testGetBinaryContentOfUri2() throws IOException {
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
        assertThrows(IllegalArgumentException.class,
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
            LOGGER.info("Offline mode: skipping testOpenOutputStreamForURI_FtpProtocol()");
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
    public void testCopy_jarUrl() throws Exception {
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

    private class URLConnectionMock extends URLConnection {

        String encoding;
        String contentTypeHeader;

        protected URLConnectionMock(URL url, String encoding, String contentTypeHeader) {
            super(url);
            this.encoding = encoding;
            this.contentTypeHeader = contentTypeHeader;
        }

        @Override
        public void connect() {
            throw new UnsupportedOperationException("connect() not implemented");
        }

        @Override
        public String getContentEncoding() {
            return encoding;
        }

        @Override
        public String getHeaderField(String name) {
            if ("Content-Type".equals(name))
                return contentTypeHeader;
            else
                return null;
        }
    }

}
