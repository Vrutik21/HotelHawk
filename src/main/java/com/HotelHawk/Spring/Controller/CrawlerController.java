package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.Crawler.Booking_crawler;
import com.HotelHawk.Spring.Crawler.Hotelsca_crawler;
import com.HotelHawk.Spring.Parser.Booking_parser;
import com.HotelHawk.Spring.Parser.Hotelsca_parser;
import com.HotelHawk.Spring.spellcheck.SpellCheck;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CrawlerController {
/// user give http reques with city name
    @RequestMapping("/booking_crawler")
    public void booking_crawl(String cityname) throws IOException {
        Booking_crawler.extract_cities(cityname);
        Booking_parser.extract_links();
    }
    @RequestMapping("/hotel_crawler")
    public void hotel_crawl(String cityname) throws IOException, InterruptedException {
        Hotelsca_crawler.cities(cityname);
        Hotelsca_parser.extract_links();
    }
    @RequestMapping("/mmt_crawler")
    public void mmt_crawl() throws IOException {
    }
}



















