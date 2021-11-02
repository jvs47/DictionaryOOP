package app.helper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioGoogleAPI {
    public static final String GOOGLE_TRANSLATE_AUDIO = "http://translate.google.com/translate_tts?";
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
    private static AudioGoogleAPI audio;

    private AudioGoogleAPI() {
    }

    public synchronized static AudioGoogleAPI getInstance() {

        if (audio == null) {
            audio = new AudioGoogleAPI();
        }
        return audio;
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public InputStream getAudio(String text, String languageOutput)
            throws IOException {

        URL url = new URL(GOOGLE_TRANSLATE_AUDIO + "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&tl=" + languageOutput +
                "&tk=" + generateNewToken() +
                "&client=t");
        URLConnection urlConn = url.openConnection();
        urlConn.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        InputStream audioSrc = urlConn.getInputStream();
        return new BufferedInputStream(audioSrc);
    }

    public void play(InputStream sound) throws JavaLayerException {
        new Player(sound).play();
    }

}