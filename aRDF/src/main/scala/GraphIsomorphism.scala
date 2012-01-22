package org.w3.rdf



abstract class GraphIsomorphism[M <: Model](m: M) {
  
  def isIsomorphicWith(g1: M#Graph, g2: M#Graph): Boolean
  
}

import org.w3.rdf.jena._

object GraphIsomorphismForJenaModel extends GraphIsomorphism[JenaModel](JenaModel) {
  
  def isIsomorphicWith(g1: JenaModel#Graph, g2: JenaModel#Graph): Boolean =
    g1.jenaGraph isIsomorphicWith g2.jenaGraph
  
  
}