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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.rapiddweller.common.depend.NodeState.FORCEABLE;
import static com.rapiddweller.common.depend.NodeState.INITIALIZABLE;
import static com.rapiddweller.common.depend.NodeState.INITIALIZED;
import static com.rapiddweller.common.depend.NodeState.PARTIALLY_INITIALIZABLE;

/**
 * Orders objects by dependency.
 *
 * @param <E> the type of the objects to process
 * @author Volker Bergmann
 * @since 0.3.04
 */
@SuppressWarnings("static-method")
public class DependencyModel<E extends Dependent<E>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DependencyModel.class);

  private final Map<E, Node<E>> nodeMappings;

  /**
   * Instantiates a new Dependency model.
   */
  public DependencyModel() {
    this.nodeMappings = new HashMap<>();
  }

  /**
   * Add node.
   *
   * @param object the object
   */
  public void addNode(E object) {
    nodeMappings.put(object, new Node<>(object));
  }

  /**
   * Dependency ordered objects list.
   *
   * @param acceptingCycles the accepting cycles
   * @return the list
   */
  public List<E> dependencyOrderedObjects(boolean acceptingCycles) {
    // set up dependencies
    for (Node<E> node : nodeMappings.values()) {
      E subject = node.getSubject();
      for (int i = 0; i < subject.countProviders(); i++) {
        E provider = subject.getProvider(i);
        Node<E> providerNode = nodeMappings.get(provider);
        if (providerNode == null) {
          throw new IllegalStateException("Node is not part of model: " + provider);
        }
        providerNode.addClient(node);
        node.addProvider(providerNode, subject.requiresProvider(i));
      }
    }

    // set up lists for processing
    List<Node<E>> heads = new ArrayList<>();
    List<Node<E>> tails = new ArrayList<>();
    List<Node<E>> pending = new ArrayList<>(nodeMappings.size());
    List<Node<E>> orderedNodes = new ArrayList<>(nodeMappings.size());
    List<Node<E>> incompletes = new ArrayList<>();

    try {
      // determine types and extract islands
      for (Node<E> node : nodeMappings.values()) {
        if (node.hasForeignClients()) {
          if (node.hasForeignProviders()) {
            pending.add(node);
          } else {
            node.initialize();
            heads.add(node);
          }
        } else {
          if (node.hasForeignProviders()) {
            tails.add(node);
          } else {
            node.initialize();
            orderedNodes.add(node);
          }
        }
      }

      // extract heads
      orderedNodes.addAll(heads);

      // sort remaining nodes
      while (pending.size() > 0) {
        boolean found = extractNodes(pending, INITIALIZABLE, orderedNodes, null);
        if (!found) {
          found = extractNodes(pending, PARTIALLY_INITIALIZABLE, orderedNodes, incompletes);
        }
        if (!found) {
          if (acceptingCycles) {
            // force one node
            Node<E> node = findForceable(pending);
            LOGGER.debug("forcing " + node);
            pending.remove(node);
            node.force();
            orderedNodes.add(node);
            incompletes.add(node);
          } else {
            throw new CyclicDependencyException("Cyclic dependency in " + pending);
          }
        }
        postProcessNodes(incompletes);
      }

      if (incompletes.size() > 0) {
        throw new IllegalStateException("Incomplete nodes left: " + incompletes);
      }

      // extract tails
      for (Node<E> tail : tails) {
        tail.initialize();
      }
      orderedNodes.addAll(tails);

      // done
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("ordered to " + orderedNodes);
      }

      // map result
      List<E> result = new ArrayList<>(orderedNodes.size());
      for (Node<E> node : orderedNodes) {
        E subject = node.getSubject();
        if (node.getState() != INITIALIZED) {
          throw new IllegalStateException("Node '" + subject
              + "' is expected to be in INITIALIZED state, found: " + node.getState());
        }
        result.add(subject);
      }
      return result;
    } catch (RuntimeException e) {
      if (!(e instanceof CyclicDependencyException)) {
        logState(pending);
      }
      throw e;
    }
  }

  private void postProcessNodes(List<Node<E>> nodes) {
    LOGGER.debug("post processing nodes: {}", nodes);
    Iterator<Node<E>> iterator = nodes.iterator();
    while (iterator.hasNext()) {
      Node<E> node = iterator.next();
      switch (node.getState()) {
        case PARTIALLY_INITIALIZABLE:
        case INITIALIZED:
          LOGGER.debug("Initializing {} partially", node);
          node.initializePartially();
          break;
        case INITIALIZABLE:
          LOGGER.debug("Initializing {}", node);
          node.initialize();
          iterator.remove();
          break;
        default:
          break;
      }
    }
  }

  private boolean extractNodes(List<Node<E>> source, NodeState requiredState, List<Node<E>> target, List<Node<E>> incompletes) {
    LOGGER.debug("extracting nodes from {}", source);
    Iterator<Node<E>> iterator;
    boolean found = false;
    iterator = source.iterator();
    while (iterator.hasNext()) {
      Node<E> node = iterator.next();
      if (node.getState() == requiredState) {
        iterator.remove();
        switch (requiredState) {
          case INITIALIZABLE:
            LOGGER.debug("Initializing {}", node);
            node.initialize();
            break;
          case PARTIALLY_INITIALIZABLE:
            LOGGER.debug("Initializing {} partially", node);
            node.initializePartially();
            if (incompletes != null) {
              incompletes.add(node);
            }
            break;
          default:
            throw new IllegalArgumentException("state not supported: " + requiredState);
        }
        if (target != null) {
          target.add(node);
        }
        found = true;
      }
    }
    return found;
  }

  private void logState(List<Node<E>> intermediates) {
    LOGGER.error(intermediates.size() + " unresolved intermediates on DependencyModel error: ");
    for (Node<E> node : intermediates) {
      LOGGER.error(node.toString());
    }
  }

  private Node<E> findForceable(List<Node<E>> candidates) {
    for (Node<E> candidate : candidates) {
      if (candidate.getState() == FORCEABLE) {
        return candidate;
      }
    }
    return candidates.get(0);
  }

/*
    private Node<E> breakCycle(List<Node<E>> remainingNodes, List<Node<E>> availableNodes, boolean hard) {
        for (Node<E> node : remainingNodes) {
            if (available(node))
            logger.warn("Cyclic dependency in " + tmpList + ". Breaking cycle by extracting " + node);
            result.add(node);
            tmpList.remove(0);
        }
    }

    private Node<E> findAvailableNode(List<Node<E>> tmpList, List<Node<E>> availableNodes) {
        for (Node<E> node : tmpList)
            if (available(node, availableNodes)) 
                return node;
        return null;
    }

    private boolean available(Node<E> candidate, List<Node<E>> availableNodes) {
        for (Node<E> usedNode : candidate.getProviders())
            if (!availableNodes.contains(usedNode))
                return false;
        return true;
    }
*/
}
