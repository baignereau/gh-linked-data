package org.w3.rdf.jena

import org.w3.rdf._
import com.hp.hpl.jena.graph.{Graph => JenaGraph, Triple => JenaTriple, Node => JenaNode, _}
import com.hp.hpl.jena.rdf.model.{AnonId}
import com.hp.hpl.jena.datatypes.{RDFDatatype, TypeMapper}

import org.w3.isomorphic._

object JenaModel extends Model {

  case class IRI(iri: String) { override def toString = '"' + iri + '"' }
  object IRI extends AlgebraicDataType1[String, IRI]

  class Graph(val jenaGraph: JenaGraph) extends GraphLike {
    def iterator: Iterator[Triple] = new Iterator[Triple] {
      val iterator = jenaGraph.find(JenaNode.ANY, JenaNode.ANY, JenaNode.ANY)
      def hasNext = iterator.hasNext
      def next = iterator.next
    }
    def ++(other: Graph):Graph = {
      val g = Factory.createDefaultGraph
      iterator foreach { t => g add t }
      other.iterator foreach { t => g add t }
      new Graph(g)
    }

    override def equals(o: Any): Boolean =
      ( o.isInstanceOf[Graph] && jenaGraph.isIsomorphicWith(o.asInstanceOf[Graph].jenaGraph) )

  }

  object Graph extends GraphObject {
    def fromJena(jenaGraph: JenaGraph): Graph = new Graph(jenaGraph)
    def empty: Graph = new Graph(Factory.createDefaultGraph)
    def apply(elems: Triple*): Graph = apply(elems.toIterable)
    def apply(it: Iterable[Triple]):Graph = {
      val jenaGraph = Factory.createDefaultGraph
      it foreach { t => jenaGraph add t }
      new Graph(jenaGraph)
    }
  }

  type Triple = JenaTriple
  object Triple extends AlgebraicDataType3[Subject, Predicate, Object, Triple] {
    def apply(s: Subject, p: Predicate, o: Object): Triple = JenaTriple.create(s, p, o)
    def unapply(t: Triple): Option[(Subject, Predicate, Object)] = Some((t.getSubject, t.getPredicate, t.getObject))
  }

  type BNode = JenaNode
  object BNode extends AlgebraicDataType1[String, BNode] {
    def apply(label: String): BNode = { val id = AnonId.create(label) ; JenaNode.createAnon(id).asInstanceOf[Node_Blank] }
    def unapply(bn: BNode): Option[String] = if (bn.isBlank) Some(bn.getBlankNodeId.getLabelString) else None
  }

  type Node = JenaNode
  type NodeIRI = Node
  object NodeIRI extends AlgebraicDataType1[IRI, NodeIRI] {
    def apply(iri: IRI): NodeIRI = { val IRI(s) = iri ; JenaNode.createURI(s).asInstanceOf[Node_URI] }
    def unapply(node: NodeIRI): Option[IRI] = if (node.isURI) Some(IRI(node.getURI)) else None
  }
  type NodeBNode = JenaNode
  object NodeBNode extends AlgebraicDataType1[BNode, NodeBNode] {
    def apply(node: BNode):NodeBNode = node
    def unapply(node: NodeBNode): Option[BNode] = if (node.isBlank) Some(node) else None
  }

  type Subject = JenaNode
  type SubjectNode = JenaNode
  object SubjectNode extends AlgebraicDataType1[Node, SubjectNode] {
    def apply(node: Node): SubjectNode = node
    def unapply(node: SubjectNode): Option[Node] = Some(node)
  }

  type Predicate = JenaNode
  type PredicateIRI = JenaNode
  object PredicateIRI extends AlgebraicDataType1[IRI, PredicateIRI] {
    def apply(iri: IRI): PredicateIRI = { val IRI(s) = iri ; JenaNode.createURI(s) }
    def unapply(node: PredicateIRI): Option[IRI] = if (node.isURI) Some(IRI(node.getURI)) else None
  }

  type Object = JenaNode
  type ObjectNode = JenaNode
  object ObjectNode extends AlgebraicDataType1[Node, ObjectNode] {
    def apply(node: Node): ObjectNode = node
    def unapply(node: ObjectNode): Option[Node] =
      if (node.isURI || node.isBlank) Some(node) else None
  }
  type ObjectLiteral = JenaNode
  object ObjectLiteral extends AlgebraicDataType1[Literal, ObjectLiteral] {
    def apply(literal: Literal): ObjectLiteral = literal
    def unapply(node: ObjectLiteral): Option[Literal] =
      if (node.isLiteral) Some(node.asInstanceOf[Node_Literal]) else None
  }

  lazy val mapper = TypeMapper.getInstance
  
  type Literal = JenaNode
  object Literal extends AlgebraicDataType3[String, Option[LangTag], Option[IRI], Literal] {
    def apply(lit: String, langtagOption: Option[LangTag], datatypeOption: Option[IRI]): Literal = {
      JenaNode.createLiteral(
        lit,
        langtagOption map { _.s } getOrElse null,
        datatypeOption map { i => mapper.getTypeByName(i.iri) } getOrElse null
      ).asInstanceOf[Literal]
    }
    def unapply(literal: Literal): Option[(String, Option[LangTag], Option[IRI])] =
      if (literal.isLiteral)
        Some((
          literal.getLiteralLexicalForm.toString,
          { val l = literal.getLiteralLanguage; if (l != "") Some(LangTag(l)) else None },
          Option(literal.getLiteralDatatype).map{typ => IRI(typ.getURI)}))
      else
        None
  }
  
  case class LangTag(s: String)
  object LangTag extends AlgebraicDataType1[String, LangTag]

}
