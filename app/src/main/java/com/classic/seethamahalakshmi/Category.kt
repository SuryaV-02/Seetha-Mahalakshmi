package com.classic.seethamahalakshmi

class Category (categoryTypeArg : CategoryType, categoryListArg : ArrayList<String>) {
    var categoryType : CategoryType
    var categoryList: ArrayList<String>

    init {
        categoryType = categoryTypeArg
        categoryList = categoryListArg
    }
}