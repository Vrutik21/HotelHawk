package com.HotelHawk.Spring.MergerJSONdata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MergeData {
    public static void merge(String cityname) throws IOException {
        File file1=new File(System.getProperty("user.dir")+"\\booking_json");
        //File file2=new File(System.getProperty("user.dir")+"\\hotelsca_json");
//        String text = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\booking_json")), StandardCharsets.UTF_8);
//        String str = text.replaceAll("\\\\", "");
//        System.out.println(str);
//        //JSONObject json=new JSONObject("{".concat(text).concat("}"));
//        JSONObject main_json=new JSONObject();
//        main_json.put("booking",text);

        BufferedReader br1=new BufferedReader(new FileReader(file1));
        //BufferedReader br2=new BufferedReader(new FileReader(file2));
        String data1= (br1.readLine());
        //String data2= (br2.readLine());

//        JSONArray jsonArray = new JSONArray();
//        JSONParser parser = new JSONParser();
//        Object obj = new JSONParser().parse(new FileReader(File.json));

        JSONObject json=new JSONObject();
        json.put("booking",data1);
        //json.put("hotelsca",data2);
        //json.put("mmt","data");

//        ObjectMapper mapper = new ObjectMapper();
//        InputStream is = Test.class.getResourceAsStream("/test.json");
//        testObj = mapper.readValue(is, Test.class);


        BufferedWriter pw= new BufferedWriter(new FileWriter(System.getProperty("user.dir").concat("\\finaldata")));
        pw.write(json.toString());
        pw.close();
        remove_overwrite();
//
//
//        //writing cityname_old data file
//        PrintWriter p= new PrintWriter(cityname.concat("_finaldata"));
//        p.println(json.toString());
//        p.close();
    }

    public static void remove_overwrite(){
        String filePath = System.getProperty("user.dir").concat("\\finaldata");

        try {
            // Read content from the file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            // Replace "\" with " "

            String mcontent = content.toString().replace("\\", " ");
            String c=mcontent.substring(0,mcontent.indexOf(":")+1).concat(mcontent.substring(mcontent.indexOf("["),mcontent.length()));
            //String ch= c.substring()
            String cf= c.substring(0,c.lastIndexOf("]")+1).concat(c.substring(c.lastIndexOf("]")+2));

            // Write the modified content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(cf);
            writer.close();

            System.out.println("File overwritten successfully.");
        } catch (IOException e) {
            e.printStackTrace();
 }

    }
    public static void main(String[] args) throws IOException {
        merge("Toronto");
    }
}
