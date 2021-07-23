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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link VMInfo} class.
 * Created: 21.06.2007 08:35:45
 *
 * @author Volker Bergmann
 */
public class VMInfoTest {

  @Test
  public void testGetJavaVersion() {
    String expectedJavaVersion = System.getProperty("java.version");
    assertEquals(expectedJavaVersion, VMInfo.getJavaVersion());
  }

  @Test
  public void testJavaVendor2() {
    String expectedJavaVendorResult = System.getProperty("java.vm.vendor");
    assertEquals(expectedJavaVendorResult, VMInfo.getJavaVendor());
  }

  @Test
  public void testGetJavaVendor() {
    String expectedJavaVendor = System.getProperty("java.vm.vendor");
    assertEquals(expectedJavaVendor, VMInfo.getJavaVendor());
  }

  @Test
  public void testJavaVendorUrl2() {
    String expectedJavaVendorUrlResult = System.getProperty("java.vendor.url");
    assertEquals(expectedJavaVendorUrlResult, VMInfo.getJavaVendorUrl());
  }

  @Test
  public void testGetJavaVendorUrl() {
    String expectedJavaVendorUrl = System.getProperty("java.vendor.url");
    assertEquals(expectedJavaVendorUrl, VMInfo.getJavaVendorUrl());
  }

  @Test
  public void testJavaSpecificationVersion2() {
    assertEquals("11", VMInfo.getJavaSpecificationVersion());
  }

  @Test
  public void testGetJavaSpecificationVersion() {
    assertEquals("11", VMInfo.getJavaSpecificationVersion());
  }

  @Test
  public void testJavaSpecificationVendor2() {
    String expectedJavaSpecificationVendorResult = System.getProperty("java.specification.vendor");
    assertEquals(expectedJavaSpecificationVendorResult, VMInfo.getJavaSpecificationVendor());
  }

  @Test
  public void testGetJavaSpecificationVendor() {
    String expectedJavaSpecificationVendor = System.getProperty("java.specification.vendor");
    assertEquals(expectedJavaSpecificationVendor, VMInfo.getJavaSpecificationVendor());
  }

  @Test
  public void testJavaSpecificationName2() {
    String expectedJavaSpecificationNameResult = System.getProperty("java.specification.name");
    assertEquals(expectedJavaSpecificationNameResult, VMInfo.getJavaSpecificationName());
  }

  @Test
  public void testGetJavaSpecificationName() {
    String expectedJavaSpecificationName = System.getProperty("java.specification.name");
    assertEquals(expectedJavaSpecificationName, VMInfo.getJavaSpecificationName());
  }

  @Test
  public void testJavaClassVersion2() {
    assertEquals("55.0", VMInfo.getJavaClassVersion());
  }

  @Test
  public void testGetJavaClassVersion() {
    assertEquals("55.0", VMInfo.getJavaClassVersion());
  }

  @Test
  public void testJavaCompiler2() {
    assertNull(VMInfo.getJavaCompiler());
  }

  @Test
  public void testGetJavaCompiler() {
    assertNull(VMInfo.getJavaCompiler());
  }

  @Test
  public void testJavaHome2() {
    String expectedJavaHomeResult = System.getProperty("java.home");
    assertEquals(expectedJavaHomeResult, VMInfo.getJavaHome());
  }

  @Test
  public void testGetJavaHome() {
    String expectedJavaHome = System.getProperty("java.home");
    assertEquals(expectedJavaHome, VMInfo.getJavaHome());
  }

  @Test
  public void testExtDirs() {
    assertNull(VMInfo.getExtDirs());
  }

  @Test
  public void testGetExtDirs() {
    assertNull(VMInfo.getExtDirs());
  }

  @Test
  public void testLibraryPath2() {
    String expectedLibraryPathResult = System.getProperty("java.library.path");
    assertEquals(expectedLibraryPathResult, VMInfo.getLibraryPath());
  }

  @Test
  public void testGetLibraryPath() {
    String expectedLibraryPath = System.getProperty("java.library.path");
    assertEquals(expectedLibraryPath, VMInfo.getLibraryPath());
  }

  @Test
  public void testJavaVmSpecificationVersion() {
    assertEquals("11", VMInfo.getJavaVmSpecificationVersion());
  }

  @Test
  public void testGetJavaVmSpecificationVersion() {
    assertEquals("11", VMInfo.getJavaVmSpecificationVersion());
  }

  @Test
  public void testJavaVmSpecificationVendor() {
    String expectedJavaVmSpecificationVendorResult = System.getProperty("java.specification.vendor");
    assertEquals(expectedJavaVmSpecificationVendorResult, VMInfo.getJavaVmSpecificationVendor());
  }

  @Test
  public void testGetJavaVmSpecificationVendor() {
    String expectedJavaVmSpecificationVendor = System.getProperty("java.specification.vendor");
    assertEquals(expectedJavaVmSpecificationVendor, VMInfo.getJavaVmSpecificationVendor());
  }

  @Test
  public void testJavaVmSpecificationName() {
    String expectedJavaVmSpecificationNameResult = System.getProperty("java.vm.specification.name");
    assertEquals(expectedJavaVmSpecificationNameResult, VMInfo.getJavaVmSpecificationName());
  }

  @Test
  public void testGetJavaVmSpecificationName() {
    String expectedJavaVmSpecificationName = System.getProperty("java.vm.specification.name");
    assertEquals(expectedJavaVmSpecificationName, VMInfo.getJavaVmSpecificationName());
  }

  @Test
  public void testJavaVmVersion() {
    String expectedJavaVmVersionResult = System.getProperty("java.runtime.version");
    assertEquals(expectedJavaVmVersionResult, VMInfo.getJavaVmVersion());
  }

  @Test
  public void testGetJavaVmVersion() {
    String expectedJavaVmVersion = System.getProperty("java.runtime.version");
    assertEquals(expectedJavaVmVersion, VMInfo.getJavaVmVersion());
  }

  @Test
  public void testJavaVmVendor() {
    String expectedJavaVmVendorResult = System.getProperty("java.vm.vendor");
    assertEquals(expectedJavaVmVendorResult, VMInfo.getJavaVmVendor());
  }

  @Test
  public void testGetJavaVmVendor() {
    String expectedJavaVmVendor = System.getProperty("java.vm.vendor");
    assertEquals(expectedJavaVmVendor, VMInfo.getJavaVmVendor());
  }

  @Test
  public void testJavaVmName() {
    String expectedJavaVmNameResult = System.getProperty("java.vm.name");
    assertEquals(expectedJavaVmNameResult, VMInfo.getJavaVmName());
  }

  @Test
  public void testGetJavaVmName() {
    String expectedJavaVmName = System.getProperty("java.vm.name");
    assertEquals(expectedJavaVmName, VMInfo.getJavaVmName());
  }

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
