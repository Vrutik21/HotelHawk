package com.HotelHawk.Spring.FrequencyCount;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class FreqCount {
    public static HashMap<String,Integer> count= new HashMap<String, Integer>();
    public static HashMap<String,String> states= new HashMap<String,String>();
    public static void initialize(String cityname,String searchString){
        states.put("toronto","Ontario");
        states.put("calgary","Alberta");
        states.put("vancouver","British%20Columbia");
        states.put("montreal","Quebec");
        states.put("ottawa","Ontario");
        states.put("windsor","Ontario");
        states.put("halifax","Ontario");
        states.put("winnipeg","Ontario");
        states.put("edmonton","Ontario");
        states.put("hamilton","Ontario");
    }
    private static int countOccurrences(String text, String searchString) {
        int count = 0;
        int index = 0;
        while ((index = text.toLowerCase().indexOf(searchString.toLowerCase(), index)) != -1) {
            count++;
            index += searchString.length();
        }
        return count;
    }
    public static String fc_booking(String cityname, String searchString) throws IOException {
        String text= searchString;
        int cnt = countOccurrences(text, cityname);
        return Integer.toString(cnt);
    }
    public static String fc_hotels(String cityname, String searchString){
        String text= searchString;
        int cnt = countOccurrences(text, cityname);
        return Integer.toString(cnt);

    }
    public static void fc_mmt(){

    }
    public static void print(){
        for(String s:count.keySet()){
            System.out.println(s);
            System.out.println(count.get(s));
        }
    }
    public static void save() throws FileNotFoundException {

        PrintWriter pw= new PrintWriter("freqcount");
        ArrayList<JSONObject> jsonarray =new ArrayList<JSONObject>();
        for(String s:count.keySet()){
            JSONObject json=new JSONObject();
            json.put("City",s);
            json.put("FreqCount",Integer.toString(count.get(s)));
            jsonarray.add(json);
        }
        pw.println(new JSONArray(jsonarray).toString());
        pw.close();
    }
    public static void main(String[] args) throws IOException {

    }
}
