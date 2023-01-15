package com.classic.seethamahalakshmi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.classic.seethamahalakshmi.enums.AppointmentAction
import com.classic.seethamahalakshmi.enums.CategoryType
import java.util.*
import java.util.stream.Collectors

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

fun removeSpecialSymbols(word: String): String {
    // TODO: Regex to replace trailing ',' symbol at word
    word.replace(SPECIAL_SYMBOLS_REMOVER,"")
    return word
}

fun getActions(categoryType: CategoryType): Map<String, Array<String>> {
    val actionNames = HashMap<String, Array<String>>()
    when(categoryType) {
        CategoryType.APPOINTMENT -> {
            for(actionName in enumValues<AppointmentAction>()) {
                actionNames[actionName.name] = actionName.keywords
            }
        }
    }
    return actionNames
}

fun getMaximumMatchedCategory(probabilityMap: HashMap<CategoryType, Int>): CategoryType {
    var MAX_CATEGORY : CategoryType = CategoryType.APPOINTMENT
    var MAX_PROBABILITY = 0
    for( (categoryType, probability) in probabilityMap ) {
        if ( probability > MAX_PROBABILITY) {
            MAX_CATEGORY = categoryType
            MAX_PROBABILITY = probability
        }
        Log.i("SKHST 96365", "$MAX_CATEGORY $MAX_PROBABILITY")
    }
    return MAX_CATEGORY
}

fun getMaximumMatchedAction(probabilityMap: HashMap<String, Int>): String {
    var MAX_ACTION_TYPE : String = "none"
    var MAX_PROBABILITY = 0
    for( (actionType, probability) in probabilityMap ) {
        if ( probability > MAX_PROBABILITY) {
            MAX_ACTION_TYPE = actionType
            MAX_PROBABILITY = probability
        }
        Log.i("SKHST 96365", "$MAX_ACTION_TYPE $MAX_PROBABILITY")
    }
    return MAX_ACTION_TYPE
}

@RequiresApi(Build.VERSION_CODES.N)
fun getMatchBetweenList(commandTokens: List<String>, categoryList: List<String>): Int {
    Log.i("SKHST 215956", "$commandTokens $categoryList")
    val commonElements : MutableList<String>? = commandTokens.stream()
        .filter(categoryList::contains)
        .collect(
            Collectors
                .toList())
    Log.i("SKHST 96365", "$commonElements")
    return commonElements?.size ?: 0
}