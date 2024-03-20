package com.HotelHawk.Spring.MergerJSONdata;

import org.json.JSONObject;

import java.io.*;


public class MergeData {
    public static void merge(String cityname) throws IOException {
        File file1=new File(System.getProperty("user.dir")+"\\booking_json");
        File file2=new File(System.getProperty("user.dir")+"\\hotelsca_json");
        BufferedReader br1=new BufferedReader(new FileReader(file1));
        BufferedReader br2=new BufferedReader(new FileReader(file2));
        String data1= (br1.readLine());
        String data2= (br2.readLine());
        JSONObject json=new JSONObject();
        json.put("booking",data1);
        json.put("hotelsca",data2);
//        json.put("mmt","data");
        PrintWriter pw= new PrintWriter("finaldata");
        pw.println(json.toString());
        pw.close();


        //writing cityname_old data file
        PrintWriter p= new PrintWriter(cityname.concat("_finaldata"));
        p.println(json.toString());
        p.close();
    }
    public static void main(String[] args) throws IOException {

    }
}
