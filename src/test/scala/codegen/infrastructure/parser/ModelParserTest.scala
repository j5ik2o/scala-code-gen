package codegen.infrastructure.parser

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import io.Source
import jp.tricreo.scala.ddd.base.model.Identifier
import codegen.domain.{FieldMeta, ClassMeta}

/**[[ModelParser]]のためのテスト
 * @author j5ik2o
 */
class ModelParserTest extends AssertionsForJUnit {

  @Test
  def test01_parseできること_packageなし {
    val mp = new ModelParser
    val source = Source.fromInputStream(classOf[ModelParserTest].getResourceAsStream("test01.config"))
    try {
      val result = mp.parse(source)
      assert(result(0).identifier == Identifier("c846266f-d4be-42dd-bfc2-ad59f72bf194"))
      assert(result(0).name == "Department")
      assert(result(0).packageName == None)
      assert(result(0).fieldMetas == List(FieldMeta("name", "java.lang.String"),FieldMeta("age", "int")))
    } catch {
      case ex: ModelParseException => println(ex.message)
      fail()
    }
  }

  @Test
  def test02_parseできること_packageあり {
    val mp = new ModelParser
    val source = Source.fromInputStream(classOf[ModelParserTest].getResourceAsStream("test02.config"))
    try {
      val result = mp.parse(source)
      assert(result(0).identifier == Identifier("4a74c322-08ab-450b-b674-793e1d7f399a"))
      assert(result(0).name == "Department")
      assert(result(0).packageName == Some("test"))
      assert(result(0).fieldMetas == List(FieldMeta("name", "java.lang.String"),FieldMeta("age", "int")))
    } catch {
      case ex: ModelParseException => println(ex.message)
      fail()
    }
  }
}