/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.xml;

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides XML related assertion methods.<br/><br/>
 * Created: 13.12.2021 08:16:44
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class XMLAssert {

  /** private constructor to prevent instantiation of this utility class. */
  private XMLAssert() {
    // private constructor to prevent instantiation of this utility class
  }

  public static void assertElementName(String expectedName, Element element, String errorId) {
    String actualName = XMLUtil.localName(element);
    if (!actualName.equals(expectedName)) {
      String message = "Expected element '" + expectedName + "', found: " + actualName;
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(message, null, errorId, element);
    }
  }

  /** Raises a {@link com.rapiddweller.common.exception.SyntaxError}
   *  if one of the attribute names listed in 'group' is missing.
   *  Beware: The signature deviates from the conventions used in this library,
   *  since the conventional signature leads to compiler error on some systems. */
  public static void assertGroupComplete(String errorId, Element element, String... group) {
    // check which attributes of group #1 are used
    Set<String> groupAttrs = CollectionUtil.toSet(group);
    Set<String> usedAttrs = XMLUtil.getAttributes(element).keySet();
    usedAttrs.retainAll(groupAttrs);

    if (!usedAttrs.isEmpty()) {
      // if any of the group's elements is used, then require usage of each group element
      for (String groupElem : group) {
        if (!usedAttrs.contains(groupElem)) {
          Set<String> unusedAttrs = new HashSet<>(groupAttrs);
          unusedAttrs.removeAll(usedAttrs);
          String unused = unusedAttrs.toString();
          unused = unused.substring(1, unused.length() - 1);
          throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
              "if <" + element.getNodeName() +"> has the attribute '" + usedAttrs.iterator().next() +
                  "' then it must have '" + unused + "' too", null, errorId, element);
        }
      }
    }
  }

  public static void assertElementName(String expectedName, Element element) {
    if (!element.getNodeName().equals(expectedName)) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          "Expected element name '" + expectedName + "', " + "found: '" + element.getNodeName(), element);
    }
  }

  public static void mutuallyExcludeAttributes(String errorId, Element element, String... attributeNames) {
    String usedAttribute = null;
    for (String attributeName : attributeNames) {
      if (!StringUtil.isEmpty(element.getAttribute(attributeName))) {
        if (usedAttribute == null) {
          usedAttribute = attributeName;
        } else {
          throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
              "The attributes '" + usedAttribute + "' and '" + attributeName + "' " +
                  "mutually exclude each other", null, errorId, element);
        }
      }
    }
  }

  public static void mutuallyExcludeAttrGroups(Element element, String errorId, String[] group1, String[] group2) {
    // check which attributes of group #1 are used
    Set<String> set1 = CollectionUtil.toSet(group1);
    Set<String> used1 = XMLUtil.getAttributes(element).keySet();
    used1.retainAll(set1);

    // check which attributes of group #2 are used
    Set<String> set2 = CollectionUtil.toSet(group2);
    Set<String> used2 = XMLUtil.getAttributes(element).keySet();
    used2.retainAll(set2);

    // if attributes of both groups where used, then throw a SyntaxError
    if (!used1.isEmpty() && !used2.isEmpty()) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          "<" + element.getNodeName() +">'s attributes '" + used1.iterator().next() + "' and '"
              + used2.iterator().next() + "' mutually exclude each other", null, errorId, element);
    }
  }

  public static void assertAtLeastOneAttributeIsSet(Element element, String errorId, String... attributeNames) {
    boolean ok = false;
    for (String attributeName : attributeNames) {
      if (!StringUtil.isEmpty(element.getAttribute(attributeName))) {
        ok = true;
      }
    }
    if (!ok) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          "At least one of these attributes must be set: " + ArrayFormat.format(attributeNames),
          null, errorId, element);
    }
  }

  public static void assertAttributeIsSet(Element element, String attributeName) {
    if (StringUtil.isEmpty(element.getAttribute(attributeName))) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          "Attribute '" + attributeName + "' is missing", element);
    }
  }

  public static void assertAttributeIsNotSet(Element element, String attributeName) {
    if (!StringUtil.isEmpty(element.getAttribute(attributeName))) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          "Attributes '" + attributeName + "' must not be set", element);
    }
  }

  public static void assertNoTextContent(Element element, String errorId) {
    String textContent = element.getTextContent();
    if (!StringUtil.isEmpty(textContent)) {
      throw ExceptionFactory.getInstance().illegalXmlTextContent(
          null, errorId, textContent, element);
    }
  }

  public static void allowOnlyInContextOf(String base, String errorId, Element element, String... dependent) {
    if (!element.hasAttribute(base)) {
      for (String test : dependent) {
        Attr attr = element.getAttributeNode(test);
        if (attr != null) {
          throw ExceptionFactory.getInstance().illegalXmlAttributeName("Element <" + element.getNodeName() +
                  ">'s attribute '" + test + "' is only permitted in combination with a '" + base + "' attribute",
              null, errorId, attr, null);
        }
      }
    }
  }

}
