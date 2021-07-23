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

package com.rapiddweller.common.validator.domain;

import com.rapiddweller.common.ValidationDomainDescriptor;
import com.rapiddweller.common.Validator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link DefaultValidationDomainDescriptor}.
 * Created: 20.12.2011 17:07:08
 *
 * @author Volker Bergmann
 * @since 0.5.14
 */
public class DefaultValidationDomainDescriptorTest {

  @Test
  public void testConstructorWithClassName() {
    // given the ValidationDomainDescriptor is constructed with the explicit name of this package
    String packageName = getClass().getPackage().getName();
    ValidationDomainDescriptor descriptor = new DefaultValidationDomainDescriptor(packageName);
    // when querying the validator classes
    List<Class<? extends Validator<?>>> validatorClasses = descriptor.getValidatorClasses();
    // then it must list all Validator classes in this package
    assertEquals(1, validatorClasses.size());
    assertEquals(OneValidator.class, validatorClasses.get(0));
  }

  @Test
  public void testDefaultConstructor() {
    // given an anonymous class which uses the default constructor
    ValidationDomainDescriptor descriptor = new DefaultValidationDomainDescriptor() {
    };
    // when querying the validator classes
    List<Class<? extends Validator<?>>> validatorClasses = descriptor.getValidatorClasses();
    // then it must list all Validator classes in this package
    assertEquals(1, validatorClasses.size());
    assertEquals(OneValidator.class, validatorClasses.get(0));
  }

}
