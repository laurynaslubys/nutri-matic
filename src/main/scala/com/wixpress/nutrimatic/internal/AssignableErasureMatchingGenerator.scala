package com.wixpress.nutrimatic.internal

import com.wixpress.nutrimatic.{ByErasure, Context, TypeAndContext}

import scala.reflect.runtime.universe._

private[nutrimatic] abstract class AssignableErasureMatchingGenerator[T](t: WeakTypeTag[T]) extends ByErasure[T] {
  override def isDefinedAt(tc: TypeAndContext): Boolean = {
    tc._1.erasure <:< t.tpe.erasure
  }

  override def apply(tc: TypeAndContext): T = {
    getValue(tc._1, tc._2)
  }

  protected def getValue(t: Type, context: Context): T
}

private[nutrimatic] object AssignableErasureMatchingGenerator {
  def cacheKeyFromType(t: Type): Type = t.erasure
}