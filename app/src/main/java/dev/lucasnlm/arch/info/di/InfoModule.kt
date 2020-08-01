package dev.lucasnlm.arch.info.di

import dev.lucasnlm.arch.R
import dev.lucasnlm.arch.info.models.Localization
import dev.lucasnlm.arch.info.viewmodel.InfoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val LocalizationModule = module {
    single {
        val context = androidContext()
        Localization(
            title = context.getString(R.string.app_name),
            processor = context.getString(R.string.processor),
            vendor = context.getString(R.string.vendor),
            model = context.getString(R.string.model),
            details = context.getString(R.string.details),
            abi = context.getString(R.string.abi),
            cores = context.getString(R.string.cores),
            revision = context.getString(R.string.revision),
            governor = context.getString(R.string.governor),
            clock = context.getString(R.string.clock),
            min = context.getString(R.string.min),
            max = context.getString(R.string.max),
            coreN = context.getString(R.string.core_n),
            graphics = context.getString(R.string.graphics),
            renderer = context.getString(R.string.renderer),
            version = context.getString(R.string.version),
            instructions = context.getString(R.string.instructions)
        )
    }
    viewModel {
        InfoViewModel(get(), get())
    }
}
