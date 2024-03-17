package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.WordCompletion.WordCompletion;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WordCompletionCrawler {
    @RequestMapping("/find/wc/{cityname}")
    public void word_completion(@PathVariable String cityname){
        List<String> list= WordCompletion.initialize(cityname);
    }
}
