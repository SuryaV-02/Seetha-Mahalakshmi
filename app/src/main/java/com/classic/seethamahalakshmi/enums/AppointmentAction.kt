package com.classic.seethamahalakshmi.enums

enum class AppointmentAction() : Action {
    BOOK() {
        override val actionName: String
            get() = "book"
        override val keywords: Array<String>
            get() = arrayOf("book", "schedule", "create", "make")
    },
    CANCEL() {
        override val actionName: String
            get() = "cancel"
        override val keywords: Array<String>
            get() = arrayOf("cancel", "delete")
    },
    RESCHEDULE() {
        override val actionName: String
            get() = "reschedule"
        override val keywords: Array<String>
            get() = arrayOf("reschedule")
    },
    GETINFO() {
        override val actionName: String
            get() = "get info"
        override val keywords: Array<String>
            get() = arrayOf("say", "details", "show", "status")
    }
}