package com.rapiddweller.common.filter;

import com.rapiddweller.common.condition.EqualsCondition;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConditionalFilterTest {
  @Test
  public void testAccept() {
    assertFalse((new ConditionalFilter<Object>(new EqualsCondition<Object>("reference"))).accept("candidate"));
    assertTrue((new ConditionalFilter<Object>(new EqualsCondition<Object>("candidate"))).accept("candidate"));
    assertFalse((new ConditionalFilter<Object>(new EqualsCondition<Object>("reference"))).accept(1));
  }
}

