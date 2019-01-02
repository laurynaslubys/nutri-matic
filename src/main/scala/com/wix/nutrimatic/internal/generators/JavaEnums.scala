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
package com.wix.nutrimatic.internal.generators

import com.wix.nutrimatic.{Generator, GeneratorGenerator, TypeAndContext}

import scala.reflect.runtime.universe._

object JavaEnums extends GeneratorGenerator[Any]{
  override def isDefinedAt(input: TypeAndContext): Boolean = {
    val (tpe, _ ) = input
    tpe <:< typeOf[Enum[_]]
  }

  override def apply(input: TypeAndContext): Generator[Any] = {
    val (tpe, _ ) = input
    val values = runtimeMirror(getClass.getClassLoader).runtimeClass(tpe).getEnumConstants

    {
      case (_, ctx) => values(ctx.randomInt(0, values.size))
    }
  }
}
