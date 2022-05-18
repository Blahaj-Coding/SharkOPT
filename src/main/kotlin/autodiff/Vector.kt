package autodiff

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

    fun plus(other: Vector): Vector {
        var vector = Vector()
        for (k in 0 until values.size) {
            vector.add(values[k] + other.get(k))
        }
        return vector
    }

    fun minus(other: Vector): Vector {
        var vector = Vector()
        for (k in 0 until values.size) {
            vector.add(values[k] - other.get(k))
        }
        return vector
    }

    override fun toString(): String {
        return values.toString()
    }
}