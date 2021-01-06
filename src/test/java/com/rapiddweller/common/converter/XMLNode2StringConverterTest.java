package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.ConversionException;
import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.xerces.dom.CDATASectionImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.TextImpl;
import org.junit.Test;

public class XMLNode2StringConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        assertEquals("'node'", (new XMLNode2StringConverter()).convert("node"));
        assertEquals("", (new XMLNode2StringConverter()).convert(null));
        assertEquals("", (new XMLNode2StringConverter()).convert(null));
    }

    @Test
    public void testConvert2() throws ConversionException {
        CDATASectionImpl node = new CDATASectionImpl(new CoreDocumentImpl(), "foo");
        assertEquals("<![CDATA[foo]]>", (new XMLNode2StringConverter()).convert(node));
    }

    @Test
    public void testConvert3() throws ConversionException {
        XMLNode2StringConverter xmlNode2StringConverter = new XMLNode2StringConverter();
        assertEquals("'null'", xmlNode2StringConverter.convert(new TextImpl()));
    }

    @Test
    public void testConvert4() throws ConversionException {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertEquals("\n<foo/>\n", (new XMLNode2StringConverter()).convert(elementImpl));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testConvert5() throws ConversionException {
        XMLNode2StringConverter xmlNode2StringConverter = new XMLNode2StringConverter();
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        assertEquals("<HTML></HTML>\n", xmlNode2StringConverter.convert(htmlDocumentImpl));
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
    }

    @Test
    public void testFormat() throws ConversionException {
        assertEquals("'node'", XMLNode2StringConverter.format("node"));
        assertEquals("<![CDATA[foo]]>",
                XMLNode2StringConverter.format(new CDATASectionImpl(new CoreDocumentImpl(), "foo")));
        assertEquals("'null'", XMLNode2StringConverter.format(new TextImpl()));
        assertEquals("", XMLNode2StringConverter.format(null));
        assertEquals("", XMLNode2StringConverter.format(null));
    }

    @Test
    public void testFormat2() throws ConversionException {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertEquals("\n<foo/>\n", XMLNode2StringConverter.format(elementImpl));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testFormat3() throws ConversionException {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        assertEquals("<HTML></HTML>\n", XMLNode2StringConverter.format(htmlDocumentImpl));
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
    }

    @Test
    public void testFormat4() throws ConversionException {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        assertEquals("<HTML></HTML>\n", XMLNode2StringConverter.format(htmlDocumentImpl));
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
    }
}

