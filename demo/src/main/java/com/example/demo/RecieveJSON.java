package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;

public class RecieveJSON {
    public static void main(String[] args) {
        try {
            // Get the ID from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the ID: ");
            int id = Integer.parseInt(reader.readLine());

            // Encode the ID as a query parameter
            String encodedId = URLEncoder.encode(String.valueOf(id), "UTF-8");

            // Open a connection to the specified URL with the ID query parameter
            URL url = new URL("https://9f46-93-166-53-22.eu.ngrok.io/people/" + encodedId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            con.setRequestMethod("GET");

            // Read the response from the server
            int status = con.getResponseCode();
            if (status != 200) {
                throw new IOException("Invalid response code: " + status);
            }
            InputStream inputStream = con.getInputStream();
            StringBuffer response = new StringBuffer();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            // Parse the JSON response
            JSONObject json = new JSONObject(response.toString());
            String name = json.getString("name");
            String age = json.getString("age");
           // String email = json.getString("email");
           // String date = json.getString("dato");

            // Print the received data
            System.out.println("Name: " + name);
            System.out.println("age " + age);
          //  System.out.println("Email " + email);
          //  System.out.println("Dato: " + date);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID entered. ID must be a number.");
        }
    }
}

