package com.HotelHawk.Spring.SearchFrequency;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class SearchFreq {
    public static HashMap<String,Integer> map=new HashMap<String, Integer>();
    public static void get_old_data(){

    }
    public static void update(){


    }
    public static void save() throws FileNotFoundException {
        PrintWriter pr = new PrintWriter("searcfreq.txt");
        for (int i=0; i<map.size() ; i++){
            pr.println(map.get(i));
        }
        pr.close();
    }
    public static void initialize(String cityname){

    }
    public static void main(String[] args){

    }
}
