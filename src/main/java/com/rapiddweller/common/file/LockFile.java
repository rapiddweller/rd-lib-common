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

package com.rapiddweller.common.file;

import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.File;
import java.io.IOException;

/**
 * <p>Utility for preventing the start of more than one process of a given application.
 * The lock is acquired calling {@link #acquireLock(File)} with a File instance.
 * The method creates the file and registers a shutdown hook that deletes the file
 * when the process finishes. When a second acquireLock is tried while the file exists
 * (no matter if by this or another process), a {@link LockAlreadyAcquiredException}
 * is thrown.</p>
 * It can be used like this:
 * <pre>
 * 		try {
 * 			LockFile.acquireLock("MyApp.lock");
 *        } catch (LockFile.LockAlreadyAcquiredException e) {
 * 			JOptionPane.showMessageDialog(null, "MyApp is already running", "Error", JOptionPane.ERROR_MESSAGE);
 * 			System.exit(-1);
 *        }
 * </pre>
 * <p>
 * Created: 29.11.2013 08:16:23
 * @author Volker Bergmann
 * @since 0.5.25
 */
public class LockFile {

  /** Acquire lock.
   *  @param lockFile the lock file
   *  @throws LockAlreadyAcquiredException the lock already acquired exception */
  public static void acquireLock(final File lockFile) throws LockAlreadyAcquiredException {
    if (lockFile.exists()) {
      throw new LockAlreadyAcquiredException(lockFile.getPath());
    } else {
      try {
        File parent = lockFile.getParentFile();
        parent.mkdirs();
        lockFile.createNewFile();
      } catch (IOException e) {
        throw ExceptionFactory.getInstance().internalError("Error acquiring lock", e);
      }
      Runtime.getRuntime().addShutdownHook(new Thread(lockFile::delete));
    }
  }

  /** The type Lock already acquired exception. */
  public static class LockAlreadyAcquiredException extends Exception {

    /** Instantiates a new Lock already acquired exception.
     *  @param fileName the file name */
    public LockAlreadyAcquiredException(String fileName) {
      super("Lock file already acquired: " + fileName);
    }
  }

}
