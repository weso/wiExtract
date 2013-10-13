package es.weso.wiExtract

import org.rogach.scallop.Scallop
import com.typesafe.config.ConfigFactory
import org.rogach.scallop.ScallopConf
import org.rogach.scallop.exceptions.Help
import org.slf4j.LoggerFactory
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.util.FileManager
import com.hp.hpl.jena.rdf.model.Model
import scala.io.Source
import es.weso.utils.JenaUtils
import com.hp.hpl.jena.query.ResultSet
import scala.collection.mutable.ArrayBuffer
import play.api.libs.json._
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter

class Opts(arguments: Array[String],
    onError: (Throwable, Scallop) => Nothing
    ) extends ScallopConf(arguments) {

    banner("""| Generate JSON data
              | Options:
              |""".stripMargin)
    footer("Enjoy!")
    version("0.1")
    val fileName = opt[String]("file",
                    required=true,
        			descr = "Turtle file")
    val output  = opt[String]("out",
    				descr = "Output file")
    val version = opt[Boolean]("version", 
    				noshort = true, 
    				descr = "Print version")
    val help 	= opt[Boolean]("help", 
    				noshort = true, 
    				descr = "Show this message")
  
  override protected def onError(e: Throwable) = onError(e, builder)
}

object Main extends App {


 override def main(args: Array[String]) {

  val logger 		= LoggerFactory.getLogger("Application")
  val conf 			= ConfigFactory.load()
  
  val opts 	= new Opts(args,onError)
  try {
   val queryFile = conf.getString("queryObservations") 
   val model = ModelFactory.createDefaultModel
   val inputStream = FileManager.get.open(opts.fileName())
   model.read(inputStream,"","TURTLE")
   
   val lsObs = createObservations(model,queryFile)
   if (opts.output.get == None) println(Observation.prettyPrintObs(lsObs))
   else {
     val fileOutput = opts.output()
     val file = new File(fileOutput)
     val bw = new BufferedWriter(new FileWriter(file))
     bw.write(Observation.stringifyObs(lsObs))
     bw.close
     println("JSOn Output saved in " + fileOutput)
   }
  } catch {
    case e: Exception => println("\nException:\n" + e.getLocalizedMessage())
  }
 }

  private def onError(e: Throwable, scallop: Scallop) = e match {
    case Help(s) =>
      println("Help: " + s)
      scallop.printHelp
      sys.exit(0)
    case _ =>
      println("Error: %s".format(e.getMessage))
      scallop.printHelp
      sys.exit(1)
  }
  
  def createObservations(model: Model, queryFile: String) : ArrayBuffer[Observation] = {
    val queryStr = Source.fromFile(queryFile).mkString
    println(queryStr)
    val result = JenaUtils.querySelectModel(queryStr,model)
    findObservations(result)
  }
  
  def findObservations(result: ResultSet) : ArrayBuffer[Observation] = {
    val answer = ArrayBuffer[Observation]()
    while (result.hasNext()) {
      val soln = result.nextSolution()
      val countryCode = soln.get("countryCode").asLiteral.getLexicalForm
      val countryName = soln.get("countryName").asLiteral.getLexicalForm
      val year = soln.get("year").asLiteral.getLexicalForm
      val indicatorCode = soln.get("indicatorCode").asLiteral.getLexicalForm
      val value 		= soln.get("value").asLiteral.getFloat
      val datasetName 	= soln.get("datasetName").asLiteral.getLexicalForm
      val sheet_type 	= soln.get("sheet_type").asResource().getLocalName()
      val obs = Observation(countryCode,countryName,year,indicatorCode,value,datasetName,sheet_type)
      answer += obs
    } 
    answer
  }
  
} 
