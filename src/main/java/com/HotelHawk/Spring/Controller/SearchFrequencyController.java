package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.SearchFrequency.SearchFreq;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchFrequencyController {
    @RequestMapping("/find/{cityname}")
    public void search(@PathVariable String cityname){
        //SearchFreq.
    }
}
