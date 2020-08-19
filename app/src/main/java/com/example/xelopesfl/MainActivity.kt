package com.example.xelopesfl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xelopesfl.Files.Activitys.FileSelectionActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, FileSelectionActivity::class.java)
        startActivity(intent)
        onStop()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
    }


}