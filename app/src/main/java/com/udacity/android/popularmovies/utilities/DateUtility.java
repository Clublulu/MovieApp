package com.udacity.android.popularmovies.utilities;

import java.util.HashMap;
import java.util.Map;

public final class DateUtility {

    private static Map<String, String> months = new HashMap<String, String>() {{
        put("01", "January");   put("02", "February");
        put("03", "March");   put("04", "April");
        put("05", "May");   put("06", "June");
        put("07", "July");   put("08", "August");
        put("09", "September");   put("10", "October");
        put("11", "November");   put("12", "December");
    }};

    private static final int YEAR_POSITION_START = 0;
    private static final int YEAR_POSITION_END = 4;

    private static final int MONTH_POSITION_START = 5;
    private static final int MONTH_POSITION_END = 7;

    private static final int DAY_POSITION_START = 8;


    public static String formatReleaseDate(String jsonReleaseDate) {
        String year = jsonReleaseDate.substring(YEAR_POSITION_START, YEAR_POSITION_END);
        String month = jsonReleaseDate.substring(MONTH_POSITION_START, MONTH_POSITION_END);
        String day = jsonReleaseDate.substring(DAY_POSITION_START, jsonReleaseDate.length());

        if (isDaySingleDigit(day)) {
            day = day.substring(1);
        }

        return months.get(month) + " " + day + ", " + year;
    }


    private static boolean isDaySingleDigit(String day) {
        return day.contains("0");
    }

}
