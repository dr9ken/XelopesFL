package com.example.xelopesfl.Files.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.xelopesfl.R

class ReadingTypeDialog(private var intent : Intent) : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            dialogBuilder
                .setView(R.layout.reading_file_dialog)
                .setTitle(getString(R.string.reading_type_dialog_heading))
                .setMessage(getString(R.string.reading_type_dialog_msg))
                .setCancelable(true)
                .setPositiveButton(R.string.reading_type_dialog_ok_btn) { dialogInterface, id ->
                    val radioGroup = super.getDialog()?.findViewById(R.id.reading_type_gr) as RadioGroup
                    when (radioGroup.checkedRadioButtonId) {
                        R.id.read_type_ver -> intent.putExtra("readType", "ver".toString())
                        R.id.read_type_hor -> intent.putExtra("readType", "hor".toString())
                    }
                    startActivity(intent)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}