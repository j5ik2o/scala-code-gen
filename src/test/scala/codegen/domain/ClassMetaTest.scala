package codegen.domain

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
/**
 * Created by IntelliJ IDEA.
 * User: junichi
 * Date: 11/04/06
 * Time: 3:57
 * To change this template use File | Settings | File Templates.
 */

class ClassMetaTest extends AssertionsForJUnit {
  @Test
  def newTest {
    val fieldMetas = List(FieldMeta("name", "String"))
    val cm = ClassMeta("Person", None, fieldMetas)
    println(cm)
  }
}