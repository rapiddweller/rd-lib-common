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
package com.rapiddweller.common.bean;

/**
 * Interface for testing the {@link ObservableFactory}.
 * Created at 17.07.2008 20:35:45
 * @since 0.4.5
 * @author Volker Bergmann
 */
public interface IPerson extends ObservableBean {
	
	String getName();
	void setName(String name);
	
	int getAge();
	void setAge(int age);
}
