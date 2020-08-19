package com.example.xelopesfl.Files.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.xelopesfl.R

class IdenticalFilesDialog(private var viewAdapter : ArrayAdapter<String>,
                           private var pathList : ArrayList<String>,
                           private val fileName : String,
                           private val path: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            dialogBuilder
                .setTitle(getString(R.string.identical_file_dialog_heading))
                .setMessage(getString(R.string.identical_file_dialog_msg))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.identical_file_dialog_no_btn)) { dialogInterface, i ->
                    dialogInterface.cancel()
                }
                .setNegativeButton(getString(R.string.identical_file_dialog_yes_btn)) { dialogInterface, i ->
                    dialogInterface.cancel()
                    pathList.add(path)
                    viewAdapter.insert(fileName, pathList.size - 1)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}