/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Lists error codes used in the rd-lib-common library.<br/><br/>
 * Created: 17.11.2021 22:44:26
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class CommonErrorIds {

  public static final String CLI_MISSING_ARGUMENT = "CLI-0001";
  public static final String CLI_ILLEGAL_ARGUMENT = "CLI-0002";
  public static final String CLI_ILLEGAL_OPTION = "CLI-0003";
  public static final String CLI_MISSING_OPTION_VALUE = "CLI-0004";
  public static final String CLI_ILLEGAL_OPTION_VALUE = "CLI-0005";

  public static final String PROGRAMMER_STATE_ERROR = "PRO-0001";
  public static final String PROGRAMMER_UNSUPPORTED = "PRO-0002";

  public static final String XML_ATTR_ILLEGAL_VALUE = "XML-001";
}
