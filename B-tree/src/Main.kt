import java.io.File

// Пусть к папке с файлами
const val PATH = "D:\\IdeaProjects\\Algorithms\\B-tree\\src\\"

// Названия файлов
const val INPUT_NAME = "input.txt"
const val OUTPUT_NAME = "output.txt"

// Минимальную степень обычно выбирают в зависимости от оперативной памяти
const val MINIMUM_DEGREE = 30

fun main() {
    val tree = BTree(MINIMUM_DEGREE)

    val fileInput = File(PATH + INPUT_NAME)
    val fileOutputWriter = File(PATH + OUTPUT_NAME).writer()


    for (line in fileInput.readLines()) {
        when (line.first()) {
            '+' -> {
                val x = line.split(' ').last().toInt()
                tree.insert(x)
            }
            '-' -> {
                val x = line.split(' ').last().toInt()
                tree.remove(x)
            }
            '?' -> {
                val x = line.split(' ').last().toInt()
                fileOutputWriter.write(tree.find(x).toString())
                fileOutputWriter.appendLine()
            }
        }
    }

    fileOutputWriter.close()
}