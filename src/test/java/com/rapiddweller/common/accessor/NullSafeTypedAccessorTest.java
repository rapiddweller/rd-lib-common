package com.rapiddweller.common.accessor;

import com.rapiddweller.common.bean.UntypedPropertyAccessor;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class NullSafeTypedAccessorTest {
  @Test
  public void testGetValueType() {
    assertNull(
        (new NullSafeTypedAccessor<Object, Object>(new UntypedPropertyAccessor("Property Name", true), "nullValue"))
            .getValueType());
    assertNull((new NullSafeTypedAccessor<>(
        new NullSafeTypedAccessor<Object, Object>(new UntypedPropertyAccessor("Property Name", true), "nullValue"),
        "nullValue")).getValueType());
  }

  @Test
  public void testGetValueType2() {
    UntypedPropertyAccessor untypedPropertyAccessor = new UntypedPropertyAccessor("Property Name", true);
    UntypedPropertyAccessor untypedPropertyAccessor1 = new UntypedPropertyAccessor("Property Name", true);
    assertNull(
        (new NullSafeTypedAccessor<Object, Object>(new TypedAccessorChain(new TypedAccessor[] {untypedPropertyAccessor,
            untypedPropertyAccessor1, new UntypedPropertyAccessor("Property Name", true)}), "nullValue"))
            .getValueType());
  }
}

