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
        this.clearRemovedEntity();
        this.getLastEntity().update();
    }

    @Override
    public void render(Screen screen) {
        this.getLastEntity().render(screen);
    }

    @Override
    public boolean isCollided(Entity e) {
        return getLastEntity().isCollided(e);
    }

    public Entity getLastEntity() {
        return this.entities.get(entities.size() - 1);
    }

    public void clearRemovedEntity() {
        Entity last = this.getLastEntity();
        if (last.isRemoved()) {
            entities.remove(last);
        }
    }
}
