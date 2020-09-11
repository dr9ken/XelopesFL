package com.example.xelopesfl.Files.Activitys

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.xelopesfl.Files.Algorithms.ClassificationAlgorithm
import com.example.xelopesfl.Files.Dialogs.AttributeDialog
import com.example.xelopesfl.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.eltech.ddm.classification.ruleset.onerule.OneRuleCountAlgorithm
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream
import org.eltech.ddm.inputdata.file.csv.MultiCsvStream.HorMultiCsvStream
import org.eltech.ddm.inputdata.file.csv.MultiCsvStream.VerMultiCsvStream
import org.omg.java.cwm.objectmodel.core.Boolean
import java.util.ArrayList

/**
 * @author Maxim Kolpashikov
 */

class SettingsActivity : AppCompatActivity() {

    // settings
    private var isHeader = true
    private var readType = String()
    private var separator : Char = '\u0000'

    private lateinit var paths : ArrayList<String>
    private var parsingSettings = CsvParsingSettings()

    private var algorithm: ClassificationAlgorithm? = null

    /**
     * Activation of activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()

        initParamOfIntent()
        if(paths.size == 1) {
            readType = "default"
            reading_type_radio.visibility = View.INVISIBLE
        } else {
            reading_type_radio.visibility = View.VISIBLE
        }

        next_btn.setOnClickListener {

            permissionStatusChecked()
            takeSettings()
            selectingAttribute()
        }
    }

    /**
     * Check the permission to read the files.
     */
    private fun permissionStatusChecked() {

        val permissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionStatus == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }

    /**
     * Gets the settings file read.
     */
    private fun takeSettings() {

        when (is_header_radio.checkedRadioButtonId) {
            R.id.header_true -> isHeader = true
            R.id.header_false -> isHeader = false
        }

        when (separators_radio.checkedRadioButtonId) {
            R.id.separators_comma -> separator = ','
            R.id.separator_semicolon -> separator = ';'
        }

        if (paths.size == 1) return
        when (reading_type_radio.checkedRadioButtonId) {
            R.id.vertical_type -> readType = "vertical"
            R.id.horizontal_type -> readType = "horizontal"
        }
    }

    /**
     * Starts working with files.
     */
    private fun selectingAttribute() {

        initParsingSettings()
        val stream = getStream()

        val attributeDialog = AttributeDialog(algorithm, stream)
        attributeDialog.show(supportFragmentManager, "SelectingAttribute")
    }

    /**
     * Initialization settings.
     */
    private fun initParamOfIntent() {

        val arguments = intent.extras
        readType = arguments?.getString("readType").toString()
        paths = arguments?.getStringArrayList("paths") as ArrayList<String>
    }

    /**
     * Initialization data parsing settings.
     */
    private fun initParsingSettings() {

        parsingSettings.separator = this.separator
        parsingSettings.headerAvailability = isHeader
    }

    /**
     * Creates a stream from a single file.
     */
    private fun getStream() : MiningInputStream {

        val pathsArray : Array<String?> = getStreamsArray()
        return when (readType) {
            "default" -> MiningCsvStream(pathsArray[0], parsingSettings)
            "vertical" -> VerMultiCsvStream(pathsArray, parsingSettings)
            "horizontal" -> HorMultiCsvStream(pathsArray, parsingSettings)
            else -> throw IllegalArgumentException() // no one will work
        }
    }

    /**
     * Gets an array array of paths from the list.
     */
    private fun getStreamsArray() : Array<String?> {

        val streams = Array<String?>(paths.size) { null }
        for (i in 0 until paths.size) {
            streams[i] = paths[i]
        }
        return streams
    }
}