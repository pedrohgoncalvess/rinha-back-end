import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import api.route.Post
import scala.io.StdIn


@main def main() =

  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext

  val route = new Post

  val bindingFuture = Http().newServerAt("localhost", 8080).bind(route.route)

  println(s"Server now online at port 8080.")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())