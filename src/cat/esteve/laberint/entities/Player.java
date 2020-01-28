package cat.esteve.laberint.entities;

import cat.esteve.laberint.gfx.MainCanvas;
import cat.esteve.laberint.level.Level;

import java.awt.*;

public class Player extends Entity{

    private double speed = 3;

    private boolean hasPickaxe = false;

    public Player() {
    }

    public void init(Level level) {
        super.init(level);
        this.w = 32;
        this.h = 32;
        this.color = Color.green;
    }

    public void update() {
        super.update();
    }

    public void render(MainCanvas canvas) {
        super.render(canvas);
        if(this.hasPickaxe) canvas.fill((int)this.x+5, (int)this.y+5, 10, 10, Color.pink);
    }

    public void onCollide(Entity other) {
        if(other instanceof Rock) {
            if(this.hasPickaxe) {
                if(this.vx > 0) other.setPosition(this.x + this.w, other.getY());
                else if(this.vx < 0) other.setPosition(this.x - other.getW(), other.getY());
                if(this.vy > 0) other.setPosition(other.getX(), this.y + this.h);
                else if(this.vy < 0) other.setPosition(other.getX(), this.y - other.getH());
            } else {
                if(this.vx > 0) this.setPosition(other.getX()-this.w, this.y);
                else if(this.vx < 0) this.setPosition(other.getX() - other.getW(), this.y);
                if(this.vy > 0) this.setPosition(this.x, other.getY()-this.h);
                else if(this.vy < 0) this.setPosition(this.x, other.getY() - other.getH());
            }
        } else if(other instanceof Pickaxe) {
            this.hasPickaxe = true;
            other.die();
        }
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
