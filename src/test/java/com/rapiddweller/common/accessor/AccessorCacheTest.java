package com.rapiddweller.common.accessor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.Accessor;
import org.junit.Test;


public class AccessorCacheTest {
    @Test
    public void testInvalidate() {
        ConditionalAccessor<Object, Boolean> condition = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Boolean> condition1 = new ConditionalAccessor<Object, Boolean>(condition, trueAccessor,
                new ConditionalAccessor<Object, Boolean>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition2 = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor1 = new ConditionalAccessor<Object, Object>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor2 = new ConditionalAccessor<Object, Object>(condition2,
                trueAccessor1, new ConditionalAccessor<Object, Object>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition3 = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor3 = new ConditionalAccessor<Object, Object>(null, null, null);
        AccessorCache<Object, Object> accessorCache = new AccessorCache<Object, Object>("Name",
                new ConditionalAccessor<Object, Object>(condition1, trueAccessor2, new ConditionalAccessor<Object, Object>(
                        condition3, trueAccessor3, new ConditionalAccessor<Object, Object>(null, null, null))));
        accessorCache.invalidate();
        assertFalse(accessorCache.isValid());
    }

    @Test
    public void testGetDependencies() {
        ConditionalAccessor<Object, Boolean> condition = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Boolean> condition1 = new ConditionalAccessor<Object, Boolean>(condition, trueAccessor,
                new ConditionalAccessor<Object, Boolean>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition2 = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor1 = new ConditionalAccessor<Object, Object>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor2 = new ConditionalAccessor<Object, Object>(condition2,
                trueAccessor1, new ConditionalAccessor<Object, Object>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition3 = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor3 = new ConditionalAccessor<Object, Object>(null, null, null);
        assertTrue((new AccessorCache<Object, Object>("Name",
                new ConditionalAccessor<Object, Object>(condition1, trueAccessor2, new ConditionalAccessor<Object, Object>(
                        condition3, trueAccessor3, new ConditionalAccessor<Object, Object>(null, null, null))))).getDependencies()
                .isEmpty());
    }

    @Test
    public void testGetDependencies3() {
        ConditionalAccessor<Object, Boolean> condition = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Boolean> trueAccessor = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Boolean> condition1 = new ConditionalAccessor<Object, Boolean>(condition, trueAccessor,
                new ConditionalAccessor<Object, Boolean>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition2 = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor1 = new ConditionalAccessor<Object, Object>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor2 = new ConditionalAccessor<Object, Object>(condition2,
                trueAccessor1, new ConditionalAccessor<Object, Object>(null, null, null));
        ConditionalAccessor<Object, Boolean> condition3 = new ConditionalAccessor<Object, Boolean>(null, null, null);
        ConditionalAccessor<Object, Object> trueAccessor3 = new ConditionalAccessor<Object, Object>(null, null, null);
        assertTrue((new AccessorCache<Object, Object>("Name",
                new AccessorCache<Object, Object>("Name",
                        new ConditionalAccessor<Object, Object>(condition1, trueAccessor2, new ConditionalAccessor<Object, Object>(
                                condition3, trueAccessor3, new ConditionalAccessor<Object, Object>(null, null, null))))))
                .getDependencies()
                .isEmpty());
    }
}

