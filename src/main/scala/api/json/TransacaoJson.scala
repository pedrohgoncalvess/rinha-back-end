package api.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class TransacaoJSM(
                        valor:Long,
                        tipo:String,
                        descricao:String
                        )


trait TransacaoJSF extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val TransacaoJsonFormat: RootJsonFormat[TransacaoJSM] = jsonFormat3(TransacaoJSM.apply)
}