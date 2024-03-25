package com.HotelHawk.Spring.DataValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    public static boolean isValidDateFormat(String date) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static boolean isCheckoutDateAfterCheckinDate(String checkinDate, String checkoutDate) {
        if (!isValidDateFormat(checkinDate) || !isValidDateFormat(checkoutDate)) {
            return false; // Invalid date format
        }

        return checkinDate.compareTo(checkoutDate) < 0;
    }

    public static String check(String checkinDate, String checkoutDate){
        if (isCheckoutDateAfterCheckinDate(checkinDate, checkoutDate)) {
            return ("Checkout date is after checkin date and both dates are in valid format.");
        } else {
            return ("Invalid date format or checkout date is not after checkin date.");
        }
    }

    public static void main(String[] args) {
        String checkinDate = "2024-04-25";
        String checkoutDate = "2024-04-30";

        if (isCheckoutDateAfterCheckinDate(checkinDate, checkoutDate)) {
            System.out.println("Checkout date is after checkin date and both dates are in valid format.");
        } else {
            System.out.println("Invalid date format or checkout date is not after checkin date.");
        }
    }
}
