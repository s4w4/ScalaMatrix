package asyncV1

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class WorkManager {

  val countDownLatch: CountDownLatch = new CountDownLatch(NUMBER_OF_TASKS)
  val NUMBER_OF_TASKS: Int = 5

  def finishWork(): Unit = {
    try {
      println("START WAITING")
      countDownLatch.await()
      println("DONE WAITING")
    } catch {
      case ex: InterruptedException => Thread.currentThread().interrupt();
    }
  }

  def startWork(): Unit = {
    val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_TASKS)
    for { i <- (1 to NUMBER_OF_TASKS) } {
      val worker: Worker = new Worker(countDownLatch)
      executorService.execute(worker)
    }
    executorService.shutdown();
  }

}