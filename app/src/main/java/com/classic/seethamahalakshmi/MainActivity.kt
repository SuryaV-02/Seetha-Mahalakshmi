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

// TODO: To add a generic type, create a entry in CategoryType enum and a initialize<TYPE> function at Resources.java and add it to globalList
// TODO: To add a sub action, create enum {$ActionName}Action in enums dir, add entry in getActions() @EngineX
        startTest();
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun startTest() {
        val testCommand = Command("Cancel my appointment at 12:30 PM today")
        val engineX = EngineX()
        engineX.startProcessing(testCommand)

    }
}