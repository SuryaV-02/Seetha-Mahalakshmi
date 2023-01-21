package com.classic.seethamahalakshmi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.classic.seethamahalakshmi.classfiles.Command
import com.classic.seethamahalakshmi.enums.CategoryType
import com.classic.seethamahalakshmi.enums.CategoryType.*
import com.classic.seethamahalakshmi.misc.Resources
import com.classic.seethamahalakshmi.misc.Resources.*

import com.classic.seethamahalakshmi.ActionClasses.Appointments.processAppointment

class EngineX {

    @RequiresApi(Build.VERSION_CODES.N)
    fun startProcessing(command : Command) {
        initializeResources()
        preprocessCommand(command)
        processFirstLayer(command)
        processSecondLayer(command)
        processThirdLayer(command)
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
        Log.i("SKHST 6853120", command.actionInfType)
    }

    fun processThirdLayer(command: Command) {
        Log.i("SKHST 84512" , command.actionInfType)
        when(command.categoryType) {
            APPOINTMENT -> {
                processAppointment(command)
            }
        }
    }




}