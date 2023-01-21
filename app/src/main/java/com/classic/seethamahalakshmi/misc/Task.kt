package com.classic.seethamahalakshmi.misc

import android.os.AsyncTask


class Task : AsyncTask<Void?, Void?, Void?>() {
    override fun doInBackground(vararg p0: Void?): Void? {
        try {
            var counter = 0
            while (!SpeechRecogniser.speechResultProcessMutex) {
                counter++
            }
            SpeechRecogniser.speechResultProcessMutex = false
        } catch (e: Exception) {
        }
        return null
    }
}