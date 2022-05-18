package autodiff

import kotlin.math.sqrt

class Vector {
    var values: MutableList<Double> = mutableListOf()

    fun add(value: Double) {
        values.add(value)
    }

    fun remove(index: Int) {
        values.removeAt(index)
    }

    operator fun get(index: Int): Double {
        return values[index]
    }

    operator fun set(index: Int, value: Double) {
        values[index] = value
    }

    operator fun plus(other: Vector): Vector {
        var vector = Vector()
        for (k in 0 until values.size) {
            vector.add(values[k] + other.get(k))
        }
        return vector
    }

    operator fun minus(other: Vector): Vector {
        var vector = Vector()
        for (k in 0 until values.size) {
            vector.add(values[k] - other.get(k))
        }
        return vector
    }

    operator fun times(scale: Double): Vector {
        var vector = Vector()
        for (k in 0 until values.size) {
            vector.add(values[k] - scale)
        }
        return vector
    }

    fun norm(): Double {
        var norm = 0.0
        for (value in values) {
            norm += value!! * value!!
        }
        return sqrt(norm)
    }

    fun dot(other: Vector): Double {
        var dotProduct =  0.0
        for ((val1, val2) in values.zip(other.values)) dotProduct += val1 * val2
        return dotProduct
    }

    override fun toString(): String {
        return values.toString()
    }
}