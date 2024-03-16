package com.HotelHawk.Spring.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {
    @RequestMapping("/search/{cityname}")
    public void crawl_all(@PathVariable String cityname) throws IOException, InterruptedException {
        //getting city name from HTTP Request
        CrawlerController c=new CrawlerController();
        c.booking_crawl(cityname);
        c.hotel_crawl(cityname);

    }
}
