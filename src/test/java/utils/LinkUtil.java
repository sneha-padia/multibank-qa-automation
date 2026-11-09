package utils;

import java.net.HttpURLConnection;
import java.net.URI;

public class LinkUtil {

    static String desktopAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0 Safari/537.36";

    // returns http status code of the link
    public static int getStatusCode(String link) {
        try {
            HttpURLConnection con = (HttpURLConnection) URI.create(link).toURL().openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", desktopAgent);
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.connect();
            int code = con.getResponseCode();
            con.disconnect();
            return code;
        } catch (Exception e) {
            System.out.println("Error checking link: " + link + " -> " + e.getMessage());
            return -1;
        }
    }

    // returns where the link redirects to (Location header), without following it
    public static String getRedirectUrl(String link, String userAgent) {
        try {
            HttpURLConnection con = (HttpURLConnection) URI.create(link).toURL().openConnection();
            con.setInstanceFollowRedirects(false);
            con.setRequestProperty("User-Agent", userAgent);
            con.setConnectTimeout(15000);
            con.connect();
            String location = con.getHeaderField("Location");
            con.disconnect();
            return location;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
