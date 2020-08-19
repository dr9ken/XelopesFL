package com.example.xelopesfl.Files.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xelopesfl.R
import kotlinx.android.synthetic.main.activity_file_processing.*
import java.util.ArrayList

class FileProcessingActivity : AppCompatActivity() {

    private lateinit var paths: ArrayList<String>
    private var readType = "non"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_processing)
        init()
        showData()
    }

    private fun init() {

        val arguments = intent.extras
        paths = arguments?.getStringArrayList("paths") as ArrayList<String>
        readType = arguments?.getString("readType").toString()
    }

    private fun showData() {

        var text = String()
        for (path in paths) {
            text += (path + "\n")
        }
        text += readType
        vectors_view.text = text
    }
}