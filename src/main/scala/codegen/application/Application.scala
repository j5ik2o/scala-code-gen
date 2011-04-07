package codegen.application

import java.io.File
import io.Source
import jp.tricreo.scala.ddd.base.model.Identifier
import codegen.domain.{ClassMeta, CodeGenService, ClassMetaRepository}
import codegen.infrastructure.parser.{CommandLine, CommandLineParseException, CommandLineParser}

object Application {

  private val defaultTemplateDir = new File("template");
  private val defaultExportDir = new File("export");

  private def generate(configFile: File,
                       templateDir: File,
                       exportDir: File,
                       ids: List[String]) {
    val repos = new ClassMetaRepository(Source.fromFile(configFile))
    val gen = new CodeGenService(exportDir, templateDir)
    val targets: List[ClassMeta] = ids match {
      case Nil => repos.toList
      case xs: List[String] => xs.map {
        e => repos.resolve(Identifier(e))
      }
    }
    gen.generate(targets)
  }

  private def getIdList(commandLine: CommandLine) =
    commandLine.configFile.ids

  private def getConfigFile(commandLine: CommandLine) =
    new File(commandLine.configFile.name)

  private def getTemplateDir(commandLine: CommandLine) =
    if (commandLine.templateDir.isDefined) new File(commandLine.templateDir.get)
    else defaultTemplateDir

  private def getExportDir(commandLine: CommandLine) =
    if (commandLine.exportDir.isDefined) new File(commandLine.exportDir.get)
    else defaultExportDir

  def main(args: Array[String]) {
    try {
      val commandLine = new CommandLineParser().parse(args.mkString(" "))
      generate(getConfigFile(commandLine),
        getTemplateDir(commandLine),
        getExportDir(commandLine),
        getIdList(commandLine))
    } catch {
      case e: CommandLineParseException => println("コマンドライン引数が不正です")
    }
  }

}