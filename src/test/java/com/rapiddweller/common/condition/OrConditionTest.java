package com.rapiddweller.common.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OrConditionTest {
    @Test
    public void testEvaluate() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<>("reference");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<>("reference");
        assertFalse((new OrCondition<>(equalsCondition, equalsCondition1, new EqualsCondition<>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate2() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<>("argument");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<>("reference");
        assertTrue((new OrCondition<>(equalsCondition, equalsCondition1, new EqualsCondition<>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate3() {
        EqualsCondition<Object> equalsCondition = new EqualsCondition<>("reference");
        EqualsCondition<Object> equalsCondition1 = new EqualsCondition<>("reference");
        OrCondition<Object> orCondition = new OrCondition<>(equalsCondition, equalsCondition1,
                new EqualsCondition<>("reference"));
        EqualsCondition<Object> equalsCondition2 = new EqualsCondition<>("reference");
        assertFalse((new OrCondition<>(orCondition, equalsCondition2, new EqualsCondition<>("reference")))
                .evaluate("argument"));
    }

    @Test
    public void testEvaluate4() {
        assertFalse((new OrCondition<>(new EqualsCondition<>("reference"))).evaluate("argument"));
    }
}

