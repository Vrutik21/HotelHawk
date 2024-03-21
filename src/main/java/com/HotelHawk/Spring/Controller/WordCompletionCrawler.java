package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.SearchFrequency.SearchFreq;
import com.HotelHawk.Spring.WordCompletion.WordCompletion;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WordCompletionCrawler {
    @RequestMapping("/find/{cityname}")
    public HttpEntity<String> find_city(@PathVariable String cityname) throws IOException {
        List<String> cities= WordCompletion.initialize(cityname);
        ArrayList<JSONObject> jsonarray=new ArrayList<JSONObject>();
        for(String s:cities){
            JSONObject json=new JSONObject();
            json.put("Cityname", s);
            json.put("Search_Freq",SearchFreq.get_data(s));
            jsonarray.add(json);
        }

//        String listString = String.join(", ", cities);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "value");
        return new HttpEntity<>(new JSONArray(jsonarray).toString(), responseHeaders);
        ///getting search freq data
        //SearchFrequencyController sc=new SearchFrequencyController();
    }
}
