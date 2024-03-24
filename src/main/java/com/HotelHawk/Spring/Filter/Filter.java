package com.HotelHawk.Spring.Filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Filter {
    public static void main(String[] args) {
        // Inputs
        int minReviewCount = 4; // Example minimum review count
        double minPrice = 50.0; // Example minimum price
        boolean ascendingSort = false; // Example sorting order

        // Read JSON array from file
        List<JsonNode> jsonList = new ArrayList<>();
        try {
            File file = new File("booking_json"); // Change to your file path
            Scanner scanner = new Scanner(file);
            StringBuilder jsonString = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonString.append(scanner.nextLine());
            }
            scanner.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonString.toString());

            for (JsonNode node : rootNode) {
                jsonList.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Filter and sort JSON objects
        List<JsonNode> filteredResults = new ArrayList<>();
        for (JsonNode node : jsonList) {
            int reviewCount = node.get("Review").asInt();
            double price = node.get("MinPrice").asDouble();

            if (reviewCount > minReviewCount && price > minPrice) {
                filteredResults.add(node);
            }
        }

//        // Sort filtered results
//        filteredResults.sort(Comparator.comparingDouble(node -> node.get("MinPrice").asDouble()));
//        if (!ascendingSort) {
//            filteredResults.sort(Comparator.comparingDouble(node -> node.get("MinPrice").asDouble()).reversed());
//        }

        // Output filtered and sorted results
        for (JsonNode node : filteredResults) {
            System.out.println("Name: " + node.get("Name").asText() +
                    ", MinPrice: " + node.get("MinPrice").asDouble() +
                    ", Review: " + node.get("Review").asInt() +
                    ", Location: " + node.get("Location").asText());
        }
    }
}
