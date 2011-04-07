package codegen.infrastructure.parser

import util.parsing.combinator.JavaTokenParsers


case class CommandLineParseException(message: String) extends Exception(message)

case class ConfigFile(name: String, ids: List[String] = List.empty[String])

case class CommandLine(configFile: ConfigFile, templateDir: Option[String] = None, exportDir: Option[String] = None)


class CommandLineParser extends JavaTokenParsers {

  def parse(source: String): CommandLine = parseAll(instruction, source) match {
    case Success(result, _) => result
    case Failure(msg, _) => throw new CommandLineParseException(msg)
    case Error(msg, _) => throw new CommandLineParseException(msg)
  }

  lazy val instruction = configFile ~ opt(templateDir) ~ opt(exportDir) ^^ {
    case configFile ~ templateDirOption ~ exportDirOption =>
      CommandLine(configFile, templateDirOption, exportDirOption)
  }

  lazy val configFile = ("-c" | "--config") ~ opt("[" ~ repsep(ident, ",") ~ "]@") ~ path ^^ {
    case _ ~ Some("[" ~ ids ~ "]@") ~ cpath => ConfigFile(cpath, ids)
    case _ ~ None ~ cpath => ConfigFile(cpath, List.empty[String])
  }

  lazy val templateDir = ("-t" | "--tempalte") ~ path ^^ {
    case _ ~ tpath => tpath
  }

  lazy val exportDir = ("-e" | "--export") ~ path ^^ {
    case _ ~ epath => epath
  }

  lazy val path = """\S*""".r

}