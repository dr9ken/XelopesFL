package com.example.xelopesfl.Files.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.xelopesfl.Files.Activitys.FileProcessingActivity
import com.example.xelopesfl.Files.Algorithms.ClassificationAlgorithm
import com.example.xelopesfl.Files.Algorithms.NaiveBayesAlgorithm
import com.example.xelopesfl.R
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm
import java.io.Serializable

/**
* @author Maxim Kolpaschikov
*/

class SelectingTargetDialog(private  val _stream : MiningInputStream,
                            private val _intent : Intent) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {

            val attributes = attributesToArray()
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.reading_settings_selecting_attribute))
                .setCancelable(true)
                .setItems(attributes) { dialog, which ->

                    _intent.putExtra("target", attributes[which].toString())
                    startActivity(_intent)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     * Creates an array of targets.
     */
    private fun attributesToArray() : Array<String?> {

        val streams = Array<String?>(_stream.logicalData.attributesNumber) { null }
        for (i in 0 until _stream.logicalData.attributesNumber) {
            streams[i] = _stream.logicalData.getAttribute(i).name
        }
        return streams
    }

}