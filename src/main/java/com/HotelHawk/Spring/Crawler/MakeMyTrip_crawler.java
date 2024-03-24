package com.HotelHawk.Spring.Crawler;

import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;

import com.HotelHawk.Spring.Parser.MakeMyTrip_parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class MakeMyTrip_crawler {

    public static Hashtable<String, Hashtable> extractCities(String[] cities,String checkin,String checkout) throws FileNotFoundException {
        Hashtable<String,Hashtable> hs = new Hashtable<>();
        String[] incheck = checkin.split("-");
        String[] outcheck=checkout.split("-");
        for (String city : cities) {
            // Create a new instance of the Chrome driver
            WebDriver driver = new ChromeDriver();

            // Open a website using Selenium
            driver.get("https://www.makemytrip.com/hotels/hotel-listing/?checkin="+incheck[1]+""+incheck[2]+""+incheck[0]+"&city=CT" +city.substring(0, 5).toUpperCase()+ "&checkout="+outcheck[1]+""+outcheck[2]+""+outcheck[0]+"&roomStayQualifier=2e0e&locusId=CT"+city.substring(0, 5).toUpperCase()+"&country=CAN&locusType=city&searchText="+city+"&regionNearByExp=3&rsc=1e2e0e");

            // wait for 5 seconds for the page to load
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Maximize the window
            driver.manage().window().maximize();
            for(int i=0;i<1; i++) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                // First scroll down , then scroll up a little and then scroll down to trigger loading of more jobs
                js.executeScript("window.scrollBy(0,1000)");
                js.executeScript("window.scrollBy(0,-200)");
                js.executeScript("window.scrollBy(0,1000)");
                // Wait for the jobs to load
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // Get the HTML content of the webpage using Selenium
            String html = driver.getPageSource();

            // Parse the HTML using Jsoup
            Document document = Jsoup.parse(html);
            //  System.out.println("document:" + document);
            Hashtable<String,String[]> hb = MakeMyTrip_parser.Make_My_Trip_Parser(document, city);

            hs.put(city, hb);
        }
        return hs;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String url = "";
        String[] cities = new String[] {
                "Toronto",
                //"Windsor"
        };
        String checkindate = "2024-05-23";
        String checkoutdate="2024-05-27";
        Hashtable<String,Hashtable> js = extractCities(cities,checkindate,checkoutdate);
        MakeMyTrip_parser.convert_json(js);
    }
}