import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

name := "WiExtract"

version := "1.0"

scalaVersion := "2.10.2"

seq(assemblySettings: _*)

libraryDependencies ++= Seq("junit" % "junit" % "4.8.1" % "test" , 
							"commons-configuration" % "commons-configuration" % "1.7",
							"org.apache.jena" % "apache-jena-libs" % "2.11.0", // excludeAll(ExclusionRule(organization = "org.slf4j")),
							"commons-io" % "commons-io" % "2.4",
							"org.rogach" %% "scallop" % "0.9.1", 
							"org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
							"com.typesafe" % "config" % "1.0.1",
							"org.scalaj" %% "scalaj-collection" % "1.5"
)

// mainClass in assembly := Some("es.weso.wiExtract.Main.class")

test in assembly := {}

mergeStrategy in assembly <<= (mergeStrategy in assembly) { mergeStrategy => {
    case entry => {
      val strategy = mergeStrategy(entry)
      if (strategy == MergeStrategy.deduplicate) MergeStrategy.first
      else strategy
    }
  }
}