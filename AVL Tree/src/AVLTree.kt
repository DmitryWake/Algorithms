import kotlin.math.max

class AVLTree : Tree {
    private var root: Node? = null

    override fun insert(data: Int) {
        root = insert(root, data)
    }

    override fun delete(data: Int) {
        root = delete(root, data)
    }

    override fun search(data: Int): Boolean {
        return search(data, root)
    }

    private fun search(data: Int, node: Node?): Boolean {
        if (node == null) {
            return false
        }
        return when {
            data < node.data -> {
                search(data, node.leftNode)
            }
            data > node.data -> {
                search(data, node.rightNode)
            }
            data == node.data -> {
                true
            }
            else -> {
                false
            }
        }
    }

    private fun insert(node: Node?, data: Int): Node {
        if (node == null) {
            return Node(data)
        }
        when {
            data < node.data -> {
                node.leftNode = insert(node.leftNode, data)
            }
            data > node.data -> {
                node.rightNode = insert(node.rightNode, data)
            }
            else -> {
                return node
            }
        }
        node.height = max(height(node.leftNode), height(node.rightNode)) + 1
        return checkBalanceAndRotate(data, node)
    }

    private fun delete(node: Node?, data: Int): Node? {
        if (node == null) return node
        when {
            data < node.data -> {
                node.leftNode = delete(node.leftNode, data)
            }
            data > node.data -> {
                node.rightNode = delete(node.rightNode, data)
            }
            else -> {
                if (node.leftNode == null && node.rightNode == null) {
                    return null
                }
                if (node.leftNode == null) {
                    return node.rightNode
                } else if (node.rightNode == null) {
                    return node.leftNode
                }
                val tempNode = getPredecessor(node.leftNode)
                node.data = tempNode.data
                node.leftNode = delete(node.leftNode, tempNode.data)
            }
        }
        node.height = max(height(node.leftNode), height(node.rightNode)) + 1

        return checkBalanceAndRotate(node)
    }

    private fun checkBalanceAndRotate(data: Int, node: Node): Node {
        val balance = getBalance(node)

        if (balance > 1 && data < node.leftNode!!.data) {
            return rightRotation(node)
        }

        if (balance < -1 && data > node.rightNode!!.data) {
            return leftRotation(node)
        }

        if (balance > 1 && data > node.leftNode!!.data) {
            node.leftNode = leftRotation(node.leftNode)
            return rightRotation(node)
        }

        if (balance < -1 && data < node.rightNode!!.data) {
            node.rightNode = rightRotation(node.rightNode)
            return leftRotation(node)
        }
        return node
    }

    private fun checkBalanceAndRotate(node: Node): Node {
        val balance = getBalance(node)

        if (balance > 1) {
            if (getBalance(node.leftNode) < 0) {
                node.leftNode = leftRotation(node.leftNode)
            }

            return rightRotation(node)
        }

        if (balance < -1) {
            if (getBalance(node.rightNode) > 0) {
                node.rightNode = rightRotation(node.rightNode)
            }

            return leftRotation(node)
        }
        return node
    }

    private fun rightRotation(node: Node?): Node {
        val newParentNode = node!!.leftNode
        val mid = newParentNode!!.rightNode
        newParentNode.rightNode = node
        node.leftNode = mid
        node.height = max(height(node.leftNode), height(node.rightNode)) + 1
        newParentNode
            .height = max(height(newParentNode.leftNode), height(newParentNode.rightNode)) + 1
        return newParentNode
    }

    private fun leftRotation(node: Node?): Node {
        val newParentNode = node!!.rightNode
        val mid = newParentNode!!.leftNode
        newParentNode.leftNode = node
        node.rightNode = mid
        node.height = max(height(node.leftNode), height(node.rightNode)) + 1
        newParentNode
            .height = max(height(newParentNode.leftNode), height(newParentNode.rightNode)) + 1
        return newParentNode
    }

    fun getBalance(node: Node? = root): Int {
        return if (node == null) {
            0
        } else height(node.leftNode) - height(node.rightNode)
    }

    private fun height(node: Node?): Int {
        return node?.height ?: -1
    }

    private fun getPredecessor(node: Node?): Node {
        var predecessor = node
        while (predecessor!!.rightNode != null) predecessor = predecessor.rightNode
        return predecessor
    }
}