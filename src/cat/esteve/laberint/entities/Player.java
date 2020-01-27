package cat.esteve.laberint.entities;

import cat.esteve.laberint.gfx.MainCanvas;
import cat.esteve.laberint.level.Level;

import java.awt.*;

public class Player extends Entity{

    private double speed = 0.2;

    public Player(Level level) {
        super(level);

        this.w = 32;
        this.h = 32;
        this.color = Color.green;
    }

    public void init() {

    }

    public void update() {
        super.update();
    }

    public void render(MainCanvas canvas) {
        super.render(canvas);
        Level.Tiles tileAt = this.level.getTileAt((int)this.x, (int)this.y);
        if(tileAt != null) canvas.fill((int)this.x + 10, (int)this.y + 10, 10, 10, tileAt.color);
    }

    public void onKeyDown(int key) {
        if(key == 37) this.vx = -this.speed; // left
        if(key == 38) this.vy = -this.speed; // Up
        if(key == 39) this.vx =  this.speed; // Right
        if(key == 40) this.vy =  this.speed; // Down
    }

    public void onKeyUp(int key) {
        if(key == 37) this.vx = 0; // left
        if(key == 38) this.vy = 0; // Up
        if(key == 39) this.vx = 0; // Right
        if(key == 40) this.vy = 0; // Down
    }
}
