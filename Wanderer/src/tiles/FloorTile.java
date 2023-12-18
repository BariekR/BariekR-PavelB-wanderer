package tiles;

import gamelogic.GameConfig;

public class FloorTile extends Tile {

    public FloorTile(int x, int y) {
        super(GameConfig.FLOOR_TILE_PATH, x, y);
    }
}
