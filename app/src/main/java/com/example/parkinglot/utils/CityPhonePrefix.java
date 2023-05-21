package com.example.parkinglot.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CityPhonePrefix {

    /**
     * A map containing prefixes for various cities
     */
    private static final Map<String, String> cityPrefixMap;

    static {
        cityPrefixMap = new HashMap<>();
        cityPrefixMap.put("Taipei", "02");
        cityPrefixMap.put("Keelung", "02");
        cityPrefixMap.put("Taoyuan", "03");
        cityPrefixMap.put("Hsinchu", "03");
        cityPrefixMap.put("HualienCounty", "03");
        cityPrefixMap.put("YilanCounty", "03");
        cityPrefixMap.put("MiaoliCounty", "037");
        cityPrefixMap.put("Taichung", "04");
        cityPrefixMap.put("NantouCounty", "049");
        cityPrefixMap.put("Chiayi", "05");
        cityPrefixMap.put("ChiayiCounty", "05");
        cityPrefixMap.put("Tainan", "06");
        cityPrefixMap.put("Kaohsiung", "07");
        cityPrefixMap.put("PingtungCounty", "08");
        cityPrefixMap.put("TaitungCounty", "089");
        cityPrefixMap.put("KinmenCounty", "082");
        cityPrefixMap.put("LianjiangCounty", "0836");
    }

    private CityPhonePrefix() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Returns the phone prefix for a given city.
     *
     * @param city the name of the city
     * @return the phone prefix for the city
     * @throws IllegalArgumentException if the city is not found in the map
     */
    public static String getCityPhonePrefix(String city) {
        if (!cityPrefixMap.containsKey(city))
            throw new IllegalArgumentException("no such region");
        else
            return cityPrefixMap.get(city);
    }


    // Method to check if a given number is a match with a cellphone number
    private static boolean isMatchCellphone(String number) {
        String pattern = "09\\d{2}(\\d{6}|-\\d{3}-\\d{3})";
        Pattern cellPhonePattern = Pattern.compile(pattern);
        return cellPhonePattern.matcher(number).find();
    }


    // This method checks if the input number matches the pattern for a number in a specific region.
    private static boolean isMatchRegionNumber(String number) {
        String pattern = "(\\d{2,3}-?|\\(\\d{2,3}\\))\\d{3,4}-?\\d{4}";
        Pattern cellPhonePattern = Pattern.compile(pattern);
        return cellPhonePattern.matcher(number).find();
    }

    /**
     * Adds region number to a given phone number if it is not already present and checks if the number is valid.
     *
     * @param number The phone number to which the region number needs to be added
     * @param city   The city for which the region number needs to be added
     * @return The phone number with region number added if not already present.
     * @throws IllegalArgumentException If the provided phone number is not of valid length or format.
     */
    public static String autoAddRegionNumber(String number, String city) throws IllegalArgumentException {
        if (number.length() >= 7 && number.length() <= 10) {
            if (!isMatchCellphone(number) && !isMatchRegionNumber(number)) {
                return CityPhonePrefix.getCityPhonePrefix(city) + " - " + number;
            } else
                return number;
        } else {
            throw new IllegalArgumentException("illegal number");
        }
    }
}
