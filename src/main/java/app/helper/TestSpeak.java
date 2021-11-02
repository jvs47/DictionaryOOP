package app.helper;

import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.io.InputStream;

public class TestSpeak {
    public static void main(String[] args) throws JavaLayerException, IOException {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        AudioFreeTSSAPI textToSpeak = new AudioFreeTSSAPI("advance");
        textToSpeak.play();

        /*AudioGoogleAPI audio = AudioGoogleAPI.getInstance();
        InputStream sound = audio.getAudio("hello", "vi");
        audio.play(sound);*/
    }
}
