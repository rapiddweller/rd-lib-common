/* (c) Copyright 2013 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common;

import java.util.Date;

/**
 * Interface for classes with a {@link #getDate()} method.<br/><br/>
 * Created: 03.11.2013 23:37:51
 *
 * @author Volker Bergmann
 * @since 1.0
 */
public interface Dated {
  /**
   * Gets date.
   *
   * @return the date
   */
  Date getDate();
}
