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
package com.rapiddweller.common;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Logs escalations to a logger.
 * @since 0.2.04
 * @author Volker Bergmann
 */
public class LoggerEscalator implements Escalator {
    
    private final Set<Escalation> escalations;
    
    public LoggerEscalator() {
        this.escalations = new HashSet<>();
    }
    
    @Override
	public void escalate(String message, Object originator, Object cause) {
        // determine logger by the originator
        Class<?> category = null;
        if (originator != null)
            if (originator instanceof Class)
                category = (Class<?>) originator;
            else
                category = originator.getClass();
        else
            originator = this.getClass();
        Logger logger = LogManager.getLogger(category);
        // create escalation
        Escalation escalation = new Escalation(message, originator, cause);
        // if the escalation is new, send it
        if (!escalations.contains(escalation)) {
            escalations.add(escalation);
            if (cause instanceof Throwable)
                logger.warn(escalation.toString(), (Throwable) cause);
            else
                logger.warn(escalation.toString());
        }
    }
}
