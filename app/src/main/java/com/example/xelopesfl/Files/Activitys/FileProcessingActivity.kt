package com.example.xelopesfl.Files.Activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xelopesfl.Files.Algorithms.ClassificationAlgorithm
import com.example.xelopesfl.Files.Algorithms.NaiveBayesAlgorithm
import com.example.xelopesfl.R
import kotlinx.android.synthetic.main.activity_file_processing.*
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream
import org.eltech.ddm.inputdata.multistream.HorMultiStream
import org.eltech.ddm.inputdata.multistream.VerMultiStream
import java.util.*

/**
 * @author Maxim Kolpaschikov
 */

class FileProcessingActivity : AppCompatActivity() {

    /**
     * Activation of activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_processing)
        supportActionBar?.hide()

        runAlgorithm()
    }

    private fun runAlgorithm() {

        val miningAlgorithm = getAlgorithm(getStream(intent.extras?.getStringArrayList("paths") as ArrayList<String>))
        val model = miningAlgorithm.run().toString()
        vectors_view.text = model
    }

    private fun getAlgorithm(stream : MiningInputStream) : ClassificationAlgorithm {

        val target = intent.extras?.getString("target").toString()
        return NaiveBayesAlgorithm(stream, target)
    }

    /**
     * Creates a stream from a files.
     */
    private fun getStream(paths : ArrayList<String>): MiningInputStream {

        val readType = intent.extras?.getString("readType").toString()
        val parsingSettings =
            intent.extras?.getSerializable(CsvParsingSettings::class.java.simpleName) as CsvParsingSettings

        val streamArray: Array<MiningCsvStream?> = getStreamsArray(paths, parsingSettings)
        return when (readType) {

            "vertical" -> VerMultiStream(streamArray)
            "horizontal" -> HorMultiStream(streamArray)
            "default" -> MiningCsvStream(paths[0], parsingSettings)
            else -> throw IllegalArgumentException() // no one will work
        }
    }

    /**
     * Gets an array array of paths from the list.
     */
    private fun getStreamsArray(paths : ArrayList<String>, parsingSettings: CsvParsingSettings)
            : Array<MiningCsvStream?> {

        val streams = Array<MiningCsvStream?>(paths.size) { null }
        for (i in 0 until paths.size) {
            streams[i] = MiningCsvStream(paths[i], parsingSettings)
        }
        return streams
    }

}