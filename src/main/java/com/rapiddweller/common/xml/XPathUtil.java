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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.List;

/**
 * Provides XPath query functionality on XML documents and elements.
 * Created: 28.03.2014 16:55:12
 *
 * @author Volker Bergmann
 * @since 0.5.29
 */
public class XPathUtil {

  /**
   * Query elements list.
   *
   * @param base       the base
   * @param expression the expression
   * @return the list
   * @throws XPathExpressionException the x path expression exception
   */
  public static List<Element> queryElements(Node base, String expression) throws XPathExpressionException {
    return XMLUtil.toElementList(queryNodes(base, expression));
  }

  /**
   * Query element element.
   *
   * @param base       the base
   * @param expression the expression
   * @return the element
   * @throws XPathExpressionException the x path expression exception
   */
  public static Element queryElement(Node base, String expression) throws XPathExpressionException {
    return (Element) query(base, expression, XPathConstants.NODE);
  }

  /**
   * Query element text string.
   *
   * @param base              the base
   * @param elementExpression the element expression
   * @return the string
   * @throws XPathExpressionException the x path expression exception
   */
  public static String queryElementText(Node base, String elementExpression) throws XPathExpressionException {
    // a direct string query for the content of a missing element would return an empty string,
    // not allowing to differ an empty element from a missing one, so I need to take a two-step approach:
    Element element = queryElement(base, elementExpression);
    if (element == null) {
      return null;
    }
    return element.getTextContent();
  }

  /**
   * Query attribute string.
   *
   * @param base              the base
   * @param elementExpression the element expression
   * @param attributeName     the attribute name
   * @return the string
   * @throws XPathExpressionException the x path expression exception
   */
  public static String queryAttribute(Node base, String elementExpression, String attributeName) throws XPathExpressionException {
    // since the result of a string query does not differentiate between empty ("") and not found (null), I need to apply a workaround
    // see http://stackoverflow.com/questions/17390684/jaxp-xpath-1-0-or-2-0-how-to-distinguish-empty-strings-from-non-existent-value
    Element element = queryElement(base, elementExpression);
    if (element == null) {
      return null;
    }
    NodeList nodes = queryNodes(element, "@" + attributeName);
    return (nodes.getLength() == 0 ? null : nodes.item(0).getTextContent());
  }

  /**
   * Query string string.
   *
   * @param base       the base
   * @param expression the expression
   * @return the string
   * @throws XPathExpressionException the x path expression exception
   */
  public static String queryString(Node base, String expression) throws XPathExpressionException {
    return (String) query(base, expression, XPathConstants.STRING);
  }

  /**
   * Query nodes node list.
   *
   * @param base       the base
   * @param expression the expression
   * @return the node list
   * @throws XPathExpressionException the x path expression exception
   */
  public static NodeList queryNodes(Node base, String expression) throws XPathExpressionException {
    return (NodeList) query(base, expression, XPathConstants.NODESET);
  }

  /**
   * Query node node.
   *
   * @param base       the base
   * @param expression the expression
   * @return the node
   * @throws XPathExpressionException the x path expression exception
   */
  public static Node queryNode(Node base, String expression) throws XPathExpressionException {
    return (Node) query(base, expression, XPathConstants.NODE);
  }

  /**
   * Query object.
   *
   * @param base       the base
   * @param expression the expression
   * @param returnType the return type
   * @return the object
   * @throws XPathExpressionException the x path expression exception
   */
  public static Object query(Node base, String expression, QName returnType) throws XPathExpressionException {
    XPath xpath = XPathFactory.newInstance().newXPath();
    return xpath.evaluate(expression, base, returnType);
  }

  /**
   * Is valid x path boolean.
   *
   * @param expression the expression
   * @return the boolean
   */
  public static boolean isValidXPath(String expression) {
    try {
      XPath xpath = XPathFactory.newInstance().newXPath();
      xpath.compile(expression);
      return true;
    } catch (XPathExpressionException e) {
      return false;
    }
  }

}
