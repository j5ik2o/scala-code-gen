package codegen.infrastructure.parser

import util.parsing.combinator.RegexParsers

case class CommandLineParseException(message: String)
  extends Exception(message)

trait CommandLine

case class ConfigFile(name: String,
                      ids: List[String] = List.empty[String])

case class Parameters(configFile: ConfigFile,
                      templateDir: Option[String] = None,
                      exportDir: Option[String] = None)
  extends CommandLine

case class Help extends CommandLine


class CommandLineParser extends RegexParsers {

  def parse(source: String): CommandLine = parseAll(instruction, source) match {
    case Success(result, _) => result
    case Failure(msg, _) => throw new CommandLineParseException(msg)
    case Error(msg, _) => throw new CommandLineParseException(msg)
  }

  lazy val instruction = helpInstruction | parametersInstruction

  lazy val helpInstruction = paramExpr("-h",Some("--help")) ^^ {
    case _ => Help()
  }

  lazy val parametersInstruction = configFile ~ opt(templateDir) ~ opt(exportDir) ^^ {
    case configFile ~ templateDirOption ~ exportDirOption =>
      Parameters(configFile, templateDirOption, exportDirOption)
  }

  lazy val configFile = paramExprWithArg("-c", configArgs, Some("--config"))
  lazy val templateDir = paramExprWithArg("-t", path, Some("--template"))
  lazy val exportDir = paramExprWithArg("-e", path, Some("--export"))

  lazy val configArgs =  opt("[" ~ repsep(id, ",") ~ "]@") ~ path ^^{
    case Some("[" ~ ids ~ "]@") ~ cpath => ConfigFile(cpath, ids)
    case None ~ cpath => ConfigFile(cpath, List.empty[String])
  }

  lazy val path = """\S*""".r
  lazy val id = """[\-0-9a-zA-Z]+""".r

  def paramParser(shortParam: String, longParamOp: Option[String]): Parser[String] = longParamOp match {
    case Some(longParam) => shortParam | longParam
    case None => shortParam
  }

  def paramExprWithArg[T](shortParam: String, argsParser : Parser[T], longParamOp: Option[String] = None) = {
    paramParser(shortParam, longParamOp) ~> argsParser
  }

  def paramExpr(shortParam: String, longParamOp: Option[String] = None) = {
    paramParser(shortParam, longParamOp)
  }

}