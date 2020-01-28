package cat.esteve.laberint.entities;

import cat.esteve.laberint.gfx.MainCanvas;
import cat.esteve.laberint.level.Level;

import java.awt.*;

public class Pickaxe extends Entity {
    public Pickaxe() {
    }

    public void init(Level level) {
        super.init(level);
        this.w = Level.Tiles.w-40;
        this.h = Level.Tiles.h-40;
        this.color = Color.PINK;
    }

    public void update() {
        super.update();
    }

    public void render(MainCanvas canvas) {
        super.render(canvas);
    }
}
