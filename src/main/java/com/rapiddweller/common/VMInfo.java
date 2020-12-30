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

/**
 * Provides the Java system properties related to the virtual machine. 
 * Created: 16.06.2007 13:23:56
 * @author Volker Bergmann
 */
public final class VMInfo {

    /** @return Java Runtime Environment version 
     * @deprecated Use {@link #getJavaVersion()} */
	@Deprecated
    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    /** @return the Java Runtime Environment version */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * @return the Java Runtime Environment vendor
     * @deprecated Use {@link #getJavaVendor()}
     */
    @Deprecated
    public static String javaVendor() {
        return System.getProperty("java.vendor");
    }

    /**
     * @return the Java Runtime Environment vendor
     */
    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    /**
     * @return the Java vendor URL
     * @deprecated Use {@link #getJavaVendorUrl()}
     */
	@Deprecated
    public static String javaVendorUrl() {
        return System.getProperty("java.vendor.url");
    }

    /**
     * @return the Java vendor URL
     */
    public static String getJavaVendorUrl() {
        return System.getProperty("java.vendor.url");
    }

    /**
     * @return the Java Runtime Environment specification version
     * @deprecated Use {@link #getJavaSpecificationVersion()}
     */
	@Deprecated
    public static String javaSpecificationVersion() {
        return System.getProperty("java.specification.version");
    }

    /**
     * @return the Java Runtime Environment specification version
     */
    public static String getJavaSpecificationVersion() {
        return System.getProperty("java.specification.version");
    }

    /**
     * @return the Java Runtime Environment specification vendor
     * @deprecated Use {@link #getJavaSpecificationVendor()}
     */
	@Deprecated
    public static String javaSpecificationVendor() {
        return System.getProperty("java.specification.vendor");
    }

    /**
     * @return the Java Runtime Environment specification vendor
     */
    public static String getJavaSpecificationVendor() {
        return System.getProperty("java.specification.vendor");
    }

    /**
     * @return the Java Runtime Environment specification name
     * @deprecated Use {@link #getJavaSpecificationName()}
     */
	@Deprecated
    public static String javaSpecificationName() {
        return System.getProperty("java.specification.name");
    }

    /**
     * @return the Java Runtime Environment specification name
     */
    public static String getJavaSpecificationName() {
        return System.getProperty("java.specification.name");
    }

    /**
     * @return the Java class format version number
     * @deprecated Use {@link #getJavaClassVersion()}
     */
	@Deprecated
    public static String javaClassVersion() {
        return System.getProperty("java.class.version");
    }

    /**
     * @return the Java class format version number
     */
    public static String getJavaClassVersion() {
        return System.getProperty("java.class.version");
    }

    /**
     * @return the name of JIT compiler to use
     * @deprecated Use {@link #getJavaCompiler()}
     */
	@Deprecated
    public static String javaCompiler() {
        return System.getProperty("java.compiler");
    }

    /**
     * @return the name of JIT compiler to use
     */
    public static String getJavaCompiler() {
        return System.getProperty("java.compiler");
    }

    /**
     * @return Java installation directory
     * @deprecated Use {@link #getJavaHome()}
     */
	@Deprecated
    public static String javaHome() {
        return System.getProperty("java.home");
    }

    /**
     * @return Java installation directory
     */
    public static String getJavaHome() {
        return System.getProperty("java.home");
    }

    /**
     * @return Path of extension directory or directories
     * @deprecated Use {@link #getExtDirs()}
     */
	@Deprecated
    public static String extDirs() {
        return System.getProperty("java.ext.dirs");
    }

    /**
     * @return Path of extension directory or directories
     */
    public static String getExtDirs() {
        return System.getProperty("java.ext.dirs");
    }

    /**
     * @return Java class path
     * @deprecated Use {@link #getClassPath()}
     */
	@Deprecated
    public static String classPath() {
        return System.getProperty("java.class.path");
    }

    /**
     * @return Java class path
     */
    public static String getClassPath() {
        return System.getProperty("java.class.path");
    }

    /**
     * @return List of paths to search when loading libraries
     * @deprecated Use {@link #getLibraryPath()}
     */
	@Deprecated
    public static String libraryPath() {
        return System.getProperty("java.library.path");
    }

    /**
     * @return List of paths to search when loading libraries
     */
    public static String getLibraryPath() {
        return System.getProperty("java.library.path");
    }

    /**
     * @return the Java Virtual Machine specification version
     * @deprecated Use {@link #getJavaVmSpecificationVersion()}
     */
	@Deprecated
    public static String javaVmSpecificationVersion() {
        return System.getProperty("java.vm.specification.version");
    }

    /**
     * @return the Java Virtual Machine specification version
     */
    public static String getJavaVmSpecificationVersion() {
        return System.getProperty("java.vm.specification.version");
    }

    /**
     * @return the Java Virtual Machine specification vendor
     * @deprecated Use {@link #getJavaVmSpecificationVendor()}
     */
	@Deprecated
    public static String javaVmSpecificationVendor() {
        return System.getProperty("java.vm.specification.vendor");
    }

    /**
     * @return the Java Virtual Machine specification vendor
     */
    public static String getJavaVmSpecificationVendor() {
        return System.getProperty("java.vm.specification.vendor");
    }

    /**
     * @return the Java Virtual Machine specification name
     * @deprecated Use {@link #getJavaVmSpecificationName()}
     */
	@Deprecated
    public static String javaVmSpecificationName() {
        return System.getProperty("java.vm.specification.name");
    }

    /**
     * @return the Java Virtual Machine specification name
     */
    public static String getJavaVmSpecificationName() {
        return System.getProperty("java.vm.specification.name");
    }

    /**
     * @return the Java Virtual Machine implementation version
     * @deprecated Use {@link #getJavaVmVersion()}
     */
	@Deprecated
    public static String javaVmVersion() {
        return System.getProperty("java.vm.version");
    }

    /**
     * @return the Java Virtual Machine implementation version
     */
    public static String getJavaVmVersion() {
        return System.getProperty("java.vm.version");
    }

    /**
     * @return the Java Virtual Machine implementation vendor
     * @deprecated Use {@link #getJavaVmVendor()}
     */
	@Deprecated
    public static String javaVmVendor() {
        return System.getProperty("java.vm.vendor");
    }

    /**
     * @return the Java Virtual Machine implementation vendor
     */
    public static String getJavaVmVendor() {
        return System.getProperty("java.vm.vendor");
    }

    /**
     * @return the Java Virtual Machine implementation name
     * @deprecated Use {@link #getJavaVmName()}
     */
	@Deprecated
    public static String javaVmName() {
        return System.getProperty("java.vm.name");
    }

    /**
     * @return the Java Virtual Machine implementation name
     */
    public static String getJavaVmName() {
        return System.getProperty("java.vm.name");
    }

}
