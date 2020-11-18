package bomberman.Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;

public interface Render {

    void update() throws IOException;

    void render(Screen screen) throws IOException;
}
