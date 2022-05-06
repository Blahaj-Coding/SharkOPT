package autodiff
fun main(args: Array<String>) {
    val x0 =  Variable("x0")
    val x1 =  Variable("x1")
    val x2 =  Variable("x2")
    val f0 = x0/x1 - x2/x0
    val f1 = ((x0 + x1) * (x1 - x2)) / (x1*x1)
    val f2 = f0 + f1
    println(f2)
    var mapping = HashMap<Variable, Double>()
    mapping.put(x0, 1.0)
    mapping.put(x1, 2.0)
    mapping.put(x2, 1.0)
    println(f0.evaluate(mapping))
    println(f1.evaluate(mapping))
    println(f2.evaluate(mapping))
//    println(f.forwardAutoDiff(x0))
//    println(f.forwardAutoDiff(x1))
//    println(f.forwardAutoDiff(x2))
//    println()
//    println(f.reverseAutoDiff(f0))
//    println(f.reverseAutoDiff(f1))
//    println(f.reverseAutoDiff(f2))
//    f.printTable()
}