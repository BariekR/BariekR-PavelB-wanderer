package gamelogic;

import tiles.FloorTile;
import tiles.Tile;
import tiles.WallTile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static gamelogic.GameConfig.*;
import static gamelogic.GameUtil.getRandomMapPath;

public class MapLoader {

    public static Tile[][] loadGamePlan() {
        List<String> loadedFile;
        Tile[][] output = new Tile[TILE_COUNT][TILE_COUNT];
        try {
            loadedFile = Files.readAllLines(Paths.get(getRandomMapPath()));
            for (int i = 0; i < TILE_COUNT; i++) {
                String[] arr = loadedFile.get(i).split(" ");
                for (int j = 0; j < TILE_COUNT; j++) {

                    if (arr[j].equals("0")) {
                        output[i][j] = new FloorTile(i, j);
                    } else {
                        output[i][j] = new WallTile(i, j);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
