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

import org.junit.Assume;
import org.junit.Test;

import static com.rapiddweller.common.SystemInfo.isLinux;
import static com.rapiddweller.common.SystemInfo.isMacOsx;
import static com.rapiddweller.common.SystemInfo.isWindows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link SpeechUtil}.
 * Created: 14.09.2010 12:52:22
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class SpeechUtilTest {

  @Test
  public void testSpeechSupportedLinux() {
    Assume.assumeTrue(isLinux());
    assertFalse(SpeechUtil.speechSupported());
  }

  @Test
  public void testSpeechSupportedMac() {
    Assume.assumeTrue(isMacOsx());
    assertTrue(SpeechUtil.speechSupported());
  }

  @Test
  public void testSpeechSupportedWindow() {
    Assume.assumeTrue(isWindows());
    assertFalse(SpeechUtil.speechSupported());
  }

  @Test
  public void testSpeechOutput() {
    if (SpeechUtil.speechSupported()) {
      SpeechUtil.say("speech test");
    }
  }

}
