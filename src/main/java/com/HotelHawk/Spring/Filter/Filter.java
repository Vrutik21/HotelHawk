package com.HotelHawk.Spring.Filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Filter {
    public static void initialize(String cityname, int price, double reviews) throws IOException {
        File file= new File((cityname.toLowerCase()).concat("_finaldata"));
        BufferedReader br= new BufferedReader(new FileReader(file));
        String line= br.readLine();
        JSONArray json_array= new JSONArray(line);

        for(int i=0;i< json_array.length();i++){

            if((Integer.parseInt(json_array.getJSONObject(i).getString("MinPrice "))<=price) && (Double.parseDouble(json_array.getJSONObject(i).getString("Reviews "))<=reviews)){

            }
        }
    }



    public static void main(String[] args) {


    }
}
