package org.w3.rdf

import org.w3.isomorphic._

trait ScalaModel extends Model {

  case class IRI(iri:String) { override def toString = '"' + iri + '"' }
  object IRI extends Isomorphic1[String, IRI]

  case class Graph(triples:Set[Triple]) extends GraphLike {
    def iterator = triples.iterator
    def ++(other:Graph):Graph = Graph(triples ++ other.triples)
  }
  object Graph extends GraphObject {
    def empty:Graph = Graph(Set[Triple]())
    def apply(elems:Triple*):Graph = Graph(Set[Triple](elems:_*))
    def apply(it:Iterable[Triple]):Graph = Graph(it.toSet)
  }

  case class Triple (s:Subject, p:Predicate, o:Object)
  object Triple extends Isomorphic3[Subject, Predicate, Object, Triple]

  case class BNode(label:String)
  object BNode extends Isomorphic1[String, BNode]

  sealed trait Node
  case class NodeIRI(i:IRI) extends Node
  object NodeIRI extends Isomorphic1[IRI, NodeIRI]
  case class NodeBNode(b:BNode) extends Node
  object NodeBNode extends Isomorphic1[BNode, NodeBNode]

  sealed trait Subject
  case class SubjectNode(n:Node) extends Subject
  object SubjectNode extends Isomorphic1[Node, SubjectNode]

  sealed trait Predicate
  case class PredicateIRI(i:IRI) extends Predicate
  object PredicateIRI extends Isomorphic1[IRI, PredicateIRI]

  sealed trait Object
  case class ObjectNode(n:Node) extends Object
  object ObjectNode extends Isomorphic1[Node, ObjectNode]
  case class ObjectLiteral (n:Literal) extends Object
  object ObjectLiteral extends Isomorphic1[Literal, ObjectLiteral]

  case class Literal(lexicalForm: String, langtag: Option[LangTag], datatype: Option[IRI])
  object Literal extends Isomorphic3[String, Option[LangTag], Option[IRI], Literal]
  
  case class LangTag(s:String)
  object LangTag extends Isomorphic1[String, LangTag]

}

object ScalaModel extends ScalaModel
