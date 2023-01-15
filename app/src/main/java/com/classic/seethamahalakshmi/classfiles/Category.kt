package com.classic.seethamahalakshmi.classfiles

import com.classic.seethamahalakshmi.enums.CategoryType

class Category (categoryTypeArg : CategoryType, categoryListArg : ArrayList<String>) {
    var categoryType : CategoryType
    var categoryList: ArrayList<String>

    init {
        categoryType = categoryTypeArg
        categoryList = categoryListArg
    }
}