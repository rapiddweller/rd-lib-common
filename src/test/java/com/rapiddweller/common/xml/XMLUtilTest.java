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
package com.rapiddweller.common.xml;

import com.rapiddweller.common.*;
import com.rapiddweller.common.SyntaxError;
import com.rapiddweller.common.converter.URLEncodeConverter;
import com.rapiddweller.common.filter.OrFilter;

import java.nio.file.Paths;

import java.util.List;

import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.xerces.dom.AttrNSImpl;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DocumentFragmentImpl;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.TextImpl;
import org.apache.xerces.impl.xs.opti.AttrImpl;
import org.apache.xerces.impl.xs.opti.DefaultElement;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Node;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the XMLUtil class.
 * Created: 19.03.2008 09:11:08
 *
 * @author Volker Bergmann
 */
public class XMLUtilTest {

    private static final String XML_TEXT = "<?xml version=\"1.0\"?><root att=\"1\"/>";

    @Test
    public void testFormat2() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        assertEquals("<HTML></HTML>\n", XMLUtil.format(htmlDocumentImpl));
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
    }


    @Test
    public void testFormat4() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertEquals("<foo/>\n", XMLUtil.format(elementImpl));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testFormat5() {
        CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
        coreDocumentImpl.setStrictErrorChecking(true);
        ElementImpl elementImpl = new ElementImpl(coreDocumentImpl, "foo");
        assertEquals("<foo/>\n", XMLUtil.format(elementImpl));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testFormat6() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        elementImpl.setAttributeNS("UTF-8", "UTF-8", "UTF-8");
        assertEquals("<foo UTF-8=\"UTF-8\"/>\n", XMLUtil.format(elementImpl));
    }

    @Test
    public void testFormatShort() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertEquals("<foo...", XMLUtil.formatShort(elementImpl));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testFormatShort2() {
        CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
        coreDocumentImpl.setStrictErrorChecking(true);
        ElementImpl elementImpl = new ElementImpl(coreDocumentImpl, "foo");
        assertEquals("<foo...", XMLUtil.formatShort(elementImpl));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testFormatStartTag() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertEquals("<foo>", XMLUtil.formatStartTag(elementImpl));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testFormatStartTag2() {
        CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
        coreDocumentImpl.setStrictErrorChecking(true);
        ElementImpl elementImpl = new ElementImpl(coreDocumentImpl, "foo");
        assertEquals("<foo>", XMLUtil.formatStartTag(elementImpl));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }


    @Test
    public void testFormat() {
        Document document = createDocument();
        String output = XMLUtil.formatShort(createElementWithChildren(document, "ns:test"));
        assertTrue(output.startsWith("<ns:test"));
    }

    @Test
    public void testLocalNameString() {
        assertEquals("test", XMLUtil.localName("ns:test"));
    }

    @Test
    public void testLocalName() {
        assertEquals("Element Name", XMLUtil.localName("Element Name"));
        assertNull(XMLUtil.localName((String) null));
        assertEquals("", XMLUtil.localName(":"));
        assertNull(XMLUtil.localName(new DefaultElement()));
    }

    @Test
    public void testLocalName2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertEquals("foo", XMLUtil.localName(elementImpl));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testLocalName3() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), ":");
        assertEquals("", XMLUtil.localName(elementImpl));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testLocalNameElement() {
        Document document = createDocument();
        Element element = createElementWithChildren(document, "ns:test");
        assertEquals("test", XMLUtil.localName(element));
    }

    @Test
    public void testGetChildElements() {
        Document document = createDocument();
        Element child1 = createElementWithChildren(document, "c1");
        Element child2 = createElementWithChildren(document, "c2");
        Element parent = createElementWithChildren(document, "p", child1, child2);
        Element[] expectedChildren = ArrayUtil.toArray(child1, child2);
        Element[] actualChildren = XMLUtil.getChildElements(parent);
        assertArrayEquals(expectedChildren, actualChildren);
    }

    @Test
    public void testGetChildElements2() {
        assertEquals(0, XMLUtil.getChildElements(new ElementImpl(new CoreDocumentImpl(), "foo")).length);
    }

    @Test
    public void testGetChildElements3() {
        assertEquals(0, XMLUtil.getChildElements(new DefaultElement()).length);
    }

    @Test
    public void testGetChildElements4() {
        assertEquals(0, XMLUtil.getChildElements(new ElementImpl(new CoreDocumentImpl(), "foo"), true, "Name").length);
    }

    @Test
    public void testGetChildElements5() {
        assertEquals(0, XMLUtil.getChildElements(new DefaultElement(), true, "Name").length);
    }

    @Test
    public void testGetChildElements_empty() {
        Document document = createDocument();
        assertArrayEquals(new Element[0], XMLUtil.getChildElements(document.getDocumentElement()));
    }

    @Test
    public void testGetChildElementsByName() {
        Document document = createDocument();
        Element child1 = createElementWithChildren(document, "c1");
        Element child2 = createElementWithChildren(document, "c2");
        Element parent = createElementWithChildren(document, "p", child1, child2);
        Element[] expectedChildren = ArrayUtil.toArray(child2);
        Element[] actualChildren = XMLUtil.getChildElements(parent, true, "c2");
        assertArrayEquals(expectedChildren, actualChildren);
    }

    @Test
    public void testToElementArray() {
        assertEquals(0, XMLUtil.toElementArray(new HTMLDocumentImpl()).length);
        assertEquals(0, XMLUtil.toElementArray(null).length);
        assertEquals(0, XMLUtil.toElementArray(new AttrNSImpl(new CoreDocumentImpl(), "foo", "foo", "foo")).length);
    }

    @Test
    public void testToElementList() {
        assertTrue(XMLUtil.toElementList(new HTMLDocumentImpl()).isEmpty());
        assertTrue(XMLUtil.toElementList(null).isEmpty());
        assertTrue(XMLUtil.toElementList(new AttrNSImpl(new CoreDocumentImpl(), "foo", "foo", "foo")).isEmpty());
    }

    @Test
    public void testGetChildElement() {
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getChildElement(new ElementImpl(new CoreDocumentImpl(), "foo"), true, true, "Name"));
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getChildElement(new DefaultElement(), true, true, "Name"));
        assertNull(XMLUtil.getChildElement(new ElementImpl(new CoreDocumentImpl(), "foo"), true, false, "Name"));
    }

    @Test
    public void testGetChildElementByName() {
        Document document = createDocument();
        Element child1 = createElementWithChildren(document, "c1");
        Element child2 = createElementWithChildren(document, "c2");
        Element parent = createElementWithChildren(document, "p", child1, child2);
        Element foundChild = XMLUtil.getChildElement(parent, true, true, "c2");
        assertEquals(child2, foundChild);
    }

    @Test
    public void testGetChildElementText() {
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getChildElementText(new ElementImpl(new CoreDocumentImpl(), "foo"), true, true, "Name"));
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getChildElementText(new DefaultElement(), true, true, "Name"));
        assertNull(XMLUtil.getChildElementText(new ElementImpl(new CoreDocumentImpl(), "foo"), true, false, "Name"));
    }

    @Test
    public void testGetChildElementDate() {
        assertThrows(IllegalArgumentException.class, () -> XMLUtil
                .getChildElementDate(new ElementImpl(new CoreDocumentImpl(), "foo"), true, true, "Name", "Pattern"));
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getChildElementDate(new DefaultElement(), true, true, "Name", "Pattern"));
        assertNull(
                XMLUtil.getChildElementDate(new ElementImpl(new CoreDocumentImpl(), "foo"), true, false, "Name", "Pattern"));
    }

    @Test
    public void testGetTexts() {
        assertEquals(1, XMLUtil.getTexts(new Element[]{new ElementImpl(new CoreDocumentImpl(), "foo")}).length);
        assertEquals(1, XMLUtil.getTexts(new Element[]{null}).length);
        assertNull(XMLUtil.getTexts(null));
    }

    @Test
    public void testGetChildElementsAtPath() {
        Document document = createDocument();
        Element child2 = createElementWithChildren(document, "c2");
        Element child1 = createElementWithChildren(document, "c1", child2);
        Element parent = createElementWithChildren(document, "p", child1);
        Element[] foundChildren = XMLUtil.getChildElementsAtPath(parent, "c1/c2", true);
        assertEquals(1, foundChildren.length);
        assertEquals(child2, foundChildren[0]);
    }

    @Test
    public void testGetChildElementsAtPath2() {
        assertEquals(0,
                XMLUtil.getChildElementsAtPath(new ElementImpl(new CoreDocumentImpl(), "foo"), "Path", true).length);
    }

    @Test
    public void testGetChildElementsAtPath3() {
        assertEquals(0, XMLUtil.getChildElementsAtPath(new DefaultElement(), "Path", true).length);
    }

    @Test
    public void testGetChildElementsAtPath4() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> XMLUtil.getChildElementsAtPath(new ElementImpl(new CoreDocumentImpl(), "foo"), "/", true));
    }

    @Test
    public void testHasName() {
        assertFalse(XMLUtil.hasName("Name", true, new HTMLDocumentImpl()));
        assertFalse(XMLUtil.hasName("Name", false, new HTMLDocumentImpl()));
    }

    @Test
    public void testGetChildElementAtPath_positive() {
        Document document = createDocument();
        Element child2 = createElementWithChildren(document, "c2");
        Element child1 = createElementWithChildren(document, "c1", child2);
        Element parent = createElementWithChildren(document, "p", child1);
        Element foundChild = XMLUtil.getChildElementAtPath(parent, "c1/c2", false, true);
        assertEquals(child2, foundChild);
    }

    @Test
    public void testGetChildElementAtPath() {
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getChildElementAtPath(new ElementImpl(new CoreDocumentImpl(), "foo"), "Path", true, true));
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getChildElementAtPath(new DefaultElement(), "Path", true, true));
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> XMLUtil.getChildElementAtPath(new ElementImpl(new CoreDocumentImpl(), "foo"), "/", true, true));
        assertNull(XMLUtil.getChildElementAtPath(new ElementImpl(new CoreDocumentImpl(), "foo"), "Path", true, false));
    }

    @Test
    public void testGetChildElementAtPath_negative_optional() {
        Document document = createDocument();
        Element parent = createElementWithChildren(document, "p");
        XMLUtil.getChildElementAtPath(parent, "nonexist", false, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetChildElementAtPath_negative_required() {
        Document document = createDocument();
        Element parent = createElementWithChildren(document, "p");
        XMLUtil.getChildElementAtPath(parent, "nonexist", false, true);
    }

    @Test
    public void testGetIntegerAttribute() {
        Document document = createDocument();
        Element element = createElement(document, "test", CollectionUtil.buildMap("value", "1"));
        assertEquals(1, (int) XMLUtil.getIntegerAttribute(element, "value", 2));
    }

    @Test
    public void testGetIntegerAttribute2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertEquals(42, XMLUtil.getIntegerAttribute(elementImpl, "Name", 42).intValue());
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testGetIntegerAttribute3() {
        assertEquals(42, XMLUtil.getIntegerAttribute(new DefaultElement(), "Name", 42).intValue());
    }

    @Test
    public void testGetLongAttribute2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertEquals(42L, XMLUtil.getLongAttribute(elementImpl, "Name", 42L).longValue());
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testGetLongAttribute3() {
        assertEquals(42L, XMLUtil.getLongAttribute(new DefaultElement(), "Name", 42L).longValue());
    }

    @Test
    public void testGetAttribute() {
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getAttribute(new ElementImpl(new CoreDocumentImpl(), "foo"), "Attribute Name", true));
        assertThrows(IllegalArgumentException.class,
                () -> XMLUtil.getAttribute(new DefaultElement(), "Attribute Name", true));
    }

    @Test
    public void testGetAttribute2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertNull(XMLUtil.getAttribute(elementImpl, "Attribute Name", false));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testGetLongAttribute() {
        Document document = createDocument();
        Element element = createElement(document, "test", CollectionUtil.buildMap("value", "1"));
        assertEquals(1, (long) XMLUtil.getIntegerAttribute(element, "value", 2));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetAttributes() {
        Document document = createDocument();
        Element element = createElementWithAttributes(document, "name1", "value1", "name2", "");
        Map<String, String> actualAttributes = XMLUtil.getAttributes(element);
        Map<String, String> expectedAttributes = CollectionUtil.buildMap("name1", "value1", "name2", "");
        assertEquals(expectedAttributes, actualAttributes);
    }

    @Test
    public void testGetAttributes2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertTrue(XMLUtil.getAttributes(elementImpl).isEmpty());
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testGetAttributes3() {
        CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
        coreDocumentImpl.setStrictErrorChecking(true);
        ElementImpl elementImpl = new ElementImpl(coreDocumentImpl, "foo");
        assertTrue(XMLUtil.getAttributes(elementImpl).isEmpty());
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testGetAttributes4() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        elementImpl.setAttributeNS("foo", "foo", "foo");
        Map<String, String> actualAttributes = XMLUtil.getAttributes(elementImpl);
        assertEquals(1, actualAttributes.size());
        assertEquals("foo", actualAttributes.get("foo"));
    }

    @Test
    public void testNormalizedAttributeValue() {
        Document document = createDocument();
        Element element = createElementWithAttributes(document, "name1", "value1", "name2", "");
        assertEquals("value1", XMLUtil.normalizedAttributeValue(element, "name1"));
        assertNull(XMLUtil.normalizedAttributeValue(element, "name2"));
    }

    @Test
    public void testNormalizedAttributeValue2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertNull(XMLUtil.normalizedAttributeValue(elementImpl, "Attribute Name"));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testNormalizedAttributeValue3() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertNull(XMLUtil.normalizedAttributeValue(elementImpl, ""));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testGetChildComments() {
        assertEquals(0, XMLUtil.getChildComments(new HTMLDocumentImpl()).length);
        assertEquals(0, XMLUtil.getChildComments(new CoreDocumentImpl()).length);
        assertEquals(0,
                XMLUtil.getChildComments(new CoreDocumentImpl(new DocumentTypeImpl(new CoreDocumentImpl(), "foo"))).length);
        assertThrows(UnsupportedOperationException.class, () -> XMLUtil.getChildComments(new AttrNSImpl()));
    }

    @Test
    public void testParseFileIfExists() throws IOException {
        assertNull(XMLUtil.parseFileIfExists(Paths.get(System.getProperty("uri"), "test.txt").toFile()));
    }

    @Test
    public void testParseUri() throws IOException {
        File file = File.createTempFile("XMLUtilTest", ".xml");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(XML_TEXT);
            writer.close();
            Document document = XMLUtil.parse(file.getAbsolutePath());
            checkXML(document);
        } finally {
            assertTrue(file.delete());
        }
    }

    @Test
    public void testParseStream() throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(XML_TEXT.getBytes(StandardCharsets.UTF_8));
        checkXML(XMLUtil.parse(stream));
    }

    @Test
    public void testCreateDocumentBuilderFactory() {
        assertTrue(XMLUtil.createDocumentBuilderFactory(null) instanceof org.apache.xerces.jaxp.DocumentBuilderFactoryImpl);
    }

    @Test
    public void testNamespaceAlias() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        NamespaceAlias actualNamespaceAliasResult = XMLUtil.namespaceAlias(htmlDocumentImpl, "Namespace Uri");
        assertEquals("", actualNamespaceAliasResult.getAliasName());
        assertEquals("Namespace Uri", actualNamespaceAliasResult.getNamespaceURI());
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
    }

    @Test
    public void testGetNamespaces() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        assertTrue(XMLUtil.getNamespaces(htmlDocumentImpl).isEmpty());
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
    }


    @Test
    public void testGetTargetNamespace() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        assertEquals("", XMLUtil.getTargetNamespace(htmlDocumentImpl));
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
    }

    @Test
    public void testGetBooleanAttribute() {
        assertThrows(SyntaxError.class,
                () -> XMLUtil.getBooleanAttribute(new ElementImpl(new CoreDocumentImpl(), "foo"), "Attribute Name", true));
        assertThrows(SyntaxError.class,
                () -> XMLUtil.getBooleanAttribute(new ElementImpl(new CoreDocumentImpl(), "foo"), "UTF-8", true));
        assertNull(XMLUtil.getBooleanAttribute(new DefaultElement(), "Attribute Name", false));
    }

    @Test
    public void testGetBooleanAttribute3() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        elementImpl.setAttributeNS("UTF-8", "UTF-8", "UTF-8");
        assertThrows(SyntaxError.class, () -> XMLUtil.getBooleanAttribute(elementImpl, "Attribute Name", true));
    }

    @Test
    public void testGetBooleanAttributeWithDefault() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertTrue(XMLUtil.getBooleanAttributeWithDefault(elementImpl, "Attribute Name", true));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testGetBooleanAttributeWithDefault2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertTrue(XMLUtil.getBooleanAttributeWithDefault(elementImpl, "", true));
        assertNull(elementImpl.getParentNode());
    }


    @Test
    public void testGetDateAttribute() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertNull(XMLUtil.getDateAttribute(elementImpl, "Name"));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testGetDateAttribute2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertNull(XMLUtil.getDateAttribute(elementImpl, ""));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testMapAttributesToProperties() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        XMLUtil.mapAttributesToProperties(elementImpl, "bean", true);
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testMapAttributesToProperties2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        XMLUtil.mapAttributesToProperties(elementImpl, 0, true);
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testMapAttributesToProperties3() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        XMLUtil.mapAttributesToProperties(elementImpl, "bean", true, new URLEncodeConverter());
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testMapAttributesToProperties4() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        XMLUtil.mapAttributesToProperties(elementImpl, "bean", true, new URLEncodeConverter("UTF-8"));
        assertNull(elementImpl.getParentNode());
        assertFalse(elementImpl.hasAttributes());
    }

    @Test
    public void testFindElementByAttribute() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertNull(XMLUtil.findElementByAttribute("Attribute Name", "Attribute Value", elementImpl));
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testFindElementByAttribute2() {
        assertNull(XMLUtil.findElementByAttribute("Attribute Name", "Attribute Value", new DefaultElement()));
    }

    @Test
    public void testFindFirstAccepted() {
        OrFilter<Element> filter = new OrFilter<Element>();
        assertNull(XMLUtil.findFirstAccepted(filter, new ElementImpl(new CoreDocumentImpl(), "foo")));
    }

    @Test
    public void testFindElementsByName() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertTrue(XMLUtil.findElementsByName("Name", true, elementImpl).isEmpty());
        assertNull(elementImpl.getParentNode());
    }

    @Test
    public void testFindElementsByName2() {
        List<Element> actualFindElementsByNameResult = XMLUtil.findElementsByName("foo", true,
                new ElementImpl(new CoreDocumentImpl(), "foo"));
        assertEquals(1, actualFindElementsByNameResult.size());
        assertNull(actualFindElementsByNameResult.get(0).getParentNode());
    }

    @Test
    public void testFindElementsByName3() {
        assertTrue(
                XMLUtil.findElementsByName("Name", true, new DefaultElement("foo", "foo", "foo", "foo", (short) 1)).isEmpty());
    }

    @Test
    public void testParseString() {
        Document document = XMLUtil.parseString(XML_TEXT);
        checkXML(document);
    }

    @Test
    public void testGetEncoding() {
        assertEquals("UTF-8", XMLUtil.getEncoding("Text", "UTF-8"));
        assertEquals("UTF-8", XMLUtil.getEncoding("<?xml", "UTF-8"));
    }

    @Test
    public void testGetText() {
        Document document = createDocument();
        Text node = document.createTextNode("my text");
        assertEquals("my text", XMLUtil.getText(node));
    }

    @Test
    public void testGetText2() {
        assertNull(XMLUtil.getText(new HTMLDocumentImpl()));
    }

    @Test
    public void testGetText3() {
        assertNull(XMLUtil.getText(null));
    }

    @Test
    public void testGetText4() {
        assertNull(XMLUtil.getText(new TextImpl()));
    }

    @Test
    public void testGetText5() {
        assertNull(XMLUtil.getText(new AttrNSImpl(new CoreDocumentImpl(), "foo", "foo", "foo")));
    }

    @Test
    public void testGetWholeText() {
        Element element = XMLUtil.parseStringAsElement("<stmt><!-- xxx -->Al<!-- xyz -->pha<!--z--></stmt>");
        assertEquals("Alpha", XMLUtil.getWholeText(element));
    }

    @Test
    public void testGetWholeText2() {
        assertEquals("", XMLUtil.getWholeText(new ElementImpl(new CoreDocumentImpl(), "foo")));
    }

    @Test
    public void testGetWholeText3() {
        CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
        coreDocumentImpl.setStrictErrorChecking(true);
        assertEquals("", XMLUtil.getWholeText(new ElementImpl(coreDocumentImpl, "foo")));
    }

    @Test
    public void testFormatText() {
        assertEquals("Text", XMLUtil.formatText("Text"));
    }

    @Test
    public void testCreateDocument() {
        Document document = XMLUtil.createDocument("theRoot");
        assertNotNull(document);
        Element rootElement = document.getDocumentElement();
        assertEquals("theRoot", rootElement.getNodeName());
        assertEquals(0, rootElement.getChildNodes().getLength());
    }

    @Test
    public void testSetProperty_root() {
        Document document = XMLUtil.createDocument();
        XMLUtil.setProperty("theRoot", "rootValue", document);
        assertEquals("rootValue", document.getDocumentElement().getTextContent());
    }

    @Test
    public void testSetProperty() {
        assertThrows(IllegalArgumentException.class, () -> XMLUtil.setProperty("Key", "value", new HTMLDocumentImpl()));
    }

    @Test
    public void testSetProperty2() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        XMLUtil.setProperty("HTML", "value", htmlDocumentImpl);
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
    }

    @Test
    public void testSetProperty3() throws DOMException {
        CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
        XMLUtil.setProperty("Key", "value", coreDocumentImpl);
        Element documentElement = coreDocumentImpl.getDocumentElement();
        assertTrue(documentElement instanceof ElementImpl);
        int actualLength = ((ElementImpl) documentElement).getLength();
        assertNull(coreDocumentImpl.getParentNode());
        assertTrue(coreDocumentImpl.hasChildNodes());
        assertEquals(1, coreDocumentImpl.getLength());
        assertEquals("value", documentElement.getTextContent());
        assertEquals("Key", documentElement.getNodeName());
        assertEquals(1, actualLength);
        assertTrue(documentElement.hasChildNodes());
        assertNull(((ElementImpl) documentElement).getUserData());
        assertNull(((ElementImpl) documentElement).getPreviousElementSibling());
    }

    @Test
    public void testSetProperty4() throws DOMException {
        CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
        XMLUtil.setProperty("com.rapiddweller.common.xml.XMLUtil", "value", coreDocumentImpl);
        Element documentElement = coreDocumentImpl.getDocumentElement();
        assertTrue(documentElement instanceof ElementImpl);
        int actualLength = ((ElementImpl) documentElement).getLength();
        assertNull(coreDocumentImpl.getParentNode());
        assertTrue(coreDocumentImpl.hasChildNodes());
        assertEquals(1, coreDocumentImpl.getLength());
        assertEquals("value", documentElement.getTextContent());
        assertEquals("com", documentElement.getNodeName());
        assertEquals(1, actualLength);
        assertTrue(documentElement.hasChildNodes());
        assertNull(((ElementImpl) documentElement).getUserData());
        assertNull(((ElementImpl) documentElement).getPreviousElementSibling());
    }

    @Test
    public void testSetProperty5() throws DOMException {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        XMLUtil.setProperty(null, "value", elementImpl, new HTMLDocumentImpl());
        Node lastChild = elementImpl.getLastChild();
        String actualData = ((TextImpl) lastChild).getData();
        assertEquals(1, elementImpl.getLength());
        assertTrue(elementImpl.hasChildNodes());
        assertEquals("value", actualData);
        assertNull(((TextImpl) lastChild).getUserData());
        assertNull(lastChild.getPreviousSibling());
    }

    @Test
    public void testSetProperty_complex() {
        Document document = XMLUtil.createDocument();
        XMLUtil.setProperty("theRoot.aGroup", "groupValue", document);
        Element rootElement = document.getDocumentElement();
        assertEquals("theRoot", rootElement.getNodeName());
        NodeList rootChildren = rootElement.getChildNodes();
        assertEquals(1, rootChildren.getLength());
        Node firstChild = rootChildren.item(0);
        assertEquals("aGroup", firstChild.getNodeName());
        assertEquals("groupValue", firstChild.getTextContent());
    }

    @Test
    public void testResolveEntities() {
        assertEquals("Xml Text", XMLUtil.resolveEntities("Xml Text"));
        assertEquals("&#", XMLUtil.resolveEntities("&#"));
    }

    @Test
    public void testGetParentNode() {
        assertNull(XMLUtil.getParentNode(new HTMLDocumentImpl()));
        assertNull(XMLUtil.getParentNode(new AttrNSImpl()));
    }

    @Test
    public void testGetParentNode2() {
        ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
        assertSame(elementImpl, XMLUtil.getParentNode(new AttrImpl(elementImpl, "foo", "foo", "foo", "foo", "foo")));
    }

    @Test
    public void testXpathTo() {
        assertEquals("", XMLUtil.xpathTo(new HTMLDocumentImpl()));
        assertEquals("", XMLUtil.xpathTo(new CoreDocumentImpl()));
        assertEquals("/@null", XMLUtil.xpathTo(new AttrNSImpl()));
        assertEquals("/#document-fragment", XMLUtil.xpathTo(new DocumentFragmentImpl()));
    }

    @Test
    public void testNodePathTo() {
        assertEquals(1, XMLUtil.nodePathTo(new HTMLDocumentImpl()).length);
        assertEquals(1, XMLUtil.nodePathTo(new AttrNSImpl()).length);
    }

    @Test
    public void testSaveAsProperties() throws IOException {
        Properties props = new Properties();
        props.setProperty("root.topProp", "topValue");
        props.setProperty("root.group.groupProp", "groupValue");
        props.setProperty("root.emptyProp", "");
        XMLUtil.saveAsProperties(props, new File("target/testSaveAsProperties-actual.xml"), Encodings.UTF_8);
        String actual = StringUtil.normalizeLineSeparators(IOUtil.getContentOfURI("target/testSaveAsProperties-actual.xml"), "\n");
        String expected = StringUtil.normalizeLineSeparators(IOUtil.getContentOfURI("com/rapiddweller/common/xml/properties.xml"), "\n");
        assertTrue(actual.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
        assertTrue(actual.contains("<root>"));
        assertTrue(actual.contains("<emptyProp/>"));
        assertTrue(actual.contains("<topProp>topValue</topProp>"));
        assertTrue(actual.contains("<group>\n" +
                "    <groupProp>groupValue</groupProp>\n" +
                "  </group>"));
    }

    // private helpers -------------------------------------------------------------------------------------------------

    private static void checkXML(Document document) {
        Element root = document.getDocumentElement();
        assertEquals("root", root.getNodeName());
        assertEquals("1", root.getAttribute("att"));
        assertEquals(1, root.getAttributes().getLength());
    }

    @SuppressWarnings("unchecked")
    private static Element createElementWithAttributes(Document document, String... attKeysAndValues) {
        Map<String, String> attMap = CollectionUtil.buildMap((Object[]) attKeysAndValues);
        return createElement(document, "test", attMap);
    }

    private static Element createElementWithChildren(Document document, String name, Element... children) {
        return createElement(document, name, new HashMap<>(), children);
    }

    private static Element createElement(Document document, String name, Map<String, String> attributes, Element... children) {
        Element element = document.createElement(name);
        for (Map.Entry<String, String> attribute : attributes.entrySet())
            element.setAttribute(attribute.getKey(), attribute.getValue());
        for (Element child : children)
            element.appendChild(child);
        return element;
    }

    private static Document createDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation impl = builder.getDOMImplementation();
            return impl.createDocument(null, "document", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
