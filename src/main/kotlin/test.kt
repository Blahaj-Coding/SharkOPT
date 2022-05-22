package autodiff

import autodiff.solver.constrained.AugmentedLagrangian
import autodiff.solver.constrained.PenaltyMethod
import org.ejml.data.SingularMatrixException
import org.ejml.simple.SimpleMatrix
import kotlin.random.Random
import kotlin.system.measureNanoTime


fun main(args: Array<String>) {
    val N = 5
    val T = Variable()

    var velocities = mutableListOf<Variable>()
    var accelerations = mutableListOf<Variable>()
    for (k in 1..N) {
        velocities.add(Variable())
        accelerations.add(Variable())
    }
    val solver = AugmentedLagrangian()
    // Boundry constraints
    solver.subjectTo(velocities[0].isEqualTo(0))
    solver.subjectTo(velocities[N - 1].isEqualTo(0))
    // Time must be positive constraint
    solver.subjectTo((T*-1).isLessThan(0))
    // Kinematic constraints
//    for (k in 0 until N - 1) {
//        solver.subjectTo((velocities[k + 1]).isEqualTo(velocities[k] + accelerations[k]))
//    }
    // Path constraints
    for (k in 0 until N) {
        solver.subjectTo(velocities[k].isLessThan(10))
        solver.subjectTo(accelerations[k].isLessThan(10))
        solver.subjectTo((accelerations[k] * Constant(-1.0)).isLessThan(10))
        velocities[k].setInitial(1.0)
        accelerations[k].setInitial(1.0)
    }
    val solution = solver.solve()
    for ((k, variable) in velocities.withIndex()) {
        println("velocity $k: ${solution[variable.index]}")
    }
    for ((k, variable) in accelerations.withIndex()) {
        println("acceleration $k: ${solution[variable.index]}")
    }
    println("Time: ${solution[T.index]}")
//    var y = Variable()
//    x.setInitial(5.0)
//    y.setInitial(2.0)
//    val solver = PenaltyMethod()
//    solver.subjectTo((x*x + y*y).isEqualTo(Constant(1.0)))
//    solver.minimize(x-y)
//    var time = measureNanoTime {
//        println(solver.solve())
//    }
//    println(time/1E9)
}