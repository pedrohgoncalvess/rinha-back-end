package api.route

import api.json.{TransacaoJSF, TransacaoJSM}
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.model.StatusCodes
import org.json4s.native.Serialization
import database.models.{Cliente, Transacao}
import database.operations.addTransaction
import database.operations.getClientInfo
import org.json4s.DefaultFormats
import scala.concurrent.Future
import scala.util.{Failure, Success}

class Post extends Directives with TransacaoJSF {

  implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats

  val route: Route =
    path("clientes" / LongNumber / "transacoes") { clienteId =>
      post {
        entity(as[TransacaoJSM]) { trns =>
          val novaTrns = Transacao(
            id=Some(1),
            id_cliente=clienteId,
            valor=trns.valor,
            tipo=trns.tipo,
            descricao=trns.descricao,
            realizada_em=Some(java.time.LocalDateTime.now)
          )
          val novaTrnsOp: Future[Unit] = addTransaction(novaTrns)
          onComplete(novaTrnsOp) {

            case Success(_) =>
              val clientInfo: Future[Option[Cliente]] = getClientInfo(clienteId)
              onComplete(clientInfo) {

                case Success(Some(cliente)) =>
                  complete(StatusCodes.OK, Serialization.write(Map("saldo" -> cliente.saldo, "limite" -> cliente.limite)))
                case Failure(exception) =>
                  complete(StatusCodes.InternalServerError, s"Message:${exception.getMessage}")
              }

            case Failure(exception) =>
              if (exception.getMessage.contains("violates foreign key")) {
                complete(StatusCodes.NotFound)
              } else if (exception.getMessage.contains("Limite excedido")) {
                complete(StatusCodes.UnprocessableContent)
              } else {
                complete(StatusCodes.InternalServerError, s"Message:${exception.getMessage}")
              }
          }

        }
      }

    }
}
