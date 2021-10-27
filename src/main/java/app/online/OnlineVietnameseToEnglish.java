package app.online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OnlineVietnameseToEnglish extends OnlineGoogleSearchManagement{
    @Override
    public String getOutputOnlineSearchText() throws IOException {
        outputOnlineSearchText = "Translated text: " + onlineTranslate("vi", "en", inputOnlineSearchText);
        return outputOnlineSearchText;
    }


}
