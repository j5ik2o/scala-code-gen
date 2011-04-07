package codegen.domain

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import java.io.File
import jp.tricreo.scala.ddd.base.model.Identifier

/**[[CodeGenService]]のためのテスト
 * @author j5ik2o
 */
class CodeGenServiceTest extends AssertionsForJUnit {

  @Test
  def test {
    val fms = List(FieldMeta("name", "java.lang.String"))
    val cms = List(ClassMeta(Identifier(), "Emp", Some("emp"), fms))
    val codeGenService = new CodeGenService(cms, new File("java"), new File("template"))
    codeGenService.generate
  }

}