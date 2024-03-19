package com.HotelHawk.Spring.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@org.springframework.stereotype.Controller

public class Controller {
    @RequestMapping("/")
    public String homepage(){
        return "index";
    }
    @GetMapping("/search/{cityname}")
    public HttpEntity<String> crawl_all(@PathVariable String cityname) throws IOException, InterruptedException {
        //getting city name from HTTP Request
        CrawlerController c=new CrawlerController();
        c.booking_crawl(cityname);
        //c.hotel_crawl(cityname);
        String path = System.getProperty("user.dir");
        //System.out.println(path);
        File file=new File(path+"\\booking_json");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=br.readLine();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", br.readLine());
        return new HttpEntity<>(temp, responseHeaders);
        //return ResponseEntity.ok().body(br.readLine());
        //return br.readLine();
        ///updating search frequency data
        //SearchFrequencyController sc=new SearchFrequencyController();
    }
    @RequestMapping("/find/{cityname}")
    public void find_city(@PathVariable String cityname){
        WordCompletionCrawler wc= new WordCompletionCrawler();
        wc.word_completion(cityname);
        ///getting search freq data
        //SearchFrequencyController sc=new SearchFrequencyController();
    }
}