package com.classic.seethamahalakshmi.misc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.TextView
import com.classic.seethamahalakshmi.MainActivity.Companion.ttsEngine
import java.util.*

class SpeechRecogniser {
    companion object {
        private var speechRecognizer: SpeechRecognizer? = null
        private var speechRecognizerIntent: Intent? = null
        private var speechResult: String = ""

        fun initializeSpeechToText(context: Context, outputTV: TextView) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechRecognizerIntent!!.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            speechRecognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

            speechRecognizer!!.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(bundle: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(v: Float) {}
                override fun onBufferReceived(bytes: ByteArray?) {}
                override fun onEndOfSpeech() {}
                override fun onError(i: Int) {}
                override fun onResults(bundle: Bundle) {
                    val result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (result != null) {
                        speechResult = result[0]
                        outputTV.text = result[0]
                        Log.i("SKHST 48512", result.toString())
                    }
                }

                override fun onPartialResults(bundle: Bundle) {}
                override fun onEvent(i: Int, bundle: Bundle?) {}

            })
            Log.i("SKHST 6513", "Initi SR success")
        }

        fun listen(): String {
            Log.i("SKHST 58962", "Listening")
            speechRecognizer!!.startListening(speechRecognizerIntent)
            return speechResult
            Log.i("SKHST 89632", "Listen done")
        }

    fun speakOut(text: String) {
        ttsEngine!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


}
}