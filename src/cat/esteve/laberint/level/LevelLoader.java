package cat.esteve.laberint.level;

import cat.esteve.laberint.entities.Entity;
import cat.esteve.laberint.entities.Pickaxe;
import cat.esteve.laberint.entities.Player;
import cat.esteve.laberint.entities.Rock;
import cat.esteve.laberint.utils.Utils;

import java.util.LinkedList;

public class LevelLoader {
    public static class LevelData {
        public String name;
        public Level.Tiles[][] tiles;
        public int w, h;
        public Player p;
        public LinkedList<Entity> entities;

        public LevelData() {
            this.entities = new LinkedList<Entity>();
        }
    }

    public static LevelData load(String path) {
        String[] lines = Utils.read_file_by_line(path);
        if (lines.length < 1) {
            System.out.println("Error! Level file is empty");
            return null;
        }
        String[] size = lines[0].split(" ");
        int w = Integer.parseInt(size[0]);
        int h = Integer.parseInt(size[1]);

        LevelData data = new LevelData();

        data.tiles = new Level.Tiles[w][h];
        data.w = w;
        data.h = h;
        data.name = Utils.get_file_name_from_path(path);

        for(int y = 0; y < h; y++) {
            String line = lines[y+1];
            for(int x = 0; x < w; x++) {
                char t = line.charAt(x);
                switch(t) {
                    case '0':
                        data.tiles[x][y] = Level.Tiles.FLOOR;
                        break;
                    case '1':
                        data.tiles[x][y] = Level.Tiles.WALL;
                        break;
                    case '@':
                        data.tiles[x][y] = Level.Tiles.BOMB;
                        break;
                    case 's':
                        data.tiles[x][y] = Level.Tiles.END;
                        break;
                    case 'p':
                        data.tiles[x][y] = Level.Tiles.FLOOR;
                        Player p = new Player();
                        p.setPosition(x*Level.Tiles.w, y*Level.Tiles.h);
                        data.entities.add(p);
                        data.p = p;
                        data.tiles[x][y] = Level.Tiles.FLOOR;
                        break;
                    case '2':
                        Rock r = new Rock();
                        r.setPosition(x*Level.Tiles.w+5, y*Level.Tiles.h+5);
                        data.entities.add(r);
                        data.tiles[x][y] = Level.Tiles.FLOOR;
                        break;
                    case 't':
                        Pickaxe pick = new Pickaxe();
                        pick.setPosition(x*Level.Tiles.w+20, y*Level.Tiles.h+20);
                        data.entities.add(pick);
                        data.tiles[x][y] = Level.Tiles.FLOOR;
                        break;
                }
            }
        }
        return data;
    }
}
