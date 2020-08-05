package dev.lucasnlm.arch.info.logic

import android.content.Context
import android.content.Intent
import dev.lucasnlm.arch.info.models.DeviceInfo
import java.util.*

class DeviceInfoSharer(
    private val context: Context
) {
    fun share(deviceInfo: DeviceInfo) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"

            val shareBody = mapOf(
                "Model" to deviceInfo.model,
                "Model Name" to deviceInfo.modelName,
                "Vendor" to deviceInfo.vendor,
                "Revision" to deviceInfo.revision,
                "Architecture" to deviceInfo.architecture,
                "CPU Cores" to deviceInfo.cpuCores.toString(),
                "Max Clock" to deviceInfo.clockInfo.maxClock.toString(),
                "Min Clock" to deviceInfo.clockInfo.minClock.toString(),
                "Stepping" to deviceInfo.stepping,
                "BogoMips" to deviceInfo.bogoMips,
                "BigLittle" to deviceInfo.bigLittle,
                "ABI" to deviceInfo.abi,
                "Serial" to deviceInfo.serial,
                "Device" to deviceInfo.device,
                "Governor" to deviceInfo.governor,
                "Flags" to
                    deviceInfo.flags.joinToString(",", "[", "]") {
                        it.toUpperCase(Locale.ENGLISH)
                    }
            ).map {
                "${it.key}: ${it.value}"
            }.joinToString("\n")

            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_SUBJECT, "Device Info")
            putExtra(Intent.EXTRA_TEXT, shareBody)
        }

        context.startActivity(intent)
    }
}
