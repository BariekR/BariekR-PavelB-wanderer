package entities;

import gamelogic.GameObject;

import static gamelogic.GameUtil.rollDice;

public abstract class Entity extends GameObject {
    private int level = 1;
    private boolean hasKey;

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    private int maxHealth;
    private int health;
    private int defense;
    private int attack;
    public Entity(int x, int y, String filePath, int health, int defense, int attack) {
        super(filePath, x, y);
        this.maxHealth = health;
        this.health = health;
        this.defense = defense;
        this.attack = attack;
    }

    public int getLevel() {
        return this.level;
    }

    public void decreaseHP(int damage) {
        this.health -= damage;
    }

    public void moveUp() {
        this.setY(this.getY() - 1);
    }
    public void moveDown() {
        this.setY(this.getY() + 1);
    }
    public void moveLeft() {
        this.setX(this.getX() - 1);
    }
    public void moveRight() {
        this.setX(this.getX() + 1);
    }
    public void moveDir(char dir) {
        switch (dir) {
            case 'U' : this.moveUp(); break;
            case 'D' : this.moveDown(); break;
            case 'L' : this.moveLeft(); break;
            case 'R' : this.moveRight(); break;
        }
    }
    public void attack(Entity target) {
        System.out.println(target.health);
        int damage = 2 * rollDice() + this.attack;
        if (damage > target.defense) {
            target.decreaseHP(damage - target.defense);
            System.out.println("attack!!!");
            System.out.println(this.health + " : " + target.health);
        }
    }
    public boolean isAlive() {
        return this.health > 0;
    }
    public void incrementLevel() {
        this.level++;
    }
    public void incrementMaxHP(int amount) {
        this.maxHealth += amount;
    }
    public void incrementDefense(int amount) {
        this.defense += amount;
    }
    public void inrementAttack(int amount) {
        this.attack += amount;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public void setKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
