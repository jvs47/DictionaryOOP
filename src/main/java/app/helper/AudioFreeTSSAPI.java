package app.helper;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class AudioFreeTSSAPI {
    private static final String VOICENAME_kevin = "kevin";
    private final String text; // string to speech

    public AudioFreeTSSAPI(String text) {
        this.text = text;
    }

    public void play() {
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICENAME_kevin);
        voice.allocate();
        try {
            voice.setRate(125);//Setting the rate of the voice
            voice.setPitch(80);//Setting the Pitch of the voice
            voice.setVolume(100);//Setting the volume of the voice
            voice.speak(text);//Calling speak() method
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
