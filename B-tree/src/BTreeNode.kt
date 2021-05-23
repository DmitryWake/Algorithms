class BTreeNode(private var minDegree: Int, var isLeaf: Boolean) {

    var keys: IntArray = IntArray(2 * minDegree - 1)
    var children: Array<BTreeNode?> = arrayOfNulls(2 * minDegree)

    /**
     * Количество активных ключей
     */
    var count: Int = 0

    fun getElements(): IntArray {
        return keys.copyOf(count)
    }

    /**
     * Удаление ключа из узла
     */
    fun remove(key: Int) {
        val index = findKey(key)

        if (index < count && keys[index] == key) {
            if (isLeaf) {
                removeFromLeafNode(index)
            } else {
                removeFromNode(index)
            }
        } else if (isLeaf) {
            // Ключа нет в дереве
            return
        } else {
            val flag = index == count
            if (children[index]!!.count < minDegree) {
                fill(index)
            }
            // Когда было слияние в последнем узле - вызываем рекурсию для дочернего узла index - 1
            // А иначе для узла index, так как до этого мы заполняли его до minDegree
            if (flag && index > count) {
                children[index - 1]!!.remove(key)
            } else {
                children[index]!!.remove(key)
            }
        }
    }

    fun insertNotFull(key: Int) {
        var i = count - 1
        if (isLeaf) {
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i]
                i--
            }

            keys[i + 1] = key
            count += 1
        } else {
            while (i >= 0 && keys[i] > key) {
                i--
            }

            if (children[i + 1]!!.count == 2 * minDegree - 1) {
                splitChild(i + 1, children[i + 1])
                if (keys[i + 1] < key) {
                    i++
                }
            }

            children[i + 1]!!.insertNotFull(key)
        }
    }

    fun splitChild(i: Int, y: BTreeNode?) {

        val z = BTreeNode(y!!.minDegree, y.isLeaf)
        z.count = minDegree - 1

        for (j in 0 until minDegree - 1) {
            z.keys[j] = y.keys[j + minDegree]
        }

        if (!y.isLeaf) {
            for (j in 0 until minDegree) {
                z.children[j] = y.children[j + minDegree]
            }
        }
        y.count = minDegree - 1

        // Вставляем нового ребенка в текущего
        for (j in count downTo i + 1) {
            children[j + 1] = children[j]
        }
        children[i + 1] = z

        for (j in count - 1 downTo i) {
            keys[j + 1] = keys[j]
        }
        keys[i] = y.keys[minDegree - 1]

        count += 1
    }

    fun search(key: Int): BTreeNode? {
        var i = 0
        while (i < count && key > keys[i]) {
            i++
        }
        return when {
            keys[i] == key -> {
                this
            }
            isLeaf -> {
                null
            }
            else -> {
                children[i]!!.search(key)
            }
        }
    }

    private fun merge(index: Int) {
        val child = children[index]
        val sibling = children[index + 1]

        child!!.keys[minDegree - 1] = keys[index]

        for (i in 0 until sibling!!.count) {
            child.keys[i + minDegree] = sibling.keys[i]
        }

        if (!child.isLeaf) {
            for (i in 0..sibling.count) {
                child.children[i + minDegree] = sibling.children[i]
            }
        }

        for (i in index + 1 until count) {
            keys[i - 1] = keys[i]
        }

        for (i in index + 2..count) {
            children[i - 1] = children[i]
        }

        child.count += sibling.count + 1
        count--
    }

    /**
     * Метод, забирающий ключ из children[index+1] узла
     */
    private fun takeKeyFromNext(index: Int) {
        val child = children[index]
        val sibling = children[index + 1]

        child!!.keys[child.count] = keys[index]
        if (!child.isLeaf) {
            child.children[child.count + 1] = sibling!!.children[0]
        }

        keys[index] = sibling!!.keys[0]
        for (i in 1 until sibling.count) {
            sibling.keys[i - 1] = sibling.keys[i]
        }

        if (!sibling.isLeaf) {
            for (i in 1..sibling.count) sibling.children[i - 1] = sibling.children[i]
        }

        child.count += 1
        sibling.count -= 1
    }

    /**
     * Метод, забирающий ключ из children[index-1] узла
     */
    private fun takeKeyFromPrevious(index: Int) {
        val child = children[index]
        val sibling = children[index - 1]

        // Число соседних элементов уменьшается на 1, а детей увеличивается на 1
        for (i in child!!.count - 1 downTo 0) {
            child.keys[i + 1] = child.keys[i]
        }

        if (!child.isLeaf) {
            for (i in child.count downTo 0) {
                child.children[i + 1] = child.children[i]
            }
        }

        child.keys[0] = keys[index - 1]
        if (!child.isLeaf) {
            child.children[0] = sibling!!.children[sibling.count]
        }

        // Перемещаем последний ключ соседнего узла на место последнего ключа текущего
        keys[index - 1] = sibling!!.keys[sibling.count - 1]
        child.count += 1
        sibling.count -= 1
    }

    /**
     * Удаление из узла-листа
     */
    private fun removeFromLeafNode(index: Int) {
        for (i in index + 1 until count) {
            keys[i - 1] = keys[i]
        }
        count--
    }

    /**
     * Удаление из обычного узла
     */
    private fun removeFromNode(index: Int) {
        val key = keys[index]

        when {
            children[index]!!.count >= minDegree -> {
                val prev = getPrevious(index)
                keys[index] = prev
                children[index]!!.remove(prev)
            }
            children[index + 1]!!.count >= minDegree -> {
                val next = getNext(index)
                keys[index] = next
                children[index + 1]!!.remove(next)
            }
            else -> {
                merge(index)
                children[index]!!.remove(key)
            }
        }
    }

    /**
     * Самый правый узел из левого под-дерева
     */
    private fun getPrevious(index: Int): Int {
        var current = children[index]
        while (!current!!.isLeaf) {
            current = current.children[current.count]
        }
        return current.keys[current.count - 1]
    }

    /**
     * Находим след. узел следуя от правого под-дерево налево
     */
    private fun getNext(index: Int): Int {
        var current = children[index + 1]
        while (!current!!.isLeaf) {
            current = current.children[0]
        }
        return current.keys[0]
    }

    /**
     * Метод заполняющий дочерний узел
     */
    private fun fill(index: Int) {
        if (index != 0 && children[index - 1]!!.count >= minDegree) {
            takeKeyFromPrevious(index)
        } else if (index != count && children[index + 1]!!.count >= minDegree) {
            takeKeyFromNext(index)
        } else {
            if (index != count) {
                merge(index)
            } else {
                merge(index - 1)
            }
        }
    }

    /**
     * Метод который ищет индекс при котором:
     * значение >= key
     * (используется для поиска ключа в дереве)
     */
    private fun findKey(key: Int): Int {
        var index = 0
        while (index < count && keys[index] < key) {
            ++index
        }
        return index
    }
}