package org.w3.rdf

import org.w3.rdf.jena._

class AbstractTest[M <: Model](m: M) {
  
  def test() = {
    
    import m._
    
    val g: Graph = Graph(
        Triple(
            SubjectNode(NodeIRI(IRI("http://www.w3.org/"))), 
            PredicateIRI(IRI("http://www.w3.org/predicate")),
            ObjectLiteral(PlainLiteral("toto", None))
        )
    )
    
    
    
    println(g)
    
  }
  
}

object Test {
  
  def main(args: Array[String]): Unit = {
    
    val scalaTest = new AbstractTest[ScalaModel](ScalaModel)
    val jenaTest = new AbstractTest[JenaModel](JenaModel)
    
    val tests = Seq(scalaTest, jenaTest)
    tests.foreach { _.test() }
    
  }
  
}