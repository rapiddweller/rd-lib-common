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
package com.rapiddweller.commons.xml;

import com.rapiddweller.commons.*;
import org.junit.Test;
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

import static org.junit.Assert.*;

/**
 * Tests the XMLUtil class.
 * Created: 19.03.2008 09:11:08
 * @author Volker Bergmann
 */
public class XMLUtilTest {
    
    private static final String XML_TEXT = "<?xml version=\"1.0\"?><root att=\"1\"/>";

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
    public void testGetChildElementByName() {
    	Document document = createDocument();
        Element child1 = createElementWithChildren(document, "c1");
        Element child2 = createElementWithChildren(document, "c2");
        Element parent = createElementWithChildren(document, "p", child1, child2);
        Element foundChild = XMLUtil.getChildElement(parent, true, true, "c2");
        assertEquals(child2, foundChild);
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
    public void testGetChildElementAtPath_positive() {
    	Document document = createDocument();
    	Element child2 = createElementWithChildren(document, "c2");
        Element child1 = createElementWithChildren(document, "c1", child2);
        Element parent = createElementWithChildren(document, "p", child1);
        Element foundChild = XMLUtil.getChildElementAtPath(parent, "c1/c2", false, true);
        assertEquals(child2, foundChild);
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
    public void testNormalizedAttributeValue() {
    	Document document = createDocument();
        Element element = createElementWithAttributes(document,"name1", "value1", "name2", "");
        assertEquals("value1", XMLUtil.normalizedAttributeValue(element, "name1"));
        assertNull(XMLUtil.normalizedAttributeValue(element, "name2"));
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
    public void testParseString() {
        Document document = XMLUtil.parseString(XML_TEXT);
        checkXML(document);
    }
    
	@Test
    public void testGetText() {
    	Document document = createDocument();
    	Text node = document.createTextNode("my text");
    	assertEquals("my text", XMLUtil.getText(node));
    }

	@Test
	public void testGetWholeText() {
		Element element = XMLUtil.parseStringAsElement("<stmt><!-- xxx -->Al<!-- xyz -->pha<!--z--></stmt>");
		assertEquals("Alpha", XMLUtil.getWholeText(element));
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
	public void testSaveAsProperties() throws IOException {
		Properties props = new Properties();
		props.setProperty("root.topProp", "topValue");
		props.setProperty("root.group.groupProp", "groupValue");
		props.setProperty("root.emptyProp", "");
		XMLUtil.saveAsProperties(props, new File("target/testSaveAsProperties-actual.xml"), Encodings.UTF_8);
		String actual = StringUtil.normalizeLineSeparators(IOUtil.getContentOfURI("target/testSaveAsProperties-actual.xml"), "\n");
		String expected = StringUtil.normalizeLineSeparators(IOUtil.getContentOfURI("com/rapiddweller/commons/xml/properties.xml"), "\n");
		assertEquals(expected, actual);
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
