package model

class FastMinQueue : BaseStructure<Long> {

    private val pairStack = Pair(FastMinStack(), FastMinStack())

    override fun insert(elem: Long) {
        pairStack.first.insert(elem)
    }

    override fun delete(): Long {
        if (pairStack.second.isEmpty()) {
            while (!pairStack.first.isEmpty()) {
                val elem = pairStack.first.delete()
                pairStack.second.insert(elem)
            }
        }
        return pairStack.second.delete()
    }

    override fun findMin(): Long = if (!pairStack.first.isEmpty() && !pairStack.second.isEmpty()) {
        minOf(pairStack.first.findMin(), pairStack.second.findMin())
    } else if (!pairStack.first.isEmpty()) {
        pairStack.first.findMin()
    } else {
        pairStack.second.findMin()
    }

    override fun isEmpty(): Boolean = pairStack.first.isEmpty() && pairStack.second.isEmpty()

    override fun size(): Int = pairStack.first.size() + pairStack.second.size()
}