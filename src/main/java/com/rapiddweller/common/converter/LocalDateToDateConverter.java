/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.JavaTimeUtil;

import java.time.LocalDate;
import java.util.Date;

/**
 * Converts {@link LocalDate} objects to {@link Date}s.<br/><br/>
 * Created: 04.12.2021 17:56:24
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class LocalDateToDateConverter extends ThreadSafeConverter<LocalDate, Date> {

  public LocalDateToDateConverter() {
    super(LocalDate.class, Date.class);
  }

  @Override
  public Date convert(LocalDate source) throws ConversionException {
    return JavaTimeUtil.toDate(source);
  }

}
