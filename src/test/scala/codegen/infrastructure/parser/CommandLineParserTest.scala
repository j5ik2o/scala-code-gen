package codegen.infrastructure.parser

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test

/**
 * Created by IntelliJ IDEA.
 * User: junichi
 * Date: 11/04/07
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */

class CommandLineParserTest extends AssertionsForJUnit {

  @Test
  def test01_configFile{
    val p = new CommandLineParser
    val result1 = p.parse("-c test")
    assert(result1 == Parameters(ConfigFile("test")))
    val result2 = p.parse("--config test")
    assert(result2 == Parameters(ConfigFile("test")))
  }

  @Test
  def test01_configFile_id{
    val p = new CommandLineParser
    val result1 = p.parse("-c [test]@test")
    assert(result1 == Parameters(ConfigFile("test", List("test"))))
    val result2 = p.parse("--config [test]@test")
    assert(result2 == Parameters(ConfigFile("test", List("test"))))

  }

  @Test
  def test01_configFile_ids{
    val p = new CommandLineParser
    val result1 = p.parse("-c [test1,test2,test3]@test")
    assert(result1 == Parameters(ConfigFile("test", List("test1","test2","test3"))))
    val result2 = p.parse("--config [test1,test2,test3]@test")
    assert(result2 == Parameters(ConfigFile("test", List("test1","test2","test3"))))
  }


  @Test
  def test01_configFile_templateDir{
    val p = new CommandLineParser
    val result1 = p.parse("-c test -t template")
    assert(result1 == Parameters(ConfigFile("test"), Some("template")))
    val result2 = p.parse("-c test --template template")
    assert(result2 == Parameters(ConfigFile("test"), Some("template")))
  }

  @Test
  def test01_configFile_exportDir{
    val p = new CommandLineParser
    val result1 = p.parse("-c test -e export")
    assert(result1 == Parameters(ConfigFile("test"), None, Some("export")))
    val result2 = p.parse("-c test --export export")
    assert(result2 == Parameters(ConfigFile("test"), None, Some("export")))
  }


}