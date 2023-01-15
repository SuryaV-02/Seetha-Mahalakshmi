package com.classic.seethamahalakshmi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.classic.seethamahalakshmi.classfiles.Command

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// TODO: Add a entry in CategoryType enum and a initialize<TYPE> function at Resources.java and add it to globalList to get started 
        startTest();
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun startTest() {
        val testCommand = Command("Get my Pills, and medicine record and appointment details")
        val engineX = EngineX()
        engineX.startProcessing(testCommand)

    }
}