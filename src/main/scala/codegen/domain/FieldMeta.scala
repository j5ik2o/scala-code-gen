package codegen.domain

import reflect.BeanProperty

/**フィールド情報を格納するためのバリューオブジェクト
 * @author j5ik2o
 */
case class FieldMeta(@BeanProperty name: String, @BeanProperty typeName: String)