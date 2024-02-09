package database.operations

import database.models.*
import database.Connection
import scala.concurrent.{ExecutionContext, Future}

def addTransaction(transacao: Transacoes): Future[Unit] = {
  import slick.jdbc.PostgresProfile.api._
  import scala.concurrent.ExecutionContext.Implicits.global

  val queryStatement = transacoesTable += transacao
  val resultQuery:Future[Int] = Connection.db.run(queryStatement)
  resultQuery.flatMap { _ =>
    Future.successful(())
  }.recoverWith{
    case ex: Throwable => Future.failed(ex)
  }
}


def listTransaction(id_cliente: Long): Future[List[Transacoes]] = {
  import slick.jdbc.PostgresProfile.api._
  import scala.concurrent.ExecutionContext.Implicits.global

  val queryStatement = transacoesTable.filter(_.id_cliente === id_cliente).result
  Connection.db.run(queryStatement).map(_.toList)
}