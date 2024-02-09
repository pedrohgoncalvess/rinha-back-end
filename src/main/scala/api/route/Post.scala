package api.route

import api.json.{TransacaoJSF, TransacaoJSM}
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.model.StatusCodes
import database.models.Transacoes
import database.operations.addTransaction

import scala.concurrent.Future
import scala.util.{Failure, Success}

class Post extends Directives with TransacaoJSF {


  val route: Route =
    path("clientes" / LongNumber / "transacoes") { clienteId =>
      post {
        entity(as[TransacaoJSM]) { trns =>
          val novaTrns = Transacoes(
            id=Some(1),
            id_cliente=clienteId,
            valor=trns.valor,
            tipo=trns.tipo,
            descricao=trns.descricao,
            realizada_em=Some(java.time.LocalDateTime.now)
          )
          val novaTrnsOp: Future[Unit] = addTransaction(novaTrns)
          onComplete(novaTrnsOp) {
            case Success(_) => complete(StatusCodes.Created)
            case Failure(exception) => complete(StatusCodes.InternalServerError, exception.getMessage)
          }

        }
      }

    }
}

