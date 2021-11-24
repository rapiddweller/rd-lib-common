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

package com.rapiddweller.common.debug;

import com.rapiddweller.common.exception.ExceptionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that monitors resources as a collection of {@link MonitoredResource}s.
 * Resources are registered and unregistered with {@link #register(Object)} and
 * {@link #unregister(Object)} and in the end you can assert that all resources
 * have been unregistered by calling {@link #assertNoRegistrations(boolean)}.
 * Created: 14.04.2011 17:16:20
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class ResourceMonitor {

  private static final Logger logger = LoggerFactory.getLogger(ResourceMonitor.class);

  private final List<MonitoredResource> registrations;

  public ResourceMonitor() {
    registrations = new ArrayList<>();
  }

  public void register(Object object) {
    Throwable t = new Throwable();
    registrations.add(new MonitoredResource(object, t.getStackTrace()));
  }

  public void unregister(Object object) {
    for (int i = registrations.size() - 1; i >= 0; i--) {
      MonitoredResource candidate = registrations.get(i);
      if (candidate.resource == object) {
        registrations.remove(i);
        return;
      }
    }
    throw ExceptionFactory.getInstance().illegalOperation("Object '" + object + "' was not registered");
  }

  public List<MonitoredResource> getRegistrations() {
    return registrations;
  }

  public void reset() {
    this.registrations.clear();
  }

  public boolean assertNoRegistrations(boolean critical) {
    if (registrations.isEmpty()) {
      return true;
    }
    String message = "There are resources which have not been unregistered:";
    logger.warn(message);
    logRegistrations();
    if (critical) {
      throw ExceptionFactory.getInstance().assertionFailed(message);
    }
    return false;
  }

  private void logRegistrations() {
    for (MonitoredResource resource : registrations) {
      logger.warn("{}", resource);
    }
  }

}
