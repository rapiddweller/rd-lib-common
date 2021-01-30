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
package com.rapiddweller.common.context;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

/**
 * Combines several contexts to a {@link Stack}, querying recursively in the {@link #get(String)} method 
 * until an entry is found or the stack is completely iterated.
 * Created: 09.01.2013 13:06:13
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class SimpleContextStack implements ContextStack {

    private static final Logger logger = LogManager.getLogger(ContextStack.class);

    protected Stack<Context> contexts;
    
    public SimpleContextStack(Context ... contexts) {
        this.contexts = new Stack<>();
        for (Context c : contexts) 
            this.contexts.push(c);
    }

    @Override
	public Object get(String key) {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Object result = contexts.get(i).get(key);
            if (result != null)
                return result;
        }
        return null;
    }

	@Override
	public boolean contains(String key) {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context c = contexts.get(i);
            if (c.contains(key))
            	return true;
        }
        return false;
    }

    @Override
	public Set<String> keySet() {
        Set<String> keySet = new HashSet<>();
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context c = contexts.get(i);
            keySet.addAll(c.keySet());
        }
        return keySet;
    }

	@Override
	public Set<Entry<String, Object>> entrySet() {
		Set<Entry<String, Object>> entrySet = new HashSet<>();
        for (Context c : contexts) {
            entrySet.addAll(c.entrySet());
        }
        return entrySet;
    }

	@Override
	public void remove(String key) {
        if (contexts.size() > 0)
            contexts.peek().remove(key);
    }

    @Override
	public void set(String key, Object value) {
    	Assert.notNull(key, "key");
        if (contexts.size() > 0)
            contexts.peek().set(key, value);
        else
            logger.warn("ContextStack is empty, ignoring element: " + key);
    }

    @Override
	public void push(Context context) {
   		this.contexts.push(context);
    }
    
    @Override
	public Context pop() {
   		return this.contexts.pop();
    }

}
