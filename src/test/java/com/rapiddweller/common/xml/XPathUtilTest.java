package com.rapiddweller.common.xml;

import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.junit.Test;

import javax.xml.xpath.XPathExpressionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class XPathUtilTest {
  @Test
  public void testQueryElements() throws XPathExpressionException {
    assertTrue(XPathUtil.queryElements(new HTMLDocumentImpl(), "Expression").isEmpty());
  }

  @Test
  public void testQueryElements2() throws XPathExpressionException {
    ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
    assertTrue(XPathUtil.queryElements(elementImpl, "Expression").isEmpty());
    assertNull(elementImpl.getParentNode());
    assertFalse(elementImpl.hasAttributes());
  }

  @Test
  public void testQueryElement() throws XPathExpressionException {
    assertNull(XPathUtil.queryElement(new HTMLDocumentImpl(), "Expression"));
  }

  @Test
  public void testQueryElement2() throws XPathExpressionException {
    ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
    assertNull(XPathUtil.queryElement(elementImpl, "Expression"));
    assertNull(elementImpl.getParentNode());
    assertFalse(elementImpl.hasAttributes());
  }

  @Test
  public void testQueryString() throws XPathExpressionException {
    assertEquals("", XPathUtil.queryString(new HTMLDocumentImpl(), "Expression"));
  }

  @Test
  public void testQueryNode() throws XPathExpressionException {
    assertNull(XPathUtil.queryNode(new HTMLDocumentImpl(), "Expression"));
  }

  @Test
  public void testQueryNode2() throws XPathExpressionException {
    ElementImpl elementImpl = new ElementImpl(new CoreDocumentImpl(), "foo");
    assertNull(XPathUtil.queryNode(elementImpl, "Expression"));
    assertNull(elementImpl.getParentNode());
    assertFalse(elementImpl.hasAttributes());
  }

  @Test
  public void testIsValidXPath() {
    assertTrue(XPathUtil.isValidXPath("Expression"));
    assertFalse(XPathUtil.isValidXPath(""));
  }
}

