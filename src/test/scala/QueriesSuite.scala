package es.weso.wiExtract

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import com.typesafe.config._
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.query._
import com.hp.hpl.jena.query.Query._
import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.util.FileManager
import scala.collection.JavaConversions._
import com.hp.hpl.jena.vocabulary.RDF
import java.io.FileOutputStream
import java.io.File
import java.io.StringWriter
import es.weso.utils.JenaUtils._
import com.hp.hpl.jena.update.UpdateFactory
import es.weso.utils.JenaUtils
import scala.io.Source


class QueriesSuite 
	extends FunSpec 
	with ShouldMatchers {
  
  val conf 			= ConfigFactory.load()
  val simpleModelURI = conf.getString("simpleModel")
  val queryDir		 = conf.getString("queryDir")
  val simpleModel = JenaUtils.parseFromURI(simpleModelURI)
   
  describe("List all indicators") {
   ignore("List all indicators") {
     val query : String = Source.fromFile(queryDir + "indicators.sparql").mkString
     val result = JenaUtils.querySelectModel(query,simpleModel)
     countResults(result) should be(6)
   }

   ignore("List all Weights") {
     val query : String = Source.fromFile(queryDir + "weights.sparql").mkString
     val result = JenaUtils.querySelectModel(query,simpleModel)
     countResults(result) should be(10)
   }

   
   def countResults(result : ResultSet) : Int = {
     var n = 0
     while (result.hasNext) {
       result.next
       n += 1
     }
     n
   }
}

}
