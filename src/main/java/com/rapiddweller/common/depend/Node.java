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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rapiddweller.common.depend.NodeState.FORCEABLE;
import static com.rapiddweller.common.depend.NodeState.FORCED;
import static com.rapiddweller.common.depend.NodeState.INACTIVE;
import static com.rapiddweller.common.depend.NodeState.INITIALIZABLE;
import static com.rapiddweller.common.depend.NodeState.INITIALIZED;
import static com.rapiddweller.common.depend.NodeState.PARTIALLY_INITIALIZABLE;
import static com.rapiddweller.common.depend.NodeState.PARTIALLY_INITIALIZED;

/**
 * Helper class for calculating dependencies.
 * @author Volker Bergmann
 * @since 0.3.04
 * @param <E>
 */
class Node<E extends Dependent<E>> {
    
    private NodeState state;
    
    private final E subject;
    private final List<Node<E>> providers;
    private final List<Boolean> providerRequired;
    private final List<Node<E>> clients;
    
    public Node(E subject) {
        super();
        this.subject = subject;
        this.providers = new ArrayList<>();
        this.providerRequired = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.state = INITIALIZABLE; // As long as no providers are added, the node is initializable
    }
    
    // properties ------------------------------------------------------------------------------------------------------

    /**
     * @return the subject
     */
    public E getSubject() {
        return subject;
    }
    
    /**
     * @return the state
     */
    public NodeState getState() {
        return state;
    }

    public boolean requires(Node<E> provider) {
        return providerRequired.get(providers.indexOf(provider));
    }
    
    public List<Node<E>> getProviders() {
        return providers;
    }
    
    public Node<E> addProvider(Node<E> provider, boolean required) {
    	if (!hasForeignProviders() && provider != this) // A provider is about to be added. If this was a standalone node so far,...
    		this.state = INACTIVE;                      // ...I need to reconsider
        if (this.providers.contains(provider)) {
            if (required && !required(provider)) {
                providerRequired.set(providers.indexOf(provider), Boolean.TRUE);
                providersChanged();
            }
        } else {
            this.providers.add(provider);
            this.providerRequired.add(required);
            provider.addClient(this);
            providersChanged();
        }
        return this;
    }
    
    public boolean hasForeignProviders() {
        if (providers.size() == 0)
        	return false;
        for (Node<E> provider : providers)
        	if (provider != this)
        		return true;
        return false;
    }
    
    public boolean required(Node<E> provider) {
        return providerRequired.get(providers.indexOf(provider));
    }
    
    public List<Node<E>> getClients() {
        return clients;
    }

    public void addClient(Node<E> client) {
        if (!this.clients.contains(client))
            this.clients.add(client);
    }
    
    public boolean hasForeignClients() {
    	if (clients.size() == 0)
    		return false;
    	for (Node<E> client : clients)
    		if (client != this)
    			return true;
    	return false;
    }
    
    // interface -------------------------------------------------------------------------------------------------------

    void providersChanged() {
        if (state == INITIALIZABLE || state == INITIALIZED)
            return;
        // check initializability
        boolean initializable = true;
        boolean partiallyInitializable = true;
        for (Node<E> provider : providers)
            if (provider != this && !allowsClientInitialization(provider.getState())) {
                initializable = false;
                if (required(provider))
                    partiallyInitializable = false;
            }
        if (initializable) {
            this.state = INITIALIZABLE;
            return;
        }
        if (state == PARTIALLY_INITIALIZED)
            return;
        if (partiallyInitializable) {
            this.state = PARTIALLY_INITIALIZABLE;
            return;
        }
        if (state != INACTIVE)
            return;
        if (!hasForeignProviders())
            this.state = FORCEABLE;
        for (Node<E> provider : providers)
            if (provider != this && provider.getState() == INITIALIZED) {
                this.state = FORCEABLE;
                return;
            }
    }

    private static boolean allowsClientInitialization(NodeState providerState) {
        return providerState == INITIALIZED || providerState == FORCED || providerState == PARTIALLY_INITIALIZED;
    }
/*
    private boolean allProvidersInState(NodeState state) {
        for (Node<E> provider : providers)
            if (provider.getState() != state)
                return false;
        return true;
    }
*/
    public void initialize() {
        if (state != INITIALIZABLE)
            throw new IllegalStateException("Node not initializable: " + this);
        setState(INITIALIZED);
    }

    private void setState(NodeState state) {
        this.state = state;
        for (Node<E> client : clients)
            client.providersChanged();
    }
    
    public void initializePartially() {
        if (state != PARTIALLY_INITIALIZABLE)
            throw new IllegalStateException("Node not partially initializable: " + this);
        setState(PARTIALLY_INITIALIZED);
    }
    
    public void force() {
        setState(FORCED);
    }
    
    void assertState(NodeState state) {
        if (this.state != state)
            throw new IllegalStateException("Expected to be in state '" + state + "', " 
                    + "found: '" + this.state + "'");
    }
    
    // java.lang.Object ------------------------------------------------------------------------------------------------
    
    @Override
    public int hashCode() {
        return subject.hashCode();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final Node that = (Node) obj;
        return (Objects.equals(this.subject, that.subject));
    }
    
    @Override
    public String toString() {
        return subject.toString();
    }

}
