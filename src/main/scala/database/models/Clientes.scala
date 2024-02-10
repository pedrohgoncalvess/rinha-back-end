package database.models

import slick.jdbc.PostgresProfile.api.*

import java.util.concurrent.atomic.AtomicLong

case class Cliente (
              id:Long,
              limite:Long,
              saldo:Long
              )

object Cliente:
  private val seq = new AtomicLong
  
  def apply(limite:Long, saldo:Long): Cliente =
    Cliente(seq.incrementAndGet(), limite,saldo)
    
  def mapperTo(id:Long, limite:Long, saldo:Long): Cliente =
    apply(id, limite, saldo)

class ClienteTable(tag: Tag) extends Table[Cliente](tag, "clientes"):
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def limite = column[Long]("limite")
  def saldo = column[Long]("saldo")
  
  def * = (id, limite, saldo) <> ((Cliente.mapperTo _).tupled, Cliente.unapply)
  
val clienteTable = TableQuery[ClienteTable]