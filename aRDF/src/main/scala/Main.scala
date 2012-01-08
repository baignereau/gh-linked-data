// afaik, the compiler doesn't not expose the unapply method
// for a companion object
trait Isomorphic[A, B] {
  def apply(x: A): B
  def unapply(x: B): Option[A]
}

// abstract module
trait Module {
  type X
  type Y <: X
  def X: Isomorphic[Int, X]
  def Y: Isomorphic[X, Y]
}

// an implementation relying on case classes
object ConcreteModule extends Module {
  sealed trait X { def v: Int = 42 }
  object X extends Isomorphic[Int, X] {
    def apply(_v: Int): X = new X { }
    def unapply(x: X): Option[Int] = Some(x.v)
  }
  case class Y(x: X) extends X
  // I guess the compiler could do that for me
  object Y extends Isomorphic[X, Y]
}

object Main {
  def foo(t: Module)(x: t.X): Unit = {
    import t._
    x match {
      case Y(_y) => println("y "+_y)
      case X(_x) => println("x "+_x)
      case x => println("___ + " + x)
    }
  }
  def bar(t: Module): Unit = {
    import t._
    val x: X = X(42)
    val y: Y = Y(x)
    foo(t)(x)
    foo(t)(y)
  }
  def main(args: Array[String]) = {
    // call bar with the concrete module
    bar(ConcreteModule)
  }
}
