package entities;

public abstract class Enemy extends Entity {

    public Enemy(int x, int y, String filePath, int health, int defense, int attack) {
        super(x, y, filePath, health, defense, attack);
    }


}
