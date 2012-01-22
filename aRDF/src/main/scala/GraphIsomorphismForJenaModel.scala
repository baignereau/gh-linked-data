package org.w3.rdf

import org.w3.rdf.Modules.JenaModel
import org.w3.rdf.jena._

trait GraphIsomorphismForJenaModel extends GraphIsomorphism[JenaModel] {
  
  def isIsomorphicWith(g1: JenaModel#Graph, g2: JenaModel#Graph): Boolean =
    g1.jenaGraph isIsomorphicWith g2.jenaGraph
  
  
}