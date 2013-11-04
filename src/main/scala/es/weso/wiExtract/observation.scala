package es.weso.wiExtract

case class Observation(
    countryCode: String,
    countryName: String,
    year: String,
    indicatorCode: String,
    value: Float,
    datasetName: String,
    sheet_type: String) {
    
  def toJson() : String = {

   "{ " + 
   List(pair("countryCode",countryCode),
        pair("countryName",countryName),
        pair("year",year),
        pair("indicatorCode",indicatorCode),
        pair("value", value),
        pair("datasetName",datasetName),
        pair("sheet-type",sheet_type)).mkString(",") + " }"
  }

  private def pair(key:String, value:String) = {
    show(key) + ":" + show(value) 
  }
  
  private def pair(key:String, value:Int) = {
    show(key) + ":" + value.toString 
  }

  private def pair(key:String, value:Float) = {
    show(key) + ":" + value.toString 
  }

  private def show(x:String) : String = "\"" + x + "\""
  
}

object Observation {
  def toJson(lsObs : Seq[Observation]) : String = {
    "[ \n" + lsObs.map(o => o.toJson).mkString(",\n") + "\n]"
  }
}