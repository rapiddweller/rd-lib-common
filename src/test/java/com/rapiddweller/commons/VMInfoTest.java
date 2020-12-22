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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests the {@link VMInfo} class.
 * Created: 21.06.2007 08:35:45
 * @author Volker Bergmann
 */
public class VMInfoTest {
	
	@Test
    public void testJavaClassVersion() {
		String value = System.getProperty("java.class.version");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaClassVersion());
    }

	@Test
    public void testJavaCompiler() {
        String value = System.getProperty("java.compiler"); // may be null
		assertEquals(value, VMInfo.getJavaCompiler());
    }

	@Test
    public void testJavaHome() {
		String value = System.getProperty("java.home");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaHome());
    }

    @Ignore
	@Test
    public void testExtDirs() {
		String value = System.getProperty("java.ext.dirs");
		assertNotNull(value);
		assertEquals(value, VMInfo.getExtDirs());
    }

	@Test
    public void testClassPath() {
		String value = System.getProperty("java.class.path");
		assertNotNull(value);
		assertEquals(value, VMInfo.getClassPath());
    }

	@Test
    public void testLibraryPath() {
		String value = System.getProperty("java.library.path");
		assertNotNull(value);
		assertEquals(value, VMInfo.getLibraryPath());
    }

	// vendor ----------------------------------------------------------------------------------------------------------
	
	@Test
    public void testJavaVendor() { 
        String value = System.getProperty("java.vendor");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVendor());
    }

	@Test
    public void testJavaVendorUrl() {
		String value = System.getProperty("java.vendor.url");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVendorUrl());
    }

	// specification ---------------------------------------------------------------------------------------------------
	
	@Test
    public void testJavaSpecificationVersion() {
		String value = System.getProperty("java.specification.version");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaSpecificationVersion());
    }

	@Test
    public void testJavaSpecificationVendor() {
		String value = System.getProperty("java.specification.vendor");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaSpecificationVendor());
    }

	@Test
    public void testJavaSpecificationName() {
		String value = System.getProperty("java.specification.name");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaSpecificationName());
    }

	// VM --------------------------------------------------------------------------------------------------------------
	
	@Test
    public void testJavaVMName() {
		String value = System.getProperty("java.vm.name");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmName());
    }
	
	@Test
    public void testJavaVersion() {
		String value = System.getProperty("java.vm.version");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmVersion());
    }

	@Test
    public void testJavaVMVendor() {
		String value = System.getProperty("java.vm.vendor");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmVendor());
    }

	// VM specification ------------------------------------------------------------------------------------------------

	@Test
    public void testJavaVMSpecificationName() {
		String value = System.getProperty("java.vm.specification.name");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmSpecificationName());
    }

	@Test
    public void testJavaVMSpecificationVersion() {
		String value = System.getProperty("java.vm.specification.version");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmSpecificationVersion());
    }

	@Test
    public void testJavaVMSpecificationVendor() {
		String value = System.getProperty("java.vm.specification.vendor");
		assertNotNull(value);
		assertEquals(value, VMInfo.getJavaVmSpecificationVendor());
    }

}
