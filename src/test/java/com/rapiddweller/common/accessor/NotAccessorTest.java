package com.rapiddweller.common.accessor;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class NotAccessorTest {
    @Test
    public void testEquals() {
        ConditionalAccessor<Object, Boolean> condition = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> condition1 = new ConditionalAccessor<>(condition, trueAccessor,
                new ConditionalAccessor<>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition2 = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor1 = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor2 = new ConditionalAccessor<>(condition2,
                trueAccessor1, new ConditionalAccessor<>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition3 = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor3 = new ConditionalAccessor<>(null, null, null);
        assertFalse((new NotAccessor<>(
                new ConditionalAccessor<>(condition1, trueAccessor2, new ConditionalAccessor<>(
                        condition3, trueAccessor3, new ConditionalAccessor<>(null, null, null))))).equals("o"));
    }

    @Test
    public void testEquals2() {
        ConditionalAccessor<Object, Boolean> condition = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> condition1 = new ConditionalAccessor<>(condition, trueAccessor,
                new ConditionalAccessor<>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition2 = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor1 = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor2 = new ConditionalAccessor<>(condition2,
                trueAccessor1, new ConditionalAccessor<>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition3 = new ConditionalAccessor<>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor3 = new ConditionalAccessor<>(null, null, null);
        new NotAccessor<>(
                new ConditionalAccessor<>(condition1, trueAccessor2, new ConditionalAccessor<>(
                        condition3, trueAccessor3, new ConditionalAccessor<>(null, null, null))));
        assertFalse(false);
    }
}

