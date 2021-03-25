package main

import model.FastMinQueue
import java.io.File

// Пусть к папке с файлами
const val PATH = "C:\\Users\\ADMIN\\Desktop\\Учеба\\2 курс\\Algorithms\\FastMinQueue\\src\\"

// Названия файлов
const val INPUT_NAME = "input.txt"
const val OUTPUT_NAME = "output.txt"

fun main() {

    val minStack = FastMinQueue()

    val fileInput = File(PATH + INPUT_NAME)
    val fileOutputWriter = File(PATH + OUTPUT_NAME).writer()

    for (line in fileInput.readLines()) {
        when (line.first()) {
            '+' -> {
                val x = line.split(' ').last().toLong()
                minStack.insert(x)
            }
            '-' -> {
                minStack.delete()
            }
            '?' -> {
                fileOutputWriter.write(minStack.findMin().toString())
                fileOutputWriter.appendLine()
            }
        }
    }

    fileOutputWriter.close()
}