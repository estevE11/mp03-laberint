package cat.esteve.laberint.level;

import cat.esteve.laberint.entities.Entity;
import cat.esteve.laberint.entities.Player;
import cat.esteve.laberint.gfx.MainCanvas;
import cat.esteve.laberint.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Level {
    public enum Tiles {
        FLOOR(0, Color.gray),
        WALL(1, Color.blue),
        BOMB(2, Color.gray),
        END(3, Color.green);

        public int id;
        public Color color;
        public static int w = 64, h = 64;

        Tiles(int id, Color color) {
            this.id = id;
            this.color = color;
        }
    }

    private LinkedList<Entity> entities = new LinkedList<Entity>();
    private Tiles[][] tiles;

    private Player player;

    public Level() {
        init();
    }

    private int tickCount = 0;

    public void init() {
        LevelLoader.LevelData level_data = LevelLoader.load("res/levels/test_level.lvl");
        this.player = level_data.p;
        this.tiles = level_data.tiles;
        if (this.tiles == null) throw new AssertionError();
        this.entities = level_data.entities;
        for(Entity e : entities) {
            e.init(this);
        }
    }

    public void update() {
        for(Entity e : entities) {
            e.update();
            for(Entity other : entities) {
                if(e != other) {
                    if(checkCollision(e, other)) {
                        e.onCollide(other);
                        other.onCollide(e);
                    }
                }
            }
            if(e.isDead()) this.entities.remove(e);
        }
    }

    public void render(MainCanvas canvas) {
        for(int y = 0; y < this.tiles[0].length; y++) {
            for(int x = 0; x < this.tiles.length; x++) {
                canvas.fill(x*Tiles.w, y*Tiles.h, Tiles.w, Tiles.h, this.tiles[x][y].color);
            }
        }

        for(Entity e : entities) {
            e.render(canvas);
        }

        // Shadow
        if(true) {
            BufferedImage shadow = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
            double shadow_radius = Math.cos(tickCount / 10) * 10 + 100;
            for (int y = 0; y < shadow.getHeight(); y++) {
                for (int x = 0; x < shadow.getWidth(); x++) {
                    double dist = new Point((int) this.player.getX() + (this.player.getW() / 2), (int) this.player.getY() + (this.player.getH() / 2)).distance(x, y);
                    if (dist > shadow_radius) {
                        shadow.setRGB(x, y, new Color(0, 0, 0, 255).getRGB());
                    } else {
                        shadow.setRGB(x, y, new Color(0, 0, 0, (int) (dist * 255 / shadow_radius)).getRGB());
                    }
                }
            }

            canvas.getG().drawImage(shadow, 0, 0, null);
        }
        this.tickCount++;
    }

    public boolean checkCollision(Entity e0, Entity e1){
        return Utils.AABBIntersects((int)e0.getX(), (int)e0.getY(), e0.getW(), e0.getH(), (int)e1.getX(), (int)e1.getY(), e1.getW(), e1.getH());
    }

    public void add() {

    }

    public void onKeyDown(int key) {
        this.player.onKeyDown(key);
    }

    public void onKeyUp(int key) {
        this.player.onKeyUp(key);
    }


    public Tiles getTile(int x, int y) {
        return this.tiles[x][y];
    }

    public Tiles getTileAt(int x, int y) {
        int xx = x/Tiles.w;
        int yy = y/Tiles.h;
        if(xx >= this.tiles.length || xx < 0 || yy >= this.tiles[0].length || yy < 0) {
            return Tiles.WALL;
        }
        return this.tiles[xx][yy];
    }

    public Player getMainPlayer() {
        return this.player;
    }
}
