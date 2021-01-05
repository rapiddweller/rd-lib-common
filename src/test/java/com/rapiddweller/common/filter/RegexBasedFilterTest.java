package com.rapiddweller.common.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegexBasedFilterTest {
    @Test
    public void testAccept() {
        assertFalse((new RegexBasedFilter("Inclusion Pattern", "Exclusion Pattern")).accept("Name"));
        assertTrue((new RegexBasedFilter(null, "Exclusion Pattern")).accept("Name"));
        assertFalse((new RegexBasedFilter("Inclusion Pattern", null)).accept("Name"));
        assertFalse((new RegexBasedFilter("Inclusion Pattern", "Exclusion Pattern")).accept("Exclusion Pattern"));
        assertTrue((new RegexBasedFilter("Inclusion Pattern", "Exclusion Pattern")).accept("Inclusion Pattern"));
        assertFalse((new RegexBasedFilter("Inclusion Pattern", null)).accept("java.lang.String"));
    }
}

