package com.HotelHawk.Spring.Controller;

import com.HotelHawk.Spring.DataValidation.DataValidation;
import com.HotelHawk.Spring.MergerJSONdata.MergeData;
import com.HotelHawk.Spring.SearchFrequency.SearchFreq;
import com.HotelHawk.Spring.spellcheck.SpellCheck;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class DataValidationController {
    @CrossOrigin
    @RequestMapping("/datavalidate/{cityname}/{checkin_date}/{checkout_date}")
    public HttpEntity<String> datavalidation(@PathVariable String cityname,@PathVariable String checkin_date, @PathVariable String checkout_date) throws IOException, InterruptedException {
        String[] result= DataValidation.check(checkin_date,checkout_date,cityname);
        if(result.length==2){
            //go to call crawler,
            SearchFreq.update(cityname);
            String[] d={cityname};
            String fcityname= SpellCheck.main(d);
            System.out.println(checkin_date);
            NewCrawlerController c= new NewCrawlerController();
            c.booking_crawl(fcityname,checkin_date,checkout_date);
            c.hotel_crawl(fcityname,checkin_date,checkout_date);
            c.mmt_crawl(fcityname,checkin_date,checkout_date);

            ///mergering data from crawlers into one json file.
            MergeData.merge(cityname);


            String path = System.getProperty("user.dir");
            //System.out.println(path);
            File file=new File(path+"\\finaldata");
            BufferedReader br=new BufferedReader(new FileReader(file));
            String temp=br.readLine();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("MyResponseHeader", br.readLine());
            return new HttpEntity<>(temp, responseHeaders);
        }
        else{
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("MyResponseHeader", "");
            return new HttpEntity<>("Please give correct input for cityname or dates", responseHeaders);
        }

    }
}
