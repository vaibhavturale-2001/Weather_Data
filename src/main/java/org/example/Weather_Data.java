package org.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@SpringBootApplication
public class Weather_Data {
    private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";
    private static final String API_BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid="+API_KEY;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\nChoose an option:");
            System.out.println("1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("4. Show all dt from API");
            System.out.println("0. Exit");

            choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character from the buffer

            switch (choice) {
                case 1:
//                    System.out.print("Enter the date (yyyy-MM-dd): ");
                    String inputDate = scanner.nextLine();
                    printWeatherForDate(inputDate);
                    break;
                case 2:
                    // System.out.print("Enter the date (yyyy-MM-dd): ");
                    inputDate = scanner.nextLine();
                    printWindSpeedForDate(inputDate);
                    break;
                case 3:
                    // System.out.print("Enter the date (yyyy-MM-dd): ");
                    inputDate = scanner.nextLine();
                    printPressureForDate(inputDate);
                    break;
                case 4:
                    showAllDTFromAPI();
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 0);
    }

    private static void printWeatherForDate(String inputDate) {
        try {
            JSONArray forecastData = getForecastData();
            SimpleDateFormat dateFormat = new SimpleDateFormat();

            for (int i = 0; i < forecastData.length(); i++) {
                JSONObject dataPoint = forecastData.getJSONObject(i);
                Date date = new Date(dataPoint.getLong("dt") * 1000);

                if (dateFormat.format(date).equals(inputDate)) {
                    JSONObject main = dataPoint.getJSONObject("main");
                    double temperature = main.getDouble("temp");
                    System.out.println("Temperature for " + inputDate + ": " + temperature + " °C");
                    return;
                }
            }

            System.out.println("Data not available for the given date: " + inputDate);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void printWindSpeedForDate(String inputDate) {
        try {
            JSONArray forecastData = getForecastData();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i < forecastData.length(); i++) {
                JSONObject dataPoint = forecastData.getJSONObject(i);
                Date date = new Date(dataPoint.getLong("dt") * 1000);

                if (dateFormat.format(date).equals(inputDate)) {
                    JSONObject wind = dataPoint.getJSONObject("wind");
                    double windSpeed = wind.getDouble("speed");
                    System.out.println("Wind Speed for " + inputDate + ": " + windSpeed + " m/s");
                    return;
                }
            }

            System.out.println("Data not available for the given date: " + inputDate);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void printPressureForDate(String inputDate) {
        try {
            JSONArray forecastData = getForecastData();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i < forecastData.length(); i++) {
                JSONObject dataPoint = forecastData.getJSONObject(i);
                Date date = new Date(dataPoint.getLong("dt") * 1000);

                if (dateFormat.format(date).equals(inputDate)) {
                    JSONObject main = dataPoint.getJSONObject("main");
                    double pressure = main.getDouble("pressure");
                    System.out.println("Pressure for " + inputDate + ": " + pressure + " hPa");
                    return;
                }
            }

            System.out.println("Data not available for the given date: " + inputDate);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static JSONArray getForecastData() throws Exception {
        URL url = new URL(API_BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        conn.disconnect();

        JSONObject jsonObject = new JSONObject(response.toString());
        return jsonObject.getJSONArray("list");
    }

    private static void showAllDTFromAPI() {
        try {
            JSONArray forecastData = getForecastData();
            System.out.println("All dt values from the API:");

            for (int i = 0; i < forecastData.length(); i++) {
                JSONObject dataPoint = forecastData.getJSONObject(i);
                long dt = dataPoint.getLong("dt");
                Date date = new Date(dt * 1000);
                System.out.println(date);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
