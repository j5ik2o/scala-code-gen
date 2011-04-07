package codegen.infrastructure.parser

import util.parsing.combinator.JavaTokenParsers
import io.BufferedSource
import codegen.domain.{ClassMeta, FieldMeta}
import jp.tricreo.scala.ddd.base.model.Identifier

case class ModelParseException(message: String) extends Exception(message)

class ModelParser extends JavaTokenParsers {

  def parse(source: BufferedSource): List[ClassMeta] = parse(source.reader)

  def parse(source: java.io.Reader) = (parseAll(rep(classMeta), source): @unchecked) match {
    case Success(result, _) => result
    case Failure(msg, _) => throw new ModelParseException(msg)
  }

  //def debug(source: String) = parseAll(rep(classMeta), source)
  lazy val idToken = """[\-0-9a-zA-Z]+""".r

  lazy val nameToken = """[a-zA-Z_.]+""".r

  lazy val fieldMeta: Parser[FieldMeta] = ident ~ "=" ~ nameToken ^^ {
    case name ~ _ ~ typeName => FieldMeta(name, typeName)
  }

  lazy val fieldMetas: Parser[List[FieldMeta]] = "fields" ~ "{" ~ repsep(fieldMeta, ",") ~ "}" ^^ {
    case _ ~ _ ~ fields ~ _ => fields
  }

  lazy val packageOption: Parser[String] = "package" ~ "=" ~ nameToken ^^ {
    case _ ~ _ ~ packageName => packageName
  }

  lazy val classMetaId: Parser[String] = "id" ~ "(" ~ idToken ~ ")" ^^ {
    case _ ~ _ ~ id ~ _ => id
  }

  lazy val classMeta: Parser[ClassMeta] = classMetaId ~ "=" ~ "class" ~ ident ~ "{" ~ opt(packageOption) ~ fieldMetas ~ "}" ^^ {
    case classMetaId ~ _ ~ _ ~ className ~ _ ~ packageOption ~ fieldMetas ~ _ =>
      ClassMeta(Identifier(classMetaId), className, packageOption, fieldMetas)
  }

}