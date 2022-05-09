package autodiff

import autodiff.operator.*

fun main(args: Array<String>) {
    val a =  Variable("a")
    val b =  Variable("b")
    val c =  Variable("c")
//    val f0 = x0/x1 - x2/x0
//    val f1 = ((x0 + x1) * (x1 + x2)) * (x1*x1)
//    val f1 = (a * b - a * c + b * b - b * c) / (b * b)
//    val f1 = (a * b * b * b + a * c * b * Log(Constant(5.0), b) + Power(b, Constant(4.0)) + b * b * b * c)
    val f1 = Log(a, b)
//    val f2 = f0 + f1
    println(f1)
    var mapping = VariableMap()
    mapping.put(a, 3.0)
    mapping.put(b, 2.0)
    mapping.put(c, 1.0)
//    println(f0.evaluate(mapping))
    println(f1.evaluate(mapping))
    var jacobian = f1.solveJacobian(mapping)

    println(jacobian)
//    println(f2.evaluate(mapping))
//    println(f.forwardAutoDiff(x0))
//    println(f.forwardAutoDiff(x1))
//    println(f.forwardAutoDiff(x2))
//    println()
//    println(f.reverseAutoDiff(f0))
//    println(f.reverseAutoDiff(f1))
//    println(f.reverseAutoDiff(f2))
//    f.printTable()
}