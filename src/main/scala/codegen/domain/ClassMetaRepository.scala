package codegen.domain

import collection.Iterator
import codegen.infrastructure.parser.ModelParser
import io.BufferedSource
import jp.tricreo.scala.ddd.base.model.Identifier
import jp.tricreo.scala.ddd.base.lifecycle.EntityResolver

class ClassMetaRepository(configSource: BufferedSource) extends EntityResolver[ClassMeta] {

  private[this] val modelList = new ModelParser().parse(configSource)
  private[this] val modelMap = modelList.map(cm => (cm.identifier, cm)).toMap

  def iterator: Iterator[ClassMeta] = modelMap.map(_._2.clone).iterator

  def resolve(identifier: Identifier): ClassMeta = modelMap(identifier)
}