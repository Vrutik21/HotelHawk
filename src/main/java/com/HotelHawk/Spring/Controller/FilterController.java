package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.Filter.Filter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FilterController {
    @CrossOrigin
    @RequestMapping()
    public HttpEntity<String> filter(@PathVariable String cityname, @PathVariable String minprice, @PathVariable String maxprice, @PathVariable String minreviews) throws IOException {
        String filtered_data= Filter.filter_data(cityname, Integer.parseInt(minprice), Integer.parseInt(maxprice), Integer.parseInt(minreviews));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", filtered_data);
        return new HttpEntity<>(filtered_data, responseHeaders);
    }
}
