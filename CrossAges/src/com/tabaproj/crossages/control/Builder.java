package com.tabaproj.crossages.control;

import com.tabaproj.crossages.model.Scene;
import com.tabaproj.crossages.model.Tile;
import com.tabaproj.crossages.model.TileModel;
import com.tabaproj.crossages.view.ToolsFrame;
import com.tabaproj.crossages.view.Viewer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class Builder {

    public static final int WIDTH = 800;

    public static final int HEIGHT = 600;

    private Scene scene;
    private final ToolsFrame tools;
    private final Viewer viewer;

    private int sceneWidth;

    private int sceneHeight;
    private int locationX = 0;
    private int locationY = 0;
    private int cursorX = -1;
    private int cursorY = -1;

    public Builder(int sceneWidth, int sceneHeight) {
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.scene = new Scene(sceneWidth, sceneHeight);
        this.viewer = new Viewer(WIDTH, HEIGHT);
        this.viewer.setDefaultCloseOperation(Viewer.EXIT_ON_CLOSE);
        this.viewer.setVisible(true);
        this.tools = new ToolsFrame(this);
        this.tools.setDefaultCloseOperation(Viewer.EXIT_ON_CLOSE);
        this.tools.setLocation(viewer.getLocation().x - tools.getWidth() - 30, viewer.getLocation().y);
        this.viewer.addMouseMotionListener(new MouseMotionListener() {
            int x = 0, y = 0;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.isShiftDown()) {
                    setLocationX(getLocationX() + e.getX() - x);
                    setLocationY(getLocationY() + e.getY() - y);
                }
                setCursorX(e.getX());
                setCursorY((e.getY() - 30));
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                setCursorX(e.getX());
                setCursorY((e.getY() - 30));
                x = e.getX();
                y = e.getY();
            }
        });
        update();
        this.tools.setVisible(true);
    }

    public BufferedImage renderFrame(int startX, int startY, int width, int height) {
        startX += locationX;
        startY += locationY;
        BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int tileStartX = startX / TileModel.WIDTH;
        int tileStartY = startY / TileModel.HEIGHT;
        int tileWidth = width / TileModel.WIDTH + 1;
        int tileHeight = height / TileModel.HEIGHT + 1;
        Graphics graphics = frame.getGraphics();
        graphics.setColor(Color.white);
        for (int x = 0; x < scene.getWidth(); x++) {
            graphics.drawLine(startX + x * TileModel.WIDTH, startY, startX + x * TileModel.WIDTH, startY + scene.height * TileModel.HEIGHT);
        }
        for (int y = 0; y < scene.getHeight(); y++) {
            graphics.drawLine(startX, startY + y * TileModel.HEIGHT, startX + scene.width * TileModel.WIDTH, startY + y * TileModel.HEIGHT);
        }
        graphics.setColor(Color.blue);
        graphics.drawRect(startX, startY, scene.width * TileModel.WIDTH, scene.height * TileModel.HEIGHT);
        graphics.setColor(Color.red);
        int cursorX = (this.cursorX - startX) / TileModel.WIDTH, cursorY = (this.cursorY - startY) / TileModel.HEIGHT;
        if (cursorX >= 0 && cursorY >= 0 && cursorX < scene.width && cursorY < scene.height) {
            graphics.drawRect(cursorX * TileModel.WIDTH + startX, cursorY * TileModel.HEIGHT + startY, TileModel.WIDTH, TileModel.HEIGHT);
        }
        graphics.setColor(Color.white);
        for (int x = tileStartX; x < tileWidth && x < scene.getWidth(); x++) {
            for (int y = tileStartY; y < tileHeight && y < scene.getHeight(); y++) {
                int pixX = x * TileModel.WIDTH - startX;
                int pixY = y * TileModel.HEIGHT - startY;
                if (x >= 0 && y >= 0 && x < scene.getWidth() && y < scene.getHeight()) {
                    Tile model = model = scene.getTile(x, y);
                    if (model != null && model.getTileModel() != null) {
                        BufferedImage tile = model.getTileModel().getPicture();
                        graphics.drawImage(tile, pixX, pixY, TileModel.WIDTH, TileModel.HEIGHT, null);
                    }
                }
            }
        }
        graphics.dispose();
        return frame;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

    public void setSceneWidth(int sceneWidth) {
        this.sceneWidth = sceneWidth;
        resizeTiles();
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public void setSceneHeight(int sceneHeight) {
        this.sceneHeight = sceneHeight;
        resizeTiles();
    }

    private void resizeTiles() {
        Scene newScene = new Scene(sceneWidth, sceneHeight);
        for (int x = 0; x < sceneWidth && x < scene.getWidth(); x++) {
            for (int y = 0; y < sceneHeight && y < scene.getHeight(); y++) {
                newScene.setTile(x, y, scene.getTile(x, y));
            }
        }
        this.scene = newScene;
        update();
    }

    public static void main(String... args) {
        new Builder(30, 20);
    }

    public int getCursorX() {
        return cursorX;
    }

    public void setCursorX(int cursorX) {
        this.cursorX = cursorX;
        update();

    }

    public int getCursorY() {
        return cursorY;
    }

    public void setCursorY(int cursorY) {
        this.cursorY = cursorY;
        update();

    }

    public void update() {
        viewer.setFrame(renderFrame(0, 0, WIDTH, HEIGHT));
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
        update();
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
        update();
    }
}
