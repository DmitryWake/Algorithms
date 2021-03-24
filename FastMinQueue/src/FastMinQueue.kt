class FastMinQueue : BaseStructure<Double> {

    private val pairStack = Pair(FastMinStack(), FastMinStack())

    override fun insert(elem: Double) {
        pairStack.first.insert(elem)
    }

    override fun delete(): Double {
        if (pairStack.second.isEmpty()) {
            while (!pairStack.first.isEmpty()) {
                val elem = pairStack.first.delete()
                pairStack.second.insert(elem)
            }
        }
        return pairStack.second.delete()
    }

    override fun findMin(): Double = minOf(pairStack.first.findMin(), pairStack.second.findMin())

    override fun isEmpty(): Boolean = pairStack.first.isEmpty() && pairStack.second.isEmpty()

    override fun size(): Int = pairStack.first.size() + pairStack.second.size()
}