package api.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import api.json.{ExtratoJson, SaldoExtrato, TransacaoExtrato}
import database.operations.{getClientInfo, listTransacao}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import database.models.Cliente
import scala.concurrent.Future
import scala.util.{Failure, Success}


class Get extends Directives {

  val objectMapper: ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule).registerModule(new JavaTimeModule())

  val route: Route =
    path("clientes" / LongNumber / "extrato") { clienteId =>
    get {
      val cltInfo: Future[Option[Cliente]] = getClientInfo(clienteId)
      onComplete(cltInfo) {

        case Success(Some(cltej)) =>
            val allTrns = listTransacao(clienteId)

            onComplete(allTrns) {
              case Success(trnss) =>
                val extrato =  ExtratoJson(
                  saldo=SaldoExtrato(total=cltej.saldo, limite=cltej.limite),
                  ultimas_transacoes=trnss.map(trns => TransacaoExtrato(valor=trns.valor, tipo=trns.tipo, descricao=trns.descricao, realizada_em=trns.realizada_em.get.toString)).toArray
                )
                complete(objectMapper.writeValueAsString(extrato))


              case Failure(exception) =>
                complete(StatusCodes.InternalServerError, exception.getMessage)
            }
        case Success(None) =>
          complete(StatusCodes.NotFound)
        }
      }
    }
}
