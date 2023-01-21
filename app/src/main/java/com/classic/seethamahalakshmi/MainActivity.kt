package com.classic.seethamahalakshmi

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.classic.seethamahalakshmi.classfiles.Command
import com.classic.seethamahalakshmi.misc.SpeechRecogniser
import com.classic.seethamahalakshmi.misc.SpeechRecogniser.Companion.initializeSpeechToText
import com.classic.seethamahalakshmi.misc.SpeechRecogniser.Companion.listen
import com.classic.seethamahalakshmi.misc.TextToSpeechEngine

class MainActivity : AppCompatActivity() {
    var ttsEngine : TextToSpeechEngine? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// TODO: To add a generic type, create a entry in CategoryType enum and a initialize<TYPE> function at Resources.java and add it to globalList
// TODO: To add a sub action, create enum {$ActionName}Action in enums dir, add entry in getActions() @EngineX
//        startTest()
//        startupTasks()
        ttsEngine = TextToSpeechEngine(this,1)
        ttsEngine!!.speakOut("Hello Sri!")
    }



    @RequiresApi(Build.VERSION_CODES.N)
    private fun startTest() {
        val testCommand = Command("Cancel my appointment at 12:30 PM today")
        val engineX = EngineX()
        engineX.startProcessing(testCommand)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun startupTasks() {
        checkAudioPermission(this)
        val tv_result = findViewById<TextView>(R.id.tv_result)
        val btn_speak = findViewById<Button>(R.id.btn_speak)
        initializeSpeechToText(this, tv_result)
        btn_speak.setOnClickListener {
            val listenResult = listen()
            val testCommand = Command(listenResult)
            val engineX = EngineX()
            engineX.startProcessing(testCommand)
        }
    }

    fun checkAudioPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  // M = 23
            if (ContextCompat.checkSelfPermission(context, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Allow Microphone Permission", Toast.LENGTH_SHORT).show()
            }
        }
    }
}