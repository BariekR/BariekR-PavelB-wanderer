package gamelogic;

public class GameConfig {

    // IMAGES PATHS -----------------------------------------------------------------
    public static final String FLOOR_TILE_PATH = "img/floor.png";
    public static final String WALL_TILE_PATH = "img/wall.png";
    public static final String PLAYER_PATH_DOWN = "img/hero-down.png";
    public static final String PLAYER_PATH_UP = "img/hero-up.png";
    public static final String PLAYER_PATH_LEFT = "img/hero-left.png";
    public static final String PLAYER_PATH_RIGHT = "img/hero-right.png";
    public static final String SKELETON_PATH = "img/skeleton.png";
    public static final String BOSS_PATH = "img/boss.png";

    // SCREEN -------------------------------------------------------------------------
    public static final int SCREEN_SIZE = 720;
    public static final int TILE_COUNT = 10;
    public static final int TILE_SIZE = SCREEN_SIZE / TILE_COUNT;

    // MAP ----------------------------------------------------------------------------
    public static final String MAP_PATH = "maps/map6.txt";
    public static final String[] MAP_PATHS = new String[]{"maps/map1.txt", "maps/map2.txt", "maps/map3.txt", "maps/map4.txt", "maps/map5.txt", "maps/map6.txt", "maps/map7.txt", "maps/map8.txt", "maps/map9.txt", "maps/map10.txt"};
}
