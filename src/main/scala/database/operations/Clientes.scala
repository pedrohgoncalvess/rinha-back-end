package database.operations

import database.Connection
import database.models.Cliente
import scala.concurrent.{Future}
import database.models.clienteTable
import slick.lifted.CanBeQueryCondition

def getClientInfo(id_cliente: Long): Future[Option[Cliente]]  =
    import slick.jdbc.PostgresProfile.api._
    val queryStatement = clienteTable.filter(_.id === id_cliente).result.headOption
    Connection.db.run(queryStatement)