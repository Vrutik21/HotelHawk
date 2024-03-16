package com.HotelHawk.Spring.Parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Booking_parser {
    public static String checkin_out="&checkin=2024-03-21&checkout=2024-03-22&dest_id=-574890&dest_type=city";
    public static ArrayList<String> links= new ArrayList<String>();
    public static void extract_links() throws IOException {
        File file=new File("booking_links");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String st;
        while((st=br.readLine())!=null){
            links.add(st);
        }
        extract_hotels(links, checkin_out);
    }
    public static void extract_hotels(ArrayList<String> links, String checkin_out) throws IOException {
        HashMap<String,ArrayList<String>> hotels=new HashMap<String,ArrayList<String>>();
        for(String link:links){
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
                    System.out.println(d.title());
                    temp_data.add(d.title());
                    //getting prices
                    Elements e = d.getElementsByTag("tr");
                    String temp_price = "";
                    int min=0;
                    if (e.hasAttr("data-hotel-rounded-price")) {
                        //Elements ef=e.getElementsByAttribute("data-hotel-rounded-price");
                        for (Element l : e) {
                            String pri = l.attr("data-hotel-rounded-price");
                            if (pri!="" && Integer.parseInt(pri)>min){
                                min=Integer.parseInt(pri);
                            }
                        }
                        System.out.println(min);
                        System.out.println("Available");
                    } else {
                        System.out.println("Not Available");
                    }
                    temp_data.add(Integer.toString(min));
                    ///reviews
                    String temp_reviews = "";
                    Elements reviews = d.getElementsByAttributeValueContaining("data-testid", "review-score-component");
                    //System.out.println(reviews);
                    for (Element l : reviews) {
                        String pri = l.getElementsByTag("span").attr("aria-label");
                        Elements pnt = l.getElementsByClass("a3b8729ab1 d86cee9b25");
                        if (pri != null) {
                            System.out.println("Reviews are");
                            //System.out.println(pri);
                            System.out.println(pnt.attr("aria-label"));
                            temp_reviews += (String) pri;
                            temp_reviews += (String) pnt.attr("aria-label") + " ";
                        } else {
                            System.out.println("no reviews");
                        }
                    }
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

    }

    public static void main(String[] args) throws IOException {
        /// as user hit search button, crawler is called, links are added to txt file, then parser is called for getting hotel data

        extract_links();
        extract_hotels(links,checkin_out);
    }
}

