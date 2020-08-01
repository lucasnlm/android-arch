package dev.lucasnlm.arch.info.data

interface DataReader {
    fun read(sourceFile: String): String
}
