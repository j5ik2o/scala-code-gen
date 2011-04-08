package codegen.application

import java.io.File
import io.Source
import jp.tricreo.scala.ddd.base.model.Identifier
import codegen.domain.{ClassMeta, CodeGenService, ClassMetaRepository}
import grizzled.slf4j.Logging
import codegen.infrastructure.parser._

object Application extends Logging {

  private val defaultTemplateDir = new File("template");
  private val defaultExportDir = new File("export");

  private def generate(configFile: File,
                       templateDir: File,
                       exportDir: File,
                       ids: List[String]) {
    info("コード生成を開始します。設定ファイル = %s, テンプレートディレクトリ = %s, 出力先ディレクトリ = %s".format(configFile, templateDir, exportDir))
    val repos = new ClassMetaRepository(Source.fromFile(configFile))
    val gen = new CodeGenService(exportDir, templateDir)
    val targets = ids match {
      case Nil => repos.toList
      case xs => xs.map {
        e => repos.resolve(Identifier(e))
      }
    }
    try {
      gen.generate(targets, Some({
        c => info("id(%s) : class %sの生成を開始します。".format(c.identifier.value, c.name))
      }),
        Some({
          c => info("id(%s) : class %sの生成が終了しました。".format(c.identifier.value, c.name))
        }))
      info("コード生成が終了しました。")
    } catch {
      case e: Exception => error("致命的な例外が発生しました。", e)
    }
  }

  private def getIdList(commandLine: Parameters) =
    commandLine.configFile.ids

  private def getConfigFile(commandLine: Parameters) =
    new File(commandLine.configFile.name)

  private def getTemplateDir(commandLine: Parameters) =
    if (commandLine.templateDir.isDefined) new File(commandLine.templateDir.get)
    else defaultTemplateDir

  private def getExportDir(commandLine: Parameters) =
    if (commandLine.exportDir.isDefined) new File(commandLine.exportDir.get)
    else defaultExportDir

  def main(args: Array[String]) {
    try {
      val commandLine = new CommandLineParser().parse(args.mkString(" "))
      commandLine match {
        case Help() => println("""-c [[id1,id2]@]file.config [-t templateDir] [-e exportDir]""")
        case parameters: Parameters => generate(getConfigFile(parameters),
          getTemplateDir(parameters),
          getExportDir(parameters),
          getIdList(parameters))
      }

    } catch {
      case e: CommandLineParseException => println("コマンドライン引数が不正です")
    }
  }

}