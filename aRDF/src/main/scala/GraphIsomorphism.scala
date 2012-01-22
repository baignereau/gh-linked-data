package org.w3.rdf

abstract class GraphIsomorphism[M <: Model] {
  
  def isIsomorphicWith(g1: M#Graph, g2: M#Graph): Boolean
  
}
