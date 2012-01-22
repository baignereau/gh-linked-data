package org.w3.rdf.Modules

import org.w3.rdf.jena._
import org.w3.rdf._

object ScalaModel extends ScalaModel
  
object JenaModel extends JenaModel
  
object ScalaToJena extends Transformer[ScalaModel, JenaModel](ScalaModel, JenaModel)
  
object JenaToScala extends Transformer[JenaModel, ScalaModel](JenaModel, ScalaModel)
  
object GraphIsomorphismForJenaModel extends GraphIsomorphismForJenaModel




object PimpMyRDF {
  
  // TODO
  
}