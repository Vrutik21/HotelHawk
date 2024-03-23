package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.Crawler.Booking_crawler;
import com.HotelHawk.Spring.Crawler.Hotelsca_crawler;
import com.HotelHawk.Spring.Crawler.MakeMyTrip_crawler;
import com.HotelHawk.Spring.MergerJSONdata.MergeData;
import com.HotelHawk.Spring.Parser.Booking_parser;
import com.HotelHawk.Spring.Parser.Hotelsca_parser;
import com.HotelHawk.Spring.Parser.MakeMyTrip_parser;
import com.HotelHawk.Spring.SearchFrequency.SearchFreq;
import com.HotelHawk.Spring.spellcheck.SpellCheck;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

@RestController
public class NewCrawlerController {
/// user give http reques with city name
    @GetMapping("/newsearch/{cityname}")
    public HttpEntity<String> crawl_all(@PathVariable String cityname) throws IOException, InterruptedException {
        SearchFreq.update(cityname);
        String fcityname= SpellCheck.initialize(cityname);
        //getting city name from HTTP Request
        booking_crawl(fcityname);
        hotel_crawl(fcityname);
        mmt_crawl(fcityname);
        ///mergering data from crawlers into one json file.
        MergeData.merge(cityname);
        String path = System.getProperty("user.dir");
        //System.out.println(path);
        File file=new File(path+"\\finaldata");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=br.readLine();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", br.readLine());
        return new HttpEntity<>(temp, responseHeaders);
    }
    @RequestMapping("/booking_crawler")
    public void booking_crawl(String cityname) throws IOException {
        Booking_crawler.extract_cities(cityname);
        Booking_parser.extract_links(cityname);
    }
    @RequestMapping("/hotel_crawler")
    public void hotel_crawl(String cityname) throws IOException, InterruptedException {
        Hotelsca_crawler.cities(cityname);
        Hotelsca_parser.extract_links();
    }
    @RequestMapping("/mmt_crawler")
    public void mmt_crawl(String cityname) throws IOException {
        String[] city={cityname};
        Hashtable<String, Hashtable> t=MakeMyTrip_crawler.extractCities(city);
        MakeMyTrip_parser.convert_json(t);

    }
}



















