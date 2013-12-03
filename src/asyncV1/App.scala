package asyncV1

object App {
  def main(args: Array[String]) {
    val workManager: WorkManager = new WorkManager();
    println("START WORK");
    workManager.startWork();
    println("WORK STARTED");
    workManager.finishWork();
    println("FINISHED WORK");
  }
}