class HashTable(private var size: Int = 16) {

    private var table = arrayOfNulls<HashTableEntry>(size)

    /**
     * Количество элементов
     */
    private var count = 0

    /**
     * Вставка элементов в таблицу
     */
    fun insert(key: Int) {
        val hash = hash(key)
        val entry = HashTableEntry(key)

        if (table[hash] == null) {
            table[hash] = entry
        } else {
            var tmp = table[hash]!!
            if (tmp.value == key) return
            while (tmp.next != null) {
                tmp = tmp.next!!
                if (tmp.value == key) return
            }
            tmp.next = entry
        }
    }

    fun find(key: Int): Boolean {
        val hash = hash(key)

        val entry = table[hash]

        when {
            entry == null -> {
                return false
            }
            entry.value == key -> {
                return true
            }
            entry.next != null -> {
                var tmp = entry.next
                while (tmp != null) {
                    if (tmp.value == key)
                        return true
                    tmp = tmp.next
                }
                return false
            }
            else -> {
                return false
            }
        }
    }

    fun remove(key: Int) {
        val hash = hash(key)
        if (table[hash] == null) {
            return
        }
        if (table[hash] != null && table[hash]!!.value == key) {
            table[hash] = table[hash]!!.next
            return
        }

        var temp = table[hash]
        var prevTemp = table[hash]
        while (temp != null) {
            if (temp.value == key) {
                prevTemp!!.next = temp.next
                return
            }
            prevTemp = temp
            temp = temp.next
        }
    }


    private fun hash(value: Int) = value % size
}