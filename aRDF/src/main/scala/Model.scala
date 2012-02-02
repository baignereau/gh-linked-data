package org.w3.rdf

import org.w3.isomorphic._

trait Model {

  type IRI
  trait GraphLike extends Iterable[Triple] { self =>
    def ++(other: Graph):Graph
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

  val IRI : AlgebraicDataType1[String, IRI]

  trait GraphObject {
    def empty: Graph
    def apply(elems: Triple*): Graph
    def apply(it: Iterable[Triple]): Graph
  }
  val Graph: GraphObject

  val Triple: AlgebraicDataType3[Subject, Predicate, Object, Triple]

  val BNode: AlgebraicDataType1[String, BNode]

  val NodeIRI: AlgebraicDataType1[IRI, NodeIRI]
  val NodeBNode: AlgebraicDataType1[BNode, NodeBNode]

  val SubjectNode: AlgebraicDataType1[Node, SubjectNode]

  val PredicateIRI: AlgebraicDataType1[IRI, PredicateIRI]

  val ObjectNode: AlgebraicDataType1[Node, ObjectNode]
  val ObjectLiteral: AlgebraicDataType1[Literal, ObjectLiteral]

  val Literal: AlgebraicDataType3[String, Option[LangTag], Option[IRI], Literal]
  val LangTag: AlgebraicDataType1[String, LangTag]

}




