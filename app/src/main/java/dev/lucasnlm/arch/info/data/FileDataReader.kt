package dev.lucasnlm.arch.info.data

import java.io.File

class FileDataReader : DataReader {
    override fun read(sourceFile: String): String = File(sourceFile).readText()
}
