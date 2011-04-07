package codegen.infrastructure.parser

import util.parsing.combinator.RegexParsers

case class CommandLineParseException(message: String) extends Exception(message)

case class ConfigFile(name: String, ids: List[String] = List.empty[String])

case class CommandLine(configFile: ConfigFile, templateDir: Option[String] = None, exportDir: Option[String] = None)


class CommandLineParser extends RegexParsers {

  def parse(source: String): CommandLine = parseAll(instruction, source) match {
    case Success(result, _) => result
    case Failure(msg, _) => throw new CommandLineParseException(msg)
    case Error(msg, _) => throw new CommandLineParseException(msg)
  }

  lazy val instruction = configFile ~ opt(templateDir) ~ opt(exportDir) ^^ {
    case configFile ~ templateDirOption ~ exportDirOption =>
      CommandLine(configFile, templateDirOption, exportDirOption)
  }

  lazy val configFile = ("-c" | "--config") ~> opt("[" ~ repsep(id, ",") ~ "]@") ~ path ^^ {
    case Some("[" ~ ids ~ "]@") ~ cpath => ConfigFile(cpath, ids)
    case None ~ cpath => ConfigFile(cpath, List.empty[String])
  }

  lazy val templateDir = ("-t" | "--template") ~> path

  lazy val exportDir = ("-e" | "--export") ~> path


  lazy val path = """\S*""".r

  lazy val id = """[\-0-9a-zA-Z]+""".r

}