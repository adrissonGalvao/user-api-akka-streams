import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

package object repository {
  implicit val ec =
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))
}
