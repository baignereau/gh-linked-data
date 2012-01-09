package org.w3.rdf

import org.w3.rdf.jena._

class AbstractTest[M <: Model](m: M) {
  
  def test() = {
    
    import m._
    
    val t = Triple(
        SubjectNode(NodeIRI(IRI("http://www.w3.org/"))), 
        PredicateIRI(IRI("http://www.w3.org/predicate")),
        ObjectLiteral(Literal("toto", None, None))
    )
    
    val g: Graph = Graph(t)
    
    t match {
      case Triple(SubjectNode(NodeIRI(IRI(s))), PredicateIRI(IRI(p)), ObjectLiteral(Literal(o, _, _))) => println(s.toString + p.toString + o.toString)
      
      
    }
    
    Literal("toto", None, None) match { case Literal(t, None, None) => () }
    
    println(g)
    
  }
  
}

object Test {
  
  def main(args: Array[String]): Unit = {
    
    val scalaTest = new AbstractTest[ScalaModel](ScalaModel)
    val jenaTest = new AbstractTest[JenaModel](JenaModel)
    
    val tests = Seq(scalaTest, jenaTest)
    tests.foreach { _.test() }
    
    import com.hp.hpl.jena._
    import com.hp.hpl.jena.rdf.model._
    import com.hp.hpl.jena.graph._
    
    val model = ModelFactory.createDefaultModel()
//    model.read("http://www.w3.org/People/Berners-Lee/card")
    model.getReader("TURTLE").read(model, "file:///tmp/card")
    
    val jenaGraph = new JenaModel.Graph(model.getGraph)
    
    val scalaGraph: ScalaModel#Graph = JenaToScala.transform(jenaGraph)
    
    println(scalaGraph)
    
  }
  
}