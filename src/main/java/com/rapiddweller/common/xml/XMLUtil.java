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
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.Filter;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.ParseUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.Visitor;
import com.rapiddweller.common.converter.NoOpConverter;
import com.rapiddweller.common.converter.String2DateConverter;
import com.rapiddweller.common.exception.ExceptionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
import java.nio.charset.StandardCharsets;
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
 * Provides XML Utility methods.<br/><br/>
 * Created: 25.08.2007 22:09:26
 * @author Volker Bergmann
 */
public class XMLUtil {

  private static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);

  private static final String DOCUMENT_BUILDER_FACTORY_IMPL = "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";

  private static String defaultDocumentBuilderClassName = DOCUMENT_BUILDER_FACTORY_IMPL;

  private XMLUtil() {
    // private constructor preventing instantiation of this utility class
  }

  public static String format(Document document) {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    SimpleXMLWriter out = new SimpleXMLWriter(buffer, Encodings.UTF_8, true);
    format(document.getDocumentElement(), out);
    out.close();
    return buffer.toString(StandardCharsets.UTF_8);
  }

  public static String format(Element element) {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    String encoding = Encodings.UTF_8;
    SimpleXMLWriter out = new SimpleXMLWriter(buffer, encoding, false);
    format(element, out);
    out.close();
    try {
      return buffer.toString(encoding);
    } catch (UnsupportedEncodingException e) {
      throw ExceptionFactory.getInstance().programmerConfig(e.getMessage(), e);
    }
  }

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

  public static String localName(Element element) {
    return localName(element.getNodeName());
  }

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

  public static Element[] getChildElements(Element parent) {
    NodeList childNodes = parent.getChildNodes();
    return toElementArray(childNodes);
  }

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

  public static Element getChildElementAtPath(Element parent, String path, boolean namespaceAware, boolean required) {
    Element[] elements = getChildElementsAtPath(parent, path, namespaceAware);
    return assertSingleSearchResult(elements, required, path);
  }

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

  public static boolean hasName(String name, boolean namespaceAware, Node item) {
    String fqName = item.getNodeName();
    if (namespaceAware) {
      return fqName.equals(name);
    } else {
      return name.equals(StringUtil.lastToken(fqName, ':'));
    }
  }

  public static Element getChildElement(Element parent, boolean namespaceAware, boolean required, String name) {
    Element[] elements = getChildElements(parent, namespaceAware, name);
    return assertSingleSearchResult(elements, required, name);
  }

  public static String getChildElementText(Element elem, boolean namespaceAware, boolean required, String name) {
    Element childElement = XMLUtil.getChildElement(elem, namespaceAware, required, name);
    if (childElement == null) {
      return null;
    }
    return childElement.getTextContent();
  }

  public static LocalDate getChildElementDate(Element elem, boolean namespaceAware, boolean required, String name, String pattern) {
    Element childElement = XMLUtil.getChildElement(elem, namespaceAware, required, name);
    if (childElement == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String dateSpec = childElement.getTextContent();
    return LocalDate.parse(dateSpec, formatter);
  }

  public static LocalDateTime getChildElementLocalDateTime(Element elem, boolean namespaceAware, boolean required, String name, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String dateSpec = XMLUtil.getChildElement(elem, namespaceAware, required, name).getTextContent();
    return LocalDateTime.parse(dateSpec, formatter);
  }

  private static Element assertSingleSearchResult(Element[] elements, boolean required, String searchTerm) {
    if (required && elements.length == 0) {
      throw ExceptionFactory.getInstance().syntaxError(null, -1, -1, "No element found in search: " + searchTerm);
    }
    if (elements.length > 1) {
      throw ExceptionFactory.getInstance().syntaxError(null, -1, -1, "More that one element found in search: " + searchTerm);
    }
    return (elements.length > 0 ? elements[0] : null);
  }

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

  public static Integer getIntegerAttribute(Element element, String name, Integer defaultValue) {
    if (logger.isDebugEnabled()) {
      logger.debug("getIntegerAttribute(" + element.getNodeName() + ", " + name + ')');
    }
    String stringValue = getAttribute(element, name, false);
    if (StringUtil.isEmpty(stringValue)) {
      return defaultValue;
    }
    return Integer.parseInt(stringValue);
  }

  public static Long getLongAttribute(Element element, String name, long defaultValue) {
    if (logger.isDebugEnabled()) {
      logger.debug("getLongAttribute(" + element.getNodeName() + ", " + name + ')');
    }
    String stringValue = getAttribute(element, name, false);
    if (StringUtil.isEmpty(stringValue)) {
      return defaultValue;
    }
    return Long.parseLong(stringValue);
  }

  public static String getAttribute(Element element, String attributeName, boolean required) {
    String value = StringUtil.emptyToNull(element.getAttribute(attributeName));
    if (value == null && required) {
      throw ExceptionFactory.getInstance().syntaxError(null, -1, -1,
          "Element <" + element.getNodeName() + ">" +
          " is missing the required attribute '" + attributeName + "'");
    }
    return value;
  }

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

  public static PrintWriter createXMLFile(String uri, String encoding) {
    try {
      PrintWriter printer = IOUtil.getPrinterForURI(uri, encoding);
      printer.println("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
      return printer;
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(uri, e);
    } catch (UnsupportedEncodingException e) {
      throw ExceptionFactory.getInstance().programmerConfig("XML file creation failed.", e);
    }
  }

  public static String normalizedAttributeValue(Element element, String attributeName) {
    String value = element.getAttribute(attributeName);
    if (StringUtil.isEmpty(value)) {
      value = null;
    }
    return value;
  }

  public static Comment[] getChildComments(Node parent) {
    NodeList children;
    if (parent instanceof Document) {
      children = parent.getChildNodes();
    } else if (parent instanceof Element) {
      children = parent.getChildNodes();
    } else {
      throw ExceptionFactory.getInstance().programmerUnsupported("Not a supported type: " + parent.getClass());
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

  public static Document parseFileIfExists(File file) {
    if (file == null || !file.exists()) {
      return null;
    }
    return parse(file.getAbsolutePath(), true, null, null, null);
  }

  public static Document parse(String uri) {
    return parse(uri, true, null, null, null);
  }

  public static Document parse(
      String uri, boolean namespaceAware, EntityResolver resolver, String schemaUri, ClassLoader classLoader) {
    try {
      try (InputStream stream = IOUtil.getInputStreamForURI(uri)) {
        return parse(stream, namespaceAware, resolver, uri, schemaUri, classLoader);
      }
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(uri, e);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileAccessException("Access to " + uri + " failed", e);
    }
  }

  public static Document parse(InputStream stream) {
    return parse(stream, null, null, null);
  }

  /** Parses a stream's output into an XML document.
   *  @param in           the {@link InputStream} to read
   *  @param resolver     an {@link EntityResolver} implementation or null, in the latter case, no validation is applied
   *  @param schemaUri    the URI of the XML document
   *  @return the resulting XML {@link Document} */
  public static Document parse(InputStream in, EntityResolver resolver, String uri, String schemaUri) {
    return parse(in, true, resolver, uri, schemaUri, null);
  }

  public static Document parse(InputStream stream, boolean namespaceAware, EntityResolver resolver,
                               String uri, String schemaUri, ClassLoader classLoader) {
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
      builder.setErrorHandler(createSaxErrorHandler());
      return builder.parse(stream);
    } catch (ParserConfigurationException e) {
      throw ExceptionFactory.getInstance().programmerConfig("Error in " + uri, e);
    } catch (SAXParseException e) {
      throw ExceptionFactory.getInstance().syntaxError(uri, e.getLineNumber(), e.getColumnNumber(), e);
    } catch (SAXException e) {
      throw ExceptionFactory.getInstance().programmerConfig("Error parsing " + uri, e);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileAccessException(uri, e);
    }
  }

  public static Document parseString(String text) {
    return parseString(text, null, null);
  }

  public static Element parseStringAsElement(String xml) {
    return XMLUtil.parseString(xml).getDocumentElement();
  }

  public static Document parseString(String text, EntityResolver resolver, ClassLoader classLoader) {
    if (logger.isDebugEnabled()) {
      logger.debug(text);
    }
    try {
      String encoding = getEncoding(text, SystemInfo.getFileEncoding());
      return parse(new ByteArrayInputStream(text.getBytes(encoding)), true, resolver, null, null, classLoader);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().programmerStateError("Unexpected error: " + e);
    }
  }

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

  public static String getDefaultDocumentBuilderClassName() {
    return defaultDocumentBuilderClassName;
  }

  public static void setDefaultDocumentBuilderClassName(String defaultDocumentBuilderClassName) {
    XMLUtil.defaultDocumentBuilderClassName = defaultDocumentBuilderClassName;
  }

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

  public static String getTargetNamespace(Document xsdDocument) {
    return xsdDocument.getDocumentElement().getAttribute("targetNamespace");
  }

  public static Boolean getBooleanAttribute(Element element, String attributeName, boolean required) {
    String stringValue = element.getAttribute(attributeName);
    if (StringUtil.isEmpty(stringValue) && required) {
      throw ExceptionFactory.getInstance().syntaxError(null, -1, -1, "Attribute missing: '" + attributeName + "'");
    }
    return ParseUtil.parseBoolean(stringValue);
  }

  public static boolean getBooleanAttributeWithDefault(Element element, String attributeName, boolean defaultValue) {
    String stringValue = element.getAttribute(attributeName);
    return (StringUtil.isEmpty(stringValue) ? defaultValue : Boolean.parseBoolean(stringValue));
  }

  public static double getDoubleAttribute(Element element, String name) {
    return Double.parseDouble(element.getAttribute(name));
  }

  public static Date getDateAttribute(Element element, String name) {
    return new String2DateConverter<>().convert(element.getAttribute(name));
  }

  public static ZonedDateTime getZoneDateTimeAttribute(Element element, String attributeName, String pattern) {
    String text = getAttribute(element, attributeName, true);
    return (text != null ? ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(pattern)) : null);
  }

  public static LocalDate getLocalDateAttribute(Element element, String attributeName, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String text = getAttribute(element, attributeName, true);
    return LocalDate.parse(text, formatter);
  }

  public static void mapAttributesToProperties(Element element, Object bean, boolean unescape) {
    mapAttributesToProperties(element, bean, unescape, new NoOpConverter<>());
  }

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

  public static void visit(Node element, Visitor<Node> visitor) {
    visitor.visit(element);
    NodeList childNodes = element.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      visit(childNodes.item(i), visitor);
    }
  }

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

  public static List<Element> findElementsByName(String name, boolean caseSensitive, Element root) {
    return findElementsByName(name, caseSensitive, root, new ArrayList<>());
  }

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
      throw ExceptionFactory.getInstance().programmerConfig("Error formatting element", e);
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
      logger.error("Error activating schema validation for schema {}, possibly you are offline or behind a proxy? {}", schemaUrl, e.getMessage());
    }
  }

  private static org.xml.sax.ErrorHandler createSaxErrorHandler() {
    return new org.xml.sax.ErrorHandler() {

      @Override
      public void error(SAXParseException e) {
        logger.error(e.getMessage(), e);
      }

      @Override
      public void fatalError(SAXParseException e) {
        logger.error(e.getMessage(), e);
      }

      @Override
      public void warning(SAXParseException e) {
        logger.warn(e.getMessage(), e);
      }

    };
  }

  @SuppressWarnings("null")
  public static void saveAsProperties(Properties properties, File file, String encoding) {
    if (properties.size() == 0) {
      throw ExceptionFactory.getInstance().programmerConfig("Cannot save empty Properties as XML", null);
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
        throw ExceptionFactory.getInstance().syntaxError(file.getAbsolutePath(),
            -1, -1, "Required prefix '" + rootElementName + "' not present in key " + key);
      }
      setProperty(prefixAndRemainingPath[1], value, document.getDocumentElement(), document);
    }
    document.setXmlStandalone(true); // needed to omit standalone="yes/no" in the XML header
    saveDocument(document, file, encoding);
  }

  public static void saveDocument(Document document, File file, String encoding) {
    try {
      FileOutputStream stream = new FileOutputStream(file);
      saveDocument(document, file.getAbsolutePath(), encoding, stream);
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(file.getAbsolutePath(), e);
    }
  }

  public static void saveDocument(Document document, String uri, String encoding, OutputStream out) {
    try {
      Transformer transformer = createTransformer(encoding);
      transformer.transform(new DOMSource(document), new StreamResult(out));
    } catch (TransformerException e) {
      throw ExceptionFactory.getInstance().programmerConfig("Error saving XML document " + uri, e);
    } finally {
      IOUtil.close(out);
    }
  }

  public static Document createDocument(String rootElementName) {
    Document document = createDocument();
    Element rootElement = document.createElement(rootElementName);
    document.appendChild(rootElement);
    return document;
  }

  public static Document createDocument() {
    try {
      DocumentBuilder documentBuilder = createDocumentBuilderFactory(null).newDocumentBuilder();
      return documentBuilder.newDocument();
    } catch (ParserConfigurationException e) {
      throw ExceptionFactory.getInstance().programmerStateError("Error creating XML document", e);
    }
  }

  public static void setProperty(String key, String value, Document document) {
    String[] prefixAndRemainingPath = StringUtil.splitOnFirstSeparator(key, '.');
    Element rootElement = document.getDocumentElement();
    if (rootElement == null) {
      rootElement = document.createElement(prefixAndRemainingPath[0]);
      document.appendChild(rootElement);
    } else if (!key.equals(rootElement.getNodeName())) {
      throw ExceptionFactory.getInstance().programmerStateError("Cannot set a property '" + key + "' on a document with root <" + rootElement.getNodeName() + ">");
    }
    setProperty(prefixAndRemainingPath[1], value, rootElement, document);
  }

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

  public static Node getParentNode(Node node) {
    if (node instanceof Attr) {
      return ((Attr) node).getOwnerElement();
    } else {
      return node.getParentNode();
    }
  }

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
    throw ExceptionFactory.getInstance().programmerStateError("Child element not found under parent");
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
    throw ExceptionFactory.getInstance().programmerStateError("Comment not found under parent");
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
      throw ExceptionFactory.getInstance().programmerStateError("Error creating Transformer", e);
    }
  }

}
