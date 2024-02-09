package database.models

import slick.jdbc.PostgresProfile.api.*

import java.util.concurrent.atomic.AtomicLong

case class Clientes (
              id:Long,
              limite:Long
              )

object Clientes:
  private val seq = new AtomicLong
  
  def apply(saldo:Long): Clientes =
    Clientes(seq.incrementAndGet(), saldo)
    
  def mapperTo(id:Long, saldo:Long): Clientes =
    apply(id, saldo)

class ClientesTable(tag: Tag) extends Table[Clientes](tag, "clientes"):
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def saldo = column[Long]("saldo")
  
  def * = (id, saldo) <> ((Clientes.mapperTo _).tupled, Clientes.unapply)
  
val clientesTable = TableQuery[ClientesTable]