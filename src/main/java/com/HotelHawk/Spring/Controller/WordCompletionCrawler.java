package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.WordCompletion.WordCompletion;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WordCompletionCrawler {
    @RequestMapping("/find/{cityname}")
    public HttpEntity<String> find_city(@PathVariable String cityname){
        List<String> cities= WordCompletion.initialize(cityname);
        String listString = String.join(", ", cities);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "value");
        return new HttpEntity<>(listString, responseHeaders);
        ///getting search freq data
        //SearchFrequencyController sc=new SearchFrequencyController();
    }
}
