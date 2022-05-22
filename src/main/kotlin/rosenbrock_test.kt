package autodiff

import autodiff.solver.constrained.*
import autodiff.operator.*
import autodiff.Variable
import autodiff.solver.unconstrained.GradientDescent

fun main(args: Array<String>) {
//    val solver = PenaltyMethod()
//    val x = Variable()
//    x.setInitial(1.0)
//    solver.subjectTo((x*x).isLessThan(5))
//    solver.subjectTo((x*x).isEqualTo(0))
//    val solution = solver.solve()
//    println(x.index)
//    println(solution)
    val solver = GradientDescent()
    val x = Variable()
    val y = Variable()
    x.setInitial(1.0)
    y.setInitial(0.0)
    solver.minimize(Power(x*x+y*y-5, 2) + Power(x*x , 4))
    println("x index: ${x.index}")
    println(solver.solve())
}