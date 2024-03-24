package com.HotelHawk.Spring.PageRank;

import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
///////
//////using Priority Queue as MAX HEAP for PAGE RANKING using Frequency Count
public class PageRank {
    public static HashMap<Integer, ArrayList<String>> inverted_map=new HashMap<Integer,ArrayList<String>>();
    public static PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>(Collections.reverseOrder());

    public static void read_file() throws IOException {
        File file=new File(System.getProperty("user.dir")+"\\freqcount");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=br.readLine();
        make_inverted_map(temp);

    }
    public static void make_inverted_map(String temp) throws FileNotFoundException {
        String s= temp.substring(1,temp.length()-1);
        String[] comma=s.split(",");
        for(String t:comma){
            String[] spl= t.split(":");
//            System.out.println(spl[0]);
//            System.out.println(spl[1].substring(1,spl[1].length()-1));
            String cname= spl[0].substring(1,spl[0].length()-1);
            int c=Integer.parseInt(spl[1].substring(1,spl[1].length()-1));
            if(inverted_map.containsKey(c)){
                inverted_map.get(c).add(cname);
            }
            else{
                ArrayList<String> ar=new ArrayList<String>();
                ar.add(cname);
                inverted_map.put(c,ar);
            }
        }
        heapify();
    }
    public static void heapify() throws FileNotFoundException {

        for(int temp:inverted_map.keySet()){
            pQueue.add(temp);
        }
        get_top_3_cities();
    }
    public static void get_top_3_cities() throws FileNotFoundException {
        //JSONObject json=new JSONObject();
        HashMap<String,String> photos= new HashMap<String,String>();
        photos.put("Calgary","https://images.unsplash.com/photo-1608872737454-1a7b8bdbddca?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTh8fGNhbGdhcnl8ZW58MHx8MHx8fDA%3D");
        photos.put("Toronto","https://images.unsplash.com/photo-1453989320917-7e755abe0453?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MzF8fHRvcm9udG98ZW58MHx8MHx8fDA%3D");
        photos.put("Vancouver","https://images.unsplash.com/photo-1567705781280-0e03ffb323f4?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8dmFuY291dmVyfGVufDB8fDB8fHww");
        photos.put("Montreal","https://images.unsplash.com/photo-1559407020-328eb3b23a0a?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjZ8fG1vbnRyZWFsfGVufDB8fDB8fHww");
        photos.put("Ottawa","https://images.unsplash.com/photo-1581225988470-afc564b38ba4?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8b3R0YXdhfGVufDB8fDB8fHww");
        photos.put("Hamilton","https://images.unsplash.com/photo-1597464718291-2c643690d7ab?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aGFtaWx0b258ZW58MHx8MHx8fDA%3D");
        photos.put("Edmonton","https://images.unsplash.com/photo-1590721405710-f8442b3edae9?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fGVkbW9udG9ufGVufDB8fDB8fHww");
        photos.put("Winnipeg","https://images.unsplash.com/photo-1610595161872-a2118a7940f0?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fHdpbm5pcGVnfGVufDB8fDB8fHww");
        photos.put("Windsor","https://images.unsplash.com/photo-1673828780152-48ba1d0f4afe?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8ODR8fHdpbmRzb3J8ZW58MHx8MHx8fDA%3D");
        photos.put("Halifax","https://images.unsplash.com/photo-1601429661559-2d7d1e9b4ed2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8aGFsaWZheHxlbnwwfHwwfHx8MA%3D%3D");

        ArrayList<String> data=new ArrayList<String>();
        PrintWriter pw= new PrintWriter("pagerank");
        int count=0;
        pw.println("{");
        while(count<5){
            int polls=pQueue.poll();

            for(String s:inverted_map.get(polls)){
                System.out.println(s.concat(" ").concat(Integer.toString(polls)));
                //json.put(s,Integer.toString(polls));
                String print= "\"".concat(s.concat("\"").concat(":").concat("\"").concat(Integer.toString(polls)).concat(" ").concat(photos.get(s)).concat("\","));
                if (count!=2){
                    pw.println(print);
                }
                else{
                    pw.println(print.substring(0,print.length()-1));
                }
            }
            count+=1;
        }
        pw.println("}");

//        pw.println(json.toString());
        pw.close();

    }
    public static void main(String[] args) throws IOException {
        read_file();

    }

}
