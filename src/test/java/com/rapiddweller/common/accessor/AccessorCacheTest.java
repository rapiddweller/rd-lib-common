package com.rapiddweller.common.accessor;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AccessorCacheTest {
  @Test
  public void testInvalidate() {
    ConditionalAccessor<Object, Boolean> condition = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Boolean> trueAccessor = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Boolean> condition1 = new ConditionalAccessor<>(condition, trueAccessor,
        new ConditionalAccessor<>(null, null, null));
    ConditionalAccessor<Object, Boolean> condition2 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor1 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor2 = new ConditionalAccessor<>(condition2,
        trueAccessor1, new ConditionalAccessor<>(null, null, null));
    ConditionalAccessor<Object, Boolean> condition3 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor3 = new ConditionalAccessor<>(null, null, null);
    AccessorCache<Object, Object> accessorCache = new AccessorCache<>("Name",
        new ConditionalAccessor<>(condition1, trueAccessor2, new ConditionalAccessor<>(
            condition3, trueAccessor3, new ConditionalAccessor<>(null, null, null))));
    accessorCache.invalidate();
    assertFalse(accessorCache.isValid());
  }

  @Test
  public void testGetDependencies() {
    ConditionalAccessor<Object, Boolean> condition = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Boolean> trueAccessor = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Boolean> condition1 = new ConditionalAccessor<>(condition, trueAccessor,
        new ConditionalAccessor<>(null, null, null));
    ConditionalAccessor<Object, Boolean> condition2 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor1 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor2 = new ConditionalAccessor<>(condition2,
        trueAccessor1, new ConditionalAccessor<>(null, null, null));
    ConditionalAccessor<Object, Boolean> condition3 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor3 = new ConditionalAccessor<>(null, null, null);
    assertTrue((new AccessorCache<>("Name",
        new ConditionalAccessor<>(condition1, trueAccessor2, new ConditionalAccessor<>(
            condition3, trueAccessor3, new ConditionalAccessor<>(null, null, null))))).getDependencies()
        .isEmpty());
  }

  @Test
  public void testGetDependencies3() {
    ConditionalAccessor<Object, Boolean> condition = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Boolean> trueAccessor = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Boolean> condition1 = new ConditionalAccessor<>(condition, trueAccessor,
        new ConditionalAccessor<>(null, null, null));
    ConditionalAccessor<Object, Boolean> condition2 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor1 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor2 = new ConditionalAccessor<>(condition2,
        trueAccessor1, new ConditionalAccessor<>(null, null, null));
    ConditionalAccessor<Object, Boolean> condition3 = new ConditionalAccessor<>(null, null, null);
    ConditionalAccessor<Object, Object> trueAccessor3 = new ConditionalAccessor<>(null, null, null);
    assertTrue((new AccessorCache<>("Name",
        new AccessorCache<>("Name",
            new ConditionalAccessor<>(condition1, trueAccessor2, new ConditionalAccessor<>(
                condition3, trueAccessor3, new ConditionalAccessor<>(null, null, null))))))
        .getDependencies()
        .isEmpty());
  }
}

