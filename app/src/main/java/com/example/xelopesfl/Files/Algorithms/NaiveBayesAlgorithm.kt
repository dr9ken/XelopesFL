package com.example.xelopesfl.Files.Algorithms

import org.eltech.ddm.classification.ClassificationFunctionSettings
import org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings
import java.io.Serializable

/**
 * @author Maxim Kolpaschikov
 */

class NaiveBayesAlgorithm(_stream: MiningInputStream, _target: String) : ClassificationAlgorithm() {

    init {

        initMiningStream(_stream)
        initMiningSettings(getAlgorithmSettings(), _target)
        initAlgorithm(NaiveBayesAlgorithm(miningSettings))
    }

    /**
     * Initialization algorithm settings.
     */
    private fun getAlgorithmSettings() : EMiningAlgorithmSettings {

        val algorithmSettings = EMiningAlgorithmSettings()
        algorithmSettings.name = "Naive Bayes"
        algorithmSettings.classname = NaiveBayesAlgorithm::class.java.simpleName

        return algorithmSettings
    }
}