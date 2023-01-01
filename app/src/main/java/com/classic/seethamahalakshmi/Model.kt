package com.classic.seethamahalakshmi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class Model {

    fun getTokens(inputText : String) : List<String> {
       return inputText.split(" ")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun processFirstLayer(command: Command) {
        command.tokens = getTokens(command.rawText) as MutableList<String>
        command.tokens = preProcessText(command.tokens)
        val engineX = EngineX()
        command.categoryType = engineX.processCommand(command)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun processCommand(command : Command) {
        processFirstLayer(command)
        Log.i("SKHST 1651", command.categoryType.toString())
    }



}