//package autodiff
//
//import kotlin.math.abs
//
//fun main(args: Array<String>) {
//    val x = Variable()
//    val f = Function(listOf(x), listOf(x*x*x - x*x))
//    x.x = 7.0
//    println("Starting value: ${x.x}")
//    var time = System.currentTimeMillis()
//    var iterating = true
//    var iterations = 0
//    var multiplier = .02
//    var lastDeriv = 0.0
//    while (iterating) {
//        iterations++
//        var derivative = f.forwardAutoDiff(x)[0]
//        if (abs(derivative) >= abs(derivative)) {
//            multiplier *= 1
//        }
//        lastDeriv = derivative
//        x.x -= multiplier * derivative
//        println("$iterations: ${x.x}")
//        if (abs(derivative) < .00005) {
//            iterating = false
//        }
//    }
//    time = System.currentTimeMillis() - time
//    println("Finished in $time")
//}