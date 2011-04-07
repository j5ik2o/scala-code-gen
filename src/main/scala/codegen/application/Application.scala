package codegen.application

import java.io.File
import io.Source
import jp.tricreo.scala.ddd.base.model.Identifier
import codegen.infrastructure.parser.{CommandLineParseException, CommandLineParser}
import codegen.domain.{ClassMeta, CodeGenService, ClassMetaRepository}

/**
 * Created by IntelliJ IDEA.
 * User: junichi
 * Date: 11/04/06
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */

object Application {

  val templateDir = new File("template");
  val exportDir = new File("export");

  def generate(configFile: File, templateDir: File = templateDir, exportDir: File = exportDir,
               ids:List[String] = List.empty[String]) {
    val repos = new ClassMetaRepository(Source.fromFile(configFile))
    val targets:List[ClassMeta] = ids match {
      case Nil => repos.toList
      case xs:List[String] => xs.map{
        e =>
        repos.resolve(Identifier(e))
      }
    }
    val gen = new CodeGenService(targets, exportDir, templateDir)
    gen.generate

  }

  def main(args: Array[String]) {

    val clp = new CommandLineParser
    try{
      val cl = clp.parse(args.mkString(" "))
      generate(new File(cl.configFile.name),
        if (cl.templateDir.isDefined) new File(cl.templateDir.get) else templateDir,
        if (cl.exportDir.isDefined) new File(cl.exportDir.get) else exportDir, cl.configFile.ids)
    }catch{
      case e:CommandLineParseException => println("コマンドライン引数が不正です")
    }
  }

}