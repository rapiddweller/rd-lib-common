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

import static com.rapiddweller.commons.depend.NodeState.*;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the Node class.
 * @author Volker Bergmann
 * @since 0.3.04
 */
public class NodeTest {
    
	@Test
    public void testInitialState() {
        Node<Dep>[] nodes = createDeps();
        nodes[0].assertState(INITIALIZABLE);
        nodes[1].assertState(INITIALIZABLE);
        nodes[2].assertState(INACTIVE);
    }

	@Test
    public void testInitialization() {
        Node<Dep>[] nodes = createDeps();
        nodes[0].initialize();
        nodes[0].assertState(INITIALIZED);
        nodes[2].assertState(FORCEABLE);
        
        try {
            nodes[2].initialize();
            fail("Exception expected");
        } catch (IllegalStateException e) {
            // expected
        }
        
        nodes[1].initialize();
        nodes[1].assertState(INITIALIZED);
        nodes[2].assertState(INITIALIZABLE);
    
        nodes[2].initialize();
        nodes[2].assertState(INITIALIZED);
    }

    @SuppressWarnings("unchecked")
    private static Node<Dep>[] createDeps() {
        Dep da1 = new Dep("a1");
        Dep da2 = new Dep("a2");
        Dep db  = new Dep("b", da1, da2);
        
        Node<Dep> na1 = new Node<Dep>(da1);
        Node<Dep> na2 = new Node<Dep>(da2);
        Node<Dep> nb = new Node<Dep>(db).addProvider(na1, true).addProvider(na2, true);
        
        return new Node[] { na1, na2, nb };
    }

}
