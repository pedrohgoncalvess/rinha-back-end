package api.json

case class SaldoExtrato(
                total:Long,
                data_extrato:String = java.time.LocalDateTime.now.toString,
                limite:Long
                )

case class TransacaoExtrato(
                           valor:Long,
                           tipo:String,
                           descricao:String,
                           realizada_em:String
                           )

case class ExtratoJson(
                      saldo: SaldoExtrato,
                      ultimas_transacoes: Array[TransacaoExtrato]
                      )
