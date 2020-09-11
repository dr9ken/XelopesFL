package com.example.xelopesfl.Files.Activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xelopesfl.R
import kotlinx.android.synthetic.main.activity_file_processing.*
import org.eltech.ddm.classification.ClassificationFunctionSettings
import org.eltech.ddm.classification.ClassificationMiningModel
import org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.inputdata.file.MiningArffStream
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream
import org.eltech.ddm.inputdata.file.csv.MultiCsvStream.HorMultiCsvStream
import org.eltech.ddm.inputdata.file.csv.MultiCsvStream.VerMultiCsvStream
import org.eltech.ddm.miningcore.MiningException
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm
import org.eltech.ddm.miningcore.miningdata.ELogicalData
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask
import java.util.*


/**
 * @author Maxim Kolpashikov
 */

class FileProcessingActivity : AppCompatActivity() {

    /**
     * Activation of activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_processing)
        supportActionBar?.hide()

    }

}