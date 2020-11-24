package bomberman.Graphics;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;

public interface Render {

    void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    void render(Screen screen) throws IOException;
}
