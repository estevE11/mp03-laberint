package cat.esteve.laberint.entities;

import cat.esteve.laberint.gfx.MainCanvas;
import cat.esteve.laberint.level.Level;

import java.awt.*;
import java.util.function.DoubleToIntFunction;

public class Entity {
    protected double x, y, vx = 0, vy = 0;
    protected int w, h;
    protected Color color;

    protected Level level;

    private Point[] points = new Point[4];

    public Entity(Level level) {
        this.level = level;
        init();
    }

    public void init() {
        for(int i = 0; i < 4; i++) {
            points[i] = new Point(0, 0);
        }
    }

    public void update() {
        this.move();
    }

    public void render(MainCanvas canvas) {
        canvas.fill((int)this.x, (int)this.y, this.w, this.h,this.color);

/*        for(Point p : points) {
            canvas.fill(p.x, p.y, 1, 1, Color.red);
        }*/
    }

    public void move() {
        if(!collision(this.vx, this.vy)) {
            this.move(this.vx, this.vy);
        }
    }
    public boolean collision(double xa, double ya) {
        boolean solid = false;

        points[0] = new Point((int) (this.x + xa), (int) (this.y + ya)); // Top left
        points[1] = new Point((int) (this.x + this.w-1 + xa), (int) (this.y + ya)); // Top right
        points[2] = new Point((int) (this.x + xa), (int) (this.y + this.h-1 + ya)); // Bot left
        points[3] = new Point((int) (this.x + this.w-1 + xa), (int) (this.y + this.h-1 + ya)); // Bot right

        for(Point p : points) {
            if(this.level.getTileAt(p.x, p.y).id != Level.Tiles.FLOOR.id) solid = true;
        }

        return solid;
    }
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void onCollide(Entity other) {

    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        System.out.println("Position set to: (" + x + ", " + y + ")");
    }
}
