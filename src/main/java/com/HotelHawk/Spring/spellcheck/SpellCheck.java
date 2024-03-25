package com.HotelHawk.Spring.spellcheck;
//done for request HTTP
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class SpellCheck {
    public static ArrayList<String> cities= new ArrayList<String>();
    public static ArrayList<Integer> dist =new ArrayList<Integer>();
    public static String initialize(String cityname){
        cities.add("TORONTO");
        cities.add("OTTAWA");
        cities.add("CALGARY");
        cities.add("VANCOUVER");
        cities.add("MONTREAL");
        cities.add("WINDSOR");
        cities.add("HALIFAX");
        cities.add("WINNIPEG");
        cities.add("EDMONTON");
        cities.add("HAMILTON");
        return edit_distance(cityname.toUpperCase());
    }
    static int min(int x, int y, int z)
    {
        if (x <= y && x <= z)
            return x;
        if (y <= x && y <= z)
            return y;
        else
            return z;
    }
    private static int edit_dist(String city, String find, int m,int n){
        if (m == 0)
            return n;
        if (n == 0)
            return m;
        if (city.charAt(m - 1) == find.charAt(n - 1))
            return edit_dist(city, find, m - 1, n - 1);
        return 1
                + min(edit_dist(city, find, m, n - 1), // Insert
                edit_dist(city, find, m - 1, n), // Remove
                edit_dist(city, find, m - 1, n - 1));//replace
    }
    public static String edit_distance(String find){
        for(String s:cities){
            int temp=edit_dist(s,find,s.length(),find.length());
            System.out.println(String.valueOf(temp)+" "+s+" "+find);
            dist.add(temp);
        }
        System.out.println(cities.get(dist.indexOf(Collections.min(dist))));

        return cities.get(dist.indexOf(Collections.min(dist)));
    }
    public static void main(String[] args){
        cities.add("TORONTO");
        cities.add("OTTAWA");
        cities.add("CALGARY");
        cities.add("VANCOUVER");
        cities.add("MONTREAL");
        ArrayList<Integer> dist = new ArrayList<Integer>();

    }
}

