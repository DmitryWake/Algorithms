import java.io.File

// Пусть к папке с файлами
const val PATH = "D:\\IdeaProjects\\Algorithms\\RadixSort\\src\\"

// Названия файлов
const val INPUT_NAME = "input.txt"
const val OUTPUT_NAME = "output.txt"

val fileInput = File(PATH + INPUT_NAME)
val fileOutputWriter = File(PATH + OUTPUT_NAME).writer()

fun main() {
    var inputArray: Array<Array<Char>>? = null
    var n = 0
    var m = 0
    var k = 0

    for ((currentLineNumber, line) in fileInput.readLines().withIndex()) {
        when (currentLineNumber) {
            0 -> {
                val inputs = line.split(' ')
                n = inputs.first().toInt()
                m = inputs[1].toInt()
                k = inputs.last().toInt()
            }
            1 -> {
                inputArray = Array(m) {
                    Array(n) { index ->
                        if (it == 0) {
                            line[index]
                        } else {
                            '0'
                        }
                    }
                }
            }
            else -> {
                inputArray!![currentLineNumber - 1].forEachIndexed { index, c ->
                    inputArray[currentLineNumber - 1][index] = line[index]
                }
            }
        }
    }

    val list = mutableListOf<String>()

    for (i in 0 until n) {
        var string = ""
        for (j in 0 until m) {
            string += inputArray!![j][i]
        }
        list.add(string)
    }

    val result = radixSort(list, k, m)

    result.forEach { string ->
        fileOutputWriter.write((list.indexOf(string) + 1).toString())
        fileOutputWriter.append(' ')
    }

    fileOutputWriter.close()
}

fun radixSort(list: MutableList<String>, k: Int, maxLength: Int): MutableList<String> {

    if (k == 0 || list.isEmpty()) {
        return list
    }

    var arrayTo = Array(256) { mutableListOf<String>() }
    var arrayFrom = Array(256) { mutableListOf<String>() }

    for (i in 1..k) {

        when (i) {
            // Формируем массив
            1 -> {
                list.forEach {
                    val char = it.last()
                    arrayTo[char - 0.toChar()].add(it)
                }
                arrayFrom = arrayTo
                arrayTo = Array(256) { mutableListOf() }
            }
            // Если i > максимальной длины строки из массива, то завершаем цикл, так как массив уже отсортирован
            maxLength + 1 -> {
                break
            }
            else -> {
                arrayFrom.forEach {
                    it.forEach { string ->
                        val char = if (i > string.length) {
                            0.toChar()
                        } else {
                            string[string.length - i]
                        }
                        arrayTo[char - 0.toChar()].add(string)
                    }
                }
                arrayFrom = arrayTo
                arrayTo = Array(256) { mutableListOf() }
            }
        }
    }

    val result = mutableListOf<String>()
    arrayFrom.forEach {
        it.forEach { string ->
            result.add(string)
        }
    }

    return result
}