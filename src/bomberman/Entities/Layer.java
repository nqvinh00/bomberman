package bomberman.Entities;

import bomberman.Graphics.Screen;
import java.util.ArrayList;
public class Layer extends Entity {
    protected ArrayList<Entity> entities = new ArrayList<Entity>();

    public Layer(int _x, int _y, Entity ... entities) {
        x = _x;
        y = _y;

        for (Entity e: entities) {
            this.entities.add(e);

            // Need code to make entities destroyable
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }


}
