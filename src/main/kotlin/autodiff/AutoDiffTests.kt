//package autodiff
//fun main(args: Array<String>) {
//    val x0 =  Variable()
//    val x1 =  Variable()
//    val x2 =  Variable()
//    val f0 = x0/x1 - x2/x0
//    val f1 = ((x0 + x1) * (x1 - x2)) / (x1*x1)
//    val f2 = x2 - x0*x1*x2*x2
//    val f = Function(listOf(x0, x1, x2), listOf(f0, f1, f2))
//    x0.x = 7.0
//    x1.x = -2.0
//    x2.x = 6.0
////    f.printTable()
//    println(f.forwardAutoDiff(x0))
//    println(f.forwardAutoDiff(x1))
//    println(f.forwardAutoDiff(x2))
//    println()
//    println(f.reverseAutoDiff(f0))
//    println(f.reverseAutoDiff(f1))
//    println(f.reverseAutoDiff(f2))
////    f.printTable()
//}