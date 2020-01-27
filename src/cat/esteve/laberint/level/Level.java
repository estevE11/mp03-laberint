package cat.esteve.laberint.level;

import cat.esteve.laberint.entities.Player;
import cat.esteve.laberint.gfx.MainCanvas;

import java.awt.*;

public class Level {
    public enum Tiles {
        FLOOR(0, Color.gray),
        WALL(1, Color.blue),
        BOMB(2, Color.red);

        public int id;
        public Color color;
        public static int w = 64, h = 64;

        Tiles(int id, Color color) {
            this.id = id;
            this.color = color;
        }
    }

    private Tiles[][] tiles;

    private Player player;

    public Level() {
        init();
    }

    public void init() {
        LevelLoader.LevelData level_data = LevelLoader.load("res/levels/test_level.lvl");
        this.tiles = level_data.tiles;
        if (this.tiles == null) throw new AssertionError();

        this.player = new Player(this);
        this.player.setPosition(level_data.playerX, level_data.playerY);
    }

    public void update() {
        this.player.update();
    }

    public void render(MainCanvas canvas) {
        for(int y = 0; y < this.tiles[0].length; y++) {
            for(int x = 0; x < this.tiles.length; x++) {
                canvas.fill(x*Tiles.w, y*Tiles.h, Tiles.w, Tiles.h, this.tiles[x][y].color);
            }
        }

        this.player.render(canvas);
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
}
