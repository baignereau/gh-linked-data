package org.w3.rdf






class Transformer[ModelA <: Model, ModelB <: Model](val a: ModelA, val b: ModelB) {
  
  def transform(graph: ModelA#Graph): ModelB#Graph =
    b.Graph(graph map (transformTriple _))
    
  def transformTriple(t: ModelA#Triple): b.Triple = {
    val a.Triple(s, p, o) = t
    b.Triple(transformSubject(s), transformPredicate(p), transformObject(o))
  }
  
  def transformIRI(iri: a.IRI): b.IRI = {
    val a.IRI(i) = iri
    b.IRI(i)
  }
  
  def transformBNode(bn: a.BNode): b.BNode = {
    val a.BNode(label) = bn
    b.BNode(label)
  }
  
  def transformNode(n: a.Node): b.Node = {
    n match {
      case a.NodeBNode(label) => b.NodeBNode(transformBNode(label))
      case a.NodeIRI(iri) => b.NodeIRI(transformIRI(iri))
    }
  }
    
  def transformSubject(s: a.Subject): b.Subject = {
    val a.SubjectNode(node) = s
    b.SubjectNode(transformNode(node))
  }
  
  def transformPredicate(p: a.Predicate): b.Predicate = {
    val a.PredicateIRI(iri) = p
    b.PredicateIRI(transformIRI(iri))
  }

  def transformObject(o: a.Object): b.Object = {
    o match {
      case a.ObjectNode(node) => b.ObjectNode(transformNode(node))
      case a.ObjectLiteral(lit) => b.ObjectLiteral(transformLiteral(lit))
    }
  }
  
  def transformLiteral(literal: a.Literal): b.Literal = {
    import a._
    val a.Literal(lit: String, langtagOption: Option[LangTag], datatypeOption: Option[IRI]) = literal
    b.Literal(
      lit,
      langtagOption map { case a.LangTag(lang) => b.LangTag(lang)},
      datatypeOption map transformIRI)
  }
  
}

