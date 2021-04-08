import java.io.File


// Пусть к папке с файлами
const val PATH = "D:\\IdeaProjects\\Algorithms\\QuickSortKiller\\src\\"

// Названия файлов
const val INPUT_NAME = "input.txt"
const val OUTPUT_NAME = "output.txt"

fun main() {
    val fileInput = File(PATH + INPUT_NAME)
    val fileOutputWriter = File(PATH + OUTPUT_NAME).writer()

    for (line in fileInput.readLines()) {
        if (line.isNotBlank()) {
            val n = line.toInt()
            val array = quickSortKillerArray(n)
            array.forEach {
                fileOutputWriter.write(it.toString())
                fileOutputWriter.append(' ')
            }
        }
    }
    fileOutputWriter.close()
}

fun quickSortKillerArray(n: Int): Array<Int> {
    val array = Array<Int>(n) {
        it + 1
    }
    for (i in 2 until array.size) {
        val tmp = array[i]
        array[i] = array[i / 2]
        array[i / 2] = tmp
    }
    return array
}