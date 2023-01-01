package com.classic.seethamahalakshmi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Resources {
    public static ArrayList<Category> globalKeywordsList;
    public static void initializeResources() {
        Log.i("SKHST 21386" , "Initialising resources");
        globalKeywordsList = new ArrayList<>();
        initializeAppointmentData();
        initializePillsData();
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