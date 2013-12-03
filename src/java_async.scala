/*  Dies habe ich verwendet, um damit asynchrone Task mittels Java-Bordmittel
 *  laufen zu lassen. Sie können JavaParTask "as is" oder mit eigenen Anpassungen
 *  verwenden. Da Java auch Futures kennt, gibt es hierzu auch etwas, das ich
 *  aber noch nicht eingesetzt habe. Executors werden natürlich immer implicit 
 *  in der letzten Parmeter-Liste angefügt. Somit muss man sie nur in seltenen
 *  Fällen explicit übergeben. Alle async Frameworks in Scala haben einen Pool
 *  auf den man implicit per Import zugreifen kann.
 */
 
 object OSEnvironment {
  import System.{getProperty => prop}
  
  val numCores=     Runtime.getRuntime.availableProcessors
  val name=         prop("os.name","unknown OS")
  val version=      prop("os.version","unknown version")
  val architecture= prop("os.arch","unknown architecture")
  val jvm=          prop("java.version", "unknown")

  def printOSSpecs {
    println("Cores : %d".format(numCores))
    println("OS    : %s %s (%s)".format(name,version,architecture))
    println("JVM   : " + jvm)
  }
}

object ExecTiming {
  
  def resultAndNanos[A](block: => A) = {
    val t = System.nanoTime
    (block,System.nanoTime - t)
  }
  
  def printMillis[A](block: => A) {
    val (result,nanoTime)= resultAndNanos(block)
    println("time: "+ "~%.3fms".format((nanoTime/1000L)/1000.0)+ "\n")
  }
  
  def printResultAndMillis[A](block: => A) {
    val (result,nanoTime)= resultAndNanos(block)
    println(result + "\ntime: "+ 
            "~%.3fms".format((nanoTime/1000L)/1000.0)+ "\n")
  }
}  

class JavaParTask {
  import java.util.concurrent._
  
  def createExecutor(numTasks: Int= OSEnvironment.numCores) = 
    Executors.newFixedThreadPool(numTasks)

  def createBarrier(numTasks: Int= OSEnvironment.numCores)= 
    new CountDownLatch(numTasks)
  
  def runTask(block: => Unit, barrier: Option[CountDownLatch]= None)
             (implicit executor: ExecutorService) =
    executor.submit(new Runnable {
                      def run= try { block } 
                           finally { for (b <- barrier) b.countDown }
                    })
                    
  def futureTask[A](block: => A, barrier: Option[CountDownLatch]= None)
                 (implicit executor: ExecutorService): Future[A]= 
    executor.submit(new Callable[A] {
                        def call= try { block } 
                              finally { for (b <- barrier) b.countDown }
                    })
                                    
  def awaitTasksDone(barrier: Option[CountDownLatch]) = 
    synchronized { for (b <- barrier) b.await } // blocks!
}
   