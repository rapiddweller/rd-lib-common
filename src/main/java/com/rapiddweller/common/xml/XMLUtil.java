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

import com.rapiddweller.common.ArrayBuilder;
import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.ErrorHandler;
import com.rapiddweller.common.Filter;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.Level;
import com.rapiddweller.common.ParseUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.SyntaxError;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.Visitor;
import com.rapiddweller.common.converter.NoOpConverter;
import com.rapiddweller.common.converter.String2DateConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Provides XML Utility methods.
 * Created: 25.08.2007 22:09:26
 *
 * @author Volker Bergmann
 */
public class XMLUtil {

  private static final Logger LOGGER = LogManager.getLogger(XMLUtil.class);
  private static final String DOCUMENT_BUILDER_FACTORY_IMPL = "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
  private static final ErrorHandler DEFAULT_ERROR_HANDLER = new ErrorHandler(XMLUtil.class.getSimpleName(), Level.error);

  private static String defaultDocumentBuilderClassName = DOCUMENT_BUILDER_FACTORY_IMPL;

  private XMLUtil() {
  }

  /**
   * Format string.
   *
   * @param document the document
   * @return the string
   */
  public static String format(Document document) {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    String encoding = Encodings.UTF_8;
    SimpleXMLWriter out = new SimpleXMLWriter(buffer, encoding, true);
    format(document.getDocumentElement(), out);
    out.close();
    try {
      return buffer.toString(encoding);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Format string.
   *
   * @param element the element
   * @return the string
   */
  public static String format(Element element) {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    String encoding = Encodings.UTF_8;
    SimpleXMLWriter out = new SimpleXMLWriter(buffer, encoding, false);
    format(element, out);
    out.close();
    try {
      return buffer.toString(encoding);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Format short string.
   *
   * @param element the element
   * @return the string
   */
  public static String formatShort(Element element) {
    StringBuilder builder = new StringBuilder();
    builder.append('<').append(element.getNodeName());
    NamedNodeMap attributes = element.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      Attr attribute = (Attr) attributes.item(i);
      builder.append(' ').append(attribute.getName()).append("=\"").append(attribute.getValue()).append('"');
    }
    builder.append("...");
    return builder.toString();
  }

  /**
   * Format start tag string.
   *
   * @param element the element
   * @return the string
   */
  public static String formatStartTag(Element element) {
    StringBuilder builder = new StringBuilder();
    builder.append('<').append(element.getNodeName());
    NamedNodeMap attributes = element.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      Attr attribute = (Attr) attributes.item(i);
      builder.append(' ').append(attribute.getName()).append("=\"").append(attribute.getValue()).append('"');
    }
    builder.append(">");
    return builder.toString();
  }

  /**
   * Local name string.
   *
   * @param element the element
   * @return the string
   */
  public static String localName(Element element) {
    return localName(element.getNodeName());
  }

  /**
   * Local name string.
   *
   * @param elementName the element name
   * @return the string
   */
  public static String localName(String elementName) {
    if (elementName == null) {
      return null;
    }
    int sep = elementName.indexOf(':');
    if (sep < 0) {
      return elementName;
    }
    return elementName.substring(sep + 1);
  }

  /**
   * Get child elements element [ ].
   *
   * @param parent the parent
   * @return the element [ ]
   */
  public static Element[] getChildElements(Element parent) {
    NodeList childNodes = parent.getChildNodes();
    return toElementArray(childNodes);
  }

  /**
   * To element array element [ ].
   *
   * @param nodeList the node list
   * @return the element [ ]
   */
  public static Element[] toElementArray(NodeList nodeList) {
    if (nodeList == null) {
      return new Element[0];
    }
    int n = nodeList.getLength();
    ArrayBuilder<Element> builder = new ArrayBuilder<>(Element.class, n);
    for (int i = 0; i < n; i++) {
      Node item = nodeList.item(i);
      if (item instanceof Element) {
        builder.add((Element) item);
      }
    }
    return builder.toArray();
  }

  /**
   * To element list list.
   *
   * @param nodeList the node list
   * @return the list
   */
  public static List<Element> toElementList(NodeList nodeList) {
    List<Element> list = new ArrayList<>(nodeList != null ? nodeList.getLength() : 0);
    if (nodeList == null) {
      return list;
    }
    int n = nodeList.getLength();
    for (int i = 0; i < n; i++) {
      Node item = nodeList.item(i);
      if (item instanceof Element) {
        list.add((Element) item);
      }
    }
    return list;
  }

  /**
   * Get child elements element [ ].
   *
   * @param parent         the parent
   * @param namespaceAware the namespace aware
   * @param name           the name
   * @return the element [ ]
   */
  public static Element[] getChildElements(Element parent, boolean namespaceAware, String name) {
    ArrayBuilder<Element> builder = new ArrayBuilder<>(Element.class);
    NodeList childNodes = parent.getChildNodes();
    if (childNodes == null) {
      return new Element[0];
    }
    int n = childNodes.getLength();
    for (int i = 0; i < n; i++) {
      Node item = childNodes.item(i);
      if (item instanceof Element && hasName(name, namespaceAware, item)) {
        builder.add((Element) item);
      }
    }
    return builder.toArray();
  }

  /**
   * Gets child element at path.
   *
   * @param parent         the parent
   * @param path           the path
   * @param namespaceAware the namespace aware
   * @param required       the required
   * @return the child element at path
   */
  public static Element getChildElementAtPath(Element parent, String path, boolean namespaceAware, boolean required) {
    Element[] elements = getChildElementsAtPath(parent, path, namespaceAware);
    return assertSingleSearchResult(elements, required, path);
  }

  /**
   * Get child elements at path element [ ].
   *
   * @param parent         the parent
   * @param path           the path
   * @param namespaceAware the namespace aware
   * @return the element [ ]
   */
  public static Element[] getChildElementsAtPath(Element parent, String path, boolean namespaceAware) {
    ArrayBuilder<Element> builder = new ArrayBuilder<>(Element.class);
    getChildElementsAtPath(parent, namespaceAware, path.split("/"), 0, builder);
    return builder.toArray();
  }

  private static void getChildElementsAtPath(Element parent, boolean namespaceAware, String[] pathComponents, int pathIndex,
                                             ArrayBuilder<Element> result) {
    NodeList childNodes = parent.getChildNodes();
    if (childNodes != null) {
      String pathComponentName = pathComponents[pathIndex];
      int n = childNodes.getLength();
      for (int i = 0; i < n; i++) {
        Node item = childNodes.item(i);
        if (item instanceof Element) {
          Element element = (Element) item;
          if (pathIndex < pathComponents.length - 1) {
            getChildElementsAtPath(element, namespaceAware, pathComponents, pathIndex + 1, result);
          } else if (hasName(pathComponentName, namespaceAware, item)) {
            result.add(element);
          }
        }
      }
    }
  }

  /**
   * Has name boolean.
   *
   * @param name           the name
   * @param namespaceAware the namespace aware
   * @param item           the item
   * @return the boolean
   */
  public static boolean hasName(String name, boolean namespaceAware, Node item) {
    String fqName = item.getNodeName();
    if (namespaceAware) {
      return fqName.equals(name);
    } else {
      return name.equals(StringUtil.lastToken(fqName, ':'));
    }
  }

  /**
   * Gets child element.
   *
   * @param parent         the parent
   * @param namespaceAware the namespace aware
   * @param required       the required
   * @param name           the name
   * @return the child element
   */
  public static Element getChildElement(Element parent, boolean namespaceAware, boolean required, String name) {
    Element[] elements = getChildElements(parent, namespaceAware, name);
    return assertSingleSearchResult(elements, required, name);
  }

  /**
   * Gets child element text.
   *
   * @param elem           the elem
   * @param namespaceAware the namespace aware
   * @param required       the required
   * @param name           the name
   * @return the child element text
   */
  public static String getChildElementText(Element elem, boolean namespaceAware, boolean required, String name) {
    Element childElement = XMLUtil.getChildElement(elem, namespaceAware, required, name);
    if (childElement == null) {
      return null;
    }
    return childElement.getTextContent();
  }

  /**
   * Gets child element date.
   *
   * @param elem           the elem
   * @param namespaceAware the namespace aware
   * @param required       the required
   * @param name           the name
   * @param pattern        the pattern
   * @return the child element date
   */
  public static LocalDate getChildElementDate(Element elem, boolean namespaceAware, boolean required, String name, String pattern) {
    Element childElement = XMLUtil.getChildElement(elem, namespaceAware, required, name);
    if (childElement == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String dateSpec = childElement.getTextContent();
    return LocalDate.parse(dateSpec, formatter);
  }

  /**
   * Gets child element local date time.
   *
   * @param elem           the elem
   * @param namespaceAware the namespace aware
   * @param required       the required
   * @param name           the name
   * @param pattern        the pattern
   * @return the child element local date time
   */
  public static LocalDateTime getChildElementLocalDateTime(Element elem, boolean namespaceAware, boolean required, String name, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String dateSpec = XMLUtil.getChildElement(elem, namespaceAware, required, name).getTextContent();
    return LocalDateTime.parse(dateSpec, formatter);
  }

  private static Element assertSingleSearchResult(Element[] elements, boolean required, String searchTerm) {
    if (required && elements.length == 0) {
      throw new IllegalArgumentException("No element found in search: " + searchTerm);
    }
    if (elements.length > 1) {
      throw new IllegalArgumentException("More that one element found in search: " + searchTerm);
    }
    return (elements.length > 0 ? elements[0] : null);
  }

  /**
   * Get texts string [ ].
   *
   * @param nodes the nodes
   * @return the string [ ]
   */
  public static String[] getTexts(Element[] nodes) {
    if (nodes == null) {
      return null;
    }
    String[] texts = new String[nodes.length];
    for (int i = 0; i < nodes.length; i++) {
      texts[i] = getText(nodes[i]);
    }
    return texts;
  }

  /**
   * Gets text.
   *
   * @param node the node
   * @return the text
   */
  public static String getText(Node node) {
    if (node == null) {
      return null;
    }
    if (node instanceof Text) {
      return node.getNodeValue();
    }
    NodeList children = node.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      if (children.item(i) instanceof Text) {
        return children.item(i).getNodeValue();
      }
    }
    return null;
  }

  /**
   * Gets integer attribute.
   *
   * @param element      the element
   * @param name         the name
   * @param defaultValue the default value
   * @return the integer attribute
   */
  public static Integer getIntegerAttribute(Element element, String name, Integer defaultValue) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getIntegerAttribute(" + element.getNodeName() + ", " + name + ')');
    }
    String stringValue = getAttribute(element, name, false);
    if (StringUtil.isEmpty(stringValue)) {
      return defaultValue;
    }
    return Integer.parseInt(stringValue);
  }

  /**
   * Gets long attribute.
   *
   * @param element      the element
   * @param name         the name
   * @param defaultValue the default value
   * @return the long attribute
   */
  public static Long getLongAttribute(Element element, String name, long defaultValue) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getLongAttribute(" + element.getNodeName() + ", " + name + ')');
    }
    String stringValue = getAttribute(element, name, false);
    if (StringUtil.isEmpty(stringValue)) {
      return defaultValue;
    }
    return Long.parseLong(stringValue);
  }

