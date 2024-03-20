package com.HotelHawk.Spring.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class FrequencyCountCrawler {
    @RequestMapping("/fc/")
    public static HttpEntity<String> freqcount() throws IOException {
        File file=new File(System.getProperty("user.dir")+"\\freqcount");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=br.readLine();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", br.readLine());
        return new HttpEntity<>(temp, responseHeaders);
    }
}
