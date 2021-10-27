package app.online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public abstract class OnlineGoogleSearchManagement {
    protected static String inputOnlineSearchText;
    protected static String outputOnlineSearchText;

    public OnlineGoogleSearchManagement() {
    }

    public String getInputOnlineSearchText() throws IOException {
        BufferedReader inputOnlineSearchTextReader = new BufferedReader(new InputStreamReader(System.in));
        inputOnlineSearchText = inputOnlineSearchTextReader.readLine();
        return inputOnlineSearchText;
    }

    public static String onlineTranslate(String langFrom, String langTo, String inputOnlineSearchText) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbw2qKkvobro8WLNZUKi2kGwGwEO4W8cBavcKqcuCIGhGBBtVts/exec" +
                "?q=" + URLEncoder.encode(inputOnlineSearchText, "UTF-8")
                + "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public abstract String getOutputOnlineSearchText() throws IOException;
}
