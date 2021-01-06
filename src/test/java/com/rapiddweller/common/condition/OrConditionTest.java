package com.rapiddweller.common.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OrConditionTest {
    @Test
    public void testEvaluate() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<Object>("reference");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<Object>("reference");
        assertFalse((new OrCondition<Object>(equalsCondition, equalsCondition1, new EqualsCondition<Object>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate2() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<Object>("argument");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<Object>("reference");
        assertTrue((new OrCondition<Object>(equalsCondition, equalsCondition1, new EqualsCondition<Object>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate3() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<Object>("reference");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<Object>("reference");
        OrCondition<Object> orCondition = new OrCondition<Object>(equalsCondition, equalsCondition1,
                new EqualsCondition<Object>("reference"));
        EqualsCondition<Object> equalsCondition2 = new EqualsCondition<Object>("reference");
        assertFalse((new OrCondition<Object>(orCondition, equalsCondition2, new EqualsCondition<Object>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate4() {
        assertFalse((new OrCondition<Object>(new EqualsCondition<Object>("reference"))).evaluate("argument"));
    }
}

