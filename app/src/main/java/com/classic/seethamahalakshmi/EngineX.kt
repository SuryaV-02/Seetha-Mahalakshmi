package com.classic.seethamahalakshmi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.classic.seethamahalakshmi.misc.Resources.globalKeywordsList
import com.classic.seethamahalakshmi.classfiles.Command
import com.classic.seethamahalakshmi.enums.CategoryType
import com.classic.seethamahalakshmi.misc.Resources
import java.util.stream.Collectors

class EngineX {

    @RequiresApi(Build.VERSION_CODES.N)
    fun startProcessing(command : Command) {
        Resources.initializeResources()
        preprocessCommand(command)
        processFirstLayer(command)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun preprocessCommand(command: Command) {
        command.tokens = getTokens(command.rawText) as MutableList<String>
        command.tokens = preProcessText(command.tokens)
    }

    /*
        Identifies the generic category of the command
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun processFirstLayer(command: Command) {
        val commandTokens = command.tokens as ArrayList<String>
        val probabilityMap = HashMap<CategoryType, Int>()
        for (category in globalKeywordsList) {
            probabilityMap[category.categoryType] =
                getMatchBetweenList(commandTokens, category.categoryList)
        }
        command.categoryType = getMaximumMatchedCategory(probabilityMap)
        Log.i("SKHST 1857", command.categoryType.toString())
    }

    private fun getMaximumMatchedCategory(probabilityMap: HashMap<CategoryType, Int>): CategoryType {
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getMatchBetweenList(commandTokens: List<String>, categoryList: List<String>): Int {
        Log.i("SKHST 215956", "$commandTokens $categoryList")
        val commonElements : MutableList<String>? = commandTokens.stream()
            .filter(categoryList::contains)
            .collect(
                Collectors
                .toList())
        Log.i("SKHST 96365", "$commonElements")
        return commonElements?.size ?: 0
    }


}