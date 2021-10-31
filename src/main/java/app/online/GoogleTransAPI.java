package app.online;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GoogleTransAPI {
    public static String content;
    public static String urlTest;
    private static final String googleTranslatorURL = "http://translate.google.com/translate_a/t?";
    private static LANGUAGE srcLang;
    private static LANGUAGE destLang;
    private static String userAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";

    public static String translate(String query, LANGUAGE srcLang, LANGUAGE destLang) throws IOException, org.json.simple.parser.ParseException {
        URL url = new URL(buildURLRequestWith(query, srcLang, destLang));
        urlTest = buildURLRequestWith(query, srcLang, destLang);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setRequestProperty("X-HTTP-Method-Override", "GET");
        conn.setRequestProperty("referer", "accounterlive.com");

        conn.addRequestProperty("User-Agent", userAgent);
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(30000);
        conn.connect();

        InputStream inputStream = conn.getInputStream();
        BufferedReader bis = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
        content = bis.readLine();

        inputStream.close();
        bis.close();
        conn.disconnect();

        StringBuilder contentBuilder = new StringBuilder();

        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject) parser.parse(content);
        JSONArray sentences = (JSONArray) jsonData.get("sentences");

        for (Object sentence : sentences) {
            contentBuilder.append(((JSONObject) sentence).get("trans").toString().trim());
        }

        return contentBuilder.toString().trim().replace(" .", ". ");
    }

    public static String buildURLRequestWith(String query, LANGUAGE srcLang, LANGUAGE destLang) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(googleTranslatorURL);
        urlBuilder.append("client=gtrans");
        urlBuilder.append("&sl=").append(srcLang);
        urlBuilder.append("&tl=").append(destLang);

        urlBuilder.append("&format=html");
        urlBuilder.append("&v=2.0");
        String queryEncoded = "";
        try {
            queryEncoded = URLEncoder.encode(query, "UTF-8");
        } catch (Exception e) {
        }
        urlBuilder.append("&q=").append(queryEncoded);
        return urlBuilder.toString();
    }

    public GoogleTransAPI(String content, String urlTest, LANGUAGE srcLang, LANGUAGE destLang, String userAgent) {
        this.content = content;
        this.urlTest = urlTest;
        this.srcLang = srcLang;
        this.destLang = destLang;
        this.userAgent = userAgent;
    }

    public LANGUAGE getSrcLang() {
        return srcLang;
    }

    public void setSrcLang(LANGUAGE srcLang) {
        this.srcLang = srcLang;
    }

    public LANGUAGE getDestLang() {
        return destLang;
    }

    public void setDestLang(LANGUAGE destLang) {
        this.destLang = destLang;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public enum LANGUAGE {
        ENGLISH("en"), VIETNAMESE("vi"), AUTO("auto");

        private String lang = "";

        private LANGUAGE(String lang) {
            this.lang = lang;
        }

        @Override
        public String toString() {
            return this.lang;
        }
    }
}
