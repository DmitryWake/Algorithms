class BTree(private var MinDeg: Int) {

    private var root: BTreeNode? = null

    /**
     * Поиск ключа в дереве
     */
    fun find(key: Int): Boolean {
        return root?.search(key) != null
    }

    /**
     * Вставка ключа в дерево
     */
    fun insert(key: Int) {
        if (root == null) {
            root = BTreeNode(MinDeg, true)
            root!!.keys[0] = key
            root!!.count = 1
        } else if (!find(key)) {
            // Когда узел заполнен
            if (root!!.count == 2 * MinDeg - 1) {
                val s = BTreeNode(MinDeg, false)

                s.children[0] = root
                s.splitChild(0, root)

                var i = 0
                if (s.keys[0] < key) {
                    i++
                }

                s.children[i]!!.insertNotFull(key)
                root = s
            } else {
                root!!.insertNotFull(key)
            }
        }
    }

    /**
     * Удаление ключа из дерева
     */
    fun remove(key: Int) {
        if (root == null) {
            return
        }
        root!!.remove(key)
        if (root!!.count == 0) {
            root = if (root!!.isLeaf) {
                null
            } else {
                root!!.children[0]
            }
        }
    }
}