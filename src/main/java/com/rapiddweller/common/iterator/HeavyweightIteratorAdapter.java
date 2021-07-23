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

package com.rapiddweller.common.iterator;

import com.rapiddweller.common.HeavyweightIterator;
import com.rapiddweller.common.IOUtil;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Wraps an {@link Iterator} with a {@link HeavyweightIterator}.
 * If the wrapped iterator implements {@link Closeable}, calls to
 * <code>close()</code> are forwarded, otherwise ignored.
 * Created at 17.10.2008 01:27:08
 *
 * @param <S> the type to iterate
 * @param <P> the type to provide
 * @author Volker Bergmann
 * @since 0.4.6
 */
public abstract class HeavyweightIteratorAdapter<S, P> implements HeavyweightIterator<P> {

  /**
   * The Source.
   */
  protected Iterator<S> source;

  /**
   * Instantiates a new Heavyweight iterator adapter.
   *
   * @param source the source
   */
  public HeavyweightIteratorAdapter(Iterator<S> source) {
    this.source = source;
  }

  @Override
  public boolean hasNext() {
    return source.hasNext();
  }

  @Override
  public void remove() {
    source.remove();
  }

  @Override
  public void close() {
    if (source instanceof Closeable) {
      IOUtil.close((Closeable) source);
    }
  }

}
