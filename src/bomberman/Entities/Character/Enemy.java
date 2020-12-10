package bomberman.Entities.Character;

import bomberman.Entities.Bomb.Bomb;
import bomberman.Entities.Bomb.BombExplosion;
import bomberman.Entities.Entity;
import bomberman.Game;
import bomberman.GameBoard;
import bomberman.Graphics.Screen;
import bomberman.Graphics.Sprite;
import bomberman.Level.Level;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class Enemy extends Character {
    protected int pointKill;
    protected double speed;
    protected double maxStep;
    protected double moveStep;
    protected int animation = 30;
    protected Sprite deadSprite;

    public Enemy(int x, int y, GameBoard board, int pointKill, double speed, Sprite sprite) {
        super(x, y, board);
        this.pointKill = pointKill;
        this.speed = speed;
        this.deadSprite = sprite;
        this.maxStep = Game.boardsprite_size / this.speed;
        this.moveStep = this.maxStep;
        this.timeDead = 20;
    }

    @Override
    public void update() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        activate();
        if (!this.alive) {
            afterDead();
            return;
        }
        Bomber bomber = this.board.getBomber();
        if (bomber != null) {
            if (Math.abs((int) bomber.getX() - (int) this.x) <= 10 && Math.abs((int) bomber.getY() - (int) this.y) <= 10) {
                bomber.setProcess(true);
                this.isCollided(bomber);
            }
        }
        this.moveStep();
    }

    @Override
    public void render(Screen screen) {
        if (this.alive) {
            this.selectSprite();
        } else {
            if (this.timeDead > 0) {
                this.sprite = this.deadSprite;
                this.move_step = 0;
            } else {
                this.sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, this.move_step, 45);
            }
        }
        screen.renderEntity((int) this.x, (int) this.y - this.sprite.getSize(), this);
    }

    @Override
    public boolean isCollided(Entity e) {
        if (e instanceof BombExplosion) {
            dead();
            return false;
        }

        if (e instanceof Bomber) {
            ((Bomber) e).dead();
            return false;
        }

        return true;
    }

    @Override
    public void dead() {
        if (!this.alive) {
            return;
        }
        this.alive = false;
        this.board.setPoint(this.board.getPoint() + this.pointKill);
    }

    @Override
    public void afterDead() {
        if (this.timeDead > 0) {
            this.timeDead--;
        } else {
            if (this.animation > 0) {
                this.animation--;
            } else {
                this.remove();
            }
        }
    }

    @Override
    protected void move(double deltaX, double deltaY) {
        if (!this.alive) {
            return;
        }
        this.x += deltaX;
        this.y += deltaY;
    }

    @Override
    protected void moveStep() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        int x_ = 0;
        int y_ = 0;
        if (this.moveStep <= 0) {
            this.direction = this.findDirection();
            this.moveStep = this.maxStep;
        }

        switch (this.direction) {
            case 0 -> y_--;
            case 1 -> x_++;
            case 2 -> y_++;
            case 3 -> x_--;
        }

        if (canMoveTo(x_, y_)) {
            this.moveStep -= 1 + (this.maxStep - (int) this.maxStep) / this.maxStep;
            this.move(x_ * this.speed, y_ * this.speed);
            this.moving = true;
        } else {
            this.moveStep = 0;
            this.moving = false;
        }
    }

    @Override
    public boolean canMoveTo(double posX, double posY) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        double x_ = this.x;
        double y_ = this.y - 16;

        switch (this.direction) {
            case 0 -> {
                x_ += this.sprite.getSize() / 2;
                y_ += this.sprite.getSize() - 1;
            }
            case 1 -> {
                x_++;
                y_ += this.sprite.getSize() / 2;
            }
            case 2 -> {
                x_ += this.sprite.getSize() / 2;
                y_++;
            }
            case 3 -> {
                x_ += this.sprite.getSize() - 1;
                y_ += this.sprite.getSize() / 2;
            }
        }

        Entity entity = this.board.getEntity((int) (x_ / Game.boardsprite_size) + (int) posX,
                (int) (y_ / Game.boardsprite_size) + (int) posY, this);
        return entity.isCollided(this);
    }

    public abstract void selectSprite();

    /**
     * use for low level enemy.
     * @return random(1, 4)
     */
    public int findDirection() {
        return new Random().nextInt(4);
    }

    public int bomberColDirection() {
        if (this.board.getBomber().getBoardSpriteX() < this.getBoardSpriteX()) {
            return 3;
        } else if (this.board.getBomber().getBoardSpriteX() > this.getBoardSpriteX()) {
            return 1;
        }
        return -1;
    }

    public int bomberRowDirection() {
        if (this.board.getBomber().getBoardSpriteY() < this.getBoardSpriteY()) {
            return 0;
        } else if (this.board.getBomber().getBoardSpriteY() > this.getBoardSpriteY()) {
            return 2;
        }
        return -1;
    }

    public int bombDetect(int x, int y) {
        int x_ = this.getBoardSpriteX();
        int y_ = this.getBoardSpriteY();

        // row
        if (y_ == y) {
            if (x - x_ > 0 && x - x_ <= Game.bomb_range) {
                return 1; // right
            }

            if (x - x_ < 0 && x - x_ >= Game.bomb_range) {
                return 3; // left
            }
        }

        // col
        if (x_ == x) {
            if (y - y_ > 0 && y - y_ <= Game.bomb_range) {
                return 2; // up
            }

            if (y - y_ < 0 && y - y_ >= Game.bomb_range) {
                return 0; // down
            }
        }

        // top corner
        if (y - y_ < 0 && y - y_ >= Game.bomb_range) {
            if (x - x_ > 0 && x - x_ <= Game.bomb_range) {
                return 12; // top right
            }

            if (x - x_ < 0 && x - x_ >= Game.bomb_range) {
                return 32; // top left
            }
        }

        // down corner
        if (y - y_ < 0 && y - y_ >= Game.bomb_range) {
            if (x - x_ > 0 && x - x_ <= Game.bomb_range) {
                return 10; // down right
            }

            if (x - x_ < 0 && x - x_ >= Game.bomb_range) {
                return 30; // down left
            }
        }
        return -1;
    }

    public int findBomberWithBFS() {
        ArrayList<Integer> path = new ArrayList<>();
        int nodeNum = 31 * 13; // map range
        int[][] nodeMatrix = new int[nodeNum][4];
        int[][] vertexMatrix = new int[13][31];
        int vertex = 1;
        int bombRange = Game.bomb_range;
        int enemyRealX = this.getBoardSpriteX();
        int enemyRealY = this.getBoardSpriteY();
        int bomberRealX = this.getBoardSpriteX();
        int bomberRealY = this.getBoardSpriteY();

        // generate vertexMatrix due to txt map, wall = 0, destroyable entity = (vertex num * -1), else = (vertex num)
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                if (Level.charMap[i][j] == '#') {
                    vertexMatrix[i][j] = 0;
                } else if (Level.charMap[i][j] == '*' || Level.charMap[i][j] == 'x' || Level.charMap[i][j] == 'b' ||
                Level.charMap[i][j] == 'f' || Level.charMap[i][j] == 's' || Level.charMap[i][j] == 'r') {
                    vertexMatrix[i][j] = vertex * -1;
                    vertex++;
                } else {
                    vertexMatrix[i][j] = vertex;
                    vertex++;
                }
            }
        }

        // update bomb position
        for (int i = 0; i < this.board.getBombs().size(); i++) {
            int bombRealX = this.board.getBombs().get(i).getBoardSpriteX();
            int bombRealY = this.board.getBombs().get(i).getBoardSpriteY();

            // if bomb, remove the vertex that pos
            vertexMatrix[bombRealY][bombRealX] *= -1;
            // remove vertex in bomb range
            // on right
//            for (int j = 1; j <= bombRange; j++) {
//                if (vertexMatrix[bombRealY][bomberRealX + j] > 0 && bomberRealY != enemyRealY
//                        && bomberRealX + j != enemyRealX) {
//                    vertexMatrix[bombRealY][bomberRealX + j] *= -1;
//                } else {
//                    break;
//                }
//            }
//
//            //on left
//            for (int j = 1; j <= bombRange; j++) {
//                if (vertexMatrix[bombRealY][bomberRealX - j] > 0 && bomberRealY != enemyRealY
//                        && bomberRealX - j != enemyRealX) {
//                    vertexMatrix[bombRealY][bomberRealX - j] *= -1;
//                } else {
//                    break;
//                }
//            }
//
//            //above
//            for (int j = 1; j <= bombRange; j++) {
//                if (vertexMatrix[bombRealY - j][bomberRealX] > 0 && bomberRealY - j != enemyRealY
//                        && bomberRealX != enemyRealX) {
//                    vertexMatrix[bombRealY - j][bomberRealX] *= -1;
//                } else {
//                    break;
//                }
//            }
//
//            //under
//            for (int j = 1; j <= bombRange; j++) {
//                if (vertexMatrix[bombRealY + j][bomberRealX] > 0 && bomberRealY + j != enemyRealY
//                        && bomberRealX != enemyRealX) {
//                    vertexMatrix[bombRealY + j][bomberRealX] *= -1;
//                } else {
//                    break;
//                }
//            }
        }

        // update broken brick
        // not implement yet

        // vertexMatrix to nodeMatrix
        for (int i = 1; i < 12; i++) {
            for (int j = 1; j < 30; j++) {
                if (vertexMatrix[i][j] > 0) {
                    if (vertexMatrix[i][j - 1] > 0) {
                        // node on the left
                        nodeMatrix[vertexMatrix[i][j]][0] = vertexMatrix[i][j - 1];
                    } else {
                        // no node on the left
                        nodeMatrix[vertexMatrix[i][j]][0] = 0;
                    }

                    if (vertexMatrix[i][j + 1] > 0) {
                        // node on the right
                        nodeMatrix[vertexMatrix[i][j]][1] = vertexMatrix[i][j + 1];
                    } else {
                        // no node on the right right
                        nodeMatrix[vertexMatrix[i][j]][1] = 0;
                    }

                    if (vertexMatrix[i - 1][j] > 0) {
                        // node above
                        nodeMatrix[vertexMatrix[i][j]][2] = vertexMatrix[i - 1][j];
                    } else {
                        // no node above
                        nodeMatrix[vertexMatrix[i][j]][2] = 0;
                    }

                    if (vertexMatrix[i + 1][j] > 0) {
                        // node under
                        nodeMatrix[vertexMatrix[i][j]][3] = vertexMatrix[i + 1][j];
                    } else {
                        nodeMatrix[vertexMatrix[i][j]][3] = 0;
                    }
                }
            }
        }

        Queue<Integer> node = new LinkedList<>();
        int[] parNode = new int[vertex + 1]; //parent node
        boolean[] visited = new boolean[vertex + 1]; //node visited or not
        int start = vertexMatrix[this.getBoardSpriteY()][this.getBoardSpriteX()];
        int end = vertexMatrix[this.board.getBomber().getBoardSpriteY()][this.board.getBomber().getBoardSpriteX()];
        if (start < 0) {
            start *= -1;
        }

        if (end < 0) {
            end *= -1;
        }

        visited[start] = false;
        parNode[start] = -1;
        parNode[end] = -1;

        node.add(start);
        while (!node.isEmpty()) {
            int currNode = node.poll();
            for (int i = 0; i < 4; i++) {
                if (!visited[nodeMatrix[currNode][i]] && nodeMatrix[currNode][i] != 0) {
                    visited[nodeMatrix[currNode][i]] = true;
                    parNode[nodeMatrix[currNode][i]] = currNode;
                    node.add(nodeMatrix[currNode][i]);
                }
            }
        }

        int path_ = parNode[end];
        int result;
        if (path_ != -1) {
            path.add(end);
            path.add(path_);
            while (path_ != start) {
                path_ = parNode[path_];
                path.add(path_);
            }
            result = path.get(path.size() - 2);
        } else {
            result = -1;
        }

        if (result - start == 1) return 1;
        if (start - result == 1) return 3;
        if (start > result) return 0;
        if (start < result) return 2;
        return new Random().nextInt(4);
    }
}
