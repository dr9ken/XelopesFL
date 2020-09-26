package com.example.xelopesfl.files.activitys

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.xelopesfl.R
import com.example.xelopesfl.files.algorithms.AlgorithmSettings
import kotlinx.android.synthetic.main.activity_settings.*
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings

/**
 * @author Maxim Kolpasсhikov
 */

class SettingsActivity : AppCompatActivity() {

    private lateinit var settings: AlgorithmSettings

    /**
     * Activation of activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()

        init()
        next_btn.setOnClickListener {

            permissionStatusChecked()
            startActivity(createIntent())
        }
    }

    /**
     * Activity initialization.
     */
    private fun init() {
        settings =
            intent.extras?.getSerializable(AlgorithmSettings::class.java.simpleName) as AlgorithmSettings

        if (settings.paths.size == 1) {
            reading_type_radio.visibility = View.INVISIBLE
        } else {
            reading_type_radio.visibility = View.VISIBLE
        }
    }

    /**
     * Gets user settings.
     */
    private fun readSettings() {

        getReadType()
        getParsingSettings()
    }

    /**
     * Gets the read type.
     */
    private fun getReadType() {

        if (settings.paths.size == 1) {
            settings.readType = "default"
            return
        }

        when (reading_type_radio.checkedRadioButtonId) {
            R.id.vertical_type -> settings.readType = "vertical"
            R.id.horizontal_type -> settings.readType = "horizontal"
            else -> throw NullPointerException("Read type is missing.") // no one will work
        }
    }

    /**
     * Gets the parsing settings.
     */
    private fun getParsingSettings() {

        val parsingSettings = CsvParsingSettings()

        when (is_header_radio.checkedRadioButtonId) {
            R.id.header_true -> parsingSettings.headerAvailability = true
            R.id.header_false -> parsingSettings.headerAvailability = false
        }

        when (separators_radio.checkedRadioButtonId) {
            R.id.separators_comma -> parsingSettings.separator = ','
            R.id.separator_semicolon -> parsingSettings.separator = ';'
        }

        settings.parsingSettings = parsingSettings
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
     * Returns intent.
     */
    private fun createIntent(): Intent {

        readSettings()

        val intent = Intent(this, StartAlgorithmActivity::class.java)
        intent.putExtra(AlgorithmSettings::class.java.simpleName, settings)
        return intent
    }
}