package codegen.infrastructure.parser

import io.Source

/**
 * Created by IntelliJ IDEA.
 * User: junichi
 * Date: 11/04/08
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */

object Cat {
  def main(args: Array[String]) {
    args match {
      case Array("-f", file) =>
        val source = Source.fromFile(file)
        source.getLines.foreach(println)
      case _ => println("プログラム引数が正しくありません。")
    }
  }
}