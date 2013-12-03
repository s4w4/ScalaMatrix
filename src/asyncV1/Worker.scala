package asyncV1

import java.util.Random
import java.util.concurrent.CountDownLatch
import java.lang.InterruptedException

class Worker(val countDownLatch: CountDownLatch) extends Runnable {

  override def run() = {
    try {
      Thread.sleep(getRandomSeconds()) // sleep random time to simulate long running task
      println("Counting down: " + Thread.currentThread().getName())
      this.countDownLatch.countDown()
    } catch {
      case ex: InterruptedException => Thread.currentThread().interrupt()
    }
  }

  // returns a long between 0 and 9999
  def getRandomSeconds(): Long = {
    import scala.math._
    val generator: Random = new Random()
    abs(generator.nextLong() % 10000)
  }
}