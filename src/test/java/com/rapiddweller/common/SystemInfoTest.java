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

import java.nio.charset.Charset;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Tests the {@link SystemInfo} class.
 * Created: 21.06.2007 08:35:00
 *
 * @author Volker Bergmann
 */
public class SystemInfoTest {

    private static final Logger logger = LogManager.getLogger(SystemInfoTest.class);

    @Test
    public void testOsName() {
        assertEquals("Linux", SystemInfo.osName());
    }

    @Test
    public void testGetOsName() {
        assertEquals("Linux", SystemInfo.getOsName());
    }

    @Test
    public void testOsArchitecture() {
        assertEquals("amd64", SystemInfo.osArchitecture());
    }

    @Test
    public void testGetOsArchitecture() {
        assertEquals("amd64", SystemInfo.getOsArchitecture());
    }

    @Test
    public void testOsVersion() {
        String expectedOsVersionResult = System.getProperty("os.version");
        assertEquals(expectedOsVersionResult, SystemInfo.osVersion());
    }

    @Test
    public void testGetOsVersion() {
        String expectedOsVersion = System.getProperty("os.version");
        assertEquals(expectedOsVersion, SystemInfo.getOsVersion());
    }

    @Test
    public void testGetLineSeparator() {
        assertEquals("\n", SystemInfo.getLineSeparator());
    }

    @Test
    public void testGetPathSeparator() {
        assertEquals(":", SystemInfo.getPathSeparator());
    }

    @Test
    public void testFileSeparator2() {
        assertEquals('/', SystemInfo.fileSeparator());
    }

    @Test
    public void testGetFileSeparator() {
        assertEquals('/', SystemInfo.getFileSeparator());
    }

    @Test
    public void testCurrentDir2() {
        String expectedCurrentDirResult = System.getProperty("user.dir");
        assertEquals(expectedCurrentDirResult, SystemInfo.currentDir());
    }

    @Test
    public void testGetCurrentDir() {
        String expectedCurrentDir = System.getProperty("user.dir");
        assertEquals(expectedCurrentDir, SystemInfo.getCurrentDir());
    }

    @Test
    public void testGetUserName() {
        assertEquals("akell", SystemInfo.getUserName());
    }

    @Test
    public void testUserHome2() {
        String expectedUserHomeResult = System.getProperty("user.home");
        assertEquals(expectedUserHomeResult, SystemInfo.userHome());
    }

    @Test
    public void testGetUserHome() {
        String expectedUserHome = System.getProperty("user.home");
        assertEquals(expectedUserHome, SystemInfo.getUserHome());
    }

    @Test
    public void testTempDir2() {
        assertEquals("/tmp", SystemInfo.tempDir());
    }

    @Test
    public void testGetTempDir() {
        assertEquals("/tmp", SystemInfo.getTempDir());
    }

    @Test
    public void testFileEncoding() {
        assertEquals("UTF-8", SystemInfo.fileEncoding());
    }

    @Test
    public void testGetFileEncoding() {
        assertEquals("UTF-8", SystemInfo.getFileEncoding());
    }

    @Test
    public void testUserLanguage2() {
        assertEquals("en", SystemInfo.userLanguage());
    }

    @Test
    public void testGetUserLanguage() {
        assertEquals("en", SystemInfo.getUserLanguage());
    }

    @Test
    public void testIsWindows() {
        assertFalse(SystemInfo.isWindows());
    }

    @Test
    public void testIsMacOsx() {
        assertFalse(SystemInfo.isMacOsx());
    }

    @Test
    public void testIsLinux() {
        assertTrue(SystemInfo.isLinux());
    }

    @Test
    public void testIsSolaris() {
        assertFalse(SystemInfo.isSolaris());
    }

    @Test
    public void testVersion() {
        assertNotNull(SystemInfo.getOsVersion());
    }

    @Test
    public void testOSArchitecture() {
        assertNotNull(SystemInfo.getOsArchitecture());
    }

    @Test
    public void testOSName() {
        assertNotNull(SystemInfo.getOsName());
    }

    @Test
    public void testLineSeparator() {
        assertNotNull(SystemInfo.getLineSeparator());
        assertEquals("\n", SystemInfo.lineSeparator());
    }

    @Test
    public void testPathSeparator() {
        assertNotNull(SystemInfo.getPathSeparator());
        assertEquals(":", SystemInfo.pathSeparator());
    }

    @Test
    public void testFileSeparator() {
        char fileSeparator = SystemInfo.getFileSeparator();
        assertEquals(File.separatorChar, fileSeparator);
    }

    @Test
    public void testCurrentDir() throws IOException {
        String currentDir = SystemInfo.getCurrentDir();
        assertNotNull(currentDir);
        assertEquals(new File(".").getCanonicalPath(), currentDir);
    }

    @Test
    public void testUserName() {
        assertNotNull(SystemInfo.getUserName());
        assertEquals("akell", SystemInfo.userName());
    }

    @Test
    public void testUserHome() {
        File userHome = new File(SystemInfo.getUserHome());
        assertTrue(userHome.exists());
    }

    @Test
    public void testTempDir() {
        String tempDir = SystemInfo.getTempDir();
        assertNotNull(tempDir);
        assertTrue(new File(tempDir).exists());
    }

    @Test
    public void testUserLanguage() {
        String userLanguage = SystemInfo.getUserLanguage();
        logger.debug("user language: " + userLanguage);
        assertNotNull(userLanguage);
    }

}
