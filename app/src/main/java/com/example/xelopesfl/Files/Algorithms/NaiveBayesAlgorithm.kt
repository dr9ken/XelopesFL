package com.example.xelopesfl.Files.Algorithms

import org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings

/**
 * @author Maxim Kolpashikov
 */

class NaiveBayesAlgorithm(_stream: MiningInputStream,
                          _attribute: String) : ClassificationAlgorithm(_stream, _attribute) {

    private var algorithmSettings = EMiningAlgorithmSettings()

    init {

        initAlgorithmSettings()
        initMiningSettings(algorithmSettings)
        initAlgorithm()
    }

    /**
     * Initialization algorithm.
     */
    private fun initAlgorithm() {

        algorithm = NaiveBayesAlgorithm(miningSettings)
    }

    /**
     * Initialization algorithm settings.
     */
    private fun initAlgorithmSettings() {

        algorithmSettings.name = "Naive Bayes"
        algorithmSettings.classname =
            "org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm"
    }

}