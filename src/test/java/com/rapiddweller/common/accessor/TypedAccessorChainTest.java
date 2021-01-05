package com.rapiddweller.common.accessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import com.rapiddweller.common.bean.UntypedPropertyAccessor;
import org.junit.Test;

public class TypedAccessorChainTest {
    @Test
    public void testGetValueType() {
        UntypedPropertyAccessor untypedPropertyAccessor = new UntypedPropertyAccessor("Property Name", true);
        UntypedPropertyAccessor untypedPropertyAccessor1 = new UntypedPropertyAccessor("Property Name", true);
        assertNull((new TypedAccessorChain(new TypedAccessor[]{untypedPropertyAccessor, untypedPropertyAccessor1,
                new UntypedPropertyAccessor("Property Name", true)})).getValueType());
    }

    @Test
    public void testGetValueType2() {
        UntypedPropertyAccessor untypedPropertyAccessor = new UntypedPropertyAccessor("Property Name", true);
        UntypedPropertyAccessor untypedPropertyAccessor1 = new UntypedPropertyAccessor("Property Name", true);
        UntypedPropertyAccessor untypedPropertyAccessor2 = new UntypedPropertyAccessor("Property Name", true);
        UntypedPropertyAccessor untypedPropertyAccessor3 = new UntypedPropertyAccessor("Property Name", true);
        assertNull((new TypedAccessorChain(new TypedAccessor[]{untypedPropertyAccessor, untypedPropertyAccessor1,
                new TypedAccessorChain(new TypedAccessor[]{untypedPropertyAccessor2, untypedPropertyAccessor3,
                        new UntypedPropertyAccessor("Property Name", true)})})).getValueType());
    }

    @Test
    public void testGetValueType3() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> (new TypedAccessorChain(new TypedAccessor[]{})).getValueType());
    }


    @Test
    public void testGetValue2() {
        UntypedPropertyAccessor untypedPropertyAccessor = new UntypedPropertyAccessor("Property Name", false);
        UntypedPropertyAccessor untypedPropertyAccessor1 = new UntypedPropertyAccessor("Property Name", true);
        assertNull((new TypedAccessorChain(new TypedAccessor[]{untypedPropertyAccessor, untypedPropertyAccessor1,
                new UntypedPropertyAccessor("Property Name", true)})).getValue("target"));
    }

    @Test
    public void testGetValue3() {
        assertEquals("target", (new TypedAccessorChain(new TypedAccessor[]{})).getValue("target"));
    }

    @Test
    public void testGetValue4() {
        UntypedPropertyAccessor untypedPropertyAccessor = new UntypedPropertyAccessor("Property Name", false);
        UntypedPropertyAccessor untypedPropertyAccessor1 = new UntypedPropertyAccessor("Property Name", true);
        TypedAccessorChain typedAccessorChain = new TypedAccessorChain(new TypedAccessor[]{untypedPropertyAccessor,
                untypedPropertyAccessor1, new UntypedPropertyAccessor("Property Name", true)});
        UntypedPropertyAccessor untypedPropertyAccessor2 = new UntypedPropertyAccessor("Property Name", true);
        assertNull((new TypedAccessorChain(new TypedAccessor[]{typedAccessorChain, untypedPropertyAccessor2,
                new UntypedPropertyAccessor("Property Name", true)})).getValue("target"));
    }
}

