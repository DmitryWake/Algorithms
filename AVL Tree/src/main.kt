import java.io.File


// Пусть к папке с файлами
const val PATH = "D:\\IdeaProjects\\Algorithms\\AVL Tree\\src\\"

// Названия файлов
const val INPUT_NAME = "input.txt"
const val OUTPUT_NAME = "output.txt"

fun main() {

    val avlTree = AVLTree()

    val fileInput = File(PATH + INPUT_NAME)
    val fileOutputWriter = File(PATH + OUTPUT_NAME).writer()

    for (line in fileInput.readLines()) {
        when (line.first()) {
            '+' -> {
                val x = line.split(' ').last().toInt()
                avlTree.insert(x)
                fileOutputWriter.write(avlTree.getBalance().toString())
                fileOutputWriter.appendLine()
            }
            '-' -> {
                val x = line.split(' ').last().toInt()
                avlTree.delete(x)
                fileOutputWriter.write(avlTree.getBalance().toString())
                fileOutputWriter.appendLine()
            }
            '?' -> {
                val x = line.split(' ').last().toInt()
                fileOutputWriter.write(avlTree.search(x).toString())
                fileOutputWriter.appendLine()
            }
        }
    }

    fileOutputWriter.close()
}
