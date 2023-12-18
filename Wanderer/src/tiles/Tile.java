package tiles;

import gamelogic.*;

public abstract class Tile extends GameObject {

    public Tile(String filePath, int x, int y) {
        super(filePath, x, y);
    }
}
