/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.commons.depend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Tests the DependencyModel class.
 * @author Volker Bergmann
 * @since 0.3.04
 */
public class DependencyModelTest {
    
    /**
     * <pre>
     *   a
     *   b - c
     * </pre>
     */
	@Test
    public void testLineDependencies() {
        Dep zero = new Dep("0");
        Dep a = new Dep("a");
        Dep b = new Dep("b", a);
        Dep c = new Dep("c", b);
        expectOrder(c, b, a, zero, 
                zero, a, b, c);
    }

    /**
     * <pre>
     *   a   d
     *    \ / \
     *     c   f
     *    / \ /
     *   b   d
     * </pre>
     */
	@Test
    public void testNetDependencies() {
        // create nodes
        Dep a = new Dep("a");
        Dep b = new Dep("b");
        Dep c = new Dep("c", a, b);
        Dep d = new Dep("d", c);
        Dep e = new Dep("e", c);
        Dep f = new Dep("f", d, e);
        // build model
        DependencyModel<Dep> model = new DependencyModel<>();
        model.addNode(f);
        model.addNode(e);
        model.addNode(d);
        model.addNode(c);
        model.addNode(b);
        model.addNode(a);
        // check
        List<Dep> oo = model.dependencyOrderedObjects(false);
        assertTrue((oo.get(0) == a && oo.get(1) == b) || (oo.get(0) == b && oo.get(1) == a));
        assertEquals(c, oo.get(2));
        assertTrue((oo.get(3) == d && oo.get(4) == e) || (oo.get(3) == e && oo.get(4) == d));
        assertEquals(f, oo.get(5));
    }

	@Test
    public void testOptionalCycle() {
        Dep a = new Dep("a");
        Dep b = new Dep("b");
        b.addRequiredProvider(a);
        a.addOptionalProvider(b);
        expectOrder(true, b, 
                a, a, b);
    }

	@Test(expected = CyclicDependencyException.class)
    public void testUnacceptedCycle() {
        Dep a = new Dep("a");
        Dep b = new Dep("b", a);
        a.addRequiredProvider(b);
        expectOrder(b, a, a, b);
    }

	@Test
    public void testAcceptedSelfCycle() {
        Dep a = new Dep("a");
        a.addRequiredProvider(a);
        expectOrder(true, a, a);
    }
   
	@Test
    public void testAcceptedCycle2() {
        DependencyModel<Dep> model = new DependencyModel<>();
        Dep a = new Dep("a");
        Dep b = new Dep("b", a);
        a.addRequiredProvider(b);
        model.addNode(b);
        model.addNode(a);
        List<Dep> oo = model.dependencyOrderedObjects(true);
        assertTrue((oo.get(0) == a && oo.get(1) == b) || (oo.get(0) == b && oo.get(1) == a));
    }

	@Test
    public void testAcceptedCycle3() {
        Dep zero = new Dep("0");
        Dep a = new Dep("a", zero);
        Dep b = new Dep("b", a);
        Dep c = new Dep("c", b);
        a.addRequiredProvider(c);
        expectOrder(true, c, b, a, zero, 
                zero, a, b, c);
    }
    
    // private helper -------------------------------------------------------------------------------
    
    private static void expectOrder(Dep... nodes) {
        expectOrder(false, nodes);
    }

    private static void expectOrder(boolean acceptingCycles, Dep... nodes) {
        DependencyModel<Dep> model = new DependencyModel<>();
        for (int i = 0; i < nodes.length / 2; i++)
            model.addNode(nodes[i]);
        List<Dep> oo = model.dependencyOrderedObjects(acceptingCycles);
        for (int i = nodes.length / 2; i < nodes.length; i++)
            assertEquals(nodes[i], oo.get(i - nodes.length / 2));
    }
    
}
