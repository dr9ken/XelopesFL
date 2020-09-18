package com.example.xelopesfl.Files.Activitys

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.xelopesfl.Files.Dialogs.SelectingTargetDialog
import com.example.xelopesfl.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream
import org.eltech.ddm.inputdata.multistream.HorMultiStream
import org.eltech.ddm.inputdata.multistream.VerMultiStream

import java.util.*

/**
 * @author Maxim Kolpasсhikov
 */

class SettingsActivity : AppCompatActivity() {

    private lateinit var paths : ArrayList<String>

    /**
     * Activation of activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()

        initPathsList()
        readTypeGroupStatus()

        next_btn.setOnClickListener {

            permissionStatusChecked()
            runAlgorithm()
        }
    }

    /**
     * Path list initialization.
     */
    private fun initPathsList() {
        paths = intent.extras?.getStringArrayList("paths") as ArrayList<String>
    }

    /**
     * Checks the number of files and enables/disables the ability to select the type of reading.
     */
    private fun readTypeGroupStatus() {

        if (paths.size == 1) {
            reading_type_radio.visibility = View.INVISIBLE
        } else {
            reading_type_radio.visibility = View.VISIBLE
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
     * Starts working with files.
     */
    private fun runAlgorithm() {

        val targetDialog = SelectingTargetDialog(getStream(), createIntent())
        targetDialog.show(supportFragmentManager, "SelectingTarget")
    }

    /**
     * Returns intent.
     */
    private fun createIntent() : Intent {

        val intent = Intent(this, FileProcessingActivity::class.java)
        intent.putExtra("paths", paths)
        intent.putExtra("readType", getReadType())
        intent.putExtra(CsvParsingSettings::class.java.simpleName, getParsingSettings())
        return intent
    }

    /**
     * Creates a stream from a single file.
     */
    private fun getStream(): MiningInputStream {

        val readType = getReadType()
        val parsingSettings = getParsingSettings()
        val streamArray: Array<MiningCsvStream?> = getStreamsArray(parsingSettings)

        return when (readType) {

            "vertical" -> VerMultiStream(streamArray)
            "horizontal" -> HorMultiStream(streamArray)
            "default" -> MiningCsvStream(paths[0], parsingSettings)
            else -> throw IllegalArgumentException() // no one will work
        }
    }

    /**
     * Gets the read type.
     */
    private fun getReadType() : String {

        if(paths.size == 1) return "default"

        return when (reading_type_radio.checkedRadioButtonId) {
            R.id.vertical_type -> "vertical"
            R.id.horizontal_type -> "horizontal"
            else -> throw NullPointerException("Read type is missing.") // no one will work
        }
    }

    /**
     * Gets the parsing settings.
     */
    private fun getParsingSettings(): CsvParsingSettings {

        val parsingSettings = CsvParsingSettings()

        when (is_header_radio.checkedRadioButtonId) {
            R.id.header_true -> parsingSettings.headerAvailability = true
            R.id.header_false -> parsingSettings.headerAvailability = false
        }

        when (separators_radio.checkedRadioButtonId) {
            R.id.separators_comma -> parsingSettings.separator = ','
            R.id.separator_semicolon -> parsingSettings.separator = ';'
        }

        return parsingSettings
    }

    /**
     * Gets an array array of paths from the list.
     */
    private fun getStreamsArray(parsingSettings: CsvParsingSettings): Array<MiningCsvStream?> {

        val streams = Array<MiningCsvStream?>(paths.size) { null }
        for (i in 0 until paths.size) {
            streams[i] = MiningCsvStream(paths[i], parsingSettings)
        }
        return streams
    }
}