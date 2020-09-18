package com.example.xelopesfl.Files.Dialogs

import android.app.Dialog
import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.xelopesfl.R

/**
 * @author Maxim Kolpaschikov
 */

class DeleteFileDialog(private val _btnPos : Int,
                       private var _pathList: ArrayList<String>,
                       private var _filesList : ArrayList<String>,
                       private var _adapter : ArrayAdapter<String>) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialogBuilder = AlertDialog.Builder(it)

            dialogBuilder
                .setTitle(getString(R.string.delete_file_dialog_heading))
                .setMessage(getString(R.string.delete_file_dialog_msg))
                .setCancelable(true)
                .setPositiveButton(R.string.delete_file_dialog_no_btn) { dialogInterface, id ->
                    dialogInterface.cancel()
                }
                .setNegativeButton(R.string.delete_file_dialog_yes_btn) { dialogInterface, id ->
                    dialogInterface.cancel()
                    _pathList.removeAt(_btnPos)
                    _filesList.removeAt(_btnPos)
                    _adapter.notifyDataSetChanged()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}