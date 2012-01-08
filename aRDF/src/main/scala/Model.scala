package org.w3.rdf

import org.w3.isomorphic._

trait Model {

  type IRI
  trait GraphLike extends Iterable[Triple] { self =>
    def ++(other:Graph):Graph
  }
  type Graph <: GraphLike
  type Triple
  type BNode
  type Node
  type NodeIRI <: Node
  type NodeBNode <: Node
  type Subject
  type SubjectNode <: Subject
  type Predicate
  type PredicateIRI <: Predicate
  type Object
  type ObjectNode <: Object
  type ObjectLiteral <: Object
  type Literal
  type LangTag

  val IRI : Isomorphic1[String, IRI]

  trait GraphObject {
    def empty:Graph
    def apply(elems:Triple*):Graph
    def apply(it:Iterable[Triple]):Graph
  }
  val Graph : GraphObject

  val Triple : Isomorphic3[Subject, Predicate, Object, Triple]

  val BNode : Isomorphic1[String, BNode]

  val NodeIRI   : Isomorphic1[IRI, NodeIRI]
  val NodeBNode : Isomorphic1[BNode, NodeBNode]

  val SubjectNode : Isomorphic1[Node, SubjectNode]

  val PredicateIRI : Isomorphic1[IRI, PredicateIRI]

  val ObjectNode    : Isomorphic1[Node, ObjectNode]
  val ObjectLiteral : Isomorphic1[Literal, ObjectLiteral]

  val Literal : Isomorphic3[String, Option[LangTag], Option[IRI], Literal]
  val LangTag : Isomorphic1[String, LangTag]

  // val StringDatatype = IRI("http://www.w3.org/2001/XMLSchema#string")
  // val IntegerDatatype = IRI("http://www.w3.org/2001/XMLSchema#integer")
  // val FloatDatatype = IRI("http://www.w3.org/2001/XMLSchema#float")
  // val DateDatatype = IRI("http://www.w3.org/2001/XMLSchema#date")
  // val DateTimeDatatype = IRI("http://www.w3.org/2001/XMLSchema#dateTime")

}




