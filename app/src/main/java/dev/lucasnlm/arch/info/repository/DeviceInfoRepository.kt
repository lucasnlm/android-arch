package dev.lucasnlm.arch.info.repository

import dev.lucasnlm.arch.info.data.FileDataReader
import dev.lucasnlm.arch.info.data.ProcessorInfoReader
import dev.lucasnlm.arch.info.models.ClockInfo
import dev.lucasnlm.arch.info.models.DeviceInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToInt

class DeviceInfoRepository(
    private val dataReader: FileDataReader,
    private val processorInfoReader: ProcessorInfoReader
) {
    fun readDeviceInfo(): Flow<DeviceInfo> = flow {
        val processorInfo = readProcessorInfo()
        val clockInfo = readClockInfo(processorInfo)

        val result = DeviceInfo(
            model = processorInfo["model", "Processor"] ?: "",
            modelName = processorInfo["model name", "Hardware"] ?: "",
            vendor = processorInfo["vendor_id", "CPU implementer"] ?: "",
            architecture = processorInfo["CPU architecture"] ?: "",
            stepping = processorInfo["stepping"] ?: "",
            clockInfo = clockInfo,
            cpuCores = clockInfo.clocks.size,
            bogoMips = processorInfo["BogoMips", "BogoMIPS", "bogomips"] ?: "",
            abi = processorInfoReader.getPlatformAbi(),
            bigLittle = 0,
            revision = processorInfo["Revision", "CPU revision"] ?: "",
            device = processorInfo["Device"] ?: "",
            serial = processorInfo["Serial"] ?: "",
            flags = parseFlags(processorInfo["flags", "Features"]),
            governor = readCpuGovernor()
        )

        emit(result)
    }

    private fun readClockInfo(propCpuInfo: List<ProcessorInfo>): ClockInfo {
        val coresCount: Int = processorInfoReader.getCpuCoresNumber()
        val clocks = IntArray(coresCount) { 0 }.apply {
            propCpuInfo.mapIndexed { index, processorInfo ->
                this[index] = processorInfo["cpu MHz"]?.toDouble()?.roundToInt()
                    ?: readCurrentCpuClock(index)?.toDoubleOrNull()?.div(1000.0)?.roundToInt() ?: 0
            }
        }.toList()

        val maxClock = (0 until coresCount).mapNotNull {
            readMaxCpuClock(it)?.toDoubleOrNull()?.div(1000.0)?.roundToInt()
        }.max()

        val minClock = (0 until coresCount).mapNotNull {
            readMinCpuClock(it)?.toDoubleOrNull()?.div(1000.0)?.roundToInt()
        }.min()

        return ClockInfo(clocks, maxClock, minClock)
    }

    private fun readCommand(command: String): String = removeTabsAndNewLines(dataReader.read(command))

    private fun readCpuGovernor(): String = readCommand(CPU_GOVERNOR)

    private fun readCurrentCpuClock(cpuNumber: Int): String? = readCommand(makeClockCommand(cpuNumber))

    private fun readMinCpuClock(cpuNumber: Int): String? = readCommand(makeMinClockCommand(cpuNumber))

    private fun readMaxCpuClock(cpuNumber: Int): String? = readCommand(makeMaxClockCommand(cpuNumber))

    private fun parseFlags(flags: String?): List<String> = flags?.split(" ").orEmpty()

    private fun readProcessorInfo(): List<ProcessorInfo> {
        var currentProcessor = 0
        return dataReader
            .read(CPU_INFO_COMMAND)
            .lines()
            .asSequence()
            .map(::removeTabsAndNewLines)
            .filterNot { it.isEmpty() }
            .map {
                it.split(":").let { keyValue ->
                    keyValue[0].trim() to if (keyValue.size > 1) keyValue[1].trim() else ""
                }
            }.groupBy {
                if (it.first == "processor") {
                    currentProcessor = it.second.trim().toIntOrNull() ?: 0
                }
                currentProcessor
            }.map {
                ProcessorInfo(
                    HashMap<String, String>().apply {
                        putAll(it.value)
                    }
                )
            }
    }

    private data class ProcessorInfo(
        val data: Map<String, String>
    ) {
        operator fun get(vararg keys: String): String? =
            data.filterKeys {
                keys.contains(it)
            }.map {
                it.value
            }.firstOrNull()
    }

    private operator fun List<ProcessorInfo>.get(vararg keys: String): String? = this.flatMap {
        it.data.filterKeys { key -> keys.contains(key) }.values
    }.firstOrNull()

    private fun removeTabsAndNewLines(target: String): String =
        target.replace("\t", "").replace("\n", "")

    companion object {
        const val CPU_INFO_COMMAND = "/proc/cpuinfo"
        const val CPU_GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"

        fun makeClockCommand(coreNumber: Int): String =
            "/sys/devices/system/cpu/cpu$coreNumber/cpufreq/scaling_cur_freq"

        fun makeMaxClockCommand(coreNumber: Int): String =
            "/sys/devices/system/cpu/cpu$coreNumber/cpufreq/cpuinfo_max_freq"

        fun makeMinClockCommand(coreNumber: Int): String =
            "/sys/devices/system/cpu/cpu$coreNumber/cpufreq/cpuinfo_min_freq"
    }
}
