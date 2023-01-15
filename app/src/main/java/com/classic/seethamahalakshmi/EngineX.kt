package com.classic.seethamahalakshmi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.classic.seethamahalakshmi.misc.Resources.globalKeywordsList
import com.classic.seethamahalakshmi.classfiles.Command
import com.classic.seethamahalakshmi.enums.AppointmentAction
import com.classic.seethamahalakshmi.enums.CategoryType
import com.classic.seethamahalakshmi.enums.CategoryType.*
import com.classic.seethamahalakshmi.misc.Resources
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EngineX {

    @RequiresApi(Build.VERSION_CODES.N)
    fun startProcessing(command : Command) {
        Resources.initializeResources()
        preprocessCommand(command)
        processFirstLayer(command)
        processSecondLayer(command)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun preprocessCommand(command: Command) {
        command.tokens = getTokens(command.rawText) as MutableList<String>
        command.tokens = preProcessText(command.tokens)
    }

    /*
        Identifies and sets the generic category of the command
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

    /*
         Identifies and sets the action type, inside of generic category of the command
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun processSecondLayer(command: Command) {
        val categoryType = command.categoryType
        var actions = getActions(categoryType)
        val actionInfProbabilityMap = HashMap<String, Int>()
        for(action in actions) {
            actionInfProbabilityMap[action.key] = getMatchBetweenList(command.tokens,
                action.value.toList()
            )
        }
        command.actionInfType = getMaximumMatchedAction(actionInfProbabilityMap)
    }

    private fun getActions(categoryType: CategoryType): Map<String, Array<String>> {
        val actionNames = HashMap<String, Array<String>>()
        when(categoryType) {
            APPOINTMENT -> {
                 for(actionName in enumValues<AppointmentAction>()) {
                     actionNames[actionName.name] = actionName.keywords
                 }
            }
        }
        return actionNames
    }

    private fun getMaximumMatchedCategory(probabilityMap: HashMap<CategoryType, Int>): CategoryType {
        var MAX_CATEGORY : CategoryType = APPOINTMENT
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

    private fun getMaximumMatchedAction(probabilityMap: HashMap<String, Int>): String {
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