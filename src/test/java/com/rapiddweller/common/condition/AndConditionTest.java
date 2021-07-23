package com.rapiddweller.common.condition;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AndConditionTest {
  @Test
  public void testEvaluate() {
    EqualsCondition<Object> equalsCondition = new EqualsCondition<>("reference");
    EqualsCondition<Object> equalsCondition1 = new EqualsCondition<>("reference");
    assertFalse((new AndCondition<>(equalsCondition, equalsCondition1, new EqualsCondition<>("reference")))
        .evaluate("argument"));
  }

  @Test
  public void testEvaluate2() {
    EqualsCondition<Object> equalsCondition = new EqualsCondition<>("argument");
    EqualsCondition<Object> equalsCondition1 = new EqualsCondition<>("reference");
    assertFalse((new AndCondition<>(equalsCondition, equalsCondition1, new EqualsCondition<>("reference")))
        .evaluate("argument"));
  }

  @Test
  public void testEvaluate3() {
    EqualsCondition<Object> equalsCondition = new EqualsCondition<>("reference");
    EqualsCondition<Object> equalsCondition1 = new EqualsCondition<>("reference");
    AndCondition<Object> andCondition = new AndCondition<>(equalsCondition, equalsCondition1,
        new EqualsCondition<>("reference"));
    EqualsCondition<Object> equalsCondition2 = new EqualsCondition<>("reference");
    assertFalse((new AndCondition<>(andCondition, equalsCondition2, new EqualsCondition<>("reference")))
        .evaluate("argument"));
  }

  @Test
  public void testEvaluate4() {
    assertFalse((new AndCondition<>(new EqualsCondition<>("reference"))).evaluate("argument"));
  }

  @Test
  public void testEvaluate5() {
    assertTrue((new AndCondition<>()).evaluate("argument"));
  }
}

