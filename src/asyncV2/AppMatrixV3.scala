package asyncV2
 
class Matrix private (val dim: Int, val vec: Vector[Double]) {

  def +(that: Matrix): Matrix = { ??? }

  def *(that: Matrix): Matrix = { ??? }

  def *(d: Double): Matrix = {
    new Matrix(dim, vec.map(_ * d))
  }

  override def toString() = (for (row <- vec.sliding(dim, dim)) yield row.mkString("(", ",", ")")).mkString("Matrix[" + dim + "x" + dim + "]\n", "\n", "")
}

object Matrix {
  import scala.math.sqrt

  def apply(a: Double, an: Double*): Option[Matrix] = {
    val content = (a +: an).toVector
    if (sqrt(content.size).isWhole) {
      val dim = sqrt(content.size).toInt
      val arr = Array.fill(dim * dim)(0.0)
      content.copyToArray(arr)
      Some(new Matrix(dim, arr.toVector))
    } else
      None
  }

  def apply(dim: Int, f: (Int, Int) => Double): Option[Matrix] = {
    val vec = (for {
      i <- (1 to dim)
      j <- (1 to dim)
    } yield f(i, j)).toVector
    Some(new Matrix(dim, vec))
  }

  def apply(arr: Array[Array[Double]]): Option[Matrix] = {
    if ((sqrt(arr(0).size * arr.size).isWhole) && (arr.filter(a => arr(0).size == a.size).size == arr.size)) {
      Some(new Matrix(arr.size, arr.flatMap(a => a).toVector))
    } else
      None
  }

} 
object AppMatrixV3 extends App {
  import Matrix._
  /**
   * *************************
   * Matrizen
   */
  lazy val mat1 = Matrix(1.0, 2.0, 3.0, 4, 5, 6, 7, 8, 9).get
  lazy val mat2 = Matrix(3, (i, j) => if (i == j) 1.0 else 0.0).get
  lazy val mat3 = Matrix(Array(Array(3.0, 2.0, 1.0), Array(0.0, 1, 0), Array(0.0, 0, 3), Array(0.0, 0, 3))).get

  lazy val mat100 = Matrix(100, (i, j) => if (i == j) 1.0 else 0.0).get
  lazy val mat200 = Matrix(200, (i, j) => if (i == j) 1.0 else 0.0).get
  lazy val mat500 = Matrix(500, (i, j) => if (i == j) 1.0 else 0.0).get
  lazy val mat1000 = Matrix(1000, (i, j) => if (i == j) 1.0 else 0.0).get

  /**
   * **************************
   * FUNCTIONs
   */

  def test01 = {
    printMat(mat1);
    printMat(mat2);
    printMat(mat3);
  }

  def test02 = {
    println(mat1 * 2.0);
  }

  def printMat(mat: Matrix) {
    println(mat)
  }

  /**
   * *************************************
   * TESTs
   */
  test01

}