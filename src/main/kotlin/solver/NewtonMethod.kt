//package autodiff
//
//import kotlin.math.abs
//
//fun main(args: Array<String>) {
//    val x = Variable()
//    val fEx = x*x*x - x*x
//    val f = Function(listOf(x), listOf(fEx))
//    x.x = 50.0
//    println("Starting value: ${x.x}")
////    var time = System.currentTimeMillis()
//    for (i in 0..50) {
//        var derivative = f.forwardAutoDiff(x)[0]
//        var yVal = fEx.x
//        x.x -= yVal / derivative
//        println("$i: ${x.x}")
//    }
////    time = System.currentTimeMillis() - time
////    println("Finished in $time")
//}