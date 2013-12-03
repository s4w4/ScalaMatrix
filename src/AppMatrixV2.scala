

class Matrix private (val dim: Int, val vec: Vector[Double]) {

  override def toString() = (for (row <- vec.sliding(dim, dim)) yield row.mkString("(", ",", ")")).mkString("Matrix[" + dim + "x" + dim + "]\n", "\n", "")
}

object Matrix {
  import scala.math._

  /**
   * erstellt eine Quadratische Matrix aus einzelnen Doublewerten
   * falls die Anzahl der Werte nicht als Quadratisch Matrix angegeben werden kann,
   * wird die nächstgrößte Quadratische Matrix gebaut, wobei die restlichen werte mit 0.0
   * gefüllt werden.
   */
  def apply(a: Double, an: Double*): Matrix = {
    val content = (a +: an).toVector
    val dim = sqrt(content.size).ceil.toInt // gibt passende Dimension an
    val arr = Array.fill(dim * dim)(0.0) // quadratisches Array mit 0.0 gefüllt
    content.copyToArray(arr) // fügt werte ins Array
    new Matrix(dim, arr.toVector)
  }

  /**
   * erstellt eine Quadratische Matrix wobei dim die Dimension angibt und 
   * f eine Funktion die die werte der Matrix angeben soll
   */
  def apply(dim: Int, f: (Int, Int) => Double): Matrix = {
    val vec = (for {
      i <- (1 to dim)
      j <- (1 to dim)
    } yield f(i, j)).toVector
    new Matrix(dim, vec)
  }

  /**
   * erstellt eine Quadratische Matrix aus einem 2 Dimensionalen Array
   * die inneren Arrays geben jeweils eine zeile der Matrix an
   * falls die anzahl an inneren Arrays mit der keine Quadratische Matrix angibt 
   * werden pro Zeile passend 0.0 eingefügt
   */
  def apply(inArr: Array[Array[Double]]): Matrix = {
    val dim = inArr.size.ceil.toInt
    val arr= Array.fill(dim)(0.0)
    inArr(0).copyToArray(arr)
    val newArr = inArr.tail
    val vec = newArr.foldLeft(arr)((s, n) => {
      val row = Array.fill(dim)(0.0)
      n.copyToArray(row)
      s ++ row
    }).toVector
      
    new Matrix(dim, vec)
  }
}

object AppMatrixV2 extends App {
    lazy val mat1 = Matrix(1.0, 2.0, 3.0, 4, 5, 6, 7, 8, 9)
    lazy val mat2 = Matrix(3, (i, j) => if (i == j) 1.0 else 0.0)
    lazy val mat3 = Matrix(Array(Array(3.0, 2.0, 1.0), Array(0.0, 1, 0), Array(0.0, 0, 3), Array(0.0, 0, 3), Array(0.0, 0, 3)))

    lazy val mat100 = Matrix(100, (i, j) => if (i == j) 1.0 else 0.0)
    lazy val mat200 = Matrix(200, (i, j) => if (i == j) 1.0 else 0.0)
    lazy val mat500 = Matrix(500, (i, j) => if (i == j) 1.0 else 0.0)
    lazy val mat1000 = Matrix(1000, (i, j) => if (i == j) 1.0 else 0.0)

  def test01 = {
//    println(mat1)
//    println(mat2)
    println(mat3)
  }

    
    test01
}