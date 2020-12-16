package bomberman.Entities;

import bomberman.Entities.BoardSprite.Brick;
import bomberman.Graphics.Screen;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

public class Layer extends Entity {
    protected ArrayList<Entity> entities = new ArrayList<Entity>();

    /**
     * constructor.
     * @param x pos
     * @param y pos
     * @param entities a set of entities
     */
    public Layer(int x, int y, Entity ... entities) {
        this.x = x;
        this.y = y;

        for (int i = 0; i < entities.length; i++) {
            this.entities.add(entities[i]);
            if (i > 0) {
                if (entities[i] instanceof Brick) {
                    ((Brick)entities[i]).addGrass(entities[i - 1].getSprite());
                }
            }
        }
    }

    @Override
    public void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this.clearRemovedEntity();
        this.getLastEntity().update();
    }

    @Override
    public void render(Screen screen) throws IOException {
        this.getLastEntity().render(screen);
    }

    @Override
    public boolean isCollided(Entity e) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        return getLastEntity().isCollided(e);
    }

    /**
     * get last entity in layer = layer on top.
     * @return entity
     */
    public Entity getLastEntity() {
        return this.entities.get(entities.size() - 1);
    }

    /**
     * clear removed entity which is destroyed.
     */
    public void clearRemovedEntity() {
        Entity last = this.getLastEntity();
        if (last.isRemoved()) {
            entities.remove(last);
        }
    }

    /**
     * add entity under top entity.
     * @param e entity
     */
    public void addEntityBeforeLast(Entity e) {
        this.entities.add(entities.size() - 1, e);
    }
}
