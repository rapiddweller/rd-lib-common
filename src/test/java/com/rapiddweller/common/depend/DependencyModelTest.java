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

package com.rapiddweller.common.depend;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the DependencyModel class.
 *
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

  /**
   * Regression for "Incomplete nodes left": a single pass of postProcessNodes strands nodes that
   * only become INITIALIZABLE part-way through the pass (a partially-initialized node whose last
   * blocking optional provider is initialized after the iterator has already passed it). The graph
   * below — a web of required and optional cycles, the shape that turns up when modelling a complex
   * database schema — leaves t8 stranded on the buggy single-pass code and must resolve completely
   * once postProcessNodes loops until stable.
   *
   * <p>Whether the strand bites depends on the order incompletes are visited, which is fixed for a
   * given set of node names, so this reproduces deterministically.</p>
   */
  @Test
  public void testPartiallyInitializableBecomingInitializableMidPass() {
    Dep t0 = new Dep("t0");
    Dep t1 = new Dep("t1");
    Dep t2 = new Dep("t2");
    Dep t3 = new Dep("t3");
    Dep t4 = new Dep("t4");
    Dep t5 = new Dep("t5");
    Dep t6 = new Dep("t6");
    Dep t7 = new Dep("t7");
    Dep t8 = new Dep("t8");

    t1.addOptionalProvider(t3);
    t2.addRequiredProvider(t8);
    t2.addRequiredProvider(t6);
    t3.addOptionalProvider(t6);
    t4.addOptionalProvider(t2);
    t4.addRequiredProvider(t0);
    t4.addOptionalProvider(t6);
    t4.addOptionalProvider(t3);
    t4.addOptionalProvider(t8);
    t5.addOptionalProvider(t8);
    t5.addOptionalProvider(t2);
    t6.addRequiredProvider(t2);
    t6.addRequiredProvider(t5);
    t6.addRequiredProvider(t7);
    t7.addRequiredProvider(t8);
    t7.addRequiredProvider(t1);
    t7.addRequiredProvider(t0);
    t7.addOptionalProvider(t6);
    t8.addOptionalProvider(t2);
    t8.addOptionalProvider(t5);
    t8.addRequiredProvider(t6);

    DependencyModel<Dep> model = new DependencyModel<>();
    for (Dep d : new Dep[] {t0, t1, t2, t3, t4, t5, t6, t7, t8}) {
      model.addNode(d);
    }

    List<Dep> oo = model.dependencyOrderedObjects(true);
    assertEquals(9, oo.size());
    for (Dep d : new Dep[] {t0, t1, t2, t3, t4, t5, t6, t7, t8}) {
      assertTrue("missing " + d, oo.contains(d));
    }
  }

  /**
   * Property/fuzz regression for the single-pass postProcessNodes stranding bug. The reporter
   * could not share the failing DB schema ("a moderate amount of tables", ~200 nodes, 2 stranded),
   * so instead of one hand-built graph we hammer thousands of random required+optional cyclic
   * graphs -- subsuming cascade depth, multiple simultaneous strands and shape diversity that a
   * single fixture cannot. The seed-0 graph alone reproduced the original failure during
   * development.
   *
   * <p>Invariant, scoped to the stranding <em>symptom</em> rather than "never throws":
   * with {@code acceptingCycles=true} (the mode jdbacl's DBUtil uses) the ordering must always
   * complete -- every node returned, none dropped or duplicated. With {@code acceptingCycles=false}
   * a {@link CyclicDependencyException} is legitimate, but the run must never strand
   * ("Incomplete nodes left" / a node left out of INITIALIZED state).</p>
   */
  @Test
  public void fuzzNeverStrandsIncompleteNodes() {
    for (long seed = 0; seed < 5000; seed++) {
      Random r = new Random(seed);
      int n = 4 + r.nextInt(8); // 4..11 nodes
      List<Dep> deps = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        deps.add(new Dep("t" + i));
      }
      int edges = n + r.nextInt(2 * n);
      for (int e = 0; e < edges; e++) {
        int a = r.nextInt(n), b = r.nextInt(n);
        if (a == b) {
          continue; // self-cycles are exercised separately; keep the random graph between distinct nodes
        }
        if (r.nextBoolean()) {
          deps.get(a).addOptionalProvider(deps.get(b));
        } else {
          deps.get(a).addRequiredProvider(deps.get(b));
        }
      }

      // acceptingCycles=true: DBUtil's mode -- must always resolve completely.
      try {
        List<Dep> ordered = modelOf(deps).dependencyOrderedObjects(true);
        assertEquals("seed " + seed + ": nodes dropped", n, ordered.size());
        assertEquals("seed " + seed + ": nodes duplicated", n, new HashSet<>(ordered).size());
      } catch (RuntimeException ex) {
        throw new AssertionError("seed " + seed + " (acceptingCycles=true) threw: " + ex, ex);
      }

      // acceptingCycles=false: a cycle is a legitimate failure; stranding is not.
      try {
        modelOf(deps).dependencyOrderedObjects(false);
      } catch (CyclicDependencyException legitimate) {
        // expected for graphs with required cycles
      } catch (RuntimeException ex) {
        if (isStrandingSymptom(ex)) {
          fail("seed " + seed + " (acceptingCycles=false) stranded: " + ex);
        }
        throw new AssertionError("seed " + seed + " (acceptingCycles=false) threw unexpectedly: " + ex, ex);
      }
    }
  }

  private static boolean isStrandingSymptom(RuntimeException ex) {
    String m = ex.getMessage();
    return m != null && (m.startsWith("Incomplete nodes left") || m.contains("INITIALIZED state"));
  }

  /** Fresh model (and fresh Node state) over the same dependency spec; Dep providers are reusable. */
  private static DependencyModel<Dep> modelOf(List<Dep> deps) {
    DependencyModel<Dep> model = new DependencyModel<>();
    for (Dep d : deps) {
      model.addNode(d);
    }
    return model;
  }

  // private helper -------------------------------------------------------------------------------

  private static void expectOrder(Dep... nodes) {
    expectOrder(false, nodes);
  }

  private static void expectOrder(boolean acceptingCycles, Dep... nodes) {
    DependencyModel<Dep> model = new DependencyModel<>();
    for (int i = 0; i < nodes.length / 2; i++) {
      model.addNode(nodes[i]);
    }
    List<Dep> oo = model.dependencyOrderedObjects(acceptingCycles);
    for (int i = nodes.length / 2; i < nodes.length; i++) {
      assertEquals(nodes[i], oo.get(i - nodes.length / 2));
    }
  }

}
