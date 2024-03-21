package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.SearchFrequency.SearchFreq;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SearchFrequencyController {
    @GetMapping("/sf/getdata/")
    public HttpEntity<String> search(String cityname) throws IOException {
        String data= SearchFreq.get_data(cityname);
        System.out.println(data);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "value");
        return new HttpEntity<>(data, responseHeaders);
    }
    @RequestMapping("/sf/savedata/{cityname}")
    public void save_data(@PathVariable String cityname) throws IOException {
        SearchFreq.update(cityname);
        //search();
    }
}
