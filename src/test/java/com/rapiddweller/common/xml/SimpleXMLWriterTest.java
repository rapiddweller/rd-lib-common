package com.rapiddweller.common.xml;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import com.rapiddweller.common.ConfigurationError;

import java.io.ByteArrayOutputStream;

import java.io.OutputStream;

import org.junit.Test;
import org.xml.sax.helpers.AttributesImpl;

public class SimpleXMLWriterTest {
    @Test
    public void testConstructor() {
        assertNull((new SimpleXMLWriter(OutputStream.nullOutputStream(), "UTF-8", true)).getSystemId());
        assertThrows(ConfigurationError.class, () -> new SimpleXMLWriter(null, "UTF-8", true));
        assertNull((new SimpleXMLWriter(OutputStream.nullOutputStream(), "UTF-8", false)).getSystemId());
    }

    @Test
    public void testGetSystemId() {
        assertNull((new SimpleXMLWriter(OutputStream.nullOutputStream(), "UTF-8", true)).getSystemId());
    }


    @Test
    public void testClose() {
        SimpleXMLWriter simpleXMLWriter = new SimpleXMLWriter(OutputStream.nullOutputStream(), "UTF-8", true);
        simpleXMLWriter.close();
        assertNull(simpleXMLWriter.handler);
    }

    @Test
    public void testClose2() {
        SimpleXMLWriter simpleXMLWriter = new SimpleXMLWriter(new ByteArrayOutputStream(), "UTF-8", true);
        simpleXMLWriter.close();
        assertNull(simpleXMLWriter.handler);
    }

    @Test
    public void testAddAttribute() {
        AttributesImpl attributesImpl = new AttributesImpl();
        assertSame(attributesImpl, SimpleXMLWriter.addAttribute("Name", "value", attributesImpl));
    }

    @Test
    public void testAddAttribute2() {
        AttributesImpl attributesImpl = new AttributesImpl();
        assertSame(attributesImpl, SimpleXMLWriter.addAttribute("Name", "", attributesImpl));
    }

    @Test
    public void testAddAttribute3() {
        AttributesImpl attributesImpl = new AttributesImpl();
        assertSame(attributesImpl, SimpleXMLWriter.addAttribute("omit-xml-declaration", "value", attributesImpl));
    }
}

