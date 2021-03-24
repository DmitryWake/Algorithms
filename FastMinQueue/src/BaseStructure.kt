interface BaseStructure<T> {
    fun insert(elem: T)
    fun delete(): T
    fun findMin(): T
    fun isEmpty(): Boolean
    fun size(): Int
}