/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Lists error codes used in the rd-lib-common library.<br/><br/>
 * Created: 17.11.2021 22:44:26
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CommonErrorIds {

  /** Private constructor to prevent instantiation of this utility class. */
  private CommonErrorIds() {
    // private constructor to prevent instantiation of this utility class
  }

  public static final String CLI_MISSING_ARGUMENT = "CLI-0001";
  public static final String CLI_ILLEGAL_ARGUMENT = "CLI-0002";
  public static final String CLI_ILLEGAL_OPTION = "CLI-0003";
  public static final String CLI_MISSING_OPTION_VALUE = "CLI-0004";
  public static final String CLI_ILLEGAL_OPTION_VALUE = "CLI-0005";

  public static final String GEN_ILLEGAL_ARGUMENT = "GEN-0001";

  public static final String PROGRAMMER_STATE_ERROR = "PRO-0001";
  public static final String PROGRAMMER_UNSUPPORTED = "PRO-0002";

  public static final String INFO_MISSING = "DAT-0001";

  public static final String XML_SYNTAX = "XML-0001";
  public static final String XML_ILLEGAL_ROOT = "XML-0002";
  public static final String XML_ATTR_ILLEGAL_ELEMENT = "XML-0003";
  public static final String XML_ILLEGAL_CHILD_ELEMENT = "XML-0004";
  public static final String XML_ATTR_ILLEGAL_NAME = "XML-0005";
  public static final String XML_ATTR_ILLEGAL_VALUE = "XML-0006";
  public static final String XML_ATTR_MISSING = "XML-0007";
  public static final String XML_ILLEGAL_TEXT_CONTENT = "XML-0008";

  public static final String SCRIPT_EXCEPTION = "SCR-0001";

  public static final String DB_QUERY_FAILED = "DBS-0001";

  public static final String OUT_OF_MEMORY = "MEM-0001";
}
