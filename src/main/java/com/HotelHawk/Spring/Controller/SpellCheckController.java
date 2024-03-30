package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.spellcheck.SpellCheck;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpellCheckController {
    @CrossOrigin
    @RequestMapping("/spellcheck/{cityname}")
    public static HttpEntity<String> spell_check(@PathVariable String cityname){
        System.out.println(cityname);
        if(cityname.matches("")){
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("MyResponseHeader", "");
            return new HttpEntity<>("Please enter city", responseHeaders);
        }
        String[] d={cityname};
        String fcityname= SpellCheck.main(d);
        String t="";
        if(cityname.matches(fcityname)){
            t="";
        }
        else{
            t= fcityname;
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", t);
        return new HttpEntity<>(t, responseHeaders);
    }
}
