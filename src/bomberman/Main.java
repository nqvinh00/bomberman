package bomberman;

import bomberman.GUI.GameFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {

    /**
     * main method.
     * @param args not use
     * @throws IOException throw io exception
     * @throws LineUnavailableException throw when line audio error
     * @throws UnsupportedAudioFileException throw when audio type not supported
     */
    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        GameFrame window = new GameFrame();
    }
}
