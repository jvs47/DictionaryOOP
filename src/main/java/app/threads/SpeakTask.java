package app.threads;

import app.controllers.panes.OnlineGoogleSearchController;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;

public class SpeakTask extends OnlineGoogleSearchController implements Runnable {
    private final String text;
    private final String language;

    public SpeakTask(String text, String language) {
        this.text = text;
        this.language = language;
    }

    @Override
    public void run() {
        try {
            playAudio(text, language);
        } catch (JavaLayerException | IOException e) {
            e.printStackTrace();
        }
    }
}
