package com.example.xelopesfl.Files.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.xelopesfl.R
import kotlinx.android.synthetic.main.activity_file_processing.*
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream
import org.eltech.ddm.inputdata.file.csv.MultiCsvStream.HorMultiCsvStream
import org.eltech.ddm.inputdata.file.csv.MultiCsvStream.MiningMultiCsvStream
import org.eltech.ddm.inputdata.file.csv.MultiCsvStream.VerMultiCsvStream
import java.util.ArrayList

class FileProcessingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_processing)
        readFiles()
    }

    private fun readFiles() {

        val arguments = intent.extras
        val readType = arguments?.getString("readType")
        val paths = arguments?.getStringArrayList("paths") as ArrayList<String>

        if(paths.size == 1) {
            createStream(paths[0])
        } else {
            createMultiStream(paths, readType.toString())
        }
    }

    private fun createStream(path: String) {

        val stream = MiningCsvStream(path)
        stream.open()

        var text = String()
        for(i in 0 until stream.vectorsNumber) {
            text += stream.getVector(i).toString() + "\n"
        }

        vectors_view.text = text
    }

    private fun createMultiStream(paths: ArrayList<String>, readType: String) {

        val pathsArray : Array<String?> = getStreams(paths)
        val stream = when (readType) {
            "ver" -> VerMultiCsvStream(pathsArray)
            "hor" -> HorMultiCsvStream(pathsArray)
            else -> null
        }

        var text = String()
        if (stream != null) {
            for (i in 0 until stream.vectorsNumber) {
                text += stream.getVector(i).toString() + "\n"
            }
        }

        vectors_view.text = text
    }

    private fun getStreams(paths:ArrayList<String>) : Array<String?> {

        val streams = Array<String?>(paths.size) { null }

        for (i in 0 until paths.size) {
            streams[i] = paths[i]
        }

        return streams
    }

}