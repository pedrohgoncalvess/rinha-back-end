package database.models

import slick.jdbc.PostgresProfile.api.*

import java.util.concurrent.atomic.AtomicLong

case class Transacao (
                       id: Option[Long],
                       id_cliente:Long,
                       valor:Long,
                       tipo:String,
                       descricao:String,
                       realizada_em:Option[java.time.LocalDateTime]
                     )

object Transacao:
  private val seq = new AtomicLong

  def apply(id_cliente:Long, valor:Long,tipo:String, descricao:String): Transacao =
    Transacao(Some(seq.incrementAndGet()), id_cliente, valor, tipo, descricao, Some(java.time.LocalDateTime.now()))

  def mapperTo(
              id: Option[Long],
              id_cliente:Long,
              valor:Long,
              tipo:String,
              descricao:String,
              realizada_em:Option[java.time.LocalDateTime]
              ): Transacao = apply(id, id_cliente, valor, tipo, descricao, realizada_em)



class TransacaoTable(tag: Tag) extends Table[Transacao](tag, "transacoes"):
  def id = column[Option[Long]]("id", O.AutoInc, O.PrimaryKey)
  def id_cliente = column[Long]("id_cliente")
  def valor = column[Long]("valor")
  def tipo = column[String]("tipo")
  def descricao = column[String]("descricao")
  def realizada_em = column[Option[java.time.LocalDateTime]]("realizada_em", O.AutoInc)

  def transacoes_pk = foreignKey("transacoes_pk", id_cliente, clienteTable)//(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

  override def * = (id, id_cliente, valor, tipo, descricao, realizada_em) <> ((Transacao.mapperTo _).tupled, Transacao.unapply)
  
val transacaoTable = TableQuery[TransacaoTable]