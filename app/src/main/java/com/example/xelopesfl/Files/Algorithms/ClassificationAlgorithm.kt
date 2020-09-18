package com.example.xelopesfl.Files.Algorithms

import org.eltech.ddm.classification.ClassificationFunctionSettings
import org.eltech.ddm.classification.ClassificationMiningModel
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask
import java.io.Serializable

/**
 * @author Maxim Kolpaschikovs
 */

abstract class ClassificationAlgorithm public constructor() : Serializable {

    private lateinit var stream : MiningInputStream
    private lateinit var miningAlgorithm : MiningAlgorithm
    protected lateinit var miningSettings : ClassificationFunctionSettings

    /**
     * Running the algorithm.
     */
    public fun run() : ClassificationMiningModel? {

        val buildTask = createBuildTask()
        return buildTask.execute() as ClassificationMiningModel?
    }

    /**
     * Initialization mining algorithm.
     */
    protected fun initAlgorithm(algorithm : MiningAlgorithm) {
        miningAlgorithm = algorithm
    }

    /**
     * Initialization mining stream.
     */
    protected fun initMiningStream(stream: MiningInputStream) {
        this.stream = stream
        this.stream.open()
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
        buildTask.executionEnvironment = ConcurrencyExecutionEnvironment(stream)
        return buildTask
    }

}