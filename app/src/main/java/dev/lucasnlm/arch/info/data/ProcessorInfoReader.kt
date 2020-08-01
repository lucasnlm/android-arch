package dev.lucasnlm.arch.info.data

import android.os.Build

open class ProcessorInfoReader {
    open fun getCpuCoresNumber(): Int {
        return Runtime.getRuntime().availableProcessors()
    }

    open fun getPlatformAbi(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Build.SUPPORTED_ABIS.first()
        } else {
            @Suppress("DEPRECATION")
            Build.CPU_ABI
        }
    }
}
