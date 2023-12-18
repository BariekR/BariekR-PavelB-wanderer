package tiles;

import gamelogic.GameConfig;

public class WallTile extends Tile {

    public WallTile(int x, int y) {
        super(GameConfig.WALL_TILE_PATH, x, y);
    }
}
