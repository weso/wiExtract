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


class GenerateJsonSuite 
	extends FunSpec 
	with ShouldMatchers {

  describe("Generate Json") {
   it("Should Generate Json") {
     info("Not implemented yet")
   }
 }

}
