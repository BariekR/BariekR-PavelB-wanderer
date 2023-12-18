package gamelogic;

import entities.*;
import tiles.WallTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static gamelogic.GameConfig.*;
import static gamelogic.GameUtil.*;

public class Game implements KeyListener {

    private Board board;
    private JPanel infoPanel;
    private JLabel info;
    private int playerMoves;
    private boolean isCombatPhase;
    private int area;

    public Game () {
        this.board = new Board();
        this.area = 1;
        spawnPlayer();
        spawnEnemies();
        isCombatPhase = false;
        this.playerMoves = 0;

        this.infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE / 25));
        infoPanel.setVisible(true);
        infoPanel.setLayout(new FlowLayout());

        info = new JLabel(String.format("Area: %d | Hero (Level: %d) HP: %d/%d DP: %d | SP: %d | has key: %b",this.area, getPlayer().getLevel(), getPlayer().getHealth(), getPlayer().getMaxHealth(), getPlayer().getDefense(), getPlayer().getAttack(), getPlayer().hasKey()));
        infoPanel.add(info);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // When the up or down keys hit, we change the position of our box
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP :
            case KeyEvent.VK_W :
                movePlayer('U'); break;

            case KeyEvent.VK_DOWN :
            case KeyEvent.VK_S:
                movePlayer('D'); break;

            case KeyEvent.VK_LEFT :
            case KeyEvent.VK_A:
                movePlayer('L'); break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                movePlayer('R'); break;

            case KeyEvent.VK_SPACE:
                battle(); break;
        }
        isCombatPhase = checkBattle();
        moveEntities();
        isCombatPhase = checkBattle();
        this.updatePanel(this.info);
        this.board.repaint();
    }
    public boolean checkBattle() {
        return getCollidingEntities() != null && !getCollidingEntities().isEmpty();
    }

    public void battle() {
        if (checkBattle()) {
            isCombatPhase = true;
            Entity entity1 = getCollidingEntities().get(0);
            Entity entity2 = getCollidingEntities().get(1);

            entity1.attack(entity2);
            if (entity2.isAlive()) {
                entity2.attack(entity1);
            }
            if (!entity1.isAlive() || !entity2.isAlive()) {
                isCombatPhase = false;
                if (!entity1.isAlive()) {
                    this.board.getEntities().remove(entity1);
                    System.exit(0);
                }
                if (!entity2.isAlive()) {
                    this.board.getEntities().remove(entity2);
                    this.getPlayer().levelUp();
                    if (entity2.hasKey()) {
                        entity1.setKey(true);
                    }
                }
                if (getPlayer().hasKey() && getBoss() == null) {
                    nextLevel();
                }
            }
            if (getPlayer().hasKey() && getBoss().hasKey()) {
                nextLevel();
            }
        }
    }

    public void updatePanel(JLabel label) {
        if (isCombatPhase) {
            Entity target = getCollidingEntities().get(1);
            label.setText(String.format("Area: %d | Hero (Level: %d) HP: %d/%d DP: %d | SP: %d | has key: %b    ||    %s (Level: %d) HP: %d/%d DP: %d | SP: %d",this.area, getPlayer().getLevel(), getPlayer().getHealth(), getPlayer().getMaxHealth(), getPlayer().getDefense(), getPlayer().getAttack(), getPlayer().hasKey(),
                    target.getClass().getSimpleName(), target.getLevel(), target.getHealth(), target.getMaxHealth(), target.getDefense(), target.getAttack()));
        } else {
            label.setText(String.format("Area: %d | Hero (Level: %d) HP: %d/%d DP: %d | SP: %d | has key: %b",this.area, getPlayer().getLevel(), getPlayer().getHealth(), getPlayer().getMaxHealth(), getPlayer().getDefense(), getPlayer().getAttack(), getPlayer().hasKey()));
        }
    }

    public void movePlayer(char dir) {
        if (!isCombatPhase) {
            switch (dir) {
                case 'U':
                    getPlayer().setImagePath(PLAYER_PATH_UP);
                    if (validateDir(getPlayer(), dir)) {
                        getPlayer().moveUp();
                        this.playerMoves++;
                    }
                    break;

                case 'D':
                    getPlayer().setImagePath(PLAYER_PATH_DOWN);
                    if (validateDir(getPlayer(), dir)) {
                        getPlayer().moveDown();
                        this.playerMoves++;
                    }
                    break;

                case 'L':
                    getPlayer().setImagePath(PLAYER_PATH_LEFT);
                    if (validateDir(getPlayer(), dir)) {
                        getPlayer().moveLeft();
                        this.playerMoves++;
                    }
                    break;

                case 'R':
                    getPlayer().setImagePath(PLAYER_PATH_RIGHT);
                    if (validateDir(getPlayer(), dir)) {
                        getPlayer().moveRight();
                        this.playerMoves++;
                    }
                    break;
            }
        }
    }

    public void moveEntities() {
        if (isCombatPhase) {
            return;
        }
        char dir;
        if (this.playerMoves >= 2) {
            for (Entity e : this.board.getEntities()) {
                int counter = 0;
                if (e instanceof Enemy) {
                    do {
                        dir = getRandomDir();
                        counter++;
                        if (counter > 100) {
                            dir = ' ';
                            break;
                        }
                    } while (!validateDir(e, dir));
                    e.moveDir(dir);
                }
            }
            this.playerMoves = 0;
        }
    }

    public Player getPlayer() {
        return (Player)this.board.getEntities().get(0);
    }

    public Board getBoard() {
        return board;
    }

    public int[] getUnoccupiedCoords() {
        int x;
        int y;
        boolean isValid;
        do {
            isValid = true;
            x = rollCoord();
            y = rollCoord();
            for (Entity e : this.board.getEntities()) {
                if (e.getX() == x && e.getY() == y) {
                    isValid = false;
                    break;
                }
            }
            for (int i = 0; i < TILE_COUNT; i++) {
                for (int j = 0; j < TILE_COUNT; j++) {

                    if (this.board.getGamePlan()[x][y] instanceof WallTile) {
                        isValid = false;
                        break;
                    }
                }
            }
        } while (!isValid);
        return new int[]{x, y};
    }

    public void spawnEnemies() {
        int[] coords = getUnoccupiedCoords();
        int level = area > 1 ? levelUpRandomly(area) : area;
        this.board.addEntity(new Boss(coords[0], coords[1], level));
        for (int i = 0; i < rollEnemiesSpawn(); i++) {
            level = area > 1 ? levelUpRandomly(area) : area;
            coords = getUnoccupiedCoords();
            this.board.addEntity(new Skeleton(coords[0], coords[1], level));
        }
        for (int i = 1; i < this.board.getEntities().size(); i++) {
            if (this.board.getEntities().get(i) instanceof Skeleton) {
                this.board.getEntities().get(i).setKey(true);
                break;
            }
        }
    }

    public void spawnPlayer() {
        int[] coords = getUnoccupiedCoords();
        this.board.addEntity(new Player(coords[0], coords[1]));
    }

    public boolean validateDir(Entity e, char dir) {

        if (e instanceof Player) {
            switch (dir) {
                case 'U' :
                    if (e.getY() == 0 || this.getBoard().getGamePlan()[e.getX()][e.getY() - 1] instanceof WallTile) {
                        return false;
                    }
                    return true;

                case 'D' :
                    if ( e.getY() == TILE_COUNT - 1 || this.getBoard().getGamePlan()[e.getX()][e.getY() + 1] instanceof WallTile) {
                        return false;
                    }
                    return true;

                case 'L' :
                    if (e.getX() == 0 || this.getBoard().getGamePlan()[e.getX() - 1][e.getY()] instanceof WallTile) {
                        return false;
                    }
                    return true;

                case 'R' :
                    if (e.getX() == TILE_COUNT - 1 || this.getBoard().getGamePlan()[e.getX() + 1][e.getY()] instanceof WallTile) {
                        return false;
                    }
                    return true;
            }
        }
        if (e instanceof Enemy) {
            switch (dir) {
                case 'U' :
                    if (e.getY() == 0 || this.getBoard().getGamePlan()[e.getX()][e.getY() - 1] instanceof WallTile) {
                        return false;
                    }
                    for (Entity examinedEntity : this.board.getEntities()) {
                        if (examinedEntity instanceof Enemy && e.getX() == examinedEntity.getX() && e.getY() - 1 == examinedEntity.getY()) {
                            return false;
                        }
                    }
                    return true;

                case 'D' :
                    if ( e.getY() == TILE_COUNT - 1 || this.getBoard().getGamePlan()[e.getX()][e.getY() + 1] instanceof WallTile) {
                        return false;
                    }
                    for (Entity examinedEntity : this.board.getEntities()) {
                        if (examinedEntity instanceof Enemy && e.getX() == examinedEntity.getX() && e.getY() + 1 == examinedEntity.getY()) {
                            return false;
                        }
                    }
                    return true;

                case 'L' :
                    if (e.getX() == 0 || this.getBoard().getGamePlan()[e.getX() - 1][e.getY()] instanceof WallTile) {
                        return false;
                    }
                    for (Entity examinedEntity : this.board.getEntities()) {
                        if (examinedEntity instanceof Enemy && e.getX() - 1 == examinedEntity.getX() && e.getY() == examinedEntity.getY()) {
                            return false;
                        }
                    }
                    return true;

                case 'R' :
                    if (e.getX() == TILE_COUNT - 1 || this.getBoard().getGamePlan()[e.getX() + 1][e.getY()] instanceof WallTile) {
                        return false;
                    }
                    for (Entity examinedEntity : this.board.getEntities()) {
                        if (examinedEntity instanceof Enemy && e.getX() + 1 == examinedEntity.getX() && e.getY() == examinedEntity.getY()) {
                            return false;
                        }
                    }
                    return true;
            }
        }
        return true;
    }

    public List<Entity> getCollidingEntities() {
        List<Entity> outputList = new ArrayList<>();
        for (int i = 0; i < this.getBoard().getEntities().size(); i++) {
            for (int j = 0; j < this.getBoard().getEntities().size(); j++) {

                if (this.getBoard().getEntities().get(i).getX() == this.getBoard().getEntities().get(j).getX() &&
                this.getBoard().getEntities().get(i).getY() == this.getBoard().getEntities().get(j).getY() && i != j) {
                    outputList.add(this.getBoard().getEntities().get(i));
                    outputList.add(this.getBoard().getEntities().get(j));
                }
            }
        }
        return outputList;
    }

    public void nextLevel() {
        this.area++;
        this.board.cleanEnemies();
        this.board.loadGamePlan();
        this.placePlayer();
        this.healPlayer();
        this.playerMoves = 0;
        this.spawnEnemies();
        getPlayer().setKey(false);
    }

    public void healPlayer() {
        int healthAmount = healPlayerRandomly(getPlayer().getMaxHealth());
        if (healthAmount > 0) {
            getPlayer().setHealth(getPlayer().getHealth() + healthAmount);
        }
        if (getPlayer().getHealth() >= getPlayer().getMaxHealth()) {
            getPlayer().setHealth(getPlayer().getMaxHealth());
        }
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }

    public Entity getBoss() {
        for (Entity e : this.board.getEntities()) {
            if (e instanceof Boss) {
                return e;
            }
        }
        return null;
    }

    public void placePlayer() {
        int[] coords = getUnoccupiedCoords();
        getPlayer().setX(coords[0]);
        getPlayer().setY(coords[1]);
    }
}
