package org.w3.rdf

import org.junit.Test
import org.junit.Assert._
import java.io._
import com.hp.hpl.jena._
import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.graph._
import org.w3.rdf.jena._

class ModelSpec {
  
  @Test()
  def mytest(): Unit = {
    
    
    val model = ModelFactory.createDefaultModel()
    model.getReader("TURTLE").read(model, new FileReader("src/test/resources/card.ttl"), "http://www.w3.org/People/Berners-Lee/card")
    
    val jenaGraph = new JenaModel.Graph(model.getGraph)
//    println(jenaGraph)
    
    val scalaGraph: ScalaModel#Graph = JenaToScala.transform(jenaGraph)
    
    val jenaGraphAgain = ScalaToJena.transform(scalaGraph)
    
//    println(jenaGraphAgain)
    
    assertTrue(jenaGraph.jenaGraph isIsomorphicWith jenaGraphAgain.jenaGraph)
    
  }
  
}