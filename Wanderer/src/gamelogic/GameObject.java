package gamelogic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static gamelogic.GameConfig.TILE_SIZE;

public class GameObject implements Drawable {
    private BufferedImage image;
    private int x;
    private int y;
    public GameObject(String filePath, int x, int y){
        try {
            this.image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(this.image, this.x * TILE_SIZE, this.y * TILE_SIZE, null);
    }

    public void setImagePath(String filePath) {
        try {
            this.image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
