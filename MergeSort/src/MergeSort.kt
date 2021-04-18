import java.io.File

// Пусть к папке с файлами
const val PATH = "D:\\IdeaProjects\\Algorithms\\MergeSort\\src\\"

// Названия файлов
const val INPUT_NAME = "input.txt"
const val OUTPUT_NAME = "output.txt"

val fileInput = File(PATH + INPUT_NAME)
val fileOutputWriter = File(PATH + OUTPUT_NAME).writer()

val listOfMerge = mutableListOf<String>()

fun mergeSort(list: List<Long>, indexStart: Int, indexEnd: Int): List<Long> {
    if (list.size <= 1) {
        return list
    }

    val middle = list.size / 2
    val left = list.subList(0, middle)
    val right = list.subList(middle, list.size)
    println()

    val result = merge(
        mergeSort(left, indexStart, middle - 1),
        mergeSort(right, middle, indexEnd)
    )

    listOfMerge.add("${indexStart + 1} ${indexEnd + 1} ${list.first()} ${list.last()}")

    return result
}

fun merge(left: List<Long>, right: List<Long>): List<Long> {
    var indexLeft = 0
    var indexRight = 0
    val newList = mutableListOf<Long>()

    while (indexLeft < left.size && indexRight < right.size) {
        if (left[indexLeft] <= right[indexRight]) {
            newList.add(left[indexLeft])
            indexLeft++
        } else {
            newList.add(right[indexRight])
            indexRight++
        }
    }

    while (indexLeft < left.size) {
        newList.add(left[indexLeft])
        indexLeft++
    }

    while (indexRight < right.size) {
        newList.add(right[indexRight])
        indexRight++
    }

    return newList
}

fun main() {
    var isFirstLine = true
    var n = 0
    var array = emptyList<Long>()

    for (line in fileInput.readLines()) {
        if (line.isNotBlank()) {
            if (isFirstLine) {
                n = line.toInt()
                isFirstLine = false
            } else {
                val numbers = line.split(' ')
                array = MutableList(n) {index ->
                    numbers[index].toLong()
                }
            }
        }
    }

    array = mergeSort(array, 0, array.size - 1)



    listOfMerge.forEach {
        fileOutputWriter.write(it)
        fileOutputWriter.appendLine()
    }

    array.forEach {
        fileOutputWriter.write(it.toString())
        fileOutputWriter.append(' ')
    }

    fileOutputWriter.close()
}
