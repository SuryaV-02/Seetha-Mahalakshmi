package com.classic.seethamahalakshmi.misc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.TextView
import java.util.*
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SpeechRecogniser {
    companion object {
        private var speechRecognizer: SpeechRecognizer? = null
        private var speechRecognizerIntent: Intent? = null
        private var speechResult: String = ""
        private var speechResultProcessMutex : Boolean = false
        var lock : Lock? = null
        var condition : Condition? = null

        fun initializeSpeechToText(context: Context, outputTV: TextView) {
            lock = ReentrantLock()
            condition = lock!!.newCondition()
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
                        speechResultProcessMutex = true
                        Log.i("SKHST 58962", "processing result done")
                        lock?.withLock {
                            Log.i("SKHST 58962", "signal lock")
                            condition?.signal()
                            Log.i("SKHST 58962", "signalledd lock")
                        }
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
            lock?.withLock {
                Log.i("SKHST 58962", "Enter lock")
                condition?.await()
                Log.i("SKHST 58962", "Exit Lock")
            }
            Log.i("SKHST 58962", "Returning Lock")
            return speechResult
        }
    }
}