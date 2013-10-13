package es.weso.wiExtract

import play.api.libs.json.JsValue
import play.api.libs.json.Json

case class Observation(
    countryCode: String,
    countryName: String,
    year: String,
    indicatorCode: String,
    value: Float,
    datasetName: String,
    sheet_type: String) {
    
  def toJson() : JsValue = {

      Json.obj("countryCode" -> countryCode,
             "countryName" -> countryName,
             "year" -> year,
             "indicatorCode" -> indicatorCode,
             "value" -> value,
             "datasetName" -> datasetName,
             "sheet-type" -> sheet_type)
  }

}

object Observation {
  def prettyPrintObs(lsObs : Seq[Observation]) : String = {
    val json = Json.toJson(lsObs.map(o => o.toJson))
    Json.prettyPrint(json)
  }

  def stringifyObs(lsObs : Seq[Observation]) : String = {
    val json = Json.toJson(lsObs.map(o => o.toJson))
    Json.stringify(json)
  }
}