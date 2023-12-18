package entities;

import gamelogic.GameConfig;
import static gamelogic.GameUtil.rollDice;

public class Player extends Entity {

    public Player(int x, int y) {
        super(x, y, GameConfig.PLAYER_PATH_DOWN, initHealth(), initDefense(), initAttack());
    }

    public static int initHealth() {
        return 20 + 3 * rollDice();
    }
    public static int initDefense() {
        return 2 * rollDice();
    }
    public static int initAttack() {
        return 5 + rollDice();
    }
    public void levelUp() {
        this.incrementLevel();
        this.incrementDefense(rollDice());
        this.inrementAttack(rollDice());
        this.incrementMaxHP(rollDice());
    }

}
