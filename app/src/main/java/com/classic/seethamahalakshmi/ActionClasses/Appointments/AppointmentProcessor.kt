package com.classic.seethamahalakshmi.ActionClasses.Appointments

import com.classic.seethamahalakshmi.classfiles.AppointmentDetails
import com.classic.seethamahalakshmi.classfiles.Command
import com.classic.seethamahalakshmi.misc.TextToSpeechEngine.Companion.speakOut


fun processAppointment(command: Command) {
        val appointmentDetails = AppointmentDetails()
        // TODO: Parse date-time details and use it as the primary key to cancel, reschedule and get info about appointments
////        appointmentDetails.time = processAppointmentTime(command)
        appointmentDetails.time = getAppointmentTime(command)

    }

fun getAppointmentTime(command: Command): Long? {
    speakOut("Please specify the time for appointment " + command.actionInfType)
    return 0L
}

//    fun processAppointmentTime(command: Command): String {
//        var date: String
//        var time : String
//        for ( token in command.tokens) {
//            if (Pattern.matches(Resources.timePattern, token)) {
//                time = token
//            }
//            if(Pattern.matches(Resources.dayPattern, token))
//        }
//    }
