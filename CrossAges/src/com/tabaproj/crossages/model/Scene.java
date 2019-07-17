package com.tabaproj.crossages.model;

import java.io.InputStream;
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

    private Scene(String source) {
        int width = 0;
        int height = 0;
        Tile[][] tiles = null;
        try {
            InputStream input = TileModel.class.getResourceAsStream("../scenes/" + source + ".scn");
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

        } catch (Exception ex) {
            System.err.println("Erro, não foi possivel importar um cenario.\n" + ex.getMessage());
        }
        this.width = width;
        this.height = height;
        this.tiles = tiles;
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

}
