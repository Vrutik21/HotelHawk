package com.HotelHawk.Spring.Parser;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Hotelsca_parser {
    public static ArrayList<String> links= new ArrayList<String>();
    public static HashMap<String,ArrayList<String>> hotels=new HashMap<String,ArrayList<String>>();
    public static void extract_links() throws IOException, InterruptedException {
        File file=new File("hotelsca_links");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String st;
        while((st=br.readLine())!=null){
            links.add(st);
        }
        hotels_parse();

    }
    public static void hotels_parse() throws IOException, InterruptedException {
        WebDriver driver =new FirefoxDriver();
        for(String link:links){
            if(link!=null){
                driver.get(link);
//                By locator= By.cssSelector(".uitk-text.uitk-type-300.uitk-text-default-theme.is-visually-hidden");
//                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//                wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//                driver.manage().window().maximize();
//                driver.wait(10000);
                //getting name of hotel
                WebElement name=driver.findElement(By.tagName("h1"));
                //getting photos
                List<WebElement> photos=  driver.findElements(By.cssSelector(".uitk-image-placeholder.uitk-image-placeholder-image"));
                System.out.println(name.getText());
                for(WebElement w:photos){
                    List<WebElement> phots=w.findElements(By.tagName("img"));
                    for(WebElement p:phots){
                        System.out.println(p.getAttribute("src"));
                    }
                }
                //getting reviews
                WebElement rev=driver.findElement(By.xpath("//meta[@itemprop='ratingValue']"));
                System.out.println(rev.getAttribute("content"));

                //getting hotel location
                WebElement loc=driver.findElement(By.xpath("//meta[@itemprop='streetAddress']"));
                System.out.println(loc.getAttribute("content"));

                //getting hotel description
                List<WebElement> desc=driver.findElements(By.cssSelector("uitk-layout-grid-item"));
                for(WebElement w:desc){
                    WebElement temp= w.findElement(By.cssSelector(".uitk-text.uitk-type-300.uitk-text-default-theme"));
                    if (temp.getText()!=null){
                        System.out.println(temp.getText());
                    }
                }
                ///getting room type and their prices

            }
        }
        convert_json();

    }
    public static void convert_json() throws FileNotFoundException {
        JSONObject json=new JSONObject(hotels);
        String json_string= json.toString();
        PrintWriter pw= new PrintWriter("booking_json");
        pw.println(json_string);
        pw.close();
    }
    public static void main(String[] args){

    }
}
