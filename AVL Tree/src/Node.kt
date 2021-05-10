class Node(var data: Int) {
    var leftNode: Node? = null
    var rightNode: Node? = null
    var height = 0

    override fun toString(): String {
        return "" + data
    }
}