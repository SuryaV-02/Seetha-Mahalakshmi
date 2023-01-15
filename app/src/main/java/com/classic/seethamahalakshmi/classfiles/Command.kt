package com.classic.seethamahalakshmi.classfiles

import com.classic.seethamahalakshmi.enums.CategoryType

class Command(rawTextArg: String) {
    var dynamicText: String? = null
    lateinit var tokens: MutableList<String>
    lateinit var categoryType : CategoryType
    var result = 0
    var rawText: String
    init {
        rawText = rawTextArg
    }
}