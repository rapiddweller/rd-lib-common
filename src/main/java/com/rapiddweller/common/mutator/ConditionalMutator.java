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
package com.rapiddweller.common.mutator;

import com.rapiddweller.common.Accessor;
import com.rapiddweller.common.Mutator;
import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.ComparableComparator;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.UpdateFailedException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Comparator;

/**
 * Mutator that is only applied if a condition is true.
 * Created: 08.05.2005 06:47:17
 */
@SuppressWarnings("unchecked")
public class ConditionalMutator extends MutatorProxy {

    public static final int ASSERT_EQUALS    = 0;
    public static final int OVERWRITE        = 1;
    public static final int SET_IF_UNDEFINED = 2;
    public static final int SET_IF_GREATER   = 3;

    protected int mode;
    
    @SuppressWarnings("rawtypes")
	private final Comparator comparator;

	@SuppressWarnings("rawtypes")
	private final Accessor accessor;

    private static final Logger logger = LogManager.getLogger(ConditionalMutator.class);

    @SuppressWarnings("rawtypes")
	public ConditionalMutator(Mutator realMutator, Accessor accessor, int mode) {
        super(realMutator);
        this.accessor = accessor;
        this.mode = mode;
        comparator = new ComparableComparator();
    }

    @Override
    public void setValue(Object target, Object value) throws UpdateFailedException {
        Object oldValue = accessor.getValue(target);
        switch (mode) {
            case OVERWRITE:
                realMutator.setValue(target, value);
                break;
            case ASSERT_EQUALS:
                if (isEmpty(oldValue)) {
                    realMutator.setValue(target, value);
                } else if (!NullSafeComparator.equals(oldValue, value))
                    throw new UpdateFailedException("Mutator " + realMutator + " expected '" + oldValue + "', "
                            + "but found '" + value + "'");
                else
                    logger.debug("no update needed by " + realMutator);
                break;
            case SET_IF_UNDEFINED:
                if (isEmpty(oldValue))
                    realMutator.setValue(target, value);
                else
                    logger.debug("no update needed by " + realMutator);
                break;
            case SET_IF_GREATER:
                if (isEmpty(oldValue))
                    realMutator.setValue(target, value);
                else if (comparator.compare(oldValue, value) == -1)
                    realMutator.setValue(target, value);
                else
                    logger.debug("no update needed by " + realMutator);
                break;
            default:
                throw new RuntimeException("Illegal mode");
        }

    }

    // private helpers -------------------------------------------------------------------------------------------------

    private static boolean isEmpty(Object value) {
        return (value instanceof String ? StringUtil.isEmpty((String)value) : value == null);
    }
    
}
