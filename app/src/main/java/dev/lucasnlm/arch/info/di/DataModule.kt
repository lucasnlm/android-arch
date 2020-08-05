package dev.lucasnlm.arch.info.di

import dev.lucasnlm.arch.info.data.FileDataReader
import dev.lucasnlm.arch.info.data.ProcessorInfoReader
import dev.lucasnlm.arch.info.repository.DeviceInfoRepository
import org.koin.dsl.module

val DataModule = module {
    single { FileDataReader() }
    single { ProcessorInfoReader() }
    single { DeviceInfoRepository(get(), get()) }
}