  /**
   * Gets attribute.
   *
   * @param element       the element
   * @param attributeName the attribute name
   * @param required      the required
   * @return the attribute
   */
  public static String getAttribute(Element element, String attributeName, boolean required) {
    String value = StringUtil.emptyToNull(element.getAttribute(attributeName));
    if (value == null && required) {
      throw new IllegalArgumentException("Element '" + element.getNodeName() + "'" +
          " is missing the required attribute '" + attributeName + "'");
    }
    return value;
  }

  /**
   * Gets attributes.
   *
   * @param element the element
   * @return the attributes
   */
  public static Map<String, String> getAttributes(Element element) {
    NamedNodeMap attributes = element.getAttributes();
    Map<String, String> result = new HashMap<>();
    int n = attributes.getLength();
    for (int i = 0; i < n; i++) {
      Attr attribute = (Attr) attributes.item(i);
      result.put(attribute.getName(), attribute.getValue());
    }
    return result;
  }

  /**
   * Create xml file print writer.
   *
   * @param uri      the uri
   * @param encoding the encoding
   * @return the print writer
   * @throws FileNotFoundException        the file not found exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static PrintWriter createXMLFile(String uri, String encoding)
      throws FileNotFoundException, UnsupportedEncodingException {
    PrintWriter printer = IOUtil.getPrinterForURI(uri, encoding);
    printer.println("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
    return printer;
  }

  /**
   * Normalized attribute value string.
   *
   * @param element       the element
   * @param attributeName the attribute name
   * @return the string
   */
  public static String normalizedAttributeValue(Element element, String attributeName) {
    String value = element.getAttribute(attributeName);
    if (StringUtil.isEmpty(value)) {
      value = null;
    }
    return value;
  }

