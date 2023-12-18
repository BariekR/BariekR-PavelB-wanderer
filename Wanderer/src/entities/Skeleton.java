package entities;

import gamelogic.GameConfig;

import static gamelogic.GameUtil.rollDice;

public class Skeleton extends Enemy {

    public Skeleton(int x, int y, int level) {
        super(x, y, GameConfig.SKELETON_PATH, initHealth(level), initDefense(level), initAttack(level));
        this.setLevel(level);
    }

    public static int initHealth(int level) {
        return 2 * level * 2 * rollDice();
    }

    public static int initDefense(int level) {
        return (int)(((double)level / 2) * rollDice());
    }

    public static int initAttack(int level) {
        return level * rollDice();
    }
}
