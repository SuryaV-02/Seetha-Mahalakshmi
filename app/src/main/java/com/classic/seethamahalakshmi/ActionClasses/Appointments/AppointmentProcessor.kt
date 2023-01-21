package com.classic.seethamahalakshmi.ActionClasses.Appointments

import android.util.Log
import com.classic.seethamahalakshmi.classfiles.AppointmentDetails
import com.classic.seethamahalakshmi.classfiles.Command
import com.classic.seethamahalakshmi.enums.AppointmentAction
import com.classic.seethamahalakshmi.misc.Resources
import com.classic.seethamahalakshmi.misc.TextToSpeechEngine.Companion.speakOut
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

var doctorNameRequiredAppointmentActions: ArrayList<String> =
    ArrayList<String>(listOf(AppointmentAction.BOOK.toString(), AppointmentAction.RESCHEDULE.toString()))

fun processAppointment(command: Command) {
    val appointmentDetails = AppointmentDetails()
    putDummyDataInAppointment(appointmentDetails)
    // TODO: Parse date-time details and use it as the primary key to cancel, reschedule and get info about appointments
    if (testAndSetProperAppointmentTime(command, appointmentDetails)) {
        fetchAndSetAppointmentDetails(appointmentDetails)
        when(command.actionInfType) {
            AppointmentAction.CANCEL.toString() -> {
                cancelAppointment(appointmentDetails)
            }
            AppointmentAction.GETINFO.toString() -> {
                speakAppointmentDetailsIfExists(appointmentDetails)
            }
            AppointmentAction.BOOK.toString() -> {
                checkAndBookAppointment(appointmentDetails)
            }
            AppointmentAction.RESCHEDULE.toString() -> {
                rescheduleAppointment(appointmentDetails)
            }
        }
    }

}

fun putDummyDataInAppointment(appointmentDetails: AppointmentDetails) {
    appointmentDetails.clinicName = "kamala clinic"
    appointmentDetails.diagnosisName = "Pediatrician"
    appointmentDetails.Id = 123456.toString()
}

fun rescheduleAppointment(appointmentDetails: AppointmentDetails) {
    // TODO: Check if appointment is scheduled with doctor at specified time
    val isAppointmentScheduledAtSpecificTime = true
    if (isAppointmentScheduledAtSpecificTime) {
        checkAndBookAppointment(appointmentDetails)
    } else {
        speakOut("Sorry, there is no appointment scheduled at the given time.")
    }
}

fun checkAndBookAppointment(appointmentDetails: AppointmentDetails) {
    // TODO: Send Appointment to doctor
    speakOut("Requesting appointment " +
            "with ${appointmentDetails.doctorName}" +
            "at ${appointmentDetails.clinicName} " +
            "for ${appointmentDetails.diagnosisName}")
}

fun speakAppointmentDetailsIfExists(appointmentDetails: AppointmentDetails) {
    if (appointmentDetails.Id != null) {
        speakOut("Appointment has already been scheduled " +
                "with ${appointmentDetails.doctorName}" +
                "at ${appointmentDetails.clinicName} " +
                "for ${appointmentDetails.diagnosisName}")
    }
}

fun fetchAndSetAppointmentDetails(appointmentDetails: AppointmentDetails) : Boolean {
//    TODO fetch And Set Appointment details if exist and return true, else return false
    return true
}

fun cancelAppointment(appointmentDetails: AppointmentDetails) {
    val appointmentFound = true
    if(appointmentFound) {
        // TODO: Logic to delete appointment from database
        speakOut("Appointment with doctor ${appointmentDetails.doctorName} " +
                "at ${appointmentDetails.clinicName} cancelled successfully!")
    } else {
        speakOut("Sorry, there is no appointment scheduled at the given time.")
    }
}

fun testAndSetProperAppointmentTime(
    command: Command,
    appointmentDetails: AppointmentDetails
): Boolean {
    if (!timeFoundInAppointment(command, appointmentDetails)) {
        speakOut("Please specify the time for appointment " + command.actionInfType)
        return false
    }
    if (doctorNameRequiredAppointmentActions.contains(command.actionInfType) && !doctorNameFoundInAppointment(command, appointmentDetails)) {
        return false
    }
    return true
}

/*
returns false if doctor name is not there in command
else sets doctor name @AppointmentDetails -> doctorName and returns true
 */
fun doctorNameFoundInAppointment(command: Command, appointmentDetails: AppointmentDetails): Boolean {
    for (i in 0 until command.tokens.count() - 1) {
        Log.i("SKHST_3254" , command.tokens.elementAt(i) + " -> " + command.tokens.elementAt(i+1))
        if("doctor" == command.tokens.elementAt(i)) {
            appointmentDetails.doctorName = command.tokens.elementAt(i+1)
            return true
        }
    }
    return false
}

/*
returns false if date, time and session specimen aren't found in command
else computes and sets timeInMills @AppointmentDetails -> time and returns true
 */
fun timeFoundInAppointment(command: Command, appointmentDetails: AppointmentDetails): Boolean {
    var timeSpecimen: String? = null
    var sessionSpecimen: String? = null
    var dateSpecimen: String? = null
    for (token in command.tokens) {
        if (Pattern.matches(Resources.timePattern, token)) {
            timeSpecimen = token
        }
        if (Pattern.matches(Resources.sessionPattern, token)) {
            sessionSpecimen = if (token.startsWith("a") || token.startsWith("A"))
                "AM"
            else
                "PM"
        }
        if (Pattern.matches(Resources.datePattern, token)) {
            dateSpecimen = token
        }
        Log.i("SKHST_95623", "$token $timeSpecimen $sessionSpecimen $dateSpecimen")
    }
    if (timeSpecimen.isNullOrEmpty() || sessionSpecimen.isNullOrEmpty() || dateSpecimen.isNullOrEmpty()) {
        return false
    }
    appointmentDetails.time = parseAndSetAppointmentDetails(timeSpecimen, sessionSpecimen, dateSpecimen)
    return true
}

fun parseAndSetAppointmentDetails(
    timeSpecimen: String,
    sessionSpecimen: String,
    dateSpecimen: String
): Long? {
    val formatter = SimpleDateFormat("dd/M hh:mm a", Locale.ENGLISH) //dd-M-yyyy hh:mm a
    val dateInString = "$dateSpecimen $timeSpecimen $sessionSpecimen" //22-01-2015 10:15 AM
    val date: Date = formatter.parse(dateInString)
    return date.time
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
