import java.util.*

class FastMinStack : BaseStructure<Double> {

    private val stack: Stack<Pair<Double, Double>> = Stack()

    override fun insert(elem: Double) {
        val min = if (stack.isEmpty()) {
            elem
        } else {
            minOf(elem, stack.peek().second)
        }
        stack.push(Pair(elem, min))
    }

    override fun delete(): Double = stack.pop().first

    override fun findMin(): Double = stack.peek().second

    override fun isEmpty(): Boolean = stack.isEmpty()

    override fun size(): Int = stack.size
}
