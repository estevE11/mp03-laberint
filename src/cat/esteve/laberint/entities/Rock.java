package cat.esteve.laberint.entities;

import cat.esteve.laberint.gfx.MainCanvas;
import cat.esteve.laberint.level.Level;

import java.awt.*;

public class Rock extends Entity{
    public Rock() {
    }

    public void init(Level level) {
        super.init(level);
        this.w = Level.Tiles.w-10;
        this.h = Level.Tiles.h-10;
        this.color = Color.orange;
    }

    public void update() {
        super.update();
    }

    public void render(MainCanvas canvas) {
        super.render(canvas);
    }
}
