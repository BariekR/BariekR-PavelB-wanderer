package entities;

import gamelogic.GameConfig;

import static gamelogic.GameUtil.rollDice;


public class Boss extends Enemy {

    public Boss(int x, int y, int level) {
        super(x, y, GameConfig.BOSS_PATH, initHealth(level), initDefense(level), initAttack(level));
        this.setLevel(level);
    }
    public static int initHealth(int level) {
        return (2 * level * rollDice()) + rollDice();
    }

    public static int initDefense(int level) {
        return (int)((((double)level / 2) * rollDice()) + (double)rollDice() / 2);
    }

    public static int initAttack(int level) {
        return level * rollDice() + level;
    }
}
