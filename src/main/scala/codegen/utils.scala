package codegen

/**
 * Created by IntelliJ IDEA.
 * User: junichi
 * Date: 11/04/06
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */

package object utils {

  /**C#のusing文のパクリ
   * @tparam A closeという名前を持っているものならなんでも
   * @tparam B 2番目の引数の関数オブジェクトの戻り値の型(これもなんでもいい)
   * @param resource 最後にcloseを必ず呼ばないといけないオブジェクト
   * @param func 1つめの引数のオブジェクトを使用して行う処理
   */
  def using[A <: {def close()}, B](resource: A)(func: A => B): Unit = {
    try {
      func(resource) //処理実行
    } finally {
      if (resource != null) resource.close()
    }
  }

}