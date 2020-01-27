package cat.esteve.laberint.level;

import cat.esteve.laberint.utils.Utils;

public class LevelLoader {
    public static class LevelData {
        public String name;
        public Level.Tiles[][] tiles;
        public int w, h;
        public int playerX, playerY;
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
                    case '@':
                    case 's':
                        data.tiles[x][y] = Level.Tiles.FLOOR;
                        break;
                    case '#':
                        data.tiles[x][y] = Level.Tiles.WALL;
                        break;
                    case 'p':
                        data.tiles[x][y] = Level.Tiles.FLOOR;
                        data.playerX = x * Level.Tiles.w;
                        data.playerY = y * Level.Tiles.h;
                        break;
                }
            }
        }
        return data;
    }
}
