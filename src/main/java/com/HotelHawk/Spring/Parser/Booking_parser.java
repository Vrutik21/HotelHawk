
package com.HotelHawk.Spring.Parser;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.servlet.tags.ArgumentAware;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Booking_parser {
    public static String checkin_out="&checkin=2024-03-21&checkout=2024-03-22&dest_id=-574890&dest_type=city";
    public static ArrayList<String> links= new ArrayList<String>();
    public static HashMap<String,ArrayList<String>> hotels=new HashMap<String,ArrayList<String>>();
    public static void extract_links(String cityname) throws IOException {
        File file=new File("booking_links");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String st;
        while((st=br.readLine())!=null){
            links.add(st);
        }
        extract_hotels(links, checkin_out,cityname);
    }
    public static void extract_hotels(ArrayList<String> links, String checkin_out, String cityname) throws IOException {

        for(String link:links){
            System.out.println(link);
            if (link.contains("city")){
                ArrayList<String> temp_data=new ArrayList<String>();
                int ind=link.lastIndexOf("city");
                String final_link= link.substring(0,ind+4).concat(checkin_out.concat(link.substring(ind+4,link.length())));
                Connection c= Jsoup.connect(final_link);
                Document d=c.get();
                ArrayList<String> prices=new ArrayList<String>();
                ////checking if website has status code of 200 or not
                if (c.response().statusCode()==200)
                    if (!hotels.containsKey(d.title())) {
                        System.out.println(final_link);
                        //System.out.println(d.title().substring(0,d.title().indexOf('-')));
                        temp_data.add(d.title().substring(0,d.title().indexOf(',')));
                        //getting prices
                        Elements e = d.getElementsByTag("tr");
                        String temp_price = "";
                        int min=1000000;
                        if (e.hasAttr("data-hotel-rounded-price")) {
                            //Elements ef=e.getElementsByAttribute("data-hotel-rounded-price");
                            for (Element l : e) {
                                String pri = l.attr("data-hotel-rounded-price");
                                if (pri!="" && Integer.parseInt(pri)<min){
                                    min=Integer.parseInt(pri);
                                }
                            }
                            System.out.println(min);
                            System.out.println("Available");
                            temp_data.add(Integer.toString(min));
                        } else {
                            System.out.println("Not Available");
                            temp_data.add(("Not Available"));
                        }
                        //temp_data.add(Integer.toString(min));
                        ///reviews
                        String temp_reviews = "";
                        Elements rev=d.getElementsByClass("a3b8729ab1 d86cee9b25");
                        System.out.println("Shrey");
                        for(Element ef:rev){
                            System.out.println(ef.text());
                            temp_reviews=ef.text();
                        }
//                    Elements reviews = d.getElementsByAttributeValueContaining("data-testid", "review-score-component");
//                    //System.out.println(reviews);
//                    for (Element l : reviews) {
//                        String pri = l.getElementsByTag("span").attr("aria-label");
//                        Elements pnt = l.getElementsByClass("a3b8729ab1 d86cee9b25");
//                        if (pri != null) {
//                            System.out.println("Reviews are");
//                            //System.out.println(pri);
//                            System.out.println(pnt.attr("aria-label"));
//                            temp_reviews += (String) pri;
//                            temp_reviews += (String) pnt.attr("aria-label") + " ";
//                        } else {
//                            System.out.println("no reviews");
//                        }
//                    }
                        temp_data.add(temp_reviews);
                        //images
                        String temp_imgs = "";
                        Elements imgs = d.getElementsByAttribute("data-thumb-url");
                        //System.out.println(imgs);
                        for (Element l : imgs) {
                            String img_link = l.attr("data-thumb-url");
                            System.out.println(img_link);
                            temp_imgs += img_link + " ";
                        }
                        temp_data.add(temp_imgs);
                        hotels.put(temp_data.get(0),temp_data);
                    }
            }
            inverted_index_data(cityname);
            convert_json();
            }


    }
    public static void inverted_index_data(String cityname) throws FileNotFoundException {
        ArrayList<String> names= new ArrayList<String>();
        for(String s:hotels.keySet()){
            names.add(s);
        }
        PrintWriter pw= new PrintWriter("inverted_index_data");
        pw.println(cityname.concat(":").concat(String.join(", ", names)));
        pw.close();

    }

    public static void convert_json() throws FileNotFoundException {
        JSONObject main_json=new JSONObject();
        ArrayList<JSONObject> ar=new ArrayList<JSONObject>();
        for(String s:hotels.keySet()){
            JSONObject json=new JSONObject();
            json.put("Name",hotels.get(s).get(0));
            json.put("Price",hotels.get(s).get(1));
            json.put("Review",hotels.get(s).get(2));
            json.put("Images",hotels.get(s).get(3));
            ar.add(json);
        }
        //main_json.put("Booking",new JSONArray(ar));
        PrintWriter pw= new PrintWriter("booking_json");
        pw.println(new JSONArray(ar).toString());
        pw.close();
    }

    public static void main(String[] args) throws IOException {
        /// as user hit search button, crawler is called, links are added to txt file, then parser is called for getting hotel data

//        extract_links();
//        extract_hotels(links,checkin_out);
    }
}
