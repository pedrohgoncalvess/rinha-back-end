import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import api.route.{Get, Post}

import scala.io.StdIn


@main def main() =

  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext

  val postRoute = new Post
  val getRoute = new Get

  val bindingFuture = Http().newServerAt("0.0.0.0", 8080).bind(Directives.concat(postRoute.route, getRoute.route))

  println(s"Server now online at port 8080.")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())