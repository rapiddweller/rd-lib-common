package com.rapiddweller.common.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AndConditionTest {
    @Test
    public void testEvaluate() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<Object>("reference");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<Object>("reference");
        assertFalse((new AndCondition<Object>(equalsCondition, equalsCondition1, new EqualsCondition<Object>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate2() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<Object>("argument");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<Object>("reference");
        assertFalse((new AndCondition<Object>(equalsCondition, equalsCondition1, new EqualsCondition<Object>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate3() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<Object>("reference");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<Object>("reference");
        AndCondition<Object> andCondition = new AndCondition<Object>(equalsCondition, equalsCondition1,
                new EqualsCondition<Object>("reference"));
        EqualsCondition<Object> equalsCondition2 = new EqualsCondition<Object>("reference");
        assertFalse((new AndCondition<Object>(andCondition, equalsCondition2, new EqualsCondition<Object>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate4() {
        assertFalse((new AndCondition<Object>(new EqualsCondition<Object>("reference"))).evaluate("argument"));
    }

    @Test
    public void testEvaluate5() {
        assertTrue((new AndCondition<Object>()).evaluate("argument"));
    }
}

