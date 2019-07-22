package com.tabaproj.crossages.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scene {

    public final int width;

    public final int height;

    private final Tile[][] tiles;

    public Scene(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    public Scene(InputStream input) throws Exception {
        int width = 0;
        int height = 0;
        Tile[][] tiles = null;
        Scanner scanner = new Scanner(input);
        int tilesMapCount = scanner.nextInt();
        TileModel[] tilesMap = new TileModel[tilesMapCount];
        for (int i = 0; i < tilesMapCount; i++) {
            tilesMap[i] = TileModel.getTile(scanner.next());
        }
        width = scanner.nextInt();
        height = scanner.nextInt();
        tiles = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int mapIndex = scanner.nextInt();
                tiles[x][y] = new Tile(tilesMap[mapIndex]);
            }
        }
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    public void export(PrintStream out) {
        List<TileModel> map = new ArrayList<>();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                if (!map.contains(null) && tiles[x][y] == null || tiles[x][y].getTileModel() == null) {
                    map.add(null);
                }
                if (!map.contains(tiles[x][y].getTileModel())) {
                    map.add(tiles[x][y].getTileModel());
                }
            }
        }
        out.println(map.size());
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i) == null) {
                out.println("NULL");
            } else {
                out.println(map.get(i).getName());
            }
        }
        out.printf("%d %d\n", width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                out.printf("%d ", tiles[x][y] == null ? map.indexOf(tiles[x][y]) : map.indexOf(tiles[x][y].getTileModel()));
            }
            out.println();
        }
    }

    public void export(OutputStream out) {
        export(new PrintStream(out));
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setTile(int x, int y, TileModel tile) {
        setTile(x, y, new Tile(tile));
    }

    public void draw(Graphics graphics, int locX, int locY) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                int pixX = x * TileModel.WIDTH + locX;
                int pixY = y * TileModel.HEIGHT + locY;
                if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
                    Tile model = getTile(x, y);
                    if (model != null && model.getTileModel() != null) {
                        graphics.drawImage(model.getTileModel().getPicture(), pixX, pixY, TileModel.WIDTH, TileModel.HEIGHT, null);
                    }
                }
            }
        }
    }

}
