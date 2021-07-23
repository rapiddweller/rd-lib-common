package com.rapiddweller.common.file;

import com.rapiddweller.common.BinaryScale;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BinaryScaleFormatterTest {
  @Test
  public void testFormat() {
    assertEquals("1 TUnit", (new BinaryScaleFormatter(BinaryScale.TERA, "Unit")).format(1L));
    assertEquals("1 Unit", (new BinaryScaleFormatter(null, "Unit")).format(1L));
    assertNotNull((new BinaryScaleFormatter(null, "Unit")).format(9223372036854775807L));
  }
}

