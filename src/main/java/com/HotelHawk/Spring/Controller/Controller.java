package com.HotelHawk.Spring.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@org.springframework.stereotype.Controller

public class Controller {
    @RequestMapping("/")
    public String homepage(){
        return "index";
    }
    @RequestMapping("/search/{cityname}")
    public void crawl_all(@PathVariable String cityname) throws IOException, InterruptedException {
        //getting city name from HTTP Request
        CrawlerController c=new CrawlerController();
        c.booking_crawl(cityname);
        c.hotel_crawl(cityname);
        ///updating search frequency data
        SearchFrequencyController sc=new SearchFrequencyController();
    }
    @RequestMapping("/find/{cityname}")
    public void find_city(@PathVariable String cityname){
        WordCompletionCrawler wc= new WordCompletionCrawler();
        wc.word_completion(cityname);
        ///getting search freq data
        SearchFrequencyController sc=new SearchFrequencyController();
    }
}
