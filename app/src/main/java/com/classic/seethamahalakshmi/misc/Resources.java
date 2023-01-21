package com.classic.seethamahalakshmi.misc;

import android.util.Log;

import com.classic.seethamahalakshmi.classfiles.Category;
import com.classic.seethamahalakshmi.enums.CategoryType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Resources {
    public static ArrayList<Category> globalKeywordsList;
    public static ArrayList<String> dayTimes;
    public static String timePattern = "[0-9]{2}:[0-9]{2}";
    public static String datePattern = "[0-9]{2}/[0-9]{2}";
    public static String sessionPattern = "(a|p){1}.{1}m{1}.*";
    public static void initializeResources() {
        Log.i("SKHST 21386" , "Initialising resources");
        globalKeywordsList = new ArrayList<>();
        initializeMiscData();
        initializeAppointmentData();
        initializePillsData();
    }

    private static void initializeMiscData() {
        initializeDayTimes();
    }

    private static void initializeDayTimes() {
        dayTimes = new ArrayList<>(List.of("yesterday", "today", "tomorrow"));
    }

    private static void initializeAppointmentData() {
        ArrayList<String> appointmentKeyWords = new ArrayList<>(List.of("appointment", "meeting"));
        Category appointmentCategory = new Category(CategoryType.APPOINTMENT, appointmentKeyWords);
        globalKeywordsList.add(appointmentCategory);
    }

    private static void initializePillsData() {
        ArrayList<String> pillsKeyWords = new ArrayList<>(List.of("pills", "tablet", "medicine"));
        Category pillsCategory = new Category(CategoryType.PILLS, pillsKeyWords);
        globalKeywordsList.add(pillsCategory);
    }
}