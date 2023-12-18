package gamelogic;

import entities.Entity;
import tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Printer {

    public static void printMap(Tile[][] gamePlan, Graphics graphics) {

        for (int i = 0; i < gamePlan.length; i++) {
            for (int j = 0; j < gamePlan.length; j++) {

                gamePlan[i][j].draw(graphics);
            }
        }
    }

    public static void printEntities(List<Entity> entities, Graphics graphics) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(entities.size() - 1 - i).draw(graphics);
        }
    }
}
