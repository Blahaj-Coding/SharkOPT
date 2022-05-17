package autodiff

class Vector {
    var values: MutableList<Double> = mutableListOf()

    fun add(value: Double) {
        values.add(value)
    }

    fun remove(index: Int) {
        values.removeAt(index)
    }

    fun get(index: Int): Double {
        return values.get(index)
    }

    fun set(index: Int, value: Double) {
        values.set(index, value)
    }

    fun plus(other: Vector): Vector {
        var vector = Vector()
        for (k in 0..(values.size-1)) {
            vector.add(values.get(k) + other.get(k))
        }
        return vector
    }

    fun minus(other: Vector): Vector {
        var vector = Vector()
        for (k in 0..(values.size-1)) {
            vector.add(values.get(k) - other.get(k))
        }
        return vector
    }

    override fun toString(): String {
        return values.toString()
    }
}