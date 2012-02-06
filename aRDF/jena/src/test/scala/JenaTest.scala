package org.w3.rdf

import org.junit.Test
import org.junit.Assert._
import java.io._
import com.hp.hpl.jena._
import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.graph._
import org.w3.rdf.jena._

class JenaTest {
  
  @Test()
  def mytest(): Unit = {
    val file = new File("jena/src/test/resources/card.ttl")
    println(file.getAbsolutePath)
    val model = ModelFactory.createDefaultModel()
    model.getReader("TURTLE").read(model, new FileReader("jena/src/test/resources/card.ttl"), "http://www.w3.org/People/Berners-Lee/card")
    
    val jenaGraph = JenaModel.Graph.fromJena(model.getGraph)
//    println(jenaGraph)
    
    val scalaGraph = JenaToScala.transform(jenaGraph)
    
    val jenaGraphAgain: JenaModel.Graph = ScalaToJena.transform(scalaGraph)
    
//    println(jenaGraphAgain)
    
    // assertTrue(jenaGraph.jenaGraph isIsomorphicWith jenaGraphAgain.jenaGraph)
    assertTrue(GraphIsomorphismForJenaModel.isIsomorphicWith(jenaGraph, jenaGraphAgain))
    
  }
  
}