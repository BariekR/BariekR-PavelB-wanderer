package gamelogic;

import entities.Entity;
import tiles.Tile;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static gamelogic.GameConfig.*;

public class Board extends JComponent {

    private Tile[][] gamePlan;
    private List<Entity> entities;

    public Board() {
        setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        setVisible(true);
        loadGamePlan();
        entities = new ArrayList<>();
    }

    public void loadGamePlan() {
        this.gamePlan = MapLoader.loadGamePlan();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Printer.printMap(this.gamePlan, graphics);
        Printer.printEntities(this.entities, graphics);
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public Tile[][] getGamePlan() {
        return gamePlan;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void cleanEnemies() {
        for (int i = this.entities.size() - 1; i > 0; i--) {
            this.entities.remove(i);
        }
    }

}