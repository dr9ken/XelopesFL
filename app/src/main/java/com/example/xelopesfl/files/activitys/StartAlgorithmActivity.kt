package com.example.xelopesfl.files.activitys

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.xelopesfl.R
import com.example.xelopesfl.files.algorithms.AlgorithmSettings
import com.example.xelopesfl.files.algorithms.ClassificationAlgorithmApp
import com.example.xelopesfl.files.algorithms.NaiveBayesAlgorithmApp
import kotlinx.android.synthetic.main.activity_start_algorithm.*
import org.eltech.ddm.classification.ClassificationMiningModel
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.inputdata.file.MiningArffStream
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream
import org.eltech.ddm.inputdata.multistream.HorMultiStream
import org.eltech.ddm.inputdata.multistream.VerMultiStream
import kotlin.system.measureTimeMillis

/**
 * @author Maxim Kolpaschikov
 */

class StartAlgorithmActivity : AppCompatActivity() {

    private lateinit var settings: AlgorithmSettings

    private lateinit var algorithm : ClassificationAlgorithmApp

    /**
     * Activation of activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_algorithm)
        supportActionBar?.hide()

        init()
        initTargetSpinner()
        initAlgorithmSpinner()

        next_btn.setOnClickListener {
            run()
        }
    }

    /**
     *
     */
    private fun init() {

        settings =
            intent.extras?.getSerializable(AlgorithmSettings::class.java.simpleName) as AlgorithmSettings
        settings.stream = getStream()
    }

    /**
     * Creates an array of targets.
     */
    private fun getTargets() : Array<String?> {

        val targets = Array<String?>(settings.stream.logicalData.attributesNumber) { null }
        for (i in 0 until settings.stream.logicalData.attributesNumber) {
            targets[i] = settings.stream.logicalData.getAttribute(i).name
        }
        return targets
    }

    /**
     *
     */
    private fun initTargetSpinner() {

        val targets = getTargets()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, targets)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

        targets_spinner.adapter = adapter
        targets_spinner.prompt = "Algorithms"
        targets_spinner.setSelection(1)
        targets_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                settings.target = targets[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                settings.target = targets[0].toString()
            }
        }
    }

    /**
     *
     */
    private fun initAlgorithmSpinner() {

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("Naive Bayes", "K-Means <none>")
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        algorithms_spinner.adapter = adapter
        algorithms_spinner.prompt = "Algorithms"
        algorithms_spinner.setSelection(0)
        algorithms_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    1 -> algorithm = NaiveBayesAlgorithmApp(settings.stream, settings.target)
                    2 -> TODO("K-Means")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                algorithm = NaiveBayesAlgorithmApp(settings.stream, settings.target)
            }
        }
    }

    /**
     * Creates a stream from a single file.
     */
    private fun getStream(): MiningInputStream {

        val streamArray: Array<MiningCsvStream?> = getStreamsArray()

        return when (settings.readType) {
            "vertical" -> VerMultiStream(streamArray)
            "horizontal" -> HorMultiStream(streamArray)
            "default" -> MiningCsvStream(settings.paths[0], settings.parsingSettings)
            else -> throw IllegalArgumentException() // no one will work
        }
    }

    /**
     * Gets an array array of paths from the list.
     */
    private fun getStreamsArray(): Array<MiningCsvStream?> {

        val streams = Array<MiningCsvStream?>(settings.paths.size) { null }
        for (i in 0 until settings.paths.size) {
            streams[i] = MiningCsvStream(settings.paths[i], settings.parsingSettings)
        }
        return streams
    }

    /**
     * Starting a mining algorithm.
     */
    private fun run() {

        // val algorithm = NaiveBayesAlgorithmApp(MiningArffStream("/storage/self/primary/work/arff files/weather-nominal.arff"), "play")
        val algorithm = NaiveBayesAlgorithmApp(MiningArffStream("/storage/self/primary/work/arff files/soybeanTrain.arff"), "class")

        var model : ClassificationMiningModel
        val elapsedTime = measureTimeMillis {
            model = algorithm.run()
        }
        Log.d("AlgorithmTime", elapsedTime.toString())

        vectors_view.text = model.toString()
    }
}