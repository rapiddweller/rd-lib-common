/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.parser;

/**
 * Parses Strings that represents floaf values.<br/><br/>
 * Created: 08.12.2021 20:15:42
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FloatParser extends TypedParser<Float> {

  protected FloatParser() {
    super("float value", float.class);
  }

  @Override
  protected Float parseImpl(String spec) {
    return Float.parseFloat(spec);
  }

}
