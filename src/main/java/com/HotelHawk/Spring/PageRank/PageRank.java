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
        photos.put("Calgary","https://cf.bstatic.com/xdata/images/city/600x600/653231.jpg?k=526fe3dd8fc78cbaccaee3be9e675b624e73b666ab757ecb0ebf31c7f7d96264&o=");
        photos.put("Toronto","https://cf.bstatic.com/xdata/images/city/600x600/971990.jpg?k=6d52fe4a57a984e2d540e3d7a1910f8a76fda3a57708faddd74e2109c3344b5e&o=");
        photos.put("Vancouver","https://q-xx.bstatic.com/xdata/images/city/170x136/653281.jpg?k=f290f027412c3954eba82a85cf40eaa703bcbb30b67fa5f2087c7db1eb406262&o=");
        photos.put("Montreal","");
        photos.put("Ottawa","");
        photos.put("Hamilton","");
        photos.put("Edmonton","");
        photos.put("Winnipeg","");
        photos.put("Windsor","");
        photos.put("Halifax","");

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
