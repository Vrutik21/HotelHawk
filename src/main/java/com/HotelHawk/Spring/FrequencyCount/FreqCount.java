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
    public static void fc_booking(ArrayList<String> cities) throws IOException {
        for(String cityname:cities){
            String url="https://www.booking.com//";
            String t=url+"/"+cityname+"-"+"Canada";
            Connection con= Jsoup.connect(t);
            Document doc=con.get();
            String text= doc.text();
            int cnt = countOccurrences(text, cityname);
            if(count.containsKey(cityname)){
                count.put(cityname,count.get(cityname)+cnt);
            }
            else{
                count.put(cityname,cnt);
            }
        }

    }

    public static void fc_hotels(ArrayList<String> cities){
        WebDriver driver=new ChromeDriver();
        for(String cityname:cities){
            String url= "https://ca.hotels.com/Hotel-Search?adults=2&d1=2024-04-21&d2=2024-04-22&destination=".concat(cityname.toLowerCase()).concat("%2C%20".concat(states.get(cityname.toLowerCase())).concat("%2C%20Canada"));
            driver.get(url);
            driver.manage().window();
            Document doc= Jsoup.parse(driver.toString());
            String text= doc.text();
            int cnt = countOccurrences(text, cityname);
            if(count.containsKey(cityname)){
                count.put(cityname,count.get(cityname)+cnt);
            }
            else{
                count.put(cityname,cnt);
            }
        }
        driver.close();

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
        ArrayList<String> cities=new ArrayList<String>();
        cities.add("Toronto");
        states.put("toronto","Ontario");
        cities.add("Calgary");
        states.put("calgary","Alberta");
        cities.add("Vancouver");
        states.put("vancouver","British%20Columbia");
        cities.add("Montreal");
        states.put("montreal","Quebec");
        cities.add("OTTAWA");
        states.put("ottawa","Ontario");
        cities.add("WINDSOR");
        states.put("windsor","Ontario");
        cities.add("HALIFAX");
        states.put("halifax","Ontario");
        cities.add("WINNIPEG");
        states.put("winnipeg","Ontario");
        cities.add("EDMONTON");
        states.put("edmonton","Ontario");
        cities.add("HAMILTON");
        states.put("hamilton","Ontario");

        fc_booking(cities);
        fc_hotels(cities);
        print();
        save();

    }
}
