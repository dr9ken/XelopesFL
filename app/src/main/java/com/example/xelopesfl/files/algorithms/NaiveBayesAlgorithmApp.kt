package com.example.xelopesfl.files.algorithms

import org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings

/**
 * @author Maxim Kolpaschikov
 */

class NaiveBayesAlgorithmApp(_stream: MiningInputStream, _target: String) : ClassificationAlgorithmApp(_stream) {

    init {

        initMiningSettings(getAlgorithmSettings(), _target)
        initAlgorithm(NaiveBayesAlgorithm(miningSettings))
    }

    /**
     * Initialization algorithm settings.
     */
    private fun getAlgorithmSettings() : EMiningAlgorithmSettings {

        val algorithmSettings = EMiningAlgorithmSettings()
        algorithmSettings.name = "Naive Bayes"
        algorithmSettings.classname = "org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm"

        return algorithmSettings
    }
}