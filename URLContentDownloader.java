package asg1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLContentDownloader {

    public static void main(String[] args) {
        String urlString = "https://www.samford.edu"; // Test URL
        String content = downloadUrlContent(urlString);
        System.out.println("Downloaded content:\n" + content);
    }

    public static String downloadUrlContent(String urlString) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // Create a URL and open a connection
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            // Optional: Set request method and headers if needed
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.setReadTimeout(5000);

            // Read data from the URL
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            System.err.println("Error occurred while downloading content: " + e.getMessage());
        } finally {
            // Close resources
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    // Ignored
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result.toString();
    }
}
