package database.models

import slick.jdbc.PostgresProfile.api.*

import java.util.concurrent.atomic.AtomicLong

case class Transacoes (
                       id: Option[Long],
                       id_cliente:Long,
                       valor:Long,
                       tipo:String,
                       descricao:String,
                       realizada_em:Option[java.time.LocalDateTime]
                     )

object Transacoes:
  private val seq = new AtomicLong

  def apply(id_cliente:Long, valor:Long,tipo:String, descricao:String): Transacoes =
    Transacoes(Some(seq.incrementAndGet()), id_cliente, valor, tipo, descricao, Some(java.time.LocalDateTime.now()))

  def mapperTo(
              id: Option[Long],
              id_cliente:Long,
              valor:Long,
              tipo:String,
              descricao:String,
              realizada_em:Option[java.time.LocalDateTime]
              ): Transacoes = apply(id, id_cliente, valor, tipo, descricao, realizada_em)



class TransacoesTable(tag: Tag) extends Table[Transacoes](tag, "transacoes"):
  def id = column[Option[Long]]("id", O.AutoInc, O.PrimaryKey)
  def id_cliente = column[Long]("id_cliente")
  def valor = column[Long]("valor")
  def tipo = column[String]("tipo")
  def descricao = column[String]("descricao")
  def realizada_em = column[Option[java.time.LocalDateTime]]("realizada_em", O.AutoInc)

  def transacoes_pk = foreignKey("transacoes_pk", id_cliente, clientesTable)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

  override def * = (id, id_cliente, valor, tipo, descricao, realizada_em) <> ((Transacoes.mapperTo _).tupled, Transacoes.unapply)
  
val transacoesTable = TableQuery[TransacoesTable]