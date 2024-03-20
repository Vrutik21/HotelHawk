package com.HotelHawk.Spring.Crawler;

import com.HotelHawk.Spring.Parser.Hotelsca_parser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Hotelsca_crawler {
    public static ArrayList<String> linkss =new ArrayList<String>();
    public static void cities(String cityname) throws IOException, InterruptedException {
        HashMap<String,String> states= new HashMap<String,String>();
        states.put("Toronto","Ontario");
        states.put("Calgary","Alberta");
        states.put("Vancouver","British%20Columbia");
        states.put("Montreal","Quebec");
        String url= "https://ca.hotels.com/Hotel-Search?adults=2&d1=2024-04-21&d2=2024-04-22&destination=".concat(cityname);
        //String url=url_city.concat("%2C%20".concat(states.get(cityname)).concat("%2C%20Canada"));
        System.out.println(url);
        WebDriver driver=new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,5000)","");
        Thread.sleep(1000);
        List<WebElement> links= driver.findElements(By.className("uitk-card-link"));
        //List<WebElement> links= driver.findElements(By.cssSelector("property-listing-results"));
        for(WebElement w:links){
            //System.out.println(w.getAttribute("href"));
            if (linkss.size()<=10){
                if (w.getAttribute("href")!=null){
                    linkss.add(w.getAttribute("href"));
                }
            }
        }
        driver.quit();

//        System.out.println(linkss.size());
        save_links();
    }
    public static void save_links() throws FileNotFoundException {
        //System.out.println("https://www.booking.com/hotel/ca/days-inn-toronto-downtown.en-gb.html?label=gen173nr-1FCBcoggI46AdIM1gEaCeIAQGYAQm4ARfIAQzYAQHoAQH4AQmIAgGoAgO4Apntzq8GwAIB0gIkZWMxZTk2NDItNmMxMi00YTFiLTliOTYtMDAwNGYxZmFlOTAz2AIG4AIB&aid=304142&ucfs=1&arphpl=1&dest_id=-574890&dest_type=city&group_adults=2&req_adults=2&no_rooms=1&group_children=0&req_children=0&hpos=1&hapos=1&sr_order=popularity&srpvid=5182138d13ba0087&srepoch=1710470811&from_sustainable_property_sr=1&from=searchresults".length());
        PrintWriter pr = new PrintWriter("hotelsca_links");
        for (int i=0; i<linkss.size() ; i++){
            pr.println(linkss.get(i));
        }

        pr.close();

    }
    public static void main(String[] args) throws IOException, InterruptedException {
        ///request is made to api stating the search city in search bar, crawler is called
        //just split url and add request city
        //String request_city= "Toronto";
        //String furl="https://ca.hotels.com/Hotel-Search?adults=2&d1=2024-05-21&d2=2024-05-22&destination=Toronto%2C%20Ontario%2C%20Canada&flexibility=0_DAY&regionId=4089&rooms=1&semdtl=&sort=RECOMMENDED&theme=&useRewards=false&userIntent=";
        //cities();
        //save_links();
    }
}
