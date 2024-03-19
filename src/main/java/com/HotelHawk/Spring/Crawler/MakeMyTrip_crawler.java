package com.HotelHawk.Spring.Crawler;

import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class MakeMyTrip_crawler {

	
    public static Hashtable<String, Hashtable> extractCities(String url, String[] cities) throws FileNotFoundException {
        Hashtable<String,Hashtable> hs = new Hashtable<>();
 
    	for (String city : cities) {
        	// Create a new instance of the Chrome driver
            WebDriver driver = new ChromeDriver();
            
                // Open a website using Selenium
            	driver.get("https://www.makemytrip.com/hotels/hotel-listing/?checkin=03242024&city=CT" +city.substring(0, 5).toUpperCase()+ "&checkout=03292024&roomStayQualifier=2e0e&locusId=CT"+city.substring(0, 5).toUpperCase()+"&country=CAN&locusType=city&searchText="+city+"&regionNearByExp=3&rsc=1e2e0e");
            	
            	// wait for 5 seconds for the page to load
            	//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            	
            	// Maximize the window
                driver.manage().window().maximize();
            	for(int i=0;i<1; i++) {  	 	
            		JavascriptExecutor js = (JavascriptExecutor) driver;
                    // First scroll down , then scroll up a little and then scroll down to trigger loading of more jobs
                    js.executeScript("window.scrollBy(0,1000)");
                    js.executeScript("window.scrollBy(0,-200)");
                    js.executeScript("window.scrollBy(0,1000)");
                    // Wait for the jobs to load
                    try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	
                // Get the HTML content of the webpage using Selenium
                String html = driver.getPageSource();
                
             // Parse the HTML using Jsoup
                Document document = Jsoup.parse(html);
              //  System.out.println("document:" + document);
                Hashtable<String,String[]> hb = parser.MakeMyTrip_Parser.Make_My_Trip_Parser(document, city);
    	  
                hs.put(city, hb);
            }
    	        return hs;
    }
        

    public static void main(String[] args) throws FileNotFoundException {
        String url = "";
        String[] cities = new String[] {
        		"Toronto",
        		//"Windsor"
        		};
        
  	  Hashtable<String,Hashtable> js = extractCities(url, cities);
       print_hotels(js);
          }
    
    
    
    public static void print_hotels(Hashtable<String,Hashtable> js)
    {
          System.out.println("here is the size of the following "+js.size());
          
          System.out.println("\n\n\n");

          
          Enumeration<String> e = js.keys();
          
          // Iterating through the Hashtable
          // object
   
          // Checking for next element in Hashtable object
          // with the help of hasMoreElements() method
          while (e.hasMoreElements()) {
   
              // Getting the key of a particular entry
              String key = e.nextElement();
              
              System.out.println("all the hotels of the "+key+" are the following");
              
              Hashtable<String,String[]> jj = js.get(key);
              
              Enumeration<String> yy = jj.keys();
              System.out.println("there are "+jj.size()+ " hotels in "+key+"");
              int i=0;
              while(yy.hasMoreElements())
              {
              	
              	String key1 = yy.nextElement();
              	
              	System.out.println("\n\n"+i+" hotel name : "+key1);
              	String[] alldetails = jj.get(key1);
              	
              	System.out.println("prize :"+alldetails[1]);
              	System.out.println("url :"+alldetails[0]);
              	System.out.println("image url :"+alldetails[2]);
              	System.out.println("rating :"+alldetails[3]);
              	
              	i++;
              	
              	
              }
   
              // Print and display the Rank and Name
              
              
          }
    }
}

	    

}



