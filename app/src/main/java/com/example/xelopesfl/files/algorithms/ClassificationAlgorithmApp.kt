package com.example.xelopesfl.files.algorithms

import org.eltech.ddm.classification.ClassificationFunctionSettings
import org.eltech.ddm.classification.ClassificationMiningModel
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask

/**
 * @author Maxim Kolpaschikov
 */

abstract class ClassificationAlgorithmApp(_stream : MiningInputStream) {

    private val stream : MiningInputStream = _stream
    private lateinit var miningAlgorithm : MiningAlgorithm
    protected lateinit var miningSettings : ClassificationFunctionSettings

    /**
     * Running the algorithm.
     */
    fun run() : ClassificationMiningModel {

        val buildTask = createBuildTask()
        return buildTask.execute() as ClassificationMiningModel
    }

    /**
     * Initialization mining algorithm.
     */
    protected fun initAlgorithm(algorithm : MiningAlgorithm) {
        miningAlgorithm = algorithm
    }

    /**
     * Initialization mining settings.
     */
    protected fun initMiningSettings(algorithmSettings : EMiningAlgorithmSettings, target: String) {

        miningSettings = ClassificationFunctionSettings(stream.logicalData)
        miningSettings.target = stream.logicalData.getAttribute(target)
        miningSettings.algorithmSettings = algorithmSettings
    }

    /**
     * Create and returning build task.
     */
    private fun createBuildTask() : EMiningBuildTask {

        val buildTask = EMiningBuildTask()
        buildTask.miningAlgorithm = miningAlgorithm
        buildTask.miningSettings = miningSettings
        buildTask.executionEnvironment = ConcurrencyExecutionEnvironment(1, stream)

        return buildTask
    }

}
