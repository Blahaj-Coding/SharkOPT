//package autodiff
//
//import kotlin.math.max
//import kotlin.math.min
//
//class Function(val variables: List<Variable>, val expressions: List<Variable>) {
//
//    private val table = mutableListOf<Variable>()
//
//    init {
//        constructTable()
//    }
//
//    private var currentNameIndex = 0
//
//    private fun constructTable() {
//        for (expression in expressions) {
//            addChild(expression)
//        }
//    }
//
//    private fun addChild(variable: Variable) {
//        for (child in variable.children) {
//            addChild(child)
//        }
//        if (!table.contains(variable)) {
//            var maxRow = table.size
//            for (parent in variable.parents) {
//                var parentIndex = table.indexOf(parent)
//                if (parentIndex != -1) {
//                    maxRow = min(maxRow, parentIndex)
//                }
//            }
//            var minRow = 0
//            for (child in variable.children) {
//                addChild(child)
//                var childIndex = table.indexOf(child)
//                if (childIndex != -1) {
//                    minRow = max(minRow, childIndex + 1)
//                }
//            }
//            if (maxRow < minRow) {
//                throw RuntimeException("error building table")
//            } else {
//                variable.name = "v${currentNameIndex++}"
//                table.add(maxRow, variable)
//            }
//        }
//    }
//
//    private fun forwardAutoDiff() {
//        for (row in table) {
//            row.updateX()
//            row.updateXDot()
//        }
//    }
//
//    private fun reverseAutoDiff() {
//        reverseAutoDiffForwardPass()
//        reverseAutoDiffReversePass()
//    }
//
//    private fun reverseAutoDiffForwardPass() {
//        for (row in table) {
//            row.updateX()
//        }
//    }
//
//    private fun reverseAutoDiffReversePass() {
//        for (i in (table.size - 1) downTo 0) {
//            table[i].updateXBar()
//        }
//    }
//
//    fun forwardAutoDiff(differentiationVariable: Variable): List<Double> {
//        for (row in table) {
//            row.xDot = 0.0
//        }
//        for (variable in variables) {
//            if (variable == differentiationVariable) {
//                variable.xDot = 1.0
//            }
//        }
//        forwardAutoDiff()
//        val expressionDerivatives = mutableListOf<Double>()
//        for (expression in expressions) {
//            expressionDerivatives.add(expression.xDot)
//        }
//        return expressionDerivatives
//    }
//
//    fun reverseAutoDiff(differentiationExpression: Variable): List<Double> {
//        for (row in table) {
//            row.xBar = 0.0
//        }
//        for (expression in expressions) {
//            if (expression == differentiationExpression) {
//                expression.xBar = 1.0
//            }
//        }
//        reverseAutoDiff()
//        val gradientComponents = mutableListOf<Double>()
//        for (variable in variables) {
//            gradientComponents.add(variable.xBar)
//        }
//        return gradientComponents
//    }
//
//    fun printTable() {
//        for (row in table) {
//            println(row)
//        }
//    }
//}