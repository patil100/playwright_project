package Utility;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class CsvReader {

    public static List<Map<String, String>> readCsv(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String[] headers = br.readLine().split(",");  // Read the first line as headers
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], values[i]);
                }
                data.add(row);
            }
        } catch (Exception e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return data;
    }
}
