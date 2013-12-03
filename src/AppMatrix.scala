object AppMatrix extends App {

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.{ Future, Promise }
  import scala.async.Async.{ async, await }
  import Thread.{ sleep => schlaf }

//  val future: Future[Int] = async {
//    val f1 = async { println(true); true }
//    val f2 = async { println(42); 42 }
//    println("before await(f1)")
//    if (await(f1)) { println(42); await(f2) } else { println(0); 0}
//  }
//  
  val future1 = async {
    schlaf(3000); 23
  }
  
  val future2 = async {
    schlaf(1000); 42
  }

//  Thread.sleep(4000)
  
  
  val result : Future[Int] = async {
    println("awaiting result")
    await(future1) + await(future2)
  }
  
//  Thread.sleep(4000)
  
  Thread.sleep(4000)
    val out: Future[Unit] = async{ println("Komplexe berechnung " + await(result)) }
    out.onSuccess[Unit]{ case u:Unit => u };
    
   
  
  
 

}