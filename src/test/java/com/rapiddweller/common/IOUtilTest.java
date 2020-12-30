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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
 * @since 0.1
 * @author Volker Bergmann
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
        assertEquals("Alice,Bob\r\nCharly", IOUtil.getContentOfURI("file:com/rapiddweller/common/names.csv"));
        assertEquals("Alice,Bob\r\nCharly", IOUtil.getContentOfURI("file://com/rapiddweller/common/names.csv"));
        assertEquals("Alice,Bob\r\nCharly", IOUtil.getContentOfURI("com/rapiddweller/common/names.csv"));
    }

	@Test    
    public void testGetInputStreamForURIOfFileProtocol() throws Exception {
        InputStream stream = null;
        BufferedReader reader;
        try {
	        stream = IOUtil.getInputStreamForURI("com/rapiddweller/common/names.csv");
	        reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.ISO_8859_1));
	        assertEquals("Alice,Bob", reader.readLine());
	        assertEquals("Charly", reader.readLine());
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
    public void testGetProtocol() {
    	assertEquals(null, IOUtil.getProtocol(null));
    	assertEquals(null, IOUtil.getProtocol(""));
    	assertEquals(null, IOUtil.getProtocol("/test/text.txt"));
    	assertEquals("http", IOUtil.getProtocol("http://files/index.dat"));
    	assertEquals("file", IOUtil.getProtocol("file:///files/index.dat"));
    	assertEquals("xyz", IOUtil.getProtocol("xyz:///files/index.dat"));
    }

	@Test    
    public void testGetReaderForURI_File() throws IOException {
        BufferedReader reader = IOUtil.getReaderForURI("com/rapiddweller/common/names.csv");
        assertEquals("Alice,Bob", reader.readLine());
        assertEquals("Charly", reader.readLine());
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
