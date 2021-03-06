/*
 * Copyright 2018 Wix.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wix.nutrimatic.internal.util

class SimpleCache[K, V](maxCacheSize: Int) {
  private val cache = new java.util.LinkedHashMap[K, V](16, 0.75f, true)

  def put(key: K, value: V): Unit = synchronized {
    if (!cache.containsKey(key)) {
      if (cache.size() > maxCacheSize - 1) {
        removeLeastRecentlyUsed()
      }
      cache.put(key, value)
    }
  }

  private def removeLeastRecentlyUsed() = {
    val iterator = cache.keySet().iterator()
    if (iterator.hasNext) {
      iterator.next()
      iterator.remove()
    }
  }

  def getIfPresent(key: K): Option[V] = synchronized {
    Option(cache.get(key))
  }
}
