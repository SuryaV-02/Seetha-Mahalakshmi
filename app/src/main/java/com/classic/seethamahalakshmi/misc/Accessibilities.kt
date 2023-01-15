package com.classic.seethamahalakshmi

import android.util.Log
import java.util.*

private val SPECIAL_SYMBOLS_REMOVER = "[^0-9a-zA-Z]+"

fun preProcessText(inputTextList: MutableList<String>): MutableList<String> {
    for (i in 0 until inputTextList.size){
        inputTextList[i] = removeSpecialSymbols(inputTextList[i])
        inputTextList[i] = changeToLowerCase(inputTextList[i])
    }
    return inputTextList;
}

fun changeToLowerCase(word: String): String {
    return word.toLowerCase(Locale.getDefault())
}

fun getTokens(inputText : String) : List<String> {
    return inputText.split(" ")
}

private fun removeSpecialSymbols(word: String): String {
    // TODO: Regex to replace trailing ',' symbol at word
    word.replace(SPECIAL_SYMBOLS_REMOVER,"")
    return word
}