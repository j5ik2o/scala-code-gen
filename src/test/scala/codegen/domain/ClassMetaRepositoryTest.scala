package codegen.domain

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import io.Source
import codegen.infrastructure.parser.ModelParserTest
import jp.tricreo.scala.ddd.base.model.Identifier

/**
 * Created by IntelliJ IDEA.
 * User: junichi
 * Date: 11/04/06
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */

class ClassMetaRepositoryTest extends AssertionsForJUnit {
  @Test
  def newRepos {
    val repos = new ClassMetaRepository(Source.fromInputStream(classOf[ModelParserTest].getResourceAsStream("test01.config")))
    assert(repos.size == 1)
    println(repos.isEmpty)

    val classMeta = repos(Identifier("c846266f-d4be-42dd-bfc2-ad59f72bf194"))
    println(classMeta)
  }
}