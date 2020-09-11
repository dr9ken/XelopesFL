package com.example.xelopesfl.Files.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.xelopesfl.Files.Activitys.FileProcessingActivity
import com.example.xelopesfl.Files.Algorithms.ClassificationAlgorithm
import com.example.xelopesfl.Files.Algorithms.NaiveBayesAlgorithm
import com.example.xelopesfl.R
import org.eltech.ddm.inputdata.MiningInputStream

/**
 * @author Maxim Kolpashikov
 */

class AttributeDialog(private var _algorithm : ClassificationAlgorithm?,
                      private  val _stream : MiningInputStream) : DialogFragment() {

    private val logicalData = _stream.logicalData

    private fun attributesToArray() : kotlin.Array<String?> {

        val streams = Array<String?>(logicalData.attributesNumber) { null }
        for (i in 0 until logicalData.attributesNumber) {
            streams[i] = logicalData.getAttribute(i).toString()
        }
        return streams
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {

            val dialogBuilder = AlertDialog.Builder(it)
            val attributes = attributesToArray()

            dialogBuilder
                .setTitle(getString(R.string.reading_settings_selecting_attribute))
                .setItems(attributes) { dialog, which ->

                    val attribute = attributes[which].toString()
                    _algorithm = NaiveBayesAlgorithm(_stream, attribute)

                    val intent = Intent(it, FileProcessingActivity::class.java)
                    startActivity(intent)
                }
                .setCancelable(true)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}