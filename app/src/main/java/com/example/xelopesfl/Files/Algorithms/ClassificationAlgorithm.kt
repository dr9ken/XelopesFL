package com.example.xelopesfl.Files.Algorithms

import org.eltech.ddm.classification.ClassificationFunctionSettings
import org.eltech.ddm.classification.ClassificationMiningModel
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm
import org.eltech.ddm.miningcore.miningdata.ELogicalData
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask

/**
 * @author Maxim Kolpashikov
 */

abstract class ClassificationAlgorithm(_stream : MiningInputStream,
                                       _attribute: String) {

    private val attribute = _attribute
    private val stream : MiningInputStream = _stream
    protected lateinit var algorithm : MiningAlgorithm
    protected lateinit var miningSettings : ClassificationFunctionSettings

    /**
     * Running the algorithm.
     */
    public fun run() : ClassificationMiningModel {

        val buildTask = createBuildTask()
        return buildTask.execute() as ClassificationMiningModel
    }

    /**
     * Create and returning build task.
     */
    private fun createBuildTask() : EMiningBuildTask {

        val environment = ConcurrencyExecutionEnvironment(stream)

        val buildTask = EMiningBuildTask()
        buildTask.miningAlgorithm = algorithm
        buildTask.miningSettings = miningSettings
        buildTask.executionEnvironment = environment
        return buildTask
    }

    /**
     * Initialization mining settings.
     */
    protected fun initMiningSettings(algorithmSettings: EMiningAlgorithmSettings) {

        val logicalData: ELogicalData = stream.logicalData
        val targetAttribute = logicalData.getAttribute(attribute)

        miningSettings = ClassificationFunctionSettings(logicalData)
        miningSettings.target = targetAttribute
        miningSettings.algorithmSettings = algorithmSettings
    }

}