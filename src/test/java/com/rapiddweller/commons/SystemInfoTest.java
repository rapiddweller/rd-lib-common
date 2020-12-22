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
package com.rapiddweller.commons;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Tests the {@link SystemInfo} class.
 * Created: 21.06.2007 08:35:00
 * @author Volker Bergmann
 */
public class SystemInfoTest {

    private static final Logger logger = LogManager.getLogger(SystemInfoTest.class);

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
    }

	@Test
    public void testPathSeparator() {
        assertNotNull(SystemInfo.getPathSeparator());
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
