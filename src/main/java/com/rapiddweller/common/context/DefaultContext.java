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

import com.rapiddweller.common.Context;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * A thread-safe implementation of Context.
 * Created: 06.07.2007 06:30:43
 *
 * @author Volker Bergmann
 */
public class DefaultContext implements Context {

  private final Context defaults;

  /**
   * The Map.
   */
  protected Map<String, Object> map;

  /**
   * Instantiates a new Default context.
   */
  public DefaultContext() {
    this((Context) null);
  }

  /**
   * Instantiates a new Default context.
   *
   * @param defaults the defaults
   */
  public DefaultContext(Context defaults) {
    this.defaults = defaults;
    this.map = new HashMap<>();
  }

  /**
   * Instantiates a new Default context.
   *
   * @param map the map
   */
  public DefaultContext(Map<String, ?> map) {
    this.defaults = null;
    this.map = new HashMap<>(map);
  }

  /**
   * Instantiates a new Default context.
   *
   * @param props the props
   */
  public DefaultContext(Properties props) {
    this.defaults = null;
    this.map = new HashMap<>(props.size());
    for (Map.Entry<?, ?> entry : props.entrySet()) {
      this.map.put((String) entry.getKey(), entry.getValue());
    }
  }

  @Override
  public synchronized Object get(String key) {
    Object value = map.get(key);
    if (value == null && defaults != null) {
      value = defaults.get(key);
    }
    return value;
  }

  @Override
  public boolean contains(String key) {
    if (map.containsKey(key)) {
      return true;
    }
    return (defaults != null && defaults.contains(key));
  }

  @Override
  public synchronized void set(String key, Object value) {
    map.put(key, value);
  }

  @Override
  public synchronized Set<Map.Entry<String, Object>> entrySet() {
    return map.entrySet();
  }

  /**
   * Sets all.
   *
   * @param <K> the type parameter
   * @param <V> the type parameter
   * @param map the map
   */
  public synchronized <K, V> void setAll(Hashtable<K, V> map) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      this.set(String.valueOf(entry.getKey()), entry.getValue());
    }
  }

  /**
   * Sets all.
   *
   * @param <K> the type parameter
   * @param <V> the type parameter
   * @param map the map
   */
  public synchronized <K, V> void setAll(Map<K, V> map) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      this.set(String.valueOf(entry.getKey()), entry.getValue());
    }
  }

  @Override
  public void remove(String key) {
    map.remove(key);
  }

  @Override
  public Set<String> keySet() {
    return map.keySet();
  }

  @Override
  public synchronized String toString() {
    return map.toString();
  }

}
