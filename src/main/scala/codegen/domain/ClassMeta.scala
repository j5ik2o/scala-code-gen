package codegen.domain

import java.lang.String
import org.apache.commons.lang.builder.ToStringBuilder
import reflect.BeanProperty
import scala.collection.JavaConverters._
import jp.tricreo.scala.ddd.base.model.{EntityCloneable, Entity, Identifier}

/**クラスの情報を格納するためのエンティティ。
 *
 * @author j5ik2o
 * @param id エンティティのID
 * @param name クラス名
 * @param packageName パッケージ名
 * @param fieldMetas [[FieldMeta]]のリスト
 */
@cloneable
class ClassMeta
(@BeanProperty val identifier: Identifier,
 @BeanProperty val name: String,
 val packageName: Option[String],
 val fieldMetas: List[FieldMeta]) extends Entity with EntityCloneable[ClassMeta] {

  override def toString: String = "ClassMeta(%s, %s, %s, %s)".format(identifier, name, packageName, fieldMetas)

  // FreeMarker用
  def getPackageName(): String = if (packageName.isEmpty) null else packageName.get
  def getFieldMetas(): java.util.List[FieldMeta] = fieldMetas.asJava
}

object ClassMeta {

  def apply(identifier: Identifier, name: String, packageName: Option[String], fieldMetas: List[FieldMeta]) =
    new ClassMeta(identifier, name, packageName, fieldMetas)

  def apply(name: String, packageName: Option[String], fieldMetas: List[FieldMeta]) =
    new ClassMeta(Identifier(), name, packageName, fieldMetas)

  def unapply(classMeta: ClassMeta) =
    Some(classMeta.identifier, classMeta.name, classMeta.packageName, classMeta.fieldMetas)

}