package model

import java.util.*

class FastMinStack : BaseStructure<Long> {

    private val stack: Stack<Pair<Long, Long>> = Stack()

    override fun insert(elem: Long) {
        val min = if (stack.isEmpty()) {
            elem
        } else {
            minOf(elem, stack.peek().second)
        }
        stack.push(Pair(elem, min))
    }

    override fun delete(): Long = stack.pop().first

    override fun findMin(): Long = stack.peek().second

    override fun isEmpty(): Boolean = stack.isEmpty()

    override fun size(): Int = stack.size
}
