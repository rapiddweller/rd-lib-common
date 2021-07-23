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

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.StringUtil;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.Closeable;
import java.io.OutputStream;
import java.util.Map;

/**
 * Writes XML to a stream. The interface is similar to {@link Transformer},
 * but setup is easier, methods have been simplified and convenience methods
 * have been added.
 * Created: 28.11.2010 06:39:04
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class SimpleXMLWriter implements Closeable {

  /**
   * The Out.
   */
  OutputStream out;
  /**
   * The Handler.
   */
  TransformerHandler handler;

  /**
   * Instantiates a new Simple xml writer.
   *
   * @param out              the out
   * @param encoding         the encoding
   * @param includeXmlHeader the include xml header
   */
  public SimpleXMLWriter(OutputStream out, String encoding, boolean includeXmlHeader) {
    try {
      // create file and write header
      SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
      handler = tf.newTransformerHandler();
      Transformer transformer = handler.getTransformer();
      transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
      transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}" + "indent-amount", "2");
      if (!includeXmlHeader) {
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      }
      handler.setResult(new StreamResult(out));
      handler.startDocument();
    } catch (TransformerConfigurationException e) {
      throw new ConfigurationError(e);
    } catch (SAXException e) {
      throw new ConfigurationError("Error in initializing XML file", e);
    }
  }

  /**
   * Print.
   *
   * @param text the text
   * @throws SAXException the sax exception
   */
  public void print(String text) throws SAXException {
    characters(text.toCharArray(), 0, text.length());
  }

  /**
   * Text.
   *
   * @param text the text
   * @throws SAXException the sax exception
   */
  public void text(String text) throws SAXException {
    handler.characters(text.toCharArray(), 0, text.length());
  }

  /**
   * Characters.
   *
   * @param ch     the ch
   * @param start  the start
   * @param length the length
   * @throws SAXException the sax exception
   */
  public void characters(char[] ch, int start, int length) throws SAXException {
    handler.characters(ch, start, length);
  }

  /**
   * Comment.
   *
   * @param ch     the ch
   * @param start  the start
   * @param length the length
   * @throws SAXException the sax exception
   */
  public void comment(char[] ch, int start, int length) throws SAXException {
    handler.comment(ch, start, length);
  }

  /**
   * End cdata.
   *
   * @throws SAXException the sax exception
   */
  public void endCDATA() throws SAXException {
    handler.endCDATA();
  }

  /**
   * End dtd.
   *
   * @throws SAXException the sax exception
   */
  public void endDTD() throws SAXException {
    handler.endDTD();
  }

  /**
   * End document.
   *
   * @throws SAXException the sax exception
   */
  public void endDocument() throws SAXException {
    handler.endDocument();
  }

  /**
   * End element.
   *
   * @param name the name
   * @throws SAXException the sax exception
   */
  public void endElement(String name)
      throws SAXException {
    handler.endElement("", "", name);
  }

  /**
   * End entity.
   *
   * @param name the name
   * @throws SAXException the sax exception
   */
  public void endEntity(String name) throws SAXException {
    handler.endEntity(name);
  }

  /**
   * End prefix mapping.
   *
   * @param prefix the prefix
   * @throws SAXException the sax exception
   */
  public void endPrefixMapping(String prefix) throws SAXException {
    handler.endPrefixMapping(prefix);
  }

  /**
   * Gets system id.
   *
   * @return the system id
   */
  public String getSystemId() {
    return handler.getSystemId();
  }

  /**
   * Gets transformer.
   *
   * @return the transformer
   */
  public Transformer getTransformer() {
    return handler.getTransformer();
  }

  /**
   * Ignorable whitespace.
   *
   * @param ch     the ch
   * @param start  the start
   * @param length the length
   * @throws SAXException the sax exception
   */
  public void ignorableWhitespace(char[] ch, int start, int length)
      throws SAXException {
    handler.ignorableWhitespace(ch, start, length);
  }

  /**
   * Notation decl.
   *
   * @param name     the name
   * @param publicId the public id
   * @param systemId the system id
   * @throws SAXException the sax exception
   */
  public void notationDecl(String name, String publicId, String systemId)
      throws SAXException {
    handler.notationDecl(name, publicId, systemId);
  }

  /**
   * Processing instruction.
   *
   * @param target the target
   * @param data   the data
   * @throws SAXException the sax exception
   */
  public void processingInstruction(String target, String data)
      throws SAXException {
    handler.processingInstruction(target, data);
  }

  /**
   * Sets document locator.
   *
   * @param locator the locator
   */
  public void setDocumentLocator(Locator locator) {
    handler.setDocumentLocator(locator);
  }

  /**
   * Sets result.
   *
   * @param result the result
   * @throws IllegalArgumentException the illegal argument exception
   */
  public void setResult(Result result) throws IllegalArgumentException {
    handler.setResult(result);
  }

  /**
   * Sets system id.
   *
   * @param systemID the system id
   */
  public void setSystemId(String systemID) {
    handler.setSystemId(systemID);
  }

  /**
   * Skipped entity.
   *
   * @param name the name
   * @throws SAXException the sax exception
   */
  public void skippedEntity(String name) throws SAXException {
    handler.skippedEntity(name);
  }

  /**
   * Start cdata.
   *
   * @throws SAXException the sax exception
   */
  public void startCDATA() throws SAXException {
    handler.startCDATA();
  }

  /**
   * Start dtd.
   *
   * @param name     the name
   * @param publicId the public id
   * @param systemId the system id
   * @throws SAXException the sax exception
   */
  public void startDTD(String name, String publicId, String systemId)
      throws SAXException {
    handler.startDTD(name, publicId, systemId);
  }

  /**
   * Start document.
   *
   * @throws SAXException the sax exception
   */
  public void startDocument() throws SAXException {
    handler.startDocument();
  }

  /**
   * Write element.
   *
   * @param name       the name
   * @param attributes the attributes
   * @throws SAXException the sax exception
   */
  public void writeElement(String name, Map<String, String> attributes) throws SAXException {
    startElement(name, attributes);
    endElement(name);
  }

  /**
   * Start element.
   *
   * @param name       the name
   * @param attributes the attributes
   * @throws SAXException the sax exception
   */
  public void startElement(String name, Map<String, String> attributes) throws SAXException {
    AttributesImpl atts = null;
    if (attributes != null) {
      atts = new AttributesImpl();
      for (Map.Entry<String, String> entry : attributes.entrySet()) {
        addAttribute(entry.getKey(), entry.getValue(), atts);
      }
    }
    handler.startElement("", "", name, atts);
  }

  /**
   * Start element.
   *
   * @param name the name
   * @param atts the atts
   * @throws SAXException the sax exception
   */
  public void startElement(String name, Attributes atts) throws SAXException {
    handler.startElement("", "", name, atts);
  }

  /**
   * Start element.
   *
   * @param name                the name
   * @param attributeNameValues the attribute name values
   * @throws SAXException the sax exception
   */
  public void startElement(String name, String... attributeNameValues) throws SAXException {
    AttributesImpl atts = null;
    if (attributeNameValues != null && attributeNameValues.length > 0) {
      if (attributeNameValues.length % 2 == 1) {
        throw new IllegalArgumentException("Even number of attribute name/name arguments required");
      }
      atts = new AttributesImpl();
      for (int i = 0; i < attributeNameValues.length; i += 2) {
        addAttribute(attributeNameValues[i], attributeNameValues[i + 1], atts);
      }
    }
    handler.startElement("", "", name, atts);
  }

  /**
   * Start entity.
   *
   * @param name the name
   * @throws SAXException the sax exception
   */
  public void startEntity(String name) throws SAXException {
    handler.startEntity(name);
  }

  /**
   * Start prefix mapping.
   *
   * @param prefix the prefix
   * @param uri    the uri
   * @throws SAXException the sax exception
   */
  public void startPrefixMapping(String prefix, String uri)
      throws SAXException {
    handler.startPrefixMapping(prefix, uri);
  }

  /**
   * Unparsed entity decl.
   *
   * @param name         the name
   * @param publicId     the public id
   * @param systemId     the system id
   * @param notationName the notation name
   * @throws SAXException the sax exception
   */
  public void unparsedEntityDecl(String name, String publicId,
                                 String systemId, String notationName) throws SAXException {
    handler.unparsedEntityDecl(name, publicId, systemId, notationName);
  }

  @Override
  public void close() {
    try {
      if (handler != null) {
        try {
          handler.endDocument();
        } catch (SAXException e) {
          throw new RuntimeException("Error in closing XML file", e);
        }
        handler = null;
      }
    } finally {
      IOUtil.close(out);
    }
  }

  /**
   * Create attributes attributes.
   *
   * @param attributeName  the attribute name
   * @param attributeValue the attribute value
   * @return the attributes
   */
  public static AttributesImpl createAttributes(String attributeName, String attributeValue) {
    AttributesImpl atts = new AttributesImpl();
    if (attributeValue != null) {
      addAttribute(attributeName, attributeValue, atts);
    }
    return atts;
  }

  /**
   * Add attribute attributes.
   *
   * @param name  the name
   * @param value the value
   * @param atts  the atts
   * @return the attributes
   */
  public static AttributesImpl addAttribute(String name, String value, AttributesImpl atts) {
    if (!StringUtil.isEmpty(value)) {
      atts.addAttribute("", "", name, "CDATA", value);
    }
    return atts;
  }

}
