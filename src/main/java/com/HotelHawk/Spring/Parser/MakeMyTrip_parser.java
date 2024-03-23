package com.HotelHawk.Spring.Parser;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;



public class MakeMyTrip_parser {

	public static Hashtable<String,String[]> Make_My_Trip_Parser(Document document,String city) throws FileNotFoundException
	{

		ArrayList<String> links = new ArrayList<String>();
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> price = new ArrayList<String>();



		Elements e= document.getElementsByAttributeValue("itemtype", "http://schema.org/Hotel");
		Hashtable<String,String[]> hb = new Hashtable<String,String[]>();//title hotel details

		for(Element l:e){
			// Getting links for the hotel
			String[] hoteldetails = new String[4];

			String str1 = l.firstChild().attr("href");
			if (!links.contains(str1)){
				links.add(str1);
				hoteldetails[0]=str1;
				// System.out.println(str1);
			}
			// Getting name of the hotel
			Elements ele2 = l.getElementsByClass("wordBreak appendRight10");
			String str2 = ele2.text();
			if (!title.contains(str2)){
				title.add(str2);

				// System.out.println(str2);
			}
			// Getting final price of the hotel
			Element ele3 = l.getElementById("hlistpg_hotel_shown_price");
			String str3 = ele3.text();
			if (!price.contains(str3)){
				price.add(str3);
				hoteldetails[1]=str3;
				// System.out.println(str3);
			}

			//saving the images of the hotels
			String str4 = l.getElementsByAttributeValue("alt","hotelImg").attr("src")+" "+l.getElementsByAttributeValue("alt","hotel_image_1").attr("src")+" "+l.getElementsByAttributeValue("alt","hotel_image_2").attr("src")+" "+l.getElementsByAttributeValue("alt","hotel_image_3").attr("src");
			String str5 =  l.getElementsByAttributeValue("itemprop","ratingValue").text();

			hoteldetails[2]=str4;
			hoteldetails[3]=str5;


			hb.put(str2,hoteldetails);
			if(hb.size()==11)
			{
				break;
			}
		}
		//  System.out.println(e.get(0).getElementsByAttributeValue("alt","hotelImg"));
		save_links(links);
		return hb;

	}
	public static void save_links(ArrayList<String> links) throws FileNotFoundException {
		//System.out.println("https://www.booking.com/hotel/ca/days-inn-toronto-downtown.en-gb.html?label=gen173nr-1FCBcoggI46AdIM1gEaCeIAQGYAQm4ARfIAQzYAQHoAQH4AQmIAgGoAgO4Apntzq8GwAIB0gIkZWMxZTk2NDItNmMxMi00YTFiLTliOTYtMDAwNGYxZmFlOTAz2AIG4AIB&aid=304142&ucfs=1&arphpl=1&dest_id=-574890&dest_type=city&group_adults=2&req_adults=2&no_rooms=1&group_children=0&req_children=0&hpos=1&hapos=1&sr_order=popularity&srpvid=5182138d13ba0087&srepoch=1710470811&from_sustainable_property_sr=1&from=searchresults".length());
		String path = System.getProperty("user.dir");
		PrintWriter pr = new PrintWriter(path+"hotelsca_links");
		for (int i=0; i<links.size() ; i++){
			pr.println(links.get(i));
		}
		pr.close();
	}

	public static void convert_json(Hashtable<String,Hashtable> js) throws FileNotFoundException {
		JSONObject main_json = new JSONObject();
		ArrayList<JSONObject> ar = new ArrayList<JSONObject>();
		Enumeration<String> e = js.keys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			Hashtable<String, String[]> jj = js.get(key);
			Enumeration<String> yy = jj.keys();

			while (yy.hasMoreElements()) {
				String key1 = yy.nextElement();
				String[] alldetails = jj.get(key1);
				System.out.println(key1);
				JSONObject json = new JSONObject();
				json.put("Name", key1);
				json.put("Price", alldetails[1]);
				json.put("Review", alldetails[3]);
				json.put("Images", alldetails[2]);


				ar.add(json);

			}
			//main_json.put("Make_My_Trip",new JSONArray(ar));
			String path = System.getProperty("user.dir");
			PrintWriter pw= new PrintWriter(path+"\\MakeMy--Trip_json");
			pw.println(new JSONArray(ar).toString());
			pw.close();
			// Print and display the Rank and Name
		}
	}
}