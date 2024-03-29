package com.HotelHawk.Spring.DataValidation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {
    private static final String[] DATE_FORMATS = {
            "yyyy-MM-dd", "dd-MM-yyyy", "MM-dd-yyyy", "yyyy/MM/dd", "dd/MM/yyyy", "MM/dd/yyyy",
            "yyyy.MM.dd", "dd.MM.yyyy", "MM.dd.yyyy", "yyyyMMdd", "ddMMyyyy", "MMddyyyy"};

    private static final String URL_REGEX = "^(https?|ftp)://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/\\S*)?$";

    public static boolean isValidURL(String url) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public static boolean isValidDateFormat(String date) {
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false);
                Date parsedDate = sdf.parse(date);
                return date.equals(sdf.format(parsedDate)); // Check if the parsed date matches the input
            } catch (ParseException e) {
                // Parsing failed, try the next format
            }
        }
        return false; // None of the formats matched
    }

    public static boolean isValidCity(String city) {
        return city.matches("[a-zA-Z]+");
    }


    public static boolean isCheckoutDateAfterCheckinDate(String checkinDate, String checkoutDate) {
        if (!isValidDateFormat(checkinDate) || !isValidDateFormat(checkoutDate)) {
            return false; // Invalid date format
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date checkin = sdf.parse(checkinDate);
            Date checkout = sdf.parse(checkoutDate);
            return checkin.before(checkout); // Check if checkout date is after checkin date
        } catch (ParseException e) {
            return false; // Parsing error, invalid date format
        }
    }

    public static String[] check(String checkinDate, String checkoutDate, String cityName) {
        if (isCheckoutDateAfterCheckinDate(checkinDate, checkoutDate) && isValidCity(cityName)) {
            return new String[]{checkinDate, checkoutDate};
        } else {
            return new String[]{};
        }
    }

    public static void main(String[] args) {
        String checkinDate = "25-04-2024"; // Example date input
        String checkoutDate = "30-04-2024"; // Example date input
        String cityname= "";

        String[] result = check(checkinDate, checkoutDate,cityname);
        if (result.length == 2) {
            System.out.println("Valid dates: Checkin Date - " + result[0] + ", Checkout Date - " + result[1]);
        } else {
            System.out.println("Invalid dates or checkout date is not after checkin date.");
        }
    }
}

