package com.classic.seethamahalakshmi.misc

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.classic.seethamahalakshmi.MainActivity
import java.util.*

class TextToSpeechEngine(context: Context, status : Int) : TextToSpeech.OnInitListener {
    var context : Context
    var status : Int
    private var tts: TextToSpeech? = null
    init {
        this.status = status
        this.context = context
        tts = TextToSpeech(context, this)
    }

    override fun onInit(p0: Int) {
        tts = TextToSpeech(context, this)
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language not supported!")
            }
        }
    }

    fun speakOut(text : String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }

    companion object {
        fun speakOut(text: String) {
            MainActivity.ttsEngine!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }
}