  /**
   * Get child comments comment [ ].
   *
   * @param parent the parent
   * @return the comment [ ]
   */
  public static Comment[] getChildComments(Node parent) {
    NodeList children;
    if (parent instanceof Document) {
      children = parent.getChildNodes();
    } else if (parent instanceof Element) {
      children = parent.getChildNodes();
    } else {
      throw new UnsupportedOperationException("Not a supported type: " + parent.getClass());
    }
    ArrayBuilder<Comment> builder = new ArrayBuilder<>(Comment.class);
    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      if (child instanceof Comment) {
        builder.add((Comment) child);
      }
    }
    return builder.toArray();
  }

  // XML operations --------------------------------------------------------------------------------------------------

  /**
   * Parse file if exists document.
   *
   * @param file the file
   * @return the document
   * @throws IOException the io exception
   */
  public static Document parseFileIfExists(File file) throws IOException {
    if (file == null || !file.exists()) {
      return null;
    }
    return parse(file.getAbsolutePath(), true, null, null, null);
  }

  /**
   * Parse document.
   *
   * @param uri the uri
   * @return the document
   * @throws IOException the io exception
   */
  public static Document parse(String uri) throws IOException {
    return parse(uri, true, null, null, null);
  }

  /**
   * Parse document.
   *
   * @param uri            the uri
   * @param namespaceAware the namespace aware
   * @param resolver       the resolver
   * @param schemaUri      the schema uri
   * @param classLoader    the class loader
   * @return the document
   * @throws IOException the io exception
   */
  public static Document parse(String uri, boolean namespaceAware, EntityResolver resolver, String schemaUri, ClassLoader classLoader)
      throws IOException {
    InputStream stream = null;
    try {
      stream = IOUtil.getInputStreamForURI(uri);
      return parse(stream, namespaceAware, resolver, schemaUri, classLoader, DEFAULT_ERROR_HANDLER);
    } catch (ConfigurationError e) {
      throw new ConfigurationError("Error parsing " + uri, e);
    } finally {
      IOUtil.close(stream);
    }
  }

  /**
   * Parse string document.
   *
   * @param text the text
   * @return the document
   */
  public static Document parseString(String text) {
    return parseString(text, null, null);
  }

  /**
   * Parse string as element element.
   *
   * @param xml the xml
   * @return the element
   */
  public static Element parseStringAsElement(String xml) {
    return XMLUtil.parseString(xml).getDocumentElement();
  }

  /**
   * Parse string document.
   *
   * @param text        the text
   * @param resolver    the resolver
   * @param classLoader the class loader
   * @return the document
   */
  public static Document parseString(String text, EntityResolver resolver, ClassLoader classLoader) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(text);
    }
    try {
      String encoding = getEncoding(text, SystemInfo.getFileEncoding());
      return parse(new ByteArrayInputStream(text.getBytes(encoding)), true, resolver, null, classLoader, DEFAULT_ERROR_HANDLER);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected error", e);
    }
  }

  /**
   * Gets encoding.
   *
   * @param text            the text
   * @param defaultEncoding the default encoding
   * @return the encoding
   */
  public static String getEncoding(String text, String defaultEncoding) {
    if (text.startsWith("<?xml")) {
      int qm2i = text.indexOf('?', 5);
      int ei = text.indexOf("encoding");
      if (ei > 0 && ei < qm2i) {
        int dq = text.indexOf('"', ei);
        int sq = text.indexOf('\'', ei);
        int q1 = (dq > 0 ? (sq > 0 ? dq : Math.min(sq, dq)) : sq);
        dq = text.indexOf('"', q1 + 1);
        sq = text.indexOf('\'', q1 + 1);
        int q2 = (dq > 0 ? (sq > 0 ? dq : Math.min(sq, dq)) : sq);
        if (q1 > 0 && q2 > 0) {
          return text.substring(q1 + 1, q2);
        }
      }
    }
    return defaultEncoding;
  }

  /**
   * Parse document.
   *
   * @param stream the stream
   * @return the document
   * @throws IOException the io exception
   */
  public static Document parse(InputStream stream) throws IOException {
    return parse(stream, null, null, DEFAULT_ERROR_HANDLER);
  }

  /**
   * Parses a stream's output into an XML document.
   *
   * @param in           the {@link InputStream} to read
   * @param resolver     an {@link EntityResolver} implementation or null, in the latter case, no validation is applied
   * @param schemaUri    the URI of the XML document
   * @param errorHandler the error handler
   * @return the resulting XML {@link Document}
   * @throws IOException if stream access fails
   */
  public static Document parse(InputStream in, EntityResolver resolver, String schemaUri, ErrorHandler errorHandler) throws IOException {
    return parse(in, true, resolver, schemaUri, null, errorHandler);
  }

  /**
   * Parse document.
   *
   * @param stream         the stream
   * @param namespaceAware the namespace aware
   * @param resolver       the resolver
   * @param schemaUri      the schema uri
   * @param classLoader    the class loader
   * @param errorHandler   the error handler
   * @return the document
   * @throws IOException the io exception
   */
  public static Document parse(InputStream stream, boolean namespaceAware, EntityResolver resolver,
                               String schemaUri, ClassLoader classLoader, ErrorHandler errorHandler)
      throws IOException {
    try {
      DocumentBuilderFactory factory = createDocumentBuilderFactory(classLoader);
      factory.setNamespaceAware(namespaceAware);
      if (schemaUri != null) {
        activateXmlSchemaValidation(factory, schemaUri);
      }
      DocumentBuilder builder = factory.newDocumentBuilder();
      if (resolver != null) {
        builder.setEntityResolver(resolver);
      }
      if (errorHandler == null) {
        errorHandler = new ErrorHandler("XMLUtil");
      }
      builder.setErrorHandler(createSaxErrorHandler(errorHandler));
      return builder.parse(stream);
    } catch (SAXParseException e) {
      throw new ConfigurationError("Error in line " + e.getLineNumber() + " column " + e.getColumnNumber(), e);
    } catch (ParserConfigurationException | SAXException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Gets default document builder class name.
   *
   * @return the default document builder class name
   */
  public static String getDefaultDocumentBuilderClassName() {
    return defaultDocumentBuilderClassName;
  }

  /**
   * Sets default document builder class name.
   *
   * @param defaultDocumentBuilderClassName the default document builder class name
   */
  public static void setDefaultDocumentBuilderClassName(String defaultDocumentBuilderClassName) {
    XMLUtil.defaultDocumentBuilderClassName = defaultDocumentBuilderClassName;
  }

  /**
   * Create document builder factory document builder factory.
   *
   * @param classLoader the class loader
   * @return the document builder factory
   */
  public static DocumentBuilderFactory createDocumentBuilderFactory(ClassLoader classLoader) {
    if (defaultDocumentBuilderClassName != null) {
      if (classLoader == null) {
        classLoader = Thread.currentThread().getContextClassLoader();
      }
      return DocumentBuilderFactory.newInstance(defaultDocumentBuilderClassName, classLoader);
    } else {
      return DocumentBuilderFactory.newInstance();
    }
  }

  /**
   * Namespace alias namespace alias.
   *
   * @param document     the document
   * @param namespaceUri the namespace uri
   * @return the namespace alias
   */
  public static NamespaceAlias namespaceAlias(Document document, String namespaceUri) {
    Map<String, String> attributes = XMLUtil.getAttributes(document.getDocumentElement());
    for (Map.Entry<String, String> entry : attributes.entrySet()) {
      String namespaceName = entry.getValue();
      if (namespaceUri.equals(namespaceName)) {
        String def = entry.getKey();
        String alias = (def.contains(":") ? StringUtil.lastToken(def, ':') : "");
        return new NamespaceAlias(alias, namespaceName);
      }
    }
    return new NamespaceAlias("", namespaceUri);
  }

  /**
   * Gets namespaces.
   *
   * @param document the document
   * @return the namespaces
   */
  public static Map<String, String> getNamespaces(Document document) {
    Map<String, String> namespaces = new HashMap<>();
    Map<String, String> attributes = XMLUtil.getAttributes(document.getDocumentElement());
    for (Map.Entry<String, String> entry : attributes.entrySet()) {
      String attributeName = entry.getKey();
      if (attributeName.startsWith("xmlns")) {
        String alias = (attributeName.contains(":") ? StringUtil.lastToken(attributeName, ':') : "");
        namespaces.put(alias, entry.getValue());
      }
    }
    return namespaces;
  }

  /**
   * Gets target namespace.
   *
   * @param xsdDocument the xsd document
   * @return the target namespace
   */
  public static String getTargetNamespace(Document xsdDocument) {
    return xsdDocument.getDocumentElement().getAttribute("targetNamespace");
  }

  /**
   * Gets boolean attribute.
   *
   * @param element       the element
   * @param attributeName the attribute name
   * @param required      the required
   * @return the boolean attribute
   */
  public static Boolean getBooleanAttribute(Element element, String attributeName, boolean required) {
    String stringValue = element.getAttribute(attributeName);
    if (StringUtil.isEmpty(stringValue) && required) {
      throw new SyntaxError("Missing attribute '" + attributeName + "'", format(element));
    }
    return ParseUtil.parseBoolean(stringValue);
  }

  /**
   * Gets boolean attribute with default.
   *
   * @param element       the element
   * @param attributeName the attribute name
   * @param defaultValue  the default value
   * @return the boolean attribute with default
   */
  public static boolean getBooleanAttributeWithDefault(Element element, String attributeName, boolean defaultValue) {
    String stringValue = element.getAttribute(attributeName);
    return (StringUtil.isEmpty(stringValue) ? defaultValue : Boolean.parseBoolean(stringValue));
  }

  /**
   * Gets double attribute.
   *
   * @param element the element
   * @param name    the name
   * @return the double attribute
   */
  public static double getDoubleAttribute(Element element, String name) {
    return Double.parseDouble(element.getAttribute(name));
  }

  /**
   * Gets date attribute.
   *
   * @param element the element
   * @param name    the name
   * @return the date attribute
   */
  public static Date getDateAttribute(Element element, String name) {
    return new String2DateConverter<>().convert(element.getAttribute(name));
  }

  /**
   * Gets zone date time attribute.
   *
   * @param element       the element
   * @param attributeName the attribute name
   * @param pattern       the pattern
   * @return the zone date time attribute
   */
  public static ZonedDateTime getZoneDateTimeAttribute(Element element, String attributeName, String pattern) {
    String text = getAttribute(element, attributeName, true);
    return (text != null ? ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(pattern)) : null);
  }

  /**
   * Gets local date attribute.
   *
   * @param element       the element
   * @param attributeName the attribute name
   * @param pattern       the pattern
   * @return the local date attribute
   */
  public static LocalDate getLocalDateAttribute(Element element, String attributeName, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String text = getAttribute(element, attributeName, true);
    return LocalDate.parse(text, formatter);
  }

  /**
   * Map attributes to properties.
   *
   * @param element  the element
   * @param bean     the bean
   * @param unescape the unescape
   */
  public static void mapAttributesToProperties(Element element, Object bean, boolean unescape) {
    mapAttributesToProperties(element, bean, unescape, new NoOpConverter<>());
  }

  /**
   * Map attributes to properties.
   *
   * @param element        the element
   * @param bean           the bean
   * @param unescape       the unescape
   * @param nameNormalizer the name normalizer
   */
  public static void mapAttributesToProperties(Element element, Object bean, boolean unescape, Converter<String, String> nameNormalizer) {
    for (Map.Entry<String, String> attribute : getAttributes(element).entrySet()) {
      String name = StringUtil.lastToken(attribute.getKey(), ':');
      name = nameNormalizer.convert(name);
      String value = attribute.getValue();
      if (unescape) {
        value = StringUtil.unescape(value);
      }
      Class<?> type = bean.getClass();
      if (BeanUtil.hasProperty(type, name)) {
        BeanUtil.setPropertyValue(bean, name, value, true, true);
      }
    }
  }

  /**
   * Visit.
   *
   * @param element the element
   * @param visitor the visitor
   */
  public static void visit(Node element, Visitor<Node> visitor) {
    visitor.visit(element);
    NodeList childNodes = element.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      visit(childNodes.item(i), visitor);
    }
  }

  /**
   * Find element by attribute element.
   *
   * @param attributeName  the attribute name
   * @param attributeValue the attribute value
   * @param root           the root
   * @return the element
   */
  public static Element findElementByAttribute(String attributeName, String attributeValue, Element root) {
    if (attributeValue.equals(root.getAttribute(attributeName))) {
      return root;
    } else {
      for (Element child : XMLUtil.getChildElements(root)) {
        Element candidate = findElementByAttribute(attributeName, attributeValue, child);
        if (candidate != null) {
          return candidate;
        }
      }
    }
    return null;
  }

  /**
   * Find first accepted element.
   *
   * @param filter  the filter
   * @param element the element
   * @return the element
   */
  public static Element findFirstAccepted(Filter<Element> filter, Element element) {
    if (filter.accept(element)) {
      return element;
    } else {
      for (Element child : XMLUtil.getChildElements(element)) {
        Element candidate = findFirstAccepted(filter, child);
        if (candidate != null) {
          return candidate;
        }
      }
    }
    return null;
  }

  /**
   * Find elements by name list.
   *
   * @param name          the name
   * @param caseSensitive the case sensitive
   * @param root          the root
   * @return the list
   */
  public static List<Element> findElementsByName(String name, boolean caseSensitive, Element root) {
    return findElementsByName(name, caseSensitive, root, new ArrayList<>());
  }

  /**
   * Gets whole text.
   *
   * @param element the element
   * @return the whole text
   */
  public static String getWholeText(Element element) {
    StringBuilder builder = new StringBuilder();
    NodeList nodeList = element.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node instanceof Text) {
        builder.append(((Text) node).getWholeText());
      } else if (node instanceof Element) {
        builder.append(getWholeText((Element) node));
      }
    }
    return builder.toString();
  }

  /**
   * Format text string.
   *
   * @param text the text
   * @return the string
   */
  public static String formatText(String text) {
    return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static void format(Element element, SimpleXMLWriter out) {
    String name = element.getNodeName();
    Map<String, String> attributes = XMLUtil.getAttributes(element);
    try {
      out.startElement(name, attributes);
      NodeList childNodes = element.getChildNodes();
      for (int i = 0; i < childNodes.getLength(); i++) {
        Node child = childNodes.item(i);
        if (child instanceof Element) {
          format((Element) child, out);
        } else if (child instanceof Text) {
          String text = child.getTextContent();
          if (!StringUtil.isEmpty(text)) {
            out.characters(text.toCharArray(), 0, text.length());
          }
        }
      }
      out.endElement(name);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  private static List<Element> findElementsByName(String name, boolean caseSensitive, Element root, List<Element> result) {
    if (root.getNodeName().equals(name)) {
      result.add(root);
    } else {
      for (Element child : getChildElements(root)) {
        findElementsByName(name, caseSensitive, child, result);
      }
    }
    return result;
  }

  private static void activateXmlSchemaValidation(DocumentBuilderFactory factory, String schemaUrl) {
    try {
      SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = schemaFactory.newSchema(new URL(schemaUrl));
      factory.setSchema(schema);
    } catch (Exception e) {
      // some XML parsers may not support attributes in general or especially XML Schema
      LOGGER.error("Error activating schema validation for schema " + schemaUrl + ", possibly you are offline or behind a proxy?", e.getMessage());
    }
  }

  private static org.xml.sax.ErrorHandler createSaxErrorHandler(
      final ErrorHandler errorHandler) {
    return new org.xml.sax.ErrorHandler() {

      @Override
      public void error(SAXParseException e) {
        errorHandler.handleError(e.getMessage(), e);
      }

      @Override
      public void fatalError(SAXParseException e) {
        errorHandler.handleError(e.getMessage(), e);
      }

      @Override
      public void warning(SAXParseException e) {
        errorHandler.handleError(e.getMessage(), e);
      }

    };
  }

  /**
   * Save as properties.
   *
   * @param properties the properties
   * @param file       the file
   * @param encoding   the encoding
   * @throws FileNotFoundException the file not found exception
   */
  @SuppressWarnings("null")
  public static void saveAsProperties(Properties properties, File file, String encoding) throws FileNotFoundException {
    if (properties.size() == 0) {
      throw new IllegalArgumentException("Cannot save empty Properties");
    }
    Document document = null;
    for (Map.Entry<Object, Object> entry : properties.entrySet()) {
      String key = (String) entry.getKey();
      String value = (String) entry.getValue();
      String[] prefixAndRemainingPath = StringUtil.splitOnFirstSeparator(key, '.');
      if (document == null) {
        document = createDocument(prefixAndRemainingPath[0]);
      }
      String rootElementName = document.getDocumentElement().getNodeName();
      if (!key.startsWith(rootElementName + '.')) {
        throw new SyntaxError("Required prefix '" + rootElementName + "' not present in key", key);
      }
      setProperty(prefixAndRemainingPath[1], value, document.getDocumentElement(), document);
    }
    document.setXmlStandalone(true); // needed to omit standalone="yes/no" in the XML header
    saveDocument(document, file, encoding);
  }

  /**
   * Save document.
   *
   * @param document the document
   * @param file     the file
   * @param encoding the encoding
   * @throws FileNotFoundException the file not found exception
   */
  public static void saveDocument(Document document, File file, String encoding) throws FileNotFoundException {
    FileOutputStream stream = new FileOutputStream(file);
    saveDocument(document, encoding, stream);
  }

  /**
   * Save document.
   *
   * @param document the document
   * @param encoding the encoding
   * @param out      the out
   * @throws TransformerFactoryConfigurationError the transformer factory configuration error
   */
  public static void saveDocument(Document document, String encoding, OutputStream out)
      throws TransformerFactoryConfigurationError {
    try {
      Transformer transformer = createTransformer(encoding);
      transformer.transform(new DOMSource(document), new StreamResult(out));
    } catch (TransformerException e) {
      throw new ConfigurationError(e);
    } finally {
      IOUtil.close(out);
    }
  }

  /**
   * Create document document.
   *
   * @param rootElementName the root element name
   * @return the document
   */
  public static Document createDocument(String rootElementName) {
    Document document = createDocument();
    Element rootElement = document.createElement(rootElementName);
    document.appendChild(rootElement);
    return document;
  }

  /**
   * Create document document.
   *
   * @return the document
   */
  public static Document createDocument() {
    try {
      DocumentBuilder documentBuilder = createDocumentBuilderFactory(null).newDocumentBuilder();
      return documentBuilder.newDocument();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sets property.
   *
   * @param key      the key
   * @param value    the value
   * @param document the document
   */
  public static void setProperty(String key, String value, Document document) {
    String[] prefixAndRemainingPath = StringUtil.splitOnFirstSeparator(key, '.');
    Element rootElement = document.getDocumentElement();
    if (rootElement == null) {
      rootElement = document.createElement(prefixAndRemainingPath[0]);
      document.appendChild(rootElement);
    } else if (!key.equals(rootElement.getNodeName())) {
      throw new IllegalArgumentException("Cannot set a property '" + key + "' on a document with root <" + rootElement.getNodeName() + ">");
    }
    setProperty(prefixAndRemainingPath[1], value, rootElement, document);
  }

  /**
   * Sets property.
   *
   * @param key      the key
   * @param value    the value
   * @param element  the element
   * @param document the document
   */
  public static void setProperty(String key, String value, Element element, Document document) {
    if (!StringUtil.isEmpty(key)) {
      String[] prefixAndRemainingPath = StringUtil.splitOnFirstSeparator(key, '.');
      String childName = prefixAndRemainingPath[0];
      Element child = getChildElement(element, false, false, childName);
      if (child == null) {
        child = document.createElement(childName);
        element.appendChild(child);
      }
      setProperty(prefixAndRemainingPath[1], value, child, document);
    } else {
      element.setTextContent(value);
    }
  }

  /**
   * Resolve entities string.
   *
   * @param xmlText the xml text
   * @return the string
   */
  public static String resolveEntities(String xmlText) {
    while (xmlText.contains("&#")) {
      int st = xmlText.indexOf("&#");
      int en = xmlText.indexOf(";", st + 1);
      if (st >= 0 && en > 0) {
        int c = Integer.parseInt(xmlText.substring(st + 2, en));
        xmlText = xmlText.substring(0, st) + (char) c + xmlText.substring(en + 1);
      } else {
        break;
      }
    }
    return xmlText;
  }

  /**
   * Gets parent node.
   *
   * @param node the node
   * @return the parent node
   */
  public static Node getParentNode(Node node) {
    if (node instanceof Attr) {
      return ((Attr) node).getOwnerElement();
    } else {
      return node.getParentNode();
    }
  }

  /**
   * Xpath to string.
   *
   * @param node the node
   * @return the string
   */
  public static String xpathTo(Node node) {
    Node[] nodePath = nodePathTo(node);
    StringBuilder builder = new StringBuilder();
    for (Node component : nodePath) {
      if (component instanceof Document) {
        continue;
      }
      builder.append("/");
      builder.append(formatXPathComponent(component));
    }
    return builder.toString();
  }

  /**
   * Node path to node [ ].
   *
   * @param node the node
   * @return the node [ ]
   */
  public static Node[] nodePathTo(Node node) {
    ArrayBuilder<Node> builder = new ArrayBuilder<>(Node.class);
    buildPath(node, builder);
    return builder.toArray();
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private static Object formatXPathComponent(Node node) {
    if (node instanceof Attr) {
      return "@" + node.getNodeName();
    } else if (node instanceof ProcessingInstruction) {
      return "/?" + ((ProcessingInstruction) node).getTarget();
    } else if (node instanceof Element) {
      return formatXPathComponentForElement((Element) node);
    } else if (node instanceof Comment) {
      return formatXPathComponentForComment((Comment) node);
    } else {
      return node.getNodeName();
    }
  }

  private static String formatXPathComponentForElement(Element child) {
    String childName = child.getNodeName();
    Node parentNode = getParentNode(child);
    if (parentNode instanceof Document) {
      return childName;
    }
    Element parent = (Element) parentNode;
    NodeList siblings = parent.getElementsByTagName(childName);
    if (siblings.getLength() == 1) {
      return childName;
    }
    for (int i = 0; i < siblings.getLength(); i++) {
      if (siblings.item(i) == child) {
        return childName + "[" + (i + 1) + "]";
      }
    }
    throw new IllegalArgumentException("Child element not found under parent");
  }

  private static String formatXPathComponentForComment(Comment child) {
    Node parentNode = getParentNode(child);
    Comment[] siblings = getChildComments(parentNode);
    if (siblings.length == 1) {
      return "comment()";
    }
    for (int i = 0; i < siblings.length; i++) {
      if (siblings[i] == child) {
        return "comment()[" + (i + 1) + "]";
      }
    }
    throw new IllegalArgumentException("Comment not found under parent");
  }

  private static void buildPath(Node node, ArrayBuilder<Node> list) {
    final Node parent = node.getParentNode();
    if (parent != null) {
      buildPath(parent, list);
    }
    list.add(node);
  }

  private static Transformer createTransformer(String encoding) {
    try {
      SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
      transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}" + "indent-amount", "2");
      return transformer;
    } catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
      throw new ConfigurationError("Error creating Transformer", e);
    }
  }

}
