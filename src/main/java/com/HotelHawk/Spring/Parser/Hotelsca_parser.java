package com.HotelHawk.Spring.Parser;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
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
        ArrayList<String> temp_data=new ArrayList<>();
        for(String link:links){
            if(link!=null){
                System.out.println("hotelsca");
                System.out.println(link);
                driver.get(link);
                By locator= By.cssSelector(".uitk-text.uitk-type-300.uitk-text-default-theme.is-visually-hidden");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//                driver.manage().window().maximize();
//                driver.wait(10000);
                //getting name of hotel
                WebElement name=driver.findElement(By.tagName("h1"));
                temp_data.add(name.getText());
                //getting photos
                List<WebElement> photos=  driver.findElements(By.cssSelector(".uitk-image-placeholder.uitk-image-placeholder-image"));
                System.out.println(name.getText());
                String im="";
                int count=0;
                for(WebElement w:photos){
                    List<WebElement> phots=w.findElements(By.tagName("img"));
                    for(WebElement p:phots){
                        if (count<6){
                            im+=(p.getAttribute("src"))+" ";
                        }
                        else{
                            break;
                        }
                    }
                }
                temp_data.add(im);
                //getting reviews
                WebElement rev=driver.findElement(By.xpath("//meta[@itemprop='ratingValue']"));
                System.out.println(rev.getAttribute("content"));
                temp_data.add(rev.getAttribute("content"));

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
                ////getting room type
//                List<WebElement> room = driver.findElements(By.cssSelector(".uitk-spacing.uitk-spacing-padding-small-blockend-half"));
//
//                for(WebElement w:room){
//                    String t=w.findElement(By.tagName("h3")).getText();
//                    if (t!=null){
//                        System.out.println(t);
//                    }
//                }

                //getting room prices, getting min price right now
                //int min=100000;
                String str = "";
                List<WebElement> pric=driver.findElements(By.cssSelector(".uitk-text.uitk-type-300.uitk-text-default-theme.is-visually-hidden"));
                for(WebElement w:pric){
                    if (w.getText()!=""){
                        String t=w.getText();
                        String t_f=t.substring(t.lastIndexOf('$')+1);
//                        if(min>Integer.parseInt(t_f)){
//                            min=Integer.parseInt(t_f);
//                        }
                        str+=t_f;
                    }
                }
                temp_data.add(str);
            }
        }
        hotels.put(temp_data.get(0), temp_data);
        convert_json();

    }
    public static void convert_json() throws FileNotFoundException {
        JSONObject json=new JSONObject(hotels);
        String json_string= json.toString();
        PrintWriter pw= new PrintWriter("hotelsca_json");
        pw.println(json_string);
        pw.close();
    }
    public static void main(String[] args){

    }
}