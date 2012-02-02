package org.w3.rdf

import org.w3.isomorphic._

object ScalaModel extends Model {

  case class IRI(iri:String) { override def toString = '"' + iri + '"' }
  object IRI extends AlgebraicDataType1[String, IRI]

  case class Graph(triples:Set[Triple]) extends GraphLike {
    def iterator = triples.iterator
    def ++(other: Graph): Graph = Graph(triples ++ other.triples)
  }
  object Graph extends GraphObject {
    def empty: Graph = Graph(Set[Triple]())
    def apply(elems: Triple*): Graph = Graph(Set[Triple](elems:_*))
    def apply(it: Iterable[Triple]): Graph = Graph(it.toSet)
  }

  case class Triple (s: Subject, p: Predicate, o: Object)
  object Triple extends AlgebraicDataType3[Subject, Predicate, Object, Triple]

  case class BNode(label: String)
  object BNode extends AlgebraicDataType1[String, BNode]

  sealed trait Node
  case class NodeIRI(i: IRI) extends Node
  object NodeIRI extends AlgebraicDataType1[IRI, NodeIRI]
  case class NodeBNode(b: BNode) extends Node
  object NodeBNode extends AlgebraicDataType1[BNode, NodeBNode]

  sealed trait Subject
  case class SubjectNode(n: Node) extends Subject
  object SubjectNode extends AlgebraicDataType1[Node, SubjectNode]

  sealed trait Predicate
  case class PredicateIRI(i: IRI) extends Predicate
  object PredicateIRI extends AlgebraicDataType1[IRI, PredicateIRI]

  sealed trait Object
  case class ObjectNode(n: Node) extends Object
  object ObjectNode extends AlgebraicDataType1[Node, ObjectNode]
  case class ObjectLiteral(n: Literal) extends Object
  object ObjectLiteral extends AlgebraicDataType1[Literal, ObjectLiteral]

  case class Literal(lexicalForm: String, langtag: Option[LangTag], datatype: Option[IRI])
  object Literal extends AlgebraicDataType3[String, Option[LangTag], Option[IRI], Literal]
  
  case class LangTag(s: String)
  object LangTag extends AlgebraicDataType1[String, LangTag]

}
