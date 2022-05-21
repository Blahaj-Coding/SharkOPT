package autodiff

import autodiff.solver.constrained.PenaltyMethod
import autodiff.solver.unconstrained.GradientDescent
import org.ejml.simple.SimpleMatrix

fun main(args: Array<String>) {
    val N = 10
    val T = Variable()
    var distances = mutableListOf<Variable>()
    var velocities = mutableListOf<Variable>()
    var accelerations = mutableListOf<Variable>()
    for (k in 1..N) {
        distances.add(Variable())
        velocities.add(Variable())
        accelerations.add(Variable())
    }
    val solver = PenaltyMethod()
    solver.subjectTo(distances[0].isEqualTo(0))
    solver.subjectTo(distances[N-1].isEqualTo(10))
    solver.subjectTo((T*-1).isLessThan(0))
    for (k in 0 until N-1) {
        solver.subjectTo((distances[k+1]).isEqualTo(distances[k] + velocities[k] * T))
        solver.subjectTo((velocities[k+1]).isEqualTo(velocities[k] + accelerations[k] * T))
    }
    for (k in 0..N-1) {
        solver.subjectTo(velocities[k].isLessThan(10))
        solver.subjectTo(accelerations[k].isLessThan(10))
        solver.subjectTo((accelerations[k] * -1).isLessThan(10))
    }

    solver.minimize(T)
    println(solver.solve())
}