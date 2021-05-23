import java.io.File

// Пусть к папке с файлами
const val PATH = "D:\\IdeaProjects\\Algorithms\\HashTable\\src\\"

// Названия файлов
const val INPUT_NAME = "input.txt"
const val OUTPUT_NAME = "output.txt"


fun main() {
    val fileInput = File(PATH + INPUT_NAME)
    val fileOutputWriter = File(PATH + OUTPUT_NAME).writer()

    var table: HashTable? = null

    for (line in fileInput.readLines()) {
        when (line.first()) {
            '+' -> {
                val x = line.split(' ').last().toInt()
                table?.insert(x)
            }
            '-' -> {
                val x = line.split(' ').last().toInt()
                table?.remove(x)
            }
            '?' -> {
                val x = line.split(' ').last().toInt()
                table?.let { fileOutputWriter.write(it.find(x).toString()) }
                fileOutputWriter.appendLine()
            }
            else -> {
                val n = line.toInt()
                table = HashTable(n)
            }
        }
    }

    fileOutputWriter.close()
